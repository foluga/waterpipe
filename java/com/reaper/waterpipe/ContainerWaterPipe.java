package com.reaper.waterpipe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerWaterPipe extends Container {
    private final TileEntityWaterPipe tileEntity;

    public ContainerWaterPipe(InventoryPlayer playerInventory, TileEntityWaterPipe tileEntity) {
        this.tileEntity = tileEntity;

        // Слот для водяной бутылки (верхний)
        this.addSlotToContainer(new SlotItemHandler(tileEntity, TileEntityWaterPipe.SLOT_BOTTLE, 70, 53) {
            @Override
            public boolean isItemValid(@Nonnull ItemStack stack) {
                // Принимаем либо обычную водяную бутылку, либо нашу многоразовую
                if (stack.getItem() != Items.POTIONITEM) return false;

                return PotionUtils.getPotionFromItem(stack) == PotionTypes.WATER ||
                        (stack.hasTagCompound() && stack.getTagCompound().hasKey("WaterUses"));
            }

            @Override
            public int getSlotStackLimit() {
                return 1;
            }
        });

        // Слот для табака (средний)
        this.addSlotToContainer(new SlotItemHandler(tileEntity, TileEntityWaterPipe.SLOT_TOBACCO, 66, 31) {
            @Override
            public boolean isItemValid(@Nonnull ItemStack stack) {
                return stack.getItem() instanceof ItemTobacco;
            }
        });

        // Слот для угля (нижний)
        this.addSlotToContainer(new SlotItemHandler(tileEntity, TileEntityWaterPipe.SLOT_COAL, 70, 9) {
            @Override
            public boolean isItemValid(@Nonnull ItemStack stack) {
                return stack.getItem() == Items.COAL;
            }
        });

        // Инвентарь игрока
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlotToContainer(new Slot(
                        playerInventory,
                        col + row * 9 + 9,
                        8 + col * 18,
                        84 + row * 18
                        ));
            }
        }

        // Хотбар
        for (int col = 0; col < 9; ++col) {
            this.addSlotToContainer(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tileEntity.isUsableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 3) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true))
                    return ItemStack.EMPTY;
            } else {
                if (PotionUtils.getPotionFromItem(itemstack1) == PotionTypes.WATER) {
                    if (!this.mergeItemStack(itemstack1, TileEntityWaterPipe.SLOT_BOTTLE, TileEntityWaterPipe.SLOT_BOTTLE + 1, false))
                        return ItemStack.EMPTY;
                } else if (itemstack1.getItem() instanceof ItemTobacco) {
                    if (!this.mergeItemStack(itemstack1, TileEntityWaterPipe.SLOT_TOBACCO, TileEntityWaterPipe.SLOT_TOBACCO + 1, false))
                        return ItemStack.EMPTY;
                } else if (itemstack1.getItem() == Items.COAL) {
                    if (!this.mergeItemStack(itemstack1, TileEntityWaterPipe.SLOT_COAL, TileEntityWaterPipe.SLOT_COAL + 1, false))
                        return ItemStack.EMPTY;
                } else
                    return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
}