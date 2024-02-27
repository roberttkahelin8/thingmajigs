package net.rk.thingamajigs.network.handler;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.rk.thingamajigs.entity.blockentity.DJLaserLightBE;
import net.rk.thingamajigs.network.data.DJLaserLightUpdatePayload;

import java.util.Optional;
import java.util.logging.Logger;

public class DJLaserLightUpdatePacket {
    public static final DJLaserLightUpdatePacket INSTANCE = new DJLaserLightUpdatePacket();

    public static DJLaserLightUpdatePacket get(){
        return INSTANCE;
    }

    public void handle(final DJLaserLightUpdatePayload payload, final PlayPayloadContext context) {
        context.workHandler().submitAsync(
                () -> {
                    Optional<Player> ply = context.player();
                    if(ply.isEmpty()){
                        return;
                    }
                    else{
                        Player sendply = ply.get();
                        Level lvl = sendply.level();
                        if(!lvl.hasChunkAt(payload.bp())){
                            return;
                        }
                        BlockState bs = lvl.getBlockState(payload.bp());
                        DJLaserLightBE be = (DJLaserLightBE) lvl.getBlockEntity(payload.bp());
                        try{
                            if(lvl != null){
                                if(be != null){
                                    be.red = payload.r();
                                    be.green = payload.g();
                                    be.blue = payload.b();
                                    be.useCustomColor = payload.customcolors();
                                    be.randomlyGenerateColor = payload.randomcolors();
                                    be.height = payload.height();
                                    be.laserSize = payload.lasersize();
                                    be.angle = payload.angle();
                                    be.verticalAngle = payload.vertangle();
                                    be.oscAngle = payload.oscangle();
                                    be.degreeOfMotionMultiplier = payload.mm();
                                    be.lightMode = payload.lightmode();
                                    be.color = payload.color();
                                    be.vertOscAngle = payload.voscangle();
                                    be.dOfMotMultiVert = payload.vmm();
                                    be.updateBlock();
                                }
                            }
                        }
                        catch (Exception e){
                            Logger.getAnonymousLogger()
                                    .warning("Exception regarding DJ Laser Light Update Packet. Error: " + e.getMessage());
                        }
                    }
                }
        );
    }
}
