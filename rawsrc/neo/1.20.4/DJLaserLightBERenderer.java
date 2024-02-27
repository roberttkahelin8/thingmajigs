package net.rk.thingamajigs.entity.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.rk.thingamajigs.entity.blockentity.DJLaserLightBE;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@SuppressWarnings("deprecated,unused")
public class DJLaserLightBERenderer implements BlockEntityRenderer<DJLaserLightBE> {
    public DJLaserLightBERenderer(BlockEntityRendererProvider.Context berpContext) {}
    private static final ResourceLocation BEAM_LOCATION = new ResourceLocation("textures/block/white_concrete_powder.png");

    @Override
    public void render(DJLaserLightBE be, float ptick, PoseStack pose, MultiBufferSource mbs, int light, int overlay) {
        long j = be.getLevel().getGameTime();
        int domm = be.degreeOfMotionMultiplier;

        pose.pushPose();
        if(!be.hidePose){
            extendedRenderBeaconBeam(pose, mbs, BEAM_LOCATION, ptick, 1, j, 0,
                    be.height, be.diffuseCol, be.laserSize, be.angle, be.ticks, domm, be.verticalAngle, be.oscAngle,be.getBlockPos(),
            be.vertOscAngle,be.dOfMotMultiVert);
        }
        pose.translate(be.getBlockPos().getX(),be.getBlockPos().getY(),be.getBlockPos().getZ());
        pose.popPose();
    }

    // we don't need the glowing outer beam piece, so it was removed
    public void extendedRenderBeaconBeam(PoseStack ps, MultiBufferSource pBufferSource, ResourceLocation pBeamLocation,
                                         float pPartialTick, float pTextureScale,
                                         long pGameTime, int pYOffset, int pHeight,
                                         float[] pColors, float pBeamRadius,
                                         float beAngle, int beTicks, int dmulti, float va, int oscangle, BlockPos bp,
    int beVertOscAngle, int beVertMultiplier){
        int i = pYOffset + pHeight;

        ps.pushPose();

        ps.translate(0.5, 0.5, 0.5);
        float f = (float)Math.floorMod(pGameTime, 40) + pPartialTick;
        float f1 = pHeight < 0 ? f : -f;
        float f2 = Mth.frac(f1 * 0.2F - (float)Mth.floor(f1 * 0.1F));

        float f3 = pColors[0];
        float f4 = pColors[1];
        float f5 = pColors[2];

        float ftick = (float)beTicks;

        beAngle = beAngle + 180;

        // math to turn and osc the beacon beam horizontally
        ps.mulPose(Axis.YP.rotationDegrees((Mth.sin(oscangle * (ftick / 360)) * dmulti) - beAngle));

        // math to turn and osc the beacon beam vertically
        //va
        ps.mulPose(Axis.XP.rotationDegrees((Mth.sin(beVertOscAngle * (ftick / 360)) * beVertMultiplier) - va));

        // perhaps multi-directional lasers independent of block dir?
        //ps.mulPose(Axis.ZP.rotationDegrees((Mth.sin(zoscangle * (ftick / 360)) * zdmulti) - bez));

        float f16 = (float)pHeight * pTextureScale * (0.5F / pBeamRadius) + -1.0F + f2;
        renderBeamPart(ps, pBufferSource.getBuffer(RenderType.energySwirl(pBeamLocation,2.0f,2.0f)), f3, f4, f5,
                0.15F, pYOffset, i,
                0.0F, pBeamRadius, pBeamRadius,
                0.0F, -pBeamRadius, 0.0F, 0.0F, -pBeamRadius, 0.0F, 1.0F, f16, -1.0F + f2
        );
        ps.popPose();
    }

    // from beacon
    public static void renderBeamPart(PoseStack pPoseStack, VertexConsumer vCon,
            float pRed, float pGreen, float pBlue, float pAlpha,
                                  int pMinY, int pMaxY,
                                  float pX0, float pZ0, float pX1, float pZ1, float pX2, float pZ2, float pX3, float pZ3,
            float pMinU, float pMaxU, float pMinV, float pMaxV
    ){
        PoseStack.Pose poseStackPoseLast = pPoseStack.last();
        Matrix4f m4f = poseStackPoseLast.pose();
        Matrix3f m3f = poseStackPoseLast.normal();

        // 4 parts
        quad(
                m4f, m3f, vCon,
                pRed, pGreen, pBlue, pAlpha, pMinY, pMaxY,
                pX0, pZ0, pX1, pZ1,
                pMinU, pMaxU, pMinV, pMaxV
        );
        quad(
                m4f, m3f, vCon,
                pRed, pGreen, pBlue, pAlpha, pMinY, pMaxY,
                pX3, pZ3, pX2, pZ2,
                pMinU, pMaxU, pMinV, pMaxV
        );
        quad(
                m4f, m3f, vCon,
                pRed, pGreen, pBlue, pAlpha, pMinY, pMaxY,
                pX1, pZ1, pX3, pZ3,
                pMinU, pMaxU, pMinV, pMaxV
        );
        quad(
                m4f, m3f, vCon, pRed, pGreen, pBlue, pAlpha, pMinY, pMaxY,
                pX2, pZ2, pX0, pZ0,
                pMinU, pMaxU, pMinV, pMaxV
        );
    }

    public static void quad(
            Matrix4f pPose,
            Matrix3f pNormal,
            VertexConsumer pConsumer,
            float pRed,
            float pGreen,
            float pBlue,
            float pAlpha,
            int pMinY,
            int pMaxY,
            float pMinX,
            float pMinZ,
            float pMaxX,
            float pMaxZ,
            float pMinU,
            float pMaxU,
            float pMinV,
            float pMaxV
    ) {
        vertex(pPose, pNormal, pConsumer, pRed, pGreen, pBlue, pAlpha, pMaxY, pMinX, pMinZ, pMaxU, pMinV);
        vertex(pPose, pNormal, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMinX, pMinZ, pMaxU, pMaxV);
        vertex(pPose, pNormal, pConsumer, pRed, pGreen, pBlue, pAlpha, pMinY, pMaxX, pMaxZ, pMinU, pMaxV);
        vertex(pPose, pNormal, pConsumer, pRed, pGreen, pBlue, pAlpha, pMaxY, pMaxX, pMaxZ, pMinU, pMinV);
    }

    public static void vertex(
            Matrix4f pPose,
            Matrix3f pNormal,
            VertexConsumer pConsumer, float pRed, float pGreen, float pBlue, float pAlpha,
            int pY, float pX, float pZ, float pU, float pV
    ) {
        pConsumer.vertex(pPose, pX, (float)pY, pZ)
                .color(pRed, pGreen, pBlue, pAlpha)
                .uv(pU, pV)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(15728880)
                .normal(pNormal, 0.0F, 1.0F, 0.0F)
                .endVertex();
    }

    @Override
    public int getViewDistance() {
        return 128;
    }

    @Override
    public boolean shouldRenderOffScreen(DJLaserLightBE pBlockEntity) {
        return true;
    }

    public boolean shouldRender(BeaconBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return Vec3.atCenterOf(pBlockEntity.getBlockPos()).multiply(1.0, 0.0, 1.0)
                .closerThan(pCameraPos.multiply(1.0, 0.0, 1.0), (double)this.getViewDistance());
    }

    @Override
    public AABB getRenderBoundingBox(DJLaserLightBE be) {
        BlockPos bp = be.getBlockPos();
        return new AABB(bp.getX() - (float)be.height,bp.getY() - getViewDistance(),bp.getZ() - (float)be.height,bp.getX() + (float)be.height,bp.getY() + getViewDistance(),bp.getZ() + (float)be.height);
    }
}
