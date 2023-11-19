package com.rae.creatingspace.server.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.level.Level;

public class EntitySandstormTail extends EntitySandstormBody {

    public EntitySandstormTail(EntityType type, Level worldIn) {
        super(type, worldIn);
    }

    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

}