package com.reaper.waterpipe.tobacco;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.init.MobEffects;

public class TobaccoCarrot extends Tobacco {
    public TobaccoCarrot(ResourceLocation tobaccoTexture) {
        this.tobaccoTexture = tobaccoTexture;
    }

    @Override
    public ResourceLocation getTexture() {
        return this.tobaccoTexture;
    }

    @Override
    public void addEffect(EntityPlayer player) {
        int duration = 3600;
        int amplifier = 0;
        if (player.isPotionActive(MobEffects.STRENGTH)) {
            PotionEffect activeEffect = player.getActivePotionEffect(MobEffects.STRENGTH);
            int activeDuration = activeEffect.getDuration();
            int activeAmplifier = activeEffect.getAmplifier();
            if (duration < 12000) {
                duration = duration / 2 + activeDuration;
            }
            amplifier = activeAmplifier;
        }
        player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, duration, amplifier));
    }
}