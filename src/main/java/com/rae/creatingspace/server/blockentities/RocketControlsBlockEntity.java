package com.rae.creatingspace.server.blockentities;

import com.rae.creatingspace.CreatingSpace;
import com.rae.creatingspace.server.blocks.RocketControlsBlock;
import com.rae.creatingspace.server.contraption.RocketContraption;
import com.rae.creatingspace.server.entities.RocketContraptionEntity;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.contraptions.AssemblyException;
import com.simibubi.create.content.contraptions.IDisplayAssemblyExceptions;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.List;

public class RocketControlsBlockEntity extends SmartBlockEntity implements IDisplayAssemblyExceptions/*implements MenuProvider*/ {

    protected AssemblyException lastException;

    private boolean assembleNextTick = false;
    private ResourceKey<Level> destination;

    public HashMap<String,BlockPos> initialPosMap = new HashMap<>();

    public static final Component TITLE = Component.translatable("container."+ CreatingSpace.MODID +".rocket_controls");
    public RocketControlsBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type,pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        registerAwardables(behaviours, AllAdvancements.CONTRAPTION_ACTORS);

    }
    @Override
    public void initialize() {
        super.initialize();
        if (!getBlockState().canSurvive(level, worldPosition))
            level.destroyBlock(worldPosition, true);

    }


    @Override
    public AssemblyException getLastAssemblyException() {
        return lastException;
    }

    public void queueAssembly(ResourceKey<Level> destination) {
        this.assembleNextTick = true;
        this.destination = destination;
    }

    private void assemble() {

        if (!(level.getBlockState(worldPosition)
                .getBlock() instanceof RocketControlsBlock)) {
            return;
        }

        RocketContraption contraption = new RocketContraption();


        try {
            lastException = null;
            if (!contraption.assemble(level, worldPosition))
                return;

            sendData();
        } catch (AssemblyException e) {
            lastException = e;
            sendData();
            return;
        }

        if (contraption.containsBlockBreakers())
            award(AllAdvancements.CONTRAPTION_ACTORS);

        contraption.removeBlocksFromWorld(level, BlockPos.ZERO);

        if (destination == null){
            destination = Level.OVERWORLD;
        }

        RocketContraptionEntity rocketContraptionEntity = RocketContraptionEntity.create(level, contraption, destination);
        BlockPos anchor = worldPosition;
        rocketContraptionEntity.setPos(anchor.getX(), anchor.getY(), anchor.getZ());
        if(initialPosMap.containsKey(destination.toString())){
            rocketContraptionEntity.rocketEntryCoordinate = initialPosMap.get(destination.toString());
        }
        level.addFreshEntity(rocketContraptionEntity);

        AllSoundEvents.CONTRAPTION_ASSEMBLE.playOnServer(level, worldPosition);

    }
    @Override
    public void tick() {
        super.tick();

        if (level.isClientSide)
            return;

        if (assembleNextTick) {
            assemble();
            assembleNextTick = false;
        }
        if (!this.initialPosMap.containsKey(this.level.dimension().toString())) {
            this.initialPosMap.put(this.level.dimension().toString(),this.getBlockPos());
        }
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        AssemblyException.write(compound, lastException);

        this.putPosMap(this.initialPosMap,compound);

        super.write(compound, clientPacket);
    }

    private void putPosMap(HashMap<String,BlockPos> initialPosMap, CompoundTag compound) {

        for (String key : initialPosMap.keySet()) {
                compound.putLong("dimensionInitialPosOf:" + key,initialPosMap.get(key).asLong());
        }

    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        lastException = AssemblyException.read(compound);

        this.initialPosMap = getPosMap(compound);

        super.read(compound, clientPacket);
    }

    private  HashMap<String,BlockPos> getPosMap(CompoundTag compound) {
        HashMap<String,BlockPos> initialPosMap = new HashMap<>();


        for (String key: compound.getAllKeys()) {
            if(key.contains("dimensionInitialPosOf:")){
                initialPosMap.put(
                        key.substring(22),
                        BlockPos.of(compound.getLong(key)));
            }
        }

        return initialPosMap;
    }

    public boolean noLocalisation() {
        return initialPosMap.isEmpty();
    }
}
