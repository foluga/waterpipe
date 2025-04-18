package com.reaper.waterpipe.tobacco;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;

public enum TobaccoType {
    NORMAL("�������", TextFormatting.GRAY,
            new PotionEffect(MobEffects.REGENERATION, 600, 0),  // 30 ��� �����������
            new PotionEffect(MobEffects.SPEED, 300, 0)         // 15 ��� ��������
            ),

    FRUIT("���������", TextFormatting.GOLD,
            new PotionEffect(MobEffects.SPEED, 600, 1),        // 30 ��� �������� II
            new PotionEffect(MobEffects.REGENERATION, 300, 0)  // 15 ��� �����������
            ),

    MAGIC("����������", TextFormatting.LIGHT_PURPLE,
            new PotionEffect(MobEffects.STRENGTH, 600, 0),     // 30 ��� ����
            new PotionEffect(MobEffects.FIRE_RESISTANCE, 1200, 0) // 60 ��� �������������
            );

    private final String displayName;
    private final PotionEffect[] effects;
    private final TextFormatting color;

    TobaccoType(String displayName, TextFormatting color, PotionEffect... effects) {
        this.displayName = displayName;
        this.effects = effects;
        this.color = color;
    }

    public String getDisplayName() {
        return this.color + this.displayName;
    }

    public PotionEffect[] getEffects() {
        return this.effects;
    }

    public void addEffect(EntityPlayer player) {
        for (PotionEffect effect : this.effects) {
            player.addPotionEffect(new PotionEffect(
                    effect.getPotion(),
                    effect.getDuration(),
                    effect.getAmplifier()
                    ));
        }
    }
}