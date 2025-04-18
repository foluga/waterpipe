package com.reaper.waterpipe.tobacco;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.init.MobEffects;

public class TobaccoMelon extends Tobacco {
    public TobaccoMelon(ResourceLocation tobaccoTexture) {
        this.tobaccoTexture = tobaccoTexture;
    }

    @Override
    public ResourceLocation getTexture() {
        return this.tobaccoTexture;
    }

    @Override
    public void addEffect(EntityPlayer player) {
        player.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 0));
    }
}