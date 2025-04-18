package com.reaper.waterpipe;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockWaterPipe extends BlockContainer {

    public BlockWaterPipe() {
        super(Material.IRON);
        this.setHardness(2.0F);
        this.setResistance(10.0F);
        this.setRegistryName("water_pipe");
        this.setTranslationKey("water_pipe");
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityWaterPipe();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote)
            return true;

        TileEntityWaterPipe tile = (TileEntityWaterPipe) world.getTileEntity(pos);
        if (tile == null)
            return false;

        ItemStack heldItem = player.getHeldItem(hand);

        // Если в руке палка - активируем эффект
        if (heldItem.getItem() == Items.STICK) {
            tile.trySmoke(player);
            return true;
        }
        // Для любого другого предмета или пустой руки - открываем GUI
        else {
            player.openGui(WaterPipe.instance, GuiHandler.WATER_PIPE_GUI, world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerModels() {
        net.minecraftforge.client.model.ModelLoader.setCustomModelResourceLocation(
                net.minecraft.item.Item.getItemFromBlock(this),
                0,
                new net.minecraft.client.renderer.block.model.ModelResourceLocation(this.getRegistryName(), "inventory")
                );
    }
}