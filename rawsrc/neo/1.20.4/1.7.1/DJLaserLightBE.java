package net.rk.thingamajigs.entity.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.rk.thingamajigs.block.DJLaserLight;

public class DJLaserLightBE extends BlockEntity {
    public int ticks;
    public boolean hidePose;
    public int height = 8;
    public int color = 0;
    public float angle = 90; // the angle for the beam to start at
    public float verticalAngle = 90;
    public int vertOscAngle = 45;
    public int dOfMotMultiVert = 16;

    public int oscAngle = 45; // the osc angle for the beam to osc towards back and forth (total distance in degrees)
    public float[] diffuseCol = DyeColor.BLUE.getTextureDiffuseColors();
    public int degreeOfMotionMultiplier = 16; // 16 is about 2.5 blocks distance for the osc effect

    public int lightMode = 0; // 0 is custom
    public float laserSize = 0.25f;

    public boolean useCustomColor = false;
    public float red = 1.0f;
    public float green = 1.0f;
    public float blue = 1.0f;
    public float[] customColor = new float[]{1.0f,1.0f,1.0f}; // temp storage, not saved to world, used for colors
    public boolean randomlyGenerateColor = false;


    public DJLaserLightBE(BlockPos pPos, BlockState pBlockState) {
        super(TBlockEntities.DJ_LASER_LIGHT_BE.get(), pPos, pBlockState);
    }

    public void generateRandomColor(){
        RandomSource rs = this.getLevel().getRandom();
        float red = rs.nextFloat();
        float green = rs.nextFloat();
        float blue = rs.nextFloat();
        this.customColor = new float[]{red,green,blue};
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket(){return ClientboundBlockEntityDataPacket.create(this);}

    public static void clientTick(Level lvl, BlockPos bp, BlockState bs, DJLaserLightBE be){
        ++be.ticks;
        if(!be.getLevel().hasNearbyAlivePlayer((double)bp.getX() + 0.5, (double)bp.getY() + 0.5, (double)bp.getZ() + 0.5, 32)){
            be.hidePose = true;
        }
        else{
            be.hidePose = !bs.getValue(DJLaserLight.ON);
            if(be.useCustomColor){
                if(be.randomlyGenerateColor){
                    if(be.getLevel().getRandom().nextInt(40) == 0){
                        be.generateRandomColor();
                    }
                }
                else{
                    be.customColor[0] = be.red;
                    be.customColor[1] = be.green;
                    be.customColor[2] = be.blue;
                }
                be.diffuseCol = be.customColor;
            }
            else{
                if(be.color == 0){
                    be.diffuseCol = DyeColor.WHITE.getTextureDiffuseColors();
                }
                else if(be.color == 1){
                    be.diffuseCol = DyeColor.LIGHT_GRAY.getTextureDiffuseColors();
                }
                else if(be.color == 2){
                    be.diffuseCol = DyeColor.GRAY.getTextureDiffuseColors();
                }
                else if(be.color == 3){
                    be.diffuseCol = DyeColor.BLACK.getTextureDiffuseColors();
                }
                else if(be.color == 4){
                    be.diffuseCol = DyeColor.BROWN.getTextureDiffuseColors();
                }
                else if(be.color == 5){
                    be.diffuseCol = DyeColor.RED.getTextureDiffuseColors();
                }
                else if(be.color == 6){
                    be.diffuseCol = DyeColor.ORANGE.getTextureDiffuseColors();
                }
                else if(be.color == 7){
                    be.diffuseCol = DyeColor.YELLOW.getTextureDiffuseColors();
                }
                else if(be.color == 8){
                    be.diffuseCol = DyeColor.LIME.getTextureDiffuseColors();
                }
                else if(be.color == 9){
                    be.diffuseCol = DyeColor.GREEN.getTextureDiffuseColors();
                }
                else if(be.color == 10){
                    be.diffuseCol = DyeColor.CYAN.getTextureDiffuseColors();
                }
                else if(be.color == 11){
                    be.diffuseCol = DyeColor.LIGHT_BLUE.getTextureDiffuseColors();
                }
                else if(be.color == 12){
                    be.diffuseCol = DyeColor.BLUE.getTextureDiffuseColors();
                }
                else if(be.color == 13){
                    be.diffuseCol = DyeColor.PURPLE.getTextureDiffuseColors();
                }
                else if(be.color == 14){
                    be.diffuseCol = DyeColor.MAGENTA.getTextureDiffuseColors();
                }
                else if(be.color == 15){
                    be.diffuseCol = DyeColor.PINK.getTextureDiffuseColors();
                }
                else{
                    be.diffuseCol = DyeColor.WHITE.getTextureDiffuseColors();
                }
            }

            switch(bs.getValue(DJLaserLight.FACING)){
                case NORTH ->{
                    be.angle = 0;
                    return;
                }
                case SOUTH ->{
                    be.angle = 180;
                    return;
                }
                case EAST ->{
                    be.angle = 90;
                    return;
                }
                case WEST ->{
                    be.angle = 270;
                    return;
                }
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putFloat("vert_angle",verticalAngle);
        pTag.putInt("color",color);
        pTag.putInt("multiplier",degreeOfMotionMultiplier);
        pTag.putInt("height",height);
        pTag.putInt("osc_angle",oscAngle);
        pTag.putInt("light_mode",lightMode);
        pTag.putFloat("laser_size",laserSize);
        pTag.putBoolean("use_custom_color",useCustomColor);
        pTag.putFloat("red",red);
        pTag.putFloat("green",green);
        pTag.putFloat("blue",blue);
        pTag.putBoolean("random_colors",randomlyGenerateColor);
        pTag.putInt("vert_osc_angle",vertOscAngle);
        pTag.putInt("vert_multiplier",dOfMotMultiVert);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        verticalAngle = pTag.getFloat("vert_angle");
        color = pTag.getInt("color");
        degreeOfMotionMultiplier = pTag.getInt("multiplier");
        height = pTag.getInt("height");
        oscAngle = pTag.getInt("osc_angle");
        lightMode = pTag.getInt("light_mode");
        laserSize = pTag.getFloat("laser_size");
        useCustomColor = pTag.getBoolean("use_custom_color");
        red = pTag.getFloat("red");
        green = pTag.getFloat("green");
        blue = pTag.getFloat("blue");
        randomlyGenerateColor = pTag.getBoolean("random_colors");
        vertOscAngle = pTag.getInt("vert_osc_angle");
        dOfMotMultiVert = pTag.getInt("vert_multiplier");
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.load(tag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag ct = new CompoundTag();
        saveAdditional(ct);
        return ct;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
    }

    @Override
    public void clearRemoved() {
        super.clearRemoved();
    }

    public void updateBlock(){
        this.setChanged();
        if(this.getLevel() != null) {
            BlockState bs2 = this.getLevel().getBlockState(this.getBlockPos());
            this.getLevel().sendBlockUpdated(this.getBlockPos(), bs2, bs2, 3);
        }
    }

    public static void serverTick(Level slvl, BlockPos sbp, BlockState sbs, DJLaserLightBE sbe){
        ++sbe.ticks;
    }
}
