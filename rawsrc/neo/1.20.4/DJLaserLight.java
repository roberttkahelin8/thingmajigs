package net.rk.thingamajigs.block;

import com.mojang.serialization.MapCodec;
import io.netty.buffer.Unpooled;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.rk.thingamajigs.TBlockStateProperties;
import net.rk.thingamajigs.entity.blockentity.DJLaserLightBE;
import net.rk.thingamajigs.entity.blockentity.TBlockEntities;
import net.rk.thingamajigs.gui.DJLaserLightMenu;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.logging.Logger;

@SuppressWarnings("deprecated,unused")
public class DJLaserLight extends BaseEntityBlock {
    public static final MapCodec<DJLaserLight> DJ_LASER_LIGHT_MAP_CODEC = simpleCodec(DJLaserLight::new);

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty ON = TBlockStateProperties.ON;
    public DJLaserLight(Properties p) {
        super(p.strength(1f,5f).sound(SoundType.LANTERN).noOcclusion());
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
                .setValue(ON,true));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return DJ_LASER_LIGHT_MAP_CODEC;
    }


    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new DJLaserLightBE(pPos,pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level lvl, BlockState bs, BlockEntityType<T> bet) {
        return createTickerHelper(bet, TBlockEntities.DJ_LASER_LIGHT_BE.get(),
                lvl.isClientSide ? DJLaserLightBE::clientTick : DJLaserLightBE::serverTick);
    }

    // interact and change the laser stuff
    @Override
    public InteractionResult use(BlockState bs, Level lvl, BlockPos bp, Player ply, InteractionHand h, BlockHitResult bhr) {
        super.use(bs,lvl,bp,ply,h,bhr);
        //return changeBE(lvl,bp,ply,h);
        try{
            if(ply instanceof ServerPlayer){
                ply.openMenu(new MenuProvider(){
                    @Override
                    public Component getDisplayName() {
                        return Component.translatable("container.thingamajigs.laser_light.title")
                                .withStyle(ChatFormatting.WHITE);
                    }
                    @Override
                    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                        return new DJLaserLightMenu(id, inventory,
                                new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(bp));
                    }
                },bp);
                return InteractionResult.sidedSuccess(lvl.isClientSide);
            }
        }
        catch (Exception e){
            Logger.getAnonymousLogger().warning("Encountered an exception. Error is: " + e.getMessage());
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean isValidSpawn(BlockState state, BlockGetter level, BlockPos pos, SpawnPlacements.Type type, EntityType<?> entityType) {
        return false;
    }

    @Override
    public boolean shouldDisplayFluidOverlay(BlockState state, BlockAndTintGetter world, BlockPos pos, FluidState fluidstate) {
        return state.getValue(WATERLOGGED);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING,WATERLOGGED,ON);
    }

    @Override
    public FluidState getFluidState(BlockState bs) {
        return bs.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(bs);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER)
                .setValue(ON,true);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("block.thingamajigs.laser_light.desc")
                .withStyle(ChatFormatting.GRAY));
    }

    private InteractionResult changeBE(Level lvl, BlockPos bp, Player ply, InteractionHand h){
        if(lvl.getBlockEntity(bp) instanceof DJLaserLightBE){
            DJLaserLightBE be = (DJLaserLightBE)lvl.getBlockEntity(bp);
            // vert ang controls
            boolean hasIron = ply.getItemInHand(h).is(Items.IRON_INGOT);
            boolean hasGold = ply.getItemInHand(h).is(Items.GOLD_INGOT);

            // height controls
            boolean hasDiamond = ply.getItemInHand(h).is(Items.DIAMOND);
            boolean hasEmerald = ply.getItemInHand(h).is(Items.EMERALD);

            // size controls
            boolean hasFlint = ply.getItemInHand(h).is(Items.FLINT);
            boolean hasPaper = ply.getItemInHand(h).is(Items.PAPER);

            // colors
            boolean hasWhiteDye = ply.getItemInHand(h).is(Items.WHITE_DYE);
            boolean hasLightGrayDye = ply.getItemInHand(h).is(Items.LIGHT_GRAY_DYE);
            boolean hasGrayDye = ply.getItemInHand(h).is(Items.GRAY_DYE);
            boolean hasBlackDye = ply.getItemInHand(h).is(Items.BLACK_DYE);
            boolean hasBrownDye = ply.getItemInHand(h).is(Items.BROWN_DYE);
            boolean hasRedDye = ply.getItemInHand(h).is(Items.RED_DYE);
            boolean hasOrangeDye = ply.getItemInHand(h).is(Items.ORANGE_DYE);
            boolean hasYellowDye = ply.getItemInHand(h).is(Items.YELLOW_DYE);
            boolean hasLimeDye = ply.getItemInHand(h).is(Items.LIME_DYE);
            boolean hasGreenDye = ply.getItemInHand(h).is(Items.GREEN_DYE);
            boolean hasCyanDye = ply.getItemInHand(h).is(Items.CYAN_DYE);
            boolean hasLightBlueDye = ply.getItemInHand(h).is(Items.LIGHT_BLUE_DYE);
            boolean hasBlueDye = ply.getItemInHand(h).is(Items.BLUE_DYE);
            boolean hasPurpleDye = ply.getItemInHand(h).is(Items.PURPLE_DYE);
            boolean hasMagentaDye = ply.getItemInHand(h).is(Items.MAGENTA_DYE);
            boolean hasPinkDye = ply.getItemInHand(h).is(Items.PINK_DYE);

            boolean hasStick = ply.getItemInHand(h).is(Items.STICK);
            boolean hasBlazeRod = ply.getItemInHand(h).is(Items.BLAZE_ROD);

            boolean finishedAny = false;

            if(be != null){
                if(hasIron){
                    be.verticalAngle += 1;
                    finishedAny = true;
                }
                else if(hasGold){
                    be.verticalAngle -= 1;
                    finishedAny = true;
                }

                if(hasDiamond){
                    be.height += 1;
                    finishedAny = true;
                }
                else if(hasEmerald){
                    be.height -= 1;
                    finishedAny = true;
                }

                if(hasStick){
                    be.useCustomColor = true;
                    be.randomlyGenerateColor = true;
                    finishedAny = true;
                }
                else if(hasBlazeRod){
                    be.useCustomColor = false;
                    be.randomlyGenerateColor = false;
                    finishedAny = true;
                }

                if(hasFlint){
                    be.laserSize += 0.1;
                    finishedAny = true;
                }
                else if(hasPaper){
                    be.laserSize -= 0.1;
                    finishedAny = true;
                }

                if(ply.getItemInHand(h).is(Items.COAL)){
                    be.useCustomColor = true;
                    finishedAny = true;
                }
                else if(ply.getItemInHand(h).is(Items.CHARCOAL)){
                    be.useCustomColor = false;
                    finishedAny = true;
                }

                if(be.useCustomColor){
                    if(hasRedDye){
                        if(be.red + 0.1f > 1.0f){
                            be.red = 0.0f;
                        }
                        else{
                            be.red += 0.1f;
                        }
                        finishedAny = true;
                    }
                    if(hasGreenDye){
                        if(be.green + 0.1f > 1.0f){
                            be.green = 0.0f;
                        }
                        else{
                            be.green += 0.1f;
                        }
                        finishedAny = true;
                    }
                    if(hasBlueDye){
                        if(be.blue + 0.1f > 1.0f){
                            be.blue = 0.0f;
                        }
                        else{
                            be.blue += 0.1f;
                        }
                        finishedAny = true;
                    }
                }
                else{
                    if(hasWhiteDye){
                        be.color = 0;
                        finishedAny = true;
                    }
                    else if(hasLightGrayDye){
                        be.color = 1;
                        finishedAny = true;
                    }
                    else if(hasGrayDye){
                        be.color = 2;
                        finishedAny = true;
                    }
                    else if(hasBlackDye){
                        be.color = 3;
                        finishedAny = true;
                    }
                    else if(hasBrownDye){
                        be.color = 4;
                        finishedAny = true;
                    }
                    else if(hasRedDye){
                        be.color = 5;
                        finishedAny = true;
                    }
                    else if(hasOrangeDye){
                        be.color = 6;
                    }
                    else if(hasYellowDye){
                        be.color = 7;
                        finishedAny = true;
                    }
                    else if(hasLimeDye){
                        be.color = 8;
                        finishedAny = true;
                    }
                    else if(hasGreenDye){
                        be.color = 9;
                        finishedAny = true;
                    }
                    else if(hasCyanDye){
                        be.color = 10;
                        finishedAny = true;
                    }
                    else if(hasLightBlueDye){
                        be.color = 11;
                        finishedAny = true;
                    }
                    else if(hasBlueDye){
                        be.color = 12;
                        finishedAny = true;
                    }
                    else if(hasPurpleDye){
                        be.color = 13;
                        finishedAny = true;
                    }
                    else if(hasMagentaDye){
                        be.color = 14;
                        finishedAny = true;
                    }
                    else if(hasPinkDye){
                        be.color = 15;
                        finishedAny = true;
                    }
                }

                //
                if(finishedAny){
                    be.updateBlock();
                    lvl.playLocalSound(bp, SoundEvents.ITEM_FRAME_ROTATE_ITEM, SoundSource.BLOCKS,1.0f,1.0f,true);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }
}
