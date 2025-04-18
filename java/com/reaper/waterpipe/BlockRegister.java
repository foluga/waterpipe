package com.reaper.waterpipe;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRegister {
    public static Block waterPipeBlock = new BlockWaterPipe();
    public static void register() {
        setRegister(waterPipeBlock);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRender() {
        setRender(waterPipeBlock);
    }

    private static void setRegister(Block waterPipeBlock) {
        ForgeRegistries.BLOCKS.register(waterPipeBlock);
        ForgeRegistries.ITEMS.register(new ItemBlock(waterPipeBlock).setRegistryName(waterPipeBlock.getRegistryName()));
    }

    @SideOnly(Side.CLIENT)
    private static void setRender(Block block) {
        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(block), stack -> {
            return new ModelResourceLocation(block.getRegistryName(), "inventory");
        });
        // Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

}
