package com.rae.creatingspace.client.renderer.entity.Sandstorm;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.rae.creatingspace.client.renderer.entity.Sandstorm.Model.ModelSandstorm;
import com.rae.creatingspace.server.entities.EntitySandstormTail;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
public class RenderSandstormTail extends MobRenderer<EntitySandstormTail, AdvancedEntityModel<EntitySandstormTail>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("alexsmobs:textures/entity/cave_centipede.png");

    public RenderSandstormTail(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelSandstorm<>(2), 0.5F);
    }

    protected float getFlipDegrees(EntitySandstormTail centipede) {
        return 180.0F;
    }

    @Override
    protected void setupRotations(EntitySandstormTail entity, PoseStack stack, float pitchIn, float yawIn, float partialTickTime) {
        float newYaw = entity.yHeadRot;
        if (this.isShaking(entity)) {
            newYaw += (float) (Math.cos((double) entity.tickCount * 3.25D) * Math.PI * (double) 0.4F);
        }

        Pose pose = entity.getPose();
        if (pose != Pose.SLEEPING) {
            stack.mulPose(Axis.YP.rotationDegrees(180.0F - newYaw));
            stack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
        }

        if (entity.deathTime > 0) {
            float f = ((float) entity.deathTime + partialTickTime - 1.0F) / 20.0F * 1.6F;
            f = Mth.sqrt(f);
            if (f > 1.0F) {
                f = 1.0F;
            }

            stack.translate(0, f * 1.15F, 0);
            stack.mulPose(Axis.ZP.rotationDegrees(f * this.getFlipDegrees(entity)));
        } else if (entity.hasCustomName()) {
            String s = ChatFormatting.stripFormatting(entity.getName().getString());
            if (("Dinnerbone".equals(s) || "Grumm".equals(s))) {
                stack.translate(0.0D, (double) (entity.getBbHeight() + 0.1F), 0.0D);
                stack.mulPose(Axis.ZP.rotationDegrees(180.0F));
            }
        }
    }
    public ResourceLocation getTextureLocation(EntitySandstormTail entity) {
        return TEXTURE;
    }
}