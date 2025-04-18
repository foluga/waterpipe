package com.reaper.waterpipe.tobacco;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class TobaccoBonemeal extends Tobacco {
   public TobaccoBonemeal(ResourceLocation tobaccoTexture) {
      this.tobaccoTexture = tobaccoTexture;
   }

   public ResourceLocation getTexture() {
      return this.tobaccoTexture;
   }

   public void addEffect(EntityPlayer player) {
      player.clearActivePotions();
   }
}
