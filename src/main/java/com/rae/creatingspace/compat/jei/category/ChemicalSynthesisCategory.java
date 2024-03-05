package com.rae.creatingspace.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.rae.creatingspace.compat.jei.AnimatedCatalystCarrier;
import com.simibubi.create.compat.jei.category.BasinCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedBlazeBurner;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.minecraft.client.gui.GuiGraphics;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ChemicalSynthesisCategory extends BasinCategory {
	private final AnimatedCatalystCarrier carrier = new AnimatedCatalystCarrier();
	private final AnimatedBlazeBurner heater = new AnimatedBlazeBurner();

	public static ChemicalSynthesisCategory standard(Info<BasinRecipe> info) {
		return new ChemicalSynthesisCategory(info);
	}

	protected ChemicalSynthesisCategory(Info<BasinRecipe> info) {
		super(info, true);
	}

	@Override
	public void draw(BasinRecipe recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
		super.draw(recipe, iRecipeSlotsView, graphics, mouseX, mouseY);

		HeatCondition requiredHeat = recipe.getRequiredHeat();
		if (requiredHeat != HeatCondition.NONE)
			heater.withHeat(requiredHeat.visualizeAsBlazeBurner())
					.draw(graphics, getBackground().getWidth() / 2 + 3, 55);
		carrier.draw(graphics, getBackground().getWidth() / 2 + 3, 34);
	}
}
