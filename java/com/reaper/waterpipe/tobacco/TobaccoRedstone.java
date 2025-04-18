package com.reaper.waterpipe.tobacco;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import java.util.Collection;

public class TobaccoRedstone extends Tobacco {
    public TobaccoRedstone(ResourceLocation tobaccoTexture) {
        this.tobaccoTexture = tobaccoTexture;
    }

    @Override
    public ResourceLocation getTexture() {
        return this.tobaccoTexture;
    }

    @Override
    public void addEffect(EntityPlayer player) {
        Collection<PotionEffect> playerActiveEffects = player.getActivePotionEffects();
        if (playerActiveEffects != null) {
            for (PotionEffect potionEffect : playerActiveEffects) {
                Potion potion = potionEffect.getPotion();
                int duration = potionEffect.getDuration();
                int amplifier = potionEffect.getAmplifier();
                player.addPotionEffect(new PotionEffect(potion, duration * 2, amplifier));
            }
        }
    }
}