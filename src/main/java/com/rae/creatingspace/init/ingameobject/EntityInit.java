package com.rae.creatingspace.init.ingameobject;

import com.rae.creatingspace.CreatingSpace;
import com.rae.creatingspace.client.renderer.entity.RocketContraptionEntityRenderer;
import com.rae.creatingspace.server.entities.EntitySandstormBody;
import com.rae.creatingspace.server.entities.EntitySandstormHead;
import com.rae.creatingspace.server.entities.EntitySandstormTail;
import com.rae.creatingspace.server.entities.RocketContraptionEntity;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.foundation.data.CreateEntityBuilder;
import com.simibubi.create.foundation.utility.Lang;
import com.tterrag.registrate.util.entry.EntityEntry;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {

    public static final DeferredRegister<EntityType<?>> DEF_REG = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CreatingSpace.MODID);

    public static final RegistryObject<EntityType<EntitySandstormHead>> SANDSTORM_HEAD = DEF_REG.register("sandstorm_head", () -> registerEntity(EntityType.Builder.of(EntitySandstormHead::new, MobCategory.MONSTER).sized(0.9F, 0.9F), "sandstorm_head"));
    public static final RegistryObject<EntityType<EntitySandstormBody>> SANDSTORM_BODY = DEF_REG.register("sandstorm_body", () -> registerEntity(EntityType.Builder.of(EntitySandstormBody::new, MobCategory.MISC).sized(0.9F, 0.9F).fireImmune().setShouldReceiveVelocityUpdates(true).setUpdateInterval(1), "sandstorm_body"));
    public static final RegistryObject<EntityType<EntitySandstormTail>> SANDSTORM_TAIL = DEF_REG.register("sandstorm_tail", () -> registerEntity(EntityType.Builder.of(EntitySandstormTail::new, MobCategory.MISC).sized(0.9F, 0.9F).fireImmune().setShouldReceiveVelocityUpdates(true).setUpdateInterval(1), "sandstorm_tail"));

    public static final EntityEntry<RocketContraptionEntity> ROCKET_CONTRAPTION =
            contraption("rocket_contraption", RocketContraptionEntity::new,
                    () -> RocketContraptionEntityRenderer::new, 15, 1, true)
                    .register();
    private static final EntityType registerEntity(EntityType.Builder builder, String entityName) {
        return (EntityType) builder.build(entityName);
    }

    private static <T extends Entity> CreateEntityBuilder<T, ?> contraption(String name, EntityType.EntityFactory<T> factory,
                                                                            NonNullSupplier<NonNullFunction<EntityRendererProvider.Context, EntityRenderer<? super T>>> renderer, int range,
                                                                            int updateFrequency, boolean sendVelocity) {
        return register(name, factory, renderer, MobCategory.MISC, range, updateFrequency, sendVelocity, true,
                AbstractContraptionEntity::build);
    }

    private static <T extends Entity> CreateEntityBuilder<T, ?> register(String name, EntityType.EntityFactory<T> factory,
                                                                         NonNullSupplier<NonNullFunction<EntityRendererProvider.Context, EntityRenderer<? super T>>> renderer,
                                                                         MobCategory group, int range, int updateFrequency, boolean sendVelocity, boolean immuneToFire,
                                                                         NonNullConsumer<EntityType.Builder<T>> propertyBuilder) {
        String id = Lang.asId(name);
        return (CreateEntityBuilder<T, ?>) CreatingSpace.REGISTRATE
                .entity(id, factory, group)
                .properties(b -> b.setTrackingRange(range)
                        .setUpdateInterval(updateFrequency)
                        .setShouldReceiveVelocityUpdates(sendVelocity))
                .properties(propertyBuilder)
                .properties(b -> {
                    if (immuneToFire)
                        b.fireImmune();
                })
                .renderer(renderer);
    }

    public static void register() {}

}