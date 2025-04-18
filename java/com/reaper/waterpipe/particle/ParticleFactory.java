package com.reaper.waterpipe.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleFactory {
    public static final ResourceLocation SMOKE_TEXTURE = new ResourceLocation("reaper_waterpipe", "textures/particle/smoke.png");
    public static void spawnCustomSmoke(World world, double x, double y, double z,
            double motionX, double motionY, double motionZ,
            float scale, int color) {
        if (world.isRemote) {
            Particle particle = new ParticleCustomSmoke(world, x, y, z,
                    motionX, motionY, motionZ,
                    scale, color);
            Minecraft.getMinecraft().effectRenderer.addEffect(particle);
        }
    }
}