package com.rae.creatingspace.init.ingameobject;


import com.rae.creatingspace.CreatingSpace;
import com.rae.creatingspace.init.TagsInit;
import com.rae.creatingspace.content.fluids.CSOpenEndedPipe;
import com.rae.creatingspace.content.fluids.CustomVirtualFluid;
import com.simibubi.create.AllFluids;
import com.simibubi.create.content.fluids.OpenEndedPipe;
import com.simibubi.create.content.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.utility.Color;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.entry.FluidEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.joml.Vector3f;

import java.util.List;
import java.util.function.Supplier;

public class FluidInit {

    private static ResourceLocation customStill(String name){
        return CreatingSpace.resource("fluid/"+ name+"_still");
    }
    private static ResourceLocation customFlowing(String name){
        return CreatingSpace.resource("fluid/"+ name +"_flow");
    }

    private static FluidBuilder<VirtualFluid, CreateRegistrate> registrateCustomVirtualLiquid(String name){
        return CreatingSpace.REGISTRATE.virtualFluid(name,
                customStill(name),
                customFlowing(name),
                CreateRegistrate::defaultFluidType,
                CustomVirtualFluid::new);
    }

    public static final FluidEntry<VirtualFluid> LIQUID_METHANE =
            registrateCustomVirtualLiquid("liquid_methane")
                    .properties(p -> p.viscosity(1000).temperature(90).density(500)
                            .canExtinguish(true))
                    .tag(TagsInit.CustomFluidTags.LIQUID_METHANE.tag)
                    .register();

    /*public static final ItemEntry<BucketItem> CREATIVE_BUCKET_METHANE =
            CreatingSpace.REGISTRATE.item("liquid_methane_bucket",
                    p-> new BucketItem(LIQUID_METHANE.get(),p))
                    .register();
                          /*fogColor(0.75f,0.21f,0.5f))*/
    public static final FluidEntry<VirtualFluid> LIQUID_OXYGEN =
            registrateCustomVirtualLiquid("liquid_oxygen")
                    .properties(p -> p.viscosity(1000).temperature(90).density(1000))
                    .tag(TagsInit.CustomFluidTags.LIQUID_OXYGEN.tag)
                    .register();
    /*public static final ItemEntry<BucketItem> CREATIVE_BUCKET_OXYGEN =
            CreatingSpace.REGISTRATE.item("liquid_oxygen_bucket",
                    p-> new BucketItem(LIQUID_OXYGEN.get(),p))
                    .register();
                    /*fogColor(0.08f,0.55f,0.81f))*/


    public static final FluidEntry<VirtualFluid> LIQUID_HYDROGEN =
            registrateCustomVirtualLiquid("liquid_hydrogen")
                    .properties(p -> p.viscosity(1000).temperature(10).density(70))
                    .tag(TagsInit.CustomFluidTags.LIQUID_HYDROGEN.tag)
                    .register();

    /*public static final ItemEntry<BucketItem> CREATIVE_BUCKET_HYDROGEN =
            CreatingSpace.REGISTRATE.item("liquid_hydrogen_bucket",
                            p-> new BucketItem(LIQUID_HYDROGEN.get(),p))
                    .tab(() -> CreativeModeTabsInit.MINERALS_TAB)
                    .register();*/

    public static final FluidEntry<VirtualFluid> LIQUID_CO2 =
            registrateCustomVirtualLiquid("liquid_co2")
                    .properties(p -> p.viscosity(1000).temperature(180).density(1100))
                    .tag(TagsInit.CustomFluidTags.LIQUID_CO2.tag)
                    .register();

    /*public static final ItemEntry<BucketItem> CREATIVE_BUCKET_CO2 =
            CreatingSpace.REGISTRATE.item("liquid_co2_bucket",
                            p -> new BucketItem(LIQUID_CO2.get(), p))
                    .tab(() -> CreativeModeTabsInit.MINERALS_TAB)
                    .register();*/


    /*fogColor(0.69f,0.34f,0.96f))*/


    public static void register() {}
    public static void registerOpenEndedEffect() {
        OpenEndedPipe.registerEffectHandler(new CSOpenEndedPipe.CryogenicLiquidEffectHandler());
    }

    public static void registerFluidInteractions() {

        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                LIQUID_HYDROGEN.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.OBSIDIAN.defaultBlockState();
                    } else {
                        return Blocks.COBBLESTONE.defaultBlockState();
                    }
                }
        ));
        FluidInteractionRegistry.addInteraction(ForgeMod.WATER_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                LIQUID_HYDROGEN.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.ICE.defaultBlockState();
                    } else {
                        return Blocks.SNOW_BLOCK.defaultBlockState();
                    }
                }
        ));

        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                LIQUID_OXYGEN.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.OBSIDIAN.defaultBlockState();
                    } else {
                        return Blocks.COBBLESTONE.defaultBlockState();
                    }
                }
        ));
        FluidInteractionRegistry.addInteraction(ForgeMod.WATER_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                LIQUID_OXYGEN.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.ICE.defaultBlockState();
                    } else {
                        return Blocks.SNOW_BLOCK.defaultBlockState();
                    }
                }
        ));

        FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                LIQUID_METHANE.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.OBSIDIAN.defaultBlockState();
                    } else {
                        return Blocks.COBBLESTONE.defaultBlockState();
                    }
                }
        ));
        FluidInteractionRegistry.addInteraction(ForgeMod.WATER_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                LIQUID_METHANE.get().getFluidType(),
                fluidState -> {
                    if (fluidState.isSource()) {
                        return Blocks.ICE.defaultBlockState();
                    } else {
                        return Blocks.SNOW_BLOCK.defaultBlockState();
                    }
                }
        ));
    }

    //copy of Create's class
    private static class SolidRenderedPlaceableFluidType extends AllFluids.TintedFluidType {

        private Vector3f fogColor;
        private Supplier<Float> fogDistance;

        public static FluidBuilder.FluidTypeFactory create(int fogColor, Supplier<Float> fogDistance) {
            return (p, s, f) -> {
                SolidRenderedPlaceableFluidType fluidType = new SolidRenderedPlaceableFluidType(p, s, f);
                fluidType.fogColor = new Color(fogColor, false).asVectorF();
                fluidType.fogDistance = fogDistance;
                return fluidType;
            };
        }

        private SolidRenderedPlaceableFluidType(Properties properties, ResourceLocation stillTexture,
                                                ResourceLocation flowingTexture) {
            super(properties, stillTexture, flowingTexture);
        }

        @Override
        protected int getTintColor(FluidStack stack) {
            return NO_TINT;
        }

        /*
         * Removing alpha from tint prevents optifine from forcibly applying biome
         * colors to modded fluids (this workaround only works for fluids in the solid
         * render layer)
         */
        @Override
        public int getTintColor(FluidState state, BlockAndTintGetter world, BlockPos pos) {
            return 0x00ffffff;
        }

        @Override
        protected Vector3f getCustomFogColor() {
            return fogColor;
        }

        @Override
        protected float getFogDistanceModifier() {
            return fogDistance.get();
        }

    }
}

