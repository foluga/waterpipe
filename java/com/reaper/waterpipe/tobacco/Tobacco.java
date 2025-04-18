package com.reaper.waterpipe.tobacco;

import java.util.Random;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.SoundCategory;
import net.minecraft.init.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.util.EnumParticleTypes;

public class Tobacco {
    protected ResourceLocation tobaccoTexture = new ResourceLocation("waterpipe:textures/models/TobaccoDefault.png");

    public ResourceLocation getTexture() {
        return this.tobaccoTexture;
    }

    public void addEffect(EntityPlayer player) {
        // Добавь эффекты для игрока
    }

    public void onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

    }


}