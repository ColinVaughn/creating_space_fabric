package com.rae.creatingspace.init.worldgen;

import com.rae.creatingspace.CreatingSpace;
import com.rae.creatingspace.server.event.CSEventHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

;

@Mod.EventBusSubscriber(modid = CreatingSpace.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class IgniteOnPlace {

    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {

        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity player = (LivingEntity) event.getEntity();
            BlockState blockState = event.getState();

            if (!CSEventHandler.isInO2(player)&&player.level().dimension().location().toString().equals("creatingspace:venus")){
                Level world = (Level) event.getLevel();
                BlockPos pos = event.getPos();
                if (blockState.isFlammable(event.getLevel(), event.getPos(), Direction.UP)) {


                    // Attempt to place fire on top of the block
                    BlockPos topPos = pos.above();
                    if (world.isEmptyBlock(topPos)) {
                        world.setBlockAndUpdate(topPos, Blocks.FIRE.defaultBlockState());
                    } else {
                        // If the top is not available, try to place fire on one of the sides
                        for (Direction direction : Direction.Plane.HORIZONTAL) {
                            BlockPos sidePos = pos.relative(direction);
                            if (world.isEmptyBlock(sidePos)) {
                                world.setBlockAndUpdate(sidePos, Blocks.FIRE.defaultBlockState());
                                break; // Stop after placing the first fire block
                            }
                        }
                    }

                }
                if (blockState.is(Blocks.DIRT) || blockState.is(Blocks.GRASS_BLOCK) || blockState.is(Blocks.PODZOL) || blockState.is(Blocks.MYCELIUM)) {
                    world.setBlockAndUpdate(pos, Blocks.SOUL_SAND.defaultBlockState());
                }
            }
        }

    }
    public static void register () {
    }
}
