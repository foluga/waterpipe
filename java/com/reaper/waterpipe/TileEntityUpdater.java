package com.reaper.waterpipe;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class TileEntityUpdater {
    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            for (World world : FMLCommonHandler.instance().getMinecraftServerInstance().worlds) {
                for (TileEntity te : world.loadedTileEntityList) {
                    if (te instanceof TileEntityWaterPipe) {
                        ((TileEntityWaterPipe) te).update();
                    }
                }
            }
        }
    }
}