package com.rea.creatingspace.init.ingameobject;

import com.rea.creatingspace.client.renderer.FlowGaugeBlockRenderer;
import com.rea.creatingspace.client.renderer.MechanicalElectrolyserBlockRenderer;
import com.rea.creatingspace.client.renderer.RocketStarterBlockRenderer;
import com.rea.creatingspace.server.blockentities.*;
import com.simibubi.create.content.kinetics.base.ShaftInstance;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import static com.rea.creatingspace.CreatingSpace.REGISTRATE;

public class BlockEntityInit {
    public static final BlockEntityEntry<RocketControlsBlockEntity> CONTROLS =
            REGISTRATE.blockEntity("controls", RocketControlsBlockEntity::new)
            .validBlocks(BlockInit.ROCKET_CONTROLS)
            .register();

    /*public static final BlockEntityEntry<GroundBuilderBlockEntity> GROUND_STATION =
            REGISTRATE.blockEntity("station", GroundBuilderBlockEntity::new)
                    .validBlocks(BlockInit.GROUND_STATION)
                    .register();*/

    public static final BlockEntityEntry<RocketMotorBlockEntity> ROCKET_MOTOR =
            REGISTRATE.blockEntity("rocket_motor", RocketMotorBlockEntity::new )
                    .instance(() -> ShaftInstance::new, false)
                    .validBlocks(BlockInit.ROCKET_MOTOR)
                    .renderer(() -> RocketStarterBlockRenderer::new)
                    .register();

    public static final BlockEntityEntry<ChemicalSynthesizerBlockEntity> SYNTHESIZER =
            REGISTRATE.blockEntity("synthesizer", ChemicalSynthesizerBlockEntity::new)
                    .validBlocks(BlockInit.CHEMICAL_SYNTHESIZER)
                    .register();

    public static final BlockEntityEntry<RocketEngineBlockEntity.BigEngine> BIG_ENGINE =
            REGISTRATE.blockEntity(
                    "big_engine", RocketEngineBlockEntity.BigEngine::new)
                    .validBlocks(BlockInit.BIG_ROCKET_ENGINE)
                    .register();

    public static final BlockEntityEntry<RocketEngineBlockEntity.SmallEngine> SMALL_ENGINE =
            REGISTRATE.blockEntity(
                            "small_engine", RocketEngineBlockEntity.SmallEngine::new)
                    .validBlocks(BlockInit.SMALL_ROCKET_ENGINE)
                    .register();

    public static final BlockEntityEntry<MechanicalElectrolyzerBlockEntity> ELECTROLIZER =
            REGISTRATE.blockEntity(
                    "electrolizer", MechanicalElectrolyzerBlockEntity::new)
                    .instance(()-> ShaftInstance::new)
                    .validBlocks( BlockInit.MECHANICAL_ELECTROLYZER)
                    .renderer(()-> MechanicalElectrolyserBlockRenderer::new)
                    .register();

    public static final BlockEntityEntry<FlowGaugeBlockEntity> FLOW_METER =
            REGISTRATE.blockEntity(
                            "flow_meter", FlowGaugeBlockEntity::new)
                    .validBlocks( BlockInit.FLOW_METER)
                    .renderer(()-> FlowGaugeBlockRenderer::new)
                    .register();

    /*public static final BlockEntityEntry<IOBlockEntity> IO_TILE = REGISTRATE
            .blockEntity("io", IOBlockEntity::new)
            .validBlocks(BlockInit.IO_BLOCK)
            .register();
    public static final BlockEntityEntry<MultiblockBlockEntity> GHOST_TILE = REGISTRATE
            .blockEntity("multiblock", MultiblockBlockEntity::new)
            .validBlocks(GHOST_BLOCK)
            .register();
    public static final BlockEntityEntry<KineticInputBlockEntity> KINETIC_INPUT_TILE = REGISTRATE
            .blockEntity("kinetic_input", KineticInputBlockEntity::new)
            .instance(() -> KineticInputInstance::new)
            .validBlocks(KINETIC_INPUT)
            .renderer(() -> KineticInputBlockEntityRenderer::new)
            .register();*/

    public static void register() {}
}
