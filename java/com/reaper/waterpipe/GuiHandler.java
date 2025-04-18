package com.reaper.waterpipe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    // ID GUI для водяной трубки
    public static final int WATER_PIPE_GUI = 0;


    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == WATER_PIPE_GUI) {
            // Возвращаем контейнер для серверной стороны
            TileEntityWaterPipe tileEntity = (TileEntityWaterPipe) world.getTileEntity(new BlockPos(x, y, z));
            return new ContainerWaterPipe(player.inventory, tileEntity);
        }
        return null;
    }

    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == WATER_PIPE_GUI) {
            // Возвращаем GUI для клиентской стороны
            TileEntityWaterPipe tileEntity = (TileEntityWaterPipe) world.getTileEntity(new BlockPos(x, y, z));
            return new GuiWaterPipe(player.inventory, tileEntity);
        }
        return null;
    }
}