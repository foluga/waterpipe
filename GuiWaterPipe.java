package com.reaper.waterpipe;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiWaterPipe extends GuiContainer {
    private static final ResourceLocation TEXTURE = new ResourceLocation("reaper_waterpipe", "textures/gui/waterpipegui.png");
    private final TileEntityWaterPipe tileEntity;

    public GuiWaterPipe(InventoryPlayer playerInventory, TileEntityWaterPipe tileEntity) {
        super(new ContainerWaterPipe(playerInventory, tileEntity));
        this.tileEntity = tileEntity;
        this.xSize = 176;
        this.ySize = 166;
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

        // Отрисовка индикатора перезарядки
        if (this.tileEntity.isOnCooldown()) {
            // Получаем процент оставшегося времени кулдауна (от 1.0 до 0.0)
            float progress = this.tileEntity.getCooldownPercent();
            // Вычисляем ширину заполненной части (24 - полная ширина текстуры)
            int width = (int)(24 * progress);

            // Рисуем индикатор перезарядки (176, 0 - координаты текстуры в texture.png)
            this.drawTexturedModalRect(
                    this.guiLeft + 76,       // X позиция
                    this.guiTop + 45,        // Y позиция
                    176,                     // Текстура X
                    0,                       // Текстура Y
                    width,                   // Ширина
                    17                       // Высота
                    );
        }
    }


    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString("Water Pipe", 8, 6, 4210752);
        this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
    }
}