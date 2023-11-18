package com.rae.creatingspace.init.graphics;

import com.rae.creatingspace.client.effects.CustomDimensionEffects;
import com.rae.creatingspace.init.worldgen.DimensionInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DimensionEffectInit {

    public DimensionEffectInit() {
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
        // Register Earth Orbit Effects
        ResourceLocation earthOrbitLocation = DimensionInit.EARTH_ORBIT_TYPE.location();
        CustomDimensionEffects.EarthOrbitEffects earthOrbitEffects = new CustomDimensionEffects.EarthOrbitEffects();
        registerDimensionEffects(event, earthOrbitLocation, earthOrbitEffects, true);

        // Register Moon Orbit Effects
        ResourceLocation moonOrbitLocation = DimensionInit.MOON_ORBIT_TYPE.location();
        CustomDimensionEffects.MoonOrbitEffect moonOrbitEffect = new CustomDimensionEffects.MoonOrbitEffect();
        registerDimensionEffects(event, moonOrbitLocation, moonOrbitEffect, true);

        // Register Moon Effects
        ResourceLocation moonLocation = DimensionInit.MOON_TYPE.location();
        CustomDimensionEffects.MoonEffect moonEffect = new CustomDimensionEffects.MoonEffect();
        registerDimensionEffects(event, moonLocation, moonEffect, true);

        // Register Mars Orbit Effects
        ResourceLocation marsOrbitLocation = DimensionInit.MARS_ORBIT_TYPE.location();
        CustomDimensionEffects.MarsOrbitEffects marsOrbitEffect = new CustomDimensionEffects.MarsOrbitEffects();
        registerDimensionEffects(event, marsOrbitLocation, marsOrbitEffect, true);

        // Register Mars Effects
        ResourceLocation marsLocation = DimensionInit.MARS_TYPE.location();
        CustomDimensionEffects.MoonEffect marsEffect = new CustomDimensionEffects.MoonEffect();
        registerDimensionEffects(event, marsLocation, marsEffect, true);
    }

    private static void registerDimensionEffects(RegisterDimensionSpecialEffectsEvent event, ResourceLocation location, CustomDimensionEffects.GenericCelestialOrbitEffect effects, boolean renderSun) {
        effects.setRenderSun(renderSun);
        event.register(location, effects);
    }
}
