package com.rae.creatingspace.init.worldgen;

import com.rae.creatingspace.CreatingSpace;
import com.rae.creatingspace.configs.CSConfigs;
import com.rae.creatingspace.utilities.AccessibilityMatrixReader;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DimensionInit {

    public static final ResourceKey<Level> EARTH_ORBIT_KEY =
            ResourceKey.create(Registries.DIMENSION,
                    new ResourceLocation(CreatingSpace.MODID,"earth_orbit"));

    public static final ResourceKey<Level> MOON_ORBIT_KEY =
            ResourceKey.create(Registries.DIMENSION,
                    new ResourceLocation(CreatingSpace.MODID,"moon_orbit"));

    public static final ResourceKey<Level> MOON_KEY =
            ResourceKey.create(Registries.DIMENSION,
                    new ResourceLocation(CreatingSpace.MODID,"the_moon"));

    public static final ResourceKey<Level> MARS_ORBIT_KEY =
            ResourceKey.create(Registries.DIMENSION,
                    new ResourceLocation(CreatingSpace.MODID,"mars_orbit"));

    public static final ResourceKey<Level> MARS_KEY =
            ResourceKey.create(Registries.DIMENSION,
                    new ResourceLocation(CreatingSpace.MODID,"mars"));

    public static final ResourceKey<DimensionType> EARTH_ORBIT_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE,
                    new ResourceLocation(CreatingSpace.MODID,"earth_orbit"));
    public static final ResourceKey<DimensionType> MOON_ORBIT_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE,
                    new ResourceLocation(CreatingSpace.MODID,"moon_orbit"));

    public static final ResourceKey<DimensionType> MOON_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE,
                    new ResourceLocation(CreatingSpace.MODID,"the_moon"));

    public static final ResourceKey<DimensionType> MARS_ORBIT_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE,
                    new ResourceLocation(CreatingSpace.MODID,"mars_orbit"));

    public static final ResourceKey<DimensionType> MARS_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE,
                    new ResourceLocation(CreatingSpace.MODID,"mars"));
    public static double SpaceSpawnHeight = 64; //make the rocket came from the top rather than hard tp -> for future version
    public static double PlanetSpawnHeight = 200;

    public static void register(IEventBus bus) {
        System.out.println("Registering Dimension for : "+ CreatingSpace.MODID);
    }

    public static float gravity(ResourceKey<DimensionType> dimensionType) {
        // Define a map to store gravity values for different dimension types
        Map<ResourceKey<DimensionType>, Float> gravityMap = new HashMap<>();

        // Assign gravity values to specific dimension types
        gravityMap.put(EARTH_ORBIT_TYPE, 0f);
        gravityMap.put(MOON_ORBIT_TYPE, 0f);
        gravityMap.put(MOON_TYPE, 0.2f);
        gravityMap.put(MARS_TYPE, 0.2f);
        gravityMap.put(MARS_ORBIT_TYPE, 0f);

        // Retrieve the gravity value for the given dimension type from the map
        return gravityMap.getOrDefault(dimensionType, 1f);
    }


    public static List<ResourceKey<Level>> accessibleFrom(ResourceKey<Level> currentDimension) {
        List<String> list = CSConfigs.COMMON.dimAccess.accessibility_matrix.get();
        HashMap<ResourceKey<Level>, ResourceKey<Level>[]> accessibilityMap = AccessibilityMatrixReader.createFromStringList(list);

        if (accessibilityMap.containsKey(currentDimension)){
            return Arrays.stream(accessibilityMap.get(currentDimension)).toList();
        }
        return List.of();
    }

    public static boolean hasO2Atmosphere(ResourceKey<Level> dimension) {
        boolean no_02 = CSConfigs.COMMON.dimAccess.no_02.get().contains(dimension.location().toString());
        return !no_02;
    }

    public static boolean isOrbit(ResourceKey<DimensionType> dimensionType) {
        return gravity(dimensionType) == 0;
    }

    public static ResourceKey<Level> planetUnder(ResourceKey<Level> dimension){
        ResourceKey<Level> underDimension = null;
        if (dimension == MOON_ORBIT_KEY){
            underDimension = MOON_KEY;
        }
        if (dimension == EARTH_ORBIT_KEY){
            underDimension = Level.OVERWORLD;
        }
        if (dimension == MARS_ORBIT_KEY){
            underDimension = MARS_KEY;
        }
        return underDimension;
    }
}


