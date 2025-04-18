package com.reaper.waterpipe.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleCustomSmoke extends Particle {
    private static final ResourceLocation SMOKE_TEXTURE = new ResourceLocation("reaper_waterpipe", "textures/particle/smoke.png");
    public ParticleCustomSmoke(World world, double x, double y, double z,
            double motionX, double motionY, double motionZ,
            float scale, int color) {
        super(world, x, y, z, motionX, motionY, motionZ);

        // Настройка внешнего вида
        this.particleRed = ((color >> 16) & 0xFF) / 255.0F;
        this.particleGreen = ((color >> 8) & 0xFF) / 255.0F;
        this.particleBlue = (color & 0xFF) / 255.0F;

        this.particleScale = scale;
        this.particleAlpha = 0.8F; // Прозрачность
        this.particleMaxAge = 50 + this.rand.nextInt(20); // Время жизни

        // Физика частицы
        this.motionX = motionX * 0.01;
        this.motionY = motionY * 0.01 + 0.02;
        this.motionZ = motionZ * 0.01;
        // Важно: Установка размера текстуры
        this.particleTextureJitterX = this.rand.nextFloat() * 3.0F;
        this.particleTextureJitterY = this.rand.nextFloat() * 3.0F;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge) {
            this.setExpired();
        }

        this.motionY += 0.002;
        this.move(this.motionX, this.motionY, this.motionZ);

        // Постепенное исчезновение
        this.particleAlpha = 0.8F * (1 - (float)this.particleAge/this.particleMaxAge);
    }

    @Override
    public int getFXLayer() {
        return 1; // 1 - для частиц с текстурами, 0 - для стандартных
    }
}