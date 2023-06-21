package com.rea.creatingspace.blockentities;

import com.rea.creatingspace.blocks.MecanicalElectrolyzerBlock;
import com.rea.creatingspace.init.BlockEntityInit;
import com.rea.creatingspace.init.FluidInit;
import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.speedController.SpeedControllerBlock;
import com.simibubi.create.foundation.fluid.SmartFluidTank;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.LangBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static java.lang.Math.abs;

public class MecanicalElectrolyzerBlockEntity extends KineticBlockEntity implements IHaveGoggleInformation {

    public MecanicalElectrolyzerBlockEntity(BlockEntityType<?> type,BlockPos pos, BlockState state) {
        super(type,pos, state);
    }
    @Override
    public void sendData() {
        if (syncCooldown > 0) {
            queuedSync = true;
            return;
        }
        super.sendData();
        queuedSync = false;
        syncCooldown = SYNC_RATE;
    }

    private static final int SYNC_RATE = 8;
    protected int syncCooldown;
    protected boolean queuedSync;

    //water
    private final LazyOptional<IFluidHandler> waterFluidOptional = LazyOptional.of(()-> this.WATER_TANK);
    private final FluidTank WATER_TANK  = new FluidTank(4000){
        @Override
        protected void onContentsChanged() {
            sendData();
            super.onContentsChanged();
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == Fluids.WATER.getSource();
        }

    };
    //hydrogen
    private final LazyOptional<IFluidHandler> hydrogenFluidOptional = LazyOptional.of(()-> this.HYDROGEN_TANK);
    private final FluidTank HYDROGEN_TANK = new FluidTank(4000){
        @Override
        protected void onContentsChanged() {
            sendData();
            super.onContentsChanged();
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == FluidInit.LIQUID_HYDROGEN.get();
        }
    };
    //oxygen




    private final LazyOptional<IFluidHandler> oxygenFluidOptional = LazyOptional.of(()-> this.OXYGEN_TANK);
    private final FluidTank OXYGEN_TANK = new FluidTank(4000){
        @Override
        protected void onContentsChanged() {
            sendData();
            super.onContentsChanged();
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == FluidInit.LIQUID_OXYGEN.get();
        }
    };

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        if (cap == ForgeCapabilities.FLUID_HANDLER){
            Direction localDir = this.getBlockState().getValue(MecanicalElectrolyzerBlock.H_FACING);
            if (!(side == Direction.DOWN || side == Direction.UP || side == null)) {
                if (localDir == side.getOpposite()) {
                    return this.waterFluidOptional.cast();
                }
                if (localDir == side.getClockWise()) {
                    return this.hydrogenFluidOptional.cast();
                }
                if (localDir == side.getCounterClockWise()) {
                    return this.oxygenFluidOptional.cast();
                }
            }
        }
        return /*super.getCapability(cap, side)*/LazyOptional.empty();
    }


    public void tick(Level level, BlockPos pos, BlockState state, MecanicalElectrolyzerBlockEntity blockEntity) {

        if (!level.isClientSide()) {
            if (syncCooldown > 0) {
                syncCooldown--;
                if (syncCooldown == 0 && queuedSync)
                    sendData();
            }
            //setChanged();
            if (hasRecipe(blockEntity)) {
                float rot_speed = this.getSpeed();
                blockEntity.HYDROGEN_TANK.fill(new FluidStack(FluidInit.LIQUID_HYDROGEN.get(),hydrogenProduction(rot_speed)), IFluidHandler.FluidAction.EXECUTE);
                blockEntity.OXYGEN_TANK.fill(new FluidStack(FluidInit.LIQUID_OXYGEN.get(),oxygenProduction(rot_speed)), IFluidHandler.FluidAction.EXECUTE);
                blockEntity.WATER_TANK.drain(waterConsumption(rot_speed), IFluidHandler.FluidAction.EXECUTE);
            }
        }
    }
    private boolean hasRecipe(MecanicalElectrolyzerBlockEntity blockEntity) {
        float rot_speed = this.getSpeed();
        boolean isRunning = !blockEntity.isOverStressed();
        boolean enoughWater = blockEntity.WATER_TANK.getFluidAmount()>waterConsumption(rot_speed);
        boolean enoughSpaceInHTank = blockEntity.HYDROGEN_TANK.getSpace()>hydrogenProduction(rot_speed);
        boolean enoughSpaceInOTank = blockEntity.OXYGEN_TANK.getSpace()>oxygenProduction(rot_speed);

        return enoughWater && enoughSpaceInHTank && enoughSpaceInOTank && isRunning;
    }

    @Override
    protected void read(CompoundTag nbt, boolean clientPacket) {
        super.read(nbt, clientPacket);

        HYDROGEN_TANK.setFluid(new FluidStack(FluidInit.LIQUID_HYDROGEN.get(), nbt.getInt("hydrogenAmount")));
        OXYGEN_TANK.setFluid(new FluidStack(FluidInit.LIQUID_OXYGEN.get(), nbt.getInt("oxygenAmount")));
        WATER_TANK.setFluid(new FluidStack(Fluids.WATER.getSource(), nbt.getInt("waterAmount")));

    }

    @Override
    protected void write(CompoundTag nbt, boolean clientPacket) {
        nbt.putInt("oxygenAmount",OXYGEN_TANK.getFluidAmount());
        nbt.putInt("hydrogenAmount",HYDROGEN_TANK.getFluidAmount());
        nbt.putInt("waterAmount",WATER_TANK.getFluidAmount());
        super.write(nbt, clientPacket);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        LangBuilder mb = Lang.translate("generic.unit.millibuckets");
        LangBuilder mbs = Lang.translate("generic.unit.fluidflow");
        Lang.translate("gui.goggles.fluid_container")
                .forGoggles(tooltip);

        for (int i = 0; i <= 2; i++) {
            FluidTank tank = switch (i){
                case 0 -> WATER_TANK;
                case 1 -> OXYGEN_TANK;
                case 2 -> HYDROGEN_TANK;
                default -> throw new IllegalStateException("Unexpected value: " + i);
            };
            String fluidName = switch (i){
                case 0 -> Fluids.WATER.getSource().getFluidType().getDescriptionId();
                case 1 -> FluidInit.LIQUID_OXYGEN.getType().getDescriptionId();
                case 2 -> FluidInit.LIQUID_HYDROGEN.getType().getDescriptionId();
                default -> throw new IllegalStateException("Unexpected value: " + i);
            };

            FluidStack fluidStack = tank.getFluidInTank(0);

            Lang.builder().add(Component.translatable(fluidName))
                    .style(ChatFormatting.GRAY)
                    .forGoggles(tooltip, 1);

            Lang.builder()
                    .add(Lang.number(fluidStack.getAmount())
                            .add(mb)
                            .style(ChatFormatting.GOLD))
                    .text(ChatFormatting.GRAY, " / ")
                    .add(Lang.number(tank.getTankCapacity(0))
                            .add(mb)
                            .style(ChatFormatting.DARK_GRAY))
                    .forGoggles(tooltip, 1);
        }
        return super.addToGoggleTooltip(tooltip, isPlayerSneaking);
    }

    private int waterConsumption(float speed){
        return  (int) (abs(speed)/10);
    }

    private int oxygenProduction(float speed){
        return (int) (abs(speed)/10*0.88/FluidInit.LIQUID_OXYGEN.getType().getDensity()*1000);
    }

    private int hydrogenProduction(float speed){
        return (int) (abs(speed)/10*0.11/FluidInit.LIQUID_HYDROGEN.getType().getDensity()*1000);
    }


}
