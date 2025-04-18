package com.reaper.waterpipe;

import java.util.List;

import com.reaper.waterpipe.tobacco.Tobacco;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemTobacco extends Item {
    private final Tobacco tobaccoType;
    private int maxUses;
    private final int smokeColor;

    public ItemTobacco(String name, Tobacco tobacco, int maxUses, int smokeColor) {
        this.tobaccoType = tobacco;
        this.maxUses = maxUses;
        this.setRegistryName(name);
        this.setTranslationKey(name);
        this.setMaxStackSize(1);
        this.smokeColor = smokeColor;
    }

    // Новый метод для создания стека с инициализированным NBT
    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = new ItemStack(this);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("RemainingUses", this.maxUses);
        stack.setTagCompound(tag);
        return stack;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        if (stack.hasTagCompound()) {
            int remaining = stack.getTagCompound().getInteger("RemainingUses");
            tooltip.add("Осталось использований: " + remaining + "/" + this.maxUses);
        }
    }

    public int getRemainingUses(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            // Если тега нет - инициализируем
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("RemainingUses", this.maxUses);
            stack.setTagCompound(tag);
            return this.maxUses;
        }
        return stack.getTagCompound().getInteger("RemainingUses");
    }

    public void consumeUse(ItemStack stack) {
        int remaining = this.getRemainingUses(stack) - 1;
        NBTTagCompound tag = stack.getTagCompound();

        if (remaining <= 0) {
            stack.shrink(1);
        } else {
            tag.setInteger("RemainingUses", remaining);
            stack.setTagCompound(tag);
        }
    }
    public int getSmokeColor() {
        return this.smokeColor;
    }
    public Tobacco getTobaccoType() {
        return this.tobaccoType;
    }
}