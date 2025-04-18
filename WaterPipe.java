package com.reaper.waterpipe;
import net.minecraft.client.particle.Particle;
import com.reaper.waterpipe.network.PacketSpawnSmoke;
import com.reaper.waterpipe.particle.ParticleCustomSmoke;
import com.reaper.waterpipe.tobacco.TobaccoApple;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = WaterPipe.MODID, name = WaterPipe.NAME, version = WaterPipe.VERSION)
public class WaterPipe {
    public static final String MODID = "reaper_waterpipe";
    public static final String NAME = "Water Pipe";
    public static final String VERSION = "1.0";
    public static final int CUSTOM_SMOKE_ID = 0;

    @Mod.Instance(MODID)
    public static WaterPipe instance;

    public static Block waterPipeBlock;
    public static ItemTobacco tobaccoApple;
    public static ItemStack WATER_BOTTLE;
    public static SimpleNetworkWrapper network;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Инициализация сети
        network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
        network.registerMessage(PacketSpawnSmoke.Handler.class, PacketSpawnSmoke.class, 0, Side.CLIENT);

        // Создание водяной бутылки
        WATER_BOTTLE = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER);

        // Инициализация блоков и предметов
        waterPipeBlock = new BlockWaterPipe();
        tobaccoApple = new ItemTobacco("tobacco_apple",
                new TobaccoApple(new ResourceLocation(MODID, "textures/items/tobacco_apple.png")),
                54,
                0xFFAA00);

        // Регистрация обработчиков событий
        MinecraftForge.EVENT_BUS.register(new TextureHandler());
        MinecraftForge.EVENT_BUS.register(new TileEntityUpdater());
        MinecraftForge.EVENT_BUS.register(this);

        // Регистрация TileEntity
        GameRegistry.registerTileEntity(TileEntityWaterPipe.class, new ResourceLocation(MODID, "water_pipe"));

        // Регистрация GUI Handler
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        if (event.getSide() == Side.CLIENT) {
            ClientRegistry.bindTileEntitySpecialRenderer(
                    TileEntityWaterPipe.class,
                    new TileEntityWaterPipeRenderer()
                    );
        }
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(waterPipeBlock);
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(waterPipeBlock).setRegistryName(waterPipeBlock.getRegistryName()));
        event.getRegistry().register(tobaccoApple);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void registerModels(ModelRegistryEvent event) {
        ((BlockWaterPipe) waterPipeBlock).registerModels();
    }

    public static void spawnCustomSmoke(World world, double x, double y, double z,
            int color, float scale) {
        if (world.isRemote) {
            Particle particle = new ParticleCustomSmoke(world, x, y, z,
                    (world.rand.nextDouble() - 0.5) * 0.01,
                    0.05,
                    (world.rand.nextDouble() - 0.5) * 0.01,
                    scale, color);
            Minecraft.getMinecraft().effectRenderer.addEffect(particle);
        } else {
            network.sendToAllAround(new PacketSpawnSmoke(x, y, z, color, scale),
                    new NetworkRegistry.TargetPoint(
                            world.provider.getDimension(),
                            x, y, z,
                            64
                            ));
        }
    }
}