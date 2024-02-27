package net.rk.thingamajigs.network.data;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.rk.thingamajigs.Thingamajigs;

public record DJLaserLightUpdatePayload(BlockPos bp,boolean customcolors,boolean randomcolors,
    int height,int color,float angle,float vertangle,int oscangle,int mm,float lasersize,
                                        float r, float g, float b, int lightmode, int voscangle,int vmm) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(Thingamajigs.MODID, "laser_light_update");

    public DJLaserLightUpdatePayload(final FriendlyByteBuf buf){
        this(buf.readBlockPos(),
                buf.readBoolean(),
                buf.readBoolean(),
                buf.readInt(),
                buf.readInt(),
                buf.readFloat(),
                buf.readFloat(),
                buf.readInt(),
                buf.readInt(),
                buf.readFloat(),
                buf.readFloat(),
                buf.readFloat(),
                buf.readFloat(),
                buf.readInt(),
                buf.readInt(),
                buf.readInt()
        );
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(bp());

        buf.writeBoolean(customcolors());
        buf.writeBoolean(randomcolors());

        buf.writeInt(height());
        buf.writeInt(color());

        buf.writeFloat(angle());
        buf.writeFloat(vertangle());
        buf.writeInt(oscangle());

        buf.writeInt(mm());
        buf.writeFloat(lasersize());

        buf.writeFloat(r());
        buf.writeFloat(g());
        buf.writeFloat(b());

        buf.writeInt(lightmode());

        buf.writeInt(voscangle());
        buf.writeInt(vmm());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
