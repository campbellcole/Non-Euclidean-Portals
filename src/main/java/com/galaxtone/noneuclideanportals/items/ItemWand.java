package com.galaxtone.noneuclideanportals.items;

import com.galaxtone.noneuclideanportals.Main;
import com.galaxtone.noneuclideanportals.Portal;
import com.galaxtone.noneuclideanportals.graphics.gui.GuiHandler;
import com.galaxtone.noneuclideanportals.network.PacketPortal;
import com.galaxtone.noneuclideanportals.utils.Selection;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public final class ItemWand extends Item {

	public static final ItemWand instance = new ItemWand();

	private ItemWand() {
		this.setUnlocalizedName(Main.modid + ".wand");
		this.setRegistryName(Main.modid + ":wand");
		this.setCreativeTab(Main.creativeTab);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		if (!player.canCommandSenderUseCommand(2, null)) {
			if (world.isRemote) player.addChatComponentMessage(new TextComponentTranslation("text." + Main.modid + ".permission"));
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
		}

		if (!world.isRemote) return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);

		Selection.updatePortalSelection();
		if (Selection.getPortal() != null) {
			player.openGui(Main.instance, GuiHandler.portalId, world, 0, 0, 0);
		} else if (Selection.getCurrentItem() == stack) {
			Selection current = Selection.getCurrent();
			Portal portal = new Portal(current.plane, current.axis);

			PacketPortal packet = PacketPortal.create(portal);
			Main.network.sendToServer(packet);

			Selection.stop();
		}

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World w, BlockPos bp,
			EnumHand hand, EnumFacing facing, float hitX, float hitY,
			float hitZ) {

		if (!w.isRemote || hand != EnumHand.MAIN_HAND) return EnumActionResult.PASS;
		if (!player.capabilities.isCreativeMode) {
			player.sendMessage(new TextComponentString(TextFormatting.RED + "You must be in creative mode and opped to use this item!"));
			player.setHeldItem(hand, null);
			return EnumActionResult.FAIL;
		}

		if (player.getHeldItem(hand) == Selection.getCurrentItem()) {
			Selection.stop();
			Selection current = Selection.getCurrent();
			System.out.println(current.plane);
		} else {
			Selection.start(player.getHeldItem(hand), bp);
		}

		return EnumActionResult.PASS;
	}
	/*
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!player.canCommandSenderUseCommand(2, null)) {
			return EnumActionResult.FAIL;
		}

		if (!world.isRemote) return EnumActionResult.SUCCESS;

		if (Selection.getPortal() == null && Selection.getCurrentItem() == null) {
			Selection.start(stack, pos);
	        return EnumActionResult.SUCCESS;
		}

        return EnumActionResult.PASS;
	}*/
}
