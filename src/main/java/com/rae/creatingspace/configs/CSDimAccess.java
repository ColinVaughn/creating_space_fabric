package com.rae.creatingspace.configs;

import java.util.Arrays;
import java.util.List;

public class CSDimAccess extends CSConfigBase {

    public enum DimensionCategory {
        PLANETS("planets", "creatingspace:the_moon", "minecraft:overworld", "creatingspace:mars", "creatingspace:venus"),
        ORBITS("orbits", "creatingspace:moon_orbit", "creatingspace:earth_orbit", "creatingspace:mars_orbit", "creatingspace:venus_orbit"),
        NO_O2("no_O2", "creatingspace:moon_orbit", "creatingspace:earth_orbit", "creatingspace:the_moon", "creatingspace:mars", "creatingspace:mars_orbit", "creatingspace:venus_orbit", "creatingspace:venus"),
        ACCESSIBILITY_MATRIX("accessibility_matrix", "minecraft:overworld-{creatingspace:moon_orbit,creatingspace:earth_orbit}", "creatingspace:earth_orbit-{creatingspace:moon_orbit,minecraft:overworld}", "creatingspace:moon_orbit-{creatingspace:the_moon,creatingspace:earth_orbit}", "creatingspace:the_moon-{creatingspace:moon_orbit}");

        private final String configName;
        private final List<String> dimensionIds;

        DimensionCategory(String configName, String... dimensionIds) {
            this.configName = configName;
            this.dimensionIds = Arrays.asList(dimensionIds);
        }

        public String getConfigName() {
            return configName;
        }

        public List<String> getDimensionIds() {
            return dimensionIds;
        }
    }

    public final ConfigList<String> planets = l(DimensionCategory.PLANETS.getDimensionIds(), DimensionCategory.PLANETS.getConfigName(), Comments.planets);
    public final ConfigList<String> orbits = l(DimensionCategory.ORBITS.getDimensionIds(), DimensionCategory.ORBITS.getConfigName(), Comments.orbits);
    public final ConfigList<String> no_02 = l(DimensionCategory.NO_O2.getDimensionIds(), DimensionCategory.NO_O2.getConfigName(), Comments.no_02);
    public final ConfigList<String> accessibility_matrix = l(DimensionCategory.ACCESSIBILITY_MATRIX.getDimensionIds(), DimensionCategory.ACCESSIBILITY_MATRIX.getConfigName(), Comments.accessibilityMatrix);

    @Override
    public String getName() {
        return "dimensionAccess";
    }

    private static class Comments {
        static String planets = "which dimensions are a planet";
        static String orbits = "which dimensions are an orbit";
        static String no_02 = "which dimensions don't have O2";
        static String accessibilityMatrix = "which dimensions are accessible from where";
    }
}
