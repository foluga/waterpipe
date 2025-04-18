package com.reaper.waterpipe;

import com.reaper.waterpipe.particle.ParticleFactory;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityWaterPipeRenderer extends TileEntitySpecialRenderer<TileEntityWaterPipe> {

    private final ModelWaterPipe model = new ModelWaterPipe();

    // Основная текстура водяной трубки
    private static final ResourceLocation TEXTURE = new ResourceLocation("reaper_waterpipe:textures/blocks/water_pipe.png");

    @Override
    public void render(TileEntityWaterPipe te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();

        // Позиционирование модели
        GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
        GlStateManager.rotate(180F, 0F, 0F, 1F); // Переворот модели

        // Привязка текстуры
        this.bindTexture(TEXTURE);

        // Рендер основной модели
        this.model.renderModel(0.0625F); // 0.0625F - стандартный масштабный коэффициент

        // Дополнительные эффекты (дым, уголь и т.д.)
        if(te != null) {
            this.renderAdditionalEffects(te);
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
    }

    private void renderAdditionalEffects(TileEntityWaterPipe te) {
        if (te.isOnCooldown()) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            // Здесь можно добавить рендер дополнительных эффектов
            // Например: дым, уголь, табак и т.д.
            // if(te.getSmokeTime() > 0) {
            GlStateManager.pushMatrix();
            // Настройки для эффектов
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();

        }

    }
    private void renderSmokeEffect(TileEntityWaterPipe te, double x, double y, double z) {
        if (!te.isOnCooldown()) return;

        World world = te.getWorld();
        float progress = te.getCooldownPercent();
        int particles = 1 + (int)(3 * (1 - progress));

        // Получаем цвет из типа табака
        int color = 0xAAAAAA; // Серый по умолчанию
        if (!te.getStackInSlot(TileEntityWaterPipe.SLOT_TOBACCO).isEmpty()) {
            ItemTobacco tobacco = (ItemTobacco)te.getStackInSlot(TileEntityWaterPipe.SLOT_TOBACCO).getItem();
            color = tobacco.getSmokeColor();
        }

        for (int i = 0; i < particles; i++) {
            double offsetX = (world.rand.nextDouble() - 0.5) * 0.2;
            double offsetZ = (world.rand.nextDouble() - 0.5) * 0.2;

            ParticleFactory.spawnCustomSmoke(
                    world,
                    x + 0.5 + offsetX,
                    y + 0.8,
                    z + 0.5 + offsetZ,
                    (world.rand.nextDouble() - 0.5) * 0.01,
                    0.05 + world.rand.nextDouble() * 0.02,
                    (world.rand.nextDouble() - 0.5) * 0.01,
                    1.0F + world.rand.nextFloat(),
                    color
                    );
        }
    }


}

//}