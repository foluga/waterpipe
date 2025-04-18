package com.reaper.waterpipe.network;

import com.reaper.waterpipe.particle.ParticleCustomSmoke;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSpawnSmoke implements IMessage {
    private double x, y, z;
    private int color;
    private float scale;

    // Конструктор по умолчанию обязателен
    public PacketSpawnSmoke() {}

    public PacketSpawnSmoke(double x, double y, double z, int color, float scale) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
        this.scale = scale;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.color = buf.readInt();
        this.scale = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeInt(this.color);
        buf.writeFloat(this.scale);
    }

    // Внутренний класс-обработчик
    public static class Handler implements IMessageHandler<PacketSpawnSmoke, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketSpawnSmoke message, MessageContext ctx) {
            // Используем Minecraft.getMinecraft().addScheduledTask для работы в основном потоке рендеринга
            Minecraft.getMinecraft().addScheduledTask(() -> {
                World world = Minecraft.getMinecraft().world;
                if (world != null) {
                    // Создаем частицу напрямую
                    ParticleCustomSmoke particle = new ParticleCustomSmoke(
                            world,
                            message.x,
                            message.y,
                            message.z,
                            (world.rand.nextDouble() - 0.5) * 0.01,
                            0.05,
                            (world.rand.nextDouble() - 0.5) * 0.01,
                            message.scale,
                            message.color
                            );
                    Minecraft.getMinecraft().effectRenderer.addEffect(particle);
                }
            });
            return null; // Возвращаем null, так как это односторонний пакет
        }
    }
}