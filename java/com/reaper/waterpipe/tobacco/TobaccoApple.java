package com.reaper.waterpipe.tobacco;

import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.init.SoundEvents;
import net.minecraft.init.MobEffects;
import net.minecraft.util.EnumParticleTypes;

public class TobaccoApple extends Tobacco {
    public TobaccoApple(ResourceLocation tobaccoTexture) {
        this.tobaccoTexture = tobaccoTexture;
    }

    @Override
    public ResourceLocation getTexture() {
        return this.tobaccoTexture;
    }

    @Override
    public void addEffect(EntityPlayer player) {
        int duration = 2400;
        int amplifier = 0;
        if (player.isPotionActive(MobEffects.SPEED)) {
            PotionEffect activeEffect = player.getActivePotionEffect(MobEffects.SPEED);
            int activeDuration = activeEffect.getDuration();
            int activeAmplifier = activeEffect.getAmplifier();
            if (duration < 12000) {
                duration = duration / 2 + activeDuration;
            }
            amplifier = activeAmplifier;
        }
        player.addPotionEffect(new PotionEffect(MobEffects.SPEED, duration, amplifier));
    }

    @Override
    public void onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

    }



}