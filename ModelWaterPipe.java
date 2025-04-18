package com.reaper.waterpipe;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelWaterPipe extends ModelBase {
    // Все компоненты модели (22 части)
    public ModelRenderer Shape1;  // Основание (6x2x6)
    public ModelRenderer Shape2;  // Нижний резервуар (4x4x4)
    public ModelRenderer Shape3;  // Центральный стержень (2x10x2)
    public ModelRenderer Shape4;  // Верхний резервуар (4x2x4)
    public ModelRenderer Shape5;  // Верхняя пластина (4x1x4)
    public ModelRenderer Shape6;  // Боковая деталь 1 (4x1x1)
    public ModelRenderer Shape7;  // Боковая деталь 2 (4x1x1)
    public ModelRenderer Shape8;  // Угловая деталь 1 (1x1x2)
    public ModelRenderer Shape9;  // Угловая деталь 2 (1x1x2)
    public ModelRenderer Shape10; // Декоративный элемент 1 (1x1x1)
    public ModelRenderer Shape11; // Декоративный элемент 2 (1x1x1)
    public ModelRenderer Shape12; // Декоративный элемент 3 (1x1x1)
    public ModelRenderer Shape13; // Декоративный элемент 4 (2x1x1)
    public ModelRenderer Shape14; // Декоративный элемент 5 (1x1x1)
    public ModelRenderer Shape15; // Декоративный элемент 6 (1x3x1)
    public ModelRenderer Shape16; // Декоративный элемент 7 (1x2x1)
    public ModelRenderer Shape17; // Декоративный элемент 8 (1x1x1)
    public ModelRenderer Shape18; // Декоративный элемент 9 (1x1x2)
    public ModelRenderer Shape19; // Декоративный элемент 10 (1x1x1)
    public ModelRenderer Shape20; // Декоративный элемент 11 (1x1x1)
    public ModelRenderer Shape21; // Декоративный элемент 12 (1x1x1)
    public ModelRenderer Shape22; // Верхняя крышка (6x1x6)

    public ModelWaterPipe() {
        this.textureWidth = 64;
        this.textureHeight = 32;

        // Инициализация всех компонентов
        this.initComponents();
    }

    private void initComponents() {
        // Основание (нижняя часть)
        this.Shape1 = this.createRenderer(0, 0, -3F, 22F, -3F, 6, 2, 6);

        // Нижний резервуар для воды
        this.Shape2 = this.createRenderer(0, 8, -2F, 18F, -2F, 4, 4, 4);

        // Центральный стержень
        this.Shape3 = this.createRenderer(16, 8, -1F, 8F, -1F, 2, 10, 2);

        // Верхний резервуар
        this.Shape4 = this.createRenderer(12, 26, -2F, 14F, -2F, 4, 2, 4);

        // Верхняя пластина
        this.Shape5 = this.createRenderer(0, 16, -2F, 7F, -2F, 4, 1, 4);

        // Боковые детали
        this.Shape6 = this.createRenderer(0, 22, -2F, 6F, 1F, 4, 1, 1);
        this.Shape7 = this.createRenderer(0, 24, -2F, 6F, -2F, 4, 1, 1);

        // Угловые детали
        this.Shape8 = this.createRenderer(0, 26, 1F, 6F, -1F, 1, 1, 2);
        this.Shape9 = this.createRenderer(6, 26, -2F, 6F, -1F, 1, 1, 2);

        // Декоративные элементы (трубка)
        this.Shape10 = this.createRenderer(16, 20, 2F, 13F, -1F, 1, 1, 1);
        this.Shape11 = this.createRenderer(24, 0, -3F, 13F, 0F, 1, 1, 1);
        this.Shape12 = this.createRenderer(24, 2, -4F, 12F, 0F, 1, 1, 1);
        this.Shape13 = this.createRenderer(24, 4, -6F, 11F, 0F, 2, 1, 1);
        this.Shape14 = this.createRenderer(24, 6, -7F, 12F, 0F, 1, 1, 1);
        this.Shape15 = this.createRenderer(24, 8, -8F, 13F, 0F, 1, 3, 1);
        this.Shape16 = this.createRenderer(24, 12, -7F, 15F, 1F, 1, 2, 1);
        this.Shape17 = this.createRenderer(24, 15, -6F, 17F, 1F, 1, 1, 1);
        this.Shape18 = this.createRenderer(24, 17, -5F, 18F, 1F, 1, 1, 2);
        this.Shape19 = this.createRenderer(24, 20, -4F, 19F, 2F, 1, 1, 1);
        this.Shape20 = this.createRenderer(24, 22, -3F, 20F, 2F, 1, 1, 1);
        this.Shape21 = this.createRenderer(24, 24, -2F, 21F, 2F, 1, 1, 1);

        // Верхняя крышка
        this.Shape22 = this.createRenderer(30, 0, -3F, 8F, -3F, 6, 1, 6);
    }

    private ModelRenderer createRenderer(int texX, int texY, float x, float y, float z, int w, int h, int d) {
        ModelRenderer renderer = new ModelRenderer(this, texX, texY);
        renderer.addBox(x, y, z, w, h, d);
        renderer.setTextureSize(this.textureWidth, this.textureHeight);
        renderer.mirror = true;
        this.setRotation(renderer, 0F, 0F, 0F);
        return renderer;
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch, float scale) {
        super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.renderModel(scale);
    }

    public void renderModel(float scale) {
        // Рендер всех компонентов в правильном порядке
        this.Shape1.render(scale);
        this.Shape2.render(scale);
        this.Shape3.render(scale);
        this.Shape4.render(scale);
        this.Shape5.render(scale);
        this.Shape6.render(scale);
        this.Shape7.render(scale);
        this.Shape8.render(scale);
        this.Shape9.render(scale);
        this.Shape10.render(scale);
        this.Shape11.render(scale);
        this.Shape12.render(scale);
        this.Shape13.render(scale);
        this.Shape14.render(scale);
        this.Shape15.render(scale);
        this.Shape16.render(scale);
        this.Shape17.render(scale);
        this.Shape18.render(scale);
        this.Shape19.render(scale);
        this.Shape20.render(scale);
        this.Shape21.render(scale);
        this.Shape22.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
        // При необходимости можно добавить анимации
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
    }
}