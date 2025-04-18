package com.reaper.waterpipe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;

public class TileEntityWaterPipe extends TileEntity implements IItemHandlerModifiable {
    // ��������� ������
    private int cooldownTimer = 0;
    private static final int COOLDOWN_DURATION = 0; // 5 ������ (20 �����/���)
    public static final int SLOT_BOTTLE = 0;    // ���� ��� ������� �������
    public static final int SLOT_TOBACCO = 1;   // ���� ��� ������
    public static final int SLOT_COAL = 2;      // ���� ��� ����
    private static final int MAX_USES = 123;      // ����. ������������� �������

    private NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);
    private String customName;

    // ===== �������� ������ =====
    public void update() {
        if (!this.world.isRemote && this.cooldownTimer > 0) {
            this.cooldownTimer--;
            this.markDirty();
        }
    }

    public void trySmoke(EntityPlayer player) {
        if (this.canSmoke() && !this.isOnCooldown()) {
            // 1. ��������� ������ �����
            this.applyEffects(player);

            // 2. ��������� �������
            this.cooldownTimer = COOLDOWN_DURATION;
            this.markDirty();
            // ����� ������
            /* if (!this.world.isRemote) {
                int color = 0xAAAAAA; // ����� �� ���������
                if (!this.getStackInSlot(SLOT_TOBACCO).isEmpty()) {
                    ItemTobacco tobacco = (ItemTobacco)this.getStackInSlot(SLOT_TOBACCO).getItem();
                    color = tobacco.getSmokeColor();
                }
                WaterPipe.spawnCustomSmoke(this.world,
                        this.pos.getX() + 0.5,
                        this.pos.getY() + 0.8,
                        this.pos.getZ() + 0.5,
                        color,
                        1.0f);*/
            WaterPipe.spawnCustomSmoke(this.world,
                    this.pos.getX() + 0.5,
                    this.pos.getY() + 1.0,
                    this.pos.getZ() + 0.5,
                    0xFF0000,  // ������� ����
                    2.0f);     // ����������� ������


        }
    }

    private void applyEffects(EntityPlayer player) {
        // ��������� ������ ������
        ItemStack tobaccoStack = this.getStackInSlot(SLOT_TOBACCO);
        if (tobaccoStack.getItem() instanceof ItemTobacco) {
            ItemTobacco tobacco = (ItemTobacco) tobaccoStack.getItem();
            tobacco.getTobaccoType().addEffect(player);
            tobacco.consumeUse(tobaccoStack);

            if (tobaccoStack.getCount() <= 0) {
                this.setStackInSlot(SLOT_TOBACCO, ItemStack.EMPTY);
            }
        }

        // ��������� �����
        this.getStackInSlot(SLOT_COAL).shrink(1);

        // ������������ �������
        ItemStack waterBottle = this.getStackInSlot(SLOT_BOTTLE);
        int usesRemaining = this.getUsesRemaining(waterBottle) - 1;

        if (usesRemaining <= 0) {
            this.setStackInSlot(SLOT_BOTTLE, new ItemStack(Items.GLASS_BOTTLE));
        } else {
            NBTTagCompound tag = waterBottle.getTagCompound();
            if (tag == null) {
                tag = new NBTTagCompound();
            }
            tag.setInteger("WaterUses", usesRemaining);
            waterBottle.setTagCompound(tag);
        }
    }

    // ===== �������� ��������� =====
    public boolean canSmoke() {
        return this.hasWaterBottle() && this.hasTobacco() && this.hasCoal();
    }

    private boolean hasWaterBottle() {
        return this.isWaterBottle(this.getStackInSlot(SLOT_BOTTLE));
    }

    private boolean hasTobacco() {
        return !this.getStackInSlot(SLOT_TOBACCO).isEmpty();
    }

    private boolean hasCoal() {
        return !this.getStackInSlot(SLOT_COAL).isEmpty();
    }

    public boolean isOnCooldown() {
        return this.cooldownTimer > 0;
    }

    public int getCooldown() {
        return this.cooldownTimer;
    }

    public float getCooldownPercent() {
        return (float)this.cooldownTimer / COOLDOWN_DURATION;
    }

    // ===== ������ � ���������� =====
    private boolean isWaterBottle(ItemStack stack) {
        if (stack.getItem() != Items.POTIONITEM) return false;
        return PotionUtils.getPotionFromItem(stack) == PotionTypes.WATER ||
                (stack.hasTagCompound() && stack.getTagCompound().hasKey("WaterUses"));
    }

    private int getUsesRemaining(ItemStack bottle) {
        if (!this.isWaterBottle(bottle)) return 0;
        NBTTagCompound tag = bottle.getTagCompound();
        return tag != null && tag.hasKey("WaterUses") ? tag.getInteger("WaterUses") : MAX_USES;
    }

    // ===== IItemHandlerModifiable ������ =====
    @Override
    public int getSlots() {
        return 3;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        this.inventory.set(slot, stack);
        this.markDirty();
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (!this.isItemValid(slot, stack)) return stack;

        ItemStack current = this.getStackInSlot(slot);
        if (current.isEmpty()) {
            if (!simulate) {
                this.setStackInSlot(slot, stack);
            }
            return ItemStack.EMPTY;
        }
        return stack;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount <= 0) return ItemStack.EMPTY;

        ItemStack stack = this.getStackInSlot(slot);
        if (stack.isEmpty()) return ItemStack.EMPTY;

        int toExtract = Math.min(amount, stack.getCount());
        ItemStack result = stack.copy();
        result.setCount(toExtract);

        if (!simulate) {
            stack.shrink(toExtract);
            if (stack.isEmpty()) {
                this.setStackInSlot(slot, ItemStack.EMPTY);
            }
            this.markDirty();
        }
        return result;
    }

    @Override
    public int getSlotLimit(int slot) {
        return slot == SLOT_BOTTLE ? 1 : 64;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        switch(slot) {
            case SLOT_BOTTLE: return this.isWaterBottle(stack);
            case SLOT_TOBACCO: return stack.getItem() instanceof ItemTobacco;
            case SLOT_COAL: return stack.getItem() == Items.COAL;
            default: return false;
        }
    }

    // ===== NBT ������ =====
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.inventory = NonNullList.withSize(3, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.inventory);
        this.cooldownTimer = compound.getInteger("Cooldown");
        if (compound.hasKey("CustomName", 8)) {
            this.customName = compound.getString("CustomName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, this.inventory);
        compound.setInteger("Cooldown", this.cooldownTimer);
        if (this.hasCustomName()) {
            compound.setString("CustomName", this.customName);
        }
        return compound;
    }

    // ===== ������ ������ =====
    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(this.pos) == this &&
                player.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64;
    }

    public String getName() {
        return this.hasCustomName() ? this.customName : "container.water_pipe";
    }

    public boolean hasCustomName() {
        return this.customName != null && !this.customName.isEmpty();
    }

    public void setCustomName(String name) {
        this.customName = name;
    }

    @Override
    public ITextComponent getDisplayName() {
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
    }

    // �������������� ������ IInventory
    public void openInventory(EntityPlayer player) {}
    public void closeInventory(EntityPlayer player) {}
    public int getField(int id) { return 0; }
    public void setField(int id, int value) {}
    public int getFieldCount() { return 0; }
    public void clear() { this.inventory.clear(); }
}