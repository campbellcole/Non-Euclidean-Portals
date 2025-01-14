package com.galaxtone.noneuclideanportals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.galaxtone.noneuclideanportals.graphics.gui.GuiHandler;
import com.galaxtone.noneuclideanportals.items.ItemWand;
import com.galaxtone.noneuclideanportals.network.PacketPortal;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Main.modid, name = Main.name, version = Main.version, guiFactory = Main.guiFactory)
public class Main {

	public static final String modid = "noneuclideanportals";
	public static final String name = "Non-Euclidean Portals";
	public static final String version = "0.3";
	public static final String guiFactory = "com.galaxtone.noneuclideanportals.graphics.gui.GuiFactory";

	public static final CreativeTabs creativeTab = new CreativeTabs(modid) {
		@SideOnly(Side.CLIENT)
		@Override
		public ItemStack getTabIconItem() {
			return ItemWand.instance.getDefaultInstance();
		}
	};

	public static final SimpleNetworkWrapper network = new SimpleNetworkWrapper(Main.modid);

	@Instance
	public static Main instance = new Main();

	public static Config config;

	public static final Logger logger = LogManager.getLogger(Main.modid);

	@EventHandler
	public static void init(FMLPreInitializationEvent event) {
		network.registerMessage(PacketPortal.Handler.class, PacketPortal.class, 0, Side.CLIENT);
		network.registerMessage(PacketPortal.Handler.class, PacketPortal.class, 1, Side.SERVER);

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

		if (event.getSide() == Side.CLIENT) {
			Framebuffer buffer = Minecraft.getMinecraft().getFramebuffer();
			if (!buffer.isStencilEnabled()) buffer.enableStencil();
		}

		config = new Config(event.getModConfigurationDirectory());
	}
}
