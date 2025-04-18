package com.reaper.waterpipe.tobacco;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.init.MobEffects;

public class TobaccoLightStoneDust extends Tobacco {
    public TobaccoLightStoneDust(ResourceLocation tobaccoTexture) {
        this.tobaccoTexture = tobaccoTexture;
    }

    @Override
    public ResourceLocation getTexture() {
        return this.tobaccoTexture;
    }

    @Override
    public void addEffect(EntityPlayer player) {
        int duration = 4800;
        int amplifier = 0;
        if (player.isPotionActive(MobEffects.NIGHT_VISION)) {
            PotionEffect activeEffect = player.getActivePotionEffect(MobEffects.NIGHT_VISION);
            int activeDuration = activeEffect.getDuration();
            int activeAmplifier = activeEffect.getAmplifier();
            if (duration < 12000) {
                duration = duration / 2 + activeDuration;
            }
            amplifier = activeAmplifier;
        }
        player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, duration, amplifier));
    }
}