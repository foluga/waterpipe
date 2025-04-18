package com.reaper.waterpipe.tobacco;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.init.MobEffects;

public class TobaccoAppleGold extends Tobacco {
    public TobaccoAppleGold(ResourceLocation tobaccoTexture) {
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
        int activeDuration;
        int activeAmplifier;

        // Ёффект скорости
        if (player.isPotionActive(MobEffects.SPEED)) {
            PotionEffect activeEffect = player.getActivePotionEffect(MobEffects.SPEED);
            activeDuration = activeEffect.getDuration();
            activeAmplifier = activeEffect.getAmplifier();
            if (duration < 12000) {
                duration = duration / 2 + activeDuration;
            }
            amplifier = activeAmplifier;
        }
        player.addPotionEffect(new PotionEffect(MobEffects.SPEED, duration, amplifier));

        // Ёффект прыжка
        duration = 2400;
        amplifier = 1;
        if (player.isPotionActive(MobEffects.JUMP_BOOST)) {
            PotionEffect activeEffect = player.getActivePotionEffect(MobEffects.JUMP_BOOST);
            activeDuration = activeEffect.getDuration();
            activeAmplifier = activeEffect.getAmplifier();
            if (duration < 12000) {
                duration = duration / 2 + activeDuration;
            }
            amplifier = activeAmplifier;
        }
        player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, duration, amplifier));
    }
}