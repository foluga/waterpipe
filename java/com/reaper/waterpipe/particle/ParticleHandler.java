package com.reaper.waterpipe.particle;

import com.reaper.waterpipe.WaterPipe;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = WaterPipe.MODID, value = Side.CLIENT)
public class ParticleHandler {
    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        // Проверяем, что Minecraft инициализирован
        if (Minecraft.getMinecraft() != null && Minecraft.getMinecraft().effectRenderer != null) {
            Minecraft.getMinecraft().effectRenderer.registerParticle(
                    WaterPipe.CUSTOM_SMOKE_ID,
                    (particleID, world, x, y, z, xSpeed, ySpeed, zSpeed, params) ->
                    new ParticleCustomSmoke(world, x, y, z, xSpeed, ySpeed, zSpeed,
                            params.length > 0 ? Float.intBitsToFloat(params[0]) : 1.0f,
                                    params.length > 1 ? params[1] : 0xFFFFFF)
                    );
        }
    }
}