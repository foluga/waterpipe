package com.reaper.waterpipe.tobacco;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.init.MobEffects;

public class TobaccoBlazePowder extends Tobacco {
    public TobaccoBlazePowder(ResourceLocation tobaccoTexture) {
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
        if (player.isPotionActive(MobEffects.FIRE_RESISTANCE)) {
            PotionEffect activeEffect = player.getActivePotionEffect(MobEffects.FIRE_RESISTANCE);
            int activeDuration = activeEffect.getDuration();
            int activeAmplifier = activeEffect.getAmplifier();
            if (duration < 12000) {
                duration = duration / 2 + activeDuration;
            }
            amplifier = activeAmplifier;
        }
        player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, duration, amplifier));
    }
}