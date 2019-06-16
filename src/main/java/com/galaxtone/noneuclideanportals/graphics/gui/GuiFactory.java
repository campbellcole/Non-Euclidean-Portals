package com.galaxtone.noneuclideanportals.graphics.gui;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

public class GuiFactory implements IModGuiFactory {

	@Override
	public void initialize(Minecraft minecraft) {}


	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasConfigGui() {
		return false;
	}
}
