package com.reaper.waterpipe;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TextureHandler {
    public static final ResourceLocation SMOKE_PARTICLE = new ResourceLocation("reaper_waterpipe", "particle/smoke");
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onTextureStitch(TextureStitchEvent.Pre event) {
        ResourceLocation smokeTexture = new ResourceLocation("reaper_waterpipe", "particle/smoke");
        //event.getMap().registerSprite(smokeTexture);
        TextureMap textureMap = event.getMap();
        textureMap.registerSprite(SMOKE_PARTICLE);
        ResourceLocation textureLocation = new ResourceLocation("reaper_waterpipe:blocks/water_pipe");

        // Проверка, зарегистрирована ли текстура
        if (textureMap.getTextureExtry(textureLocation.toString()) != null) {
            System.out.println("Текстура зарегистрирована: " + textureLocation);
        } else {
            System.out.println("Текстура НЕ зарегистрирована: " + textureLocation);
        }
    }
}