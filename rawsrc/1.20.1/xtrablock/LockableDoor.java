package net.rk.thingamajigs.xtrablock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.rk.thingamajigs.item.ThingamajigsItems;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class LockableDoor extends DoorBlock {
    public BlockSetType type = new BlockSetType("lockable_door",
            false, SoundType.NETHERITE_BLOCK,
            SoundEvents.IRON_DOOR_CLOSE,SoundEvents.IRON_DOOR_OPEN,
            SoundEvents.IRON_TRAPDOOR_CLOSE,SoundEvents.IRON_TRAPDOOR_OPEN,
            SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF,SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON,
            SoundEvents.STONE_BUTTON_CLICK_OFF,SoundEvents.STONE_BUTTON_CLICK_ON);

    public static BooleanProperty LOCKED = BooleanProperty.create("locked");

    public LockableDoor(Properties p) {
        super(p.requiresCorrectToolForDrops().strength(75.0F,100F).noOcclusion().sound(SoundType.NETHERITE_BLOCK), BlockSetType.IRON);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false).setValue(HINGE, DoorHingeSide.LEFT).setValue(POWERED, false).setValue(HALF, DoubleBlockHalf.LOWER).setValue(LOCKED,false));
    }

    @Override
    public InteractionResult use(BlockState bs, Level lvl, BlockPos bp, Player ply, InteractionHand ih, BlockHitResult bhr) {
        ItemStack playerItem = ply.getItemInHand(ih);
        String halfName = lvl.getBlockState(bp).getValue(HALF).getSerializedName();

        if(Objects.equals(halfName,"lower")){
            if(playerItem.is(ThingamajigsItems.KEY.get())){
                bs = bs.cycle(LOCKED);
                lvl.setBlock(bp,bs,10);

                BlockState bs2 = lvl.getBlockState(bp.above());
                if(bs2.is(this)){
                    bs2 = bs2.cycle(LOCKED);
                    lvl.setBlock(bp.above(),bs2,10);
                }
                customPlaySound(bs,lvl,bp,true);
                ply.swing(ih);
                return InteractionResult.CONSUME;
            }
            else{
                if(!bs.getValue(LOCKED)){
                    bs = bs.cycle(OPEN);
                    lvl.setBlock(bp, bs, 10);
                    customPlaySound(bs,lvl,bp,false);
                    return InteractionResult.sidedSuccess(lvl.isClientSide);
                }
                else{
                    lvl.playSound(null,bp,SoundEvents.CHEST_LOCKED,SoundSource.BLOCKS,0.75F,1.0F);
                    ply.displayClientMessage(Component.translatable("block.thingamajigs.lockable_door.locked"),true);
                    return InteractionResult.PASS;
                }
            }
        }
        else{
            return InteractionResult.PASS;
        }
    }

    public void customPlaySound(BlockState bs, Level lvl, BlockPos bp1, boolean isForLock){
        if(isForLock){
            if(bs.getValue(LOCKED)){
                lvl.playSound(null,bp1,SoundEvents.ARMOR_EQUIP_CHAIN, SoundSource.BLOCKS,1.0F,1.0F);
            }
            else{
                lvl.playSound(null,bp1,SoundEvents.ARMOR_EQUIP_NETHERITE,SoundSource.BLOCKS,1.0F,1.0F);
            }
        }
        else{
            if(bs.getValue(OPEN)){
                lvl.playSound(null,bp1,SoundEvents.IRON_DOOR_CLOSE, SoundSource.BLOCKS,1.0F,1.0F);
            }
            else{
                lvl.playSound(null,bp1,SoundEvents.IRON_DOOR_OPEN,SoundSource.BLOCKS,1.0F,1.0F);
            }
        }
    }

    @Override
    public void neighborChanged(BlockState bs, Level lvl, BlockPos bp1, Block b1, BlockPos bp2, boolean bl1) {
        boolean flag = lvl.hasNeighborSignal(bp1) || lvl.hasNeighborSignal(bp1.relative(bs.getValue(HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN));
        if(!bs.getValue(LOCKED)){
            if (!this.defaultBlockState().is(b1) && flag != bs.getValue(POWERED)) {
                if (flag != bs.getValue(OPEN)) {
                    lvl.gameEvent((Entity)null, flag ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, bp1);
                }
                //
                if(bs.getValue(OPEN)){
                    lvl.playSound(null,bp1,SoundEvents.IRON_DOOR_CLOSE, SoundSource.BLOCKS,1.0F,1.0F);
                }
                else{
                    lvl.playSound(null,bp1,SoundEvents.IRON_DOOR_OPEN,SoundSource.BLOCKS,1.0F,1.0F);
                }
                lvl.setBlock(bp1, bs.setValue(POWERED, flag).setValue(OPEN, flag), 2);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_52803_) {
        super.createBlockStateDefinition(p_52803_);
        p_52803_.add(LOCKED);
    }

    // copied only to allow LOCKED property to pass through hinge check that original block requires to function
    private DoorHingeSide getHinge(BlockPlaceContext bpc1) {
        BlockGetter blockgetter = bpc1.getLevel();
        BlockPos blockpos = bpc1.getClickedPos();
        Direction direction = bpc1.getHorizontalDirection();
        BlockPos blockpos1 = blockpos.above();
        Direction direction1 = direction.getCounterClockWise();
        BlockPos blockpos2 = blockpos.relative(direction1);
        BlockState blockstate = blockgetter.getBlockState(blockpos2);
        BlockPos blockpos3 = blockpos1.relative(direction1);
        BlockState blockstate1 = blockgetter.getBlockState(blockpos3);
        Direction direction2 = direction.getClockWise();
        BlockPos blockpos4 = blockpos.relative(direction2);
        BlockState blockstate2 = blockgetter.getBlockState(blockpos4);
        BlockPos blockpos5 = blockpos1.relative(direction2);
        BlockState blockstate3 = blockgetter.getBlockState(blockpos5);
        int i = (blockstate.isCollisionShapeFullBlock(blockgetter, blockpos2) ? -1 : 0) + (blockstate1.isCollisionShapeFullBlock(blockgetter, blockpos3) ? -1 : 0) + (blockstate2.isCollisionShapeFullBlock(blockgetter, blockpos4) ? 1 : 0) + (blockstate3.isCollisionShapeFullBlock(blockgetter, blockpos5) ? 1 : 0);
        boolean flag = blockstate.is(this) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
        boolean flag1 = blockstate2.is(this) && blockstate2.getValue(HALF) == DoubleBlockHalf.LOWER;
        if ((!flag || flag1) && i <= 0) {
            if ((!flag1 || flag) && i >= 0) {
                int j = direction.getStepX();
                int k = direction.getStepZ();
                Vec3 vec3 = bpc1.getClickLocation();
                double d0 = vec3.x - (double)blockpos.getX();
                double d1 = vec3.z - (double)blockpos.getZ();
                return (j >= 0 || !(d1 < 0.5D)) && (j <= 0 || !(d1 > 0.5D)) && (k >= 0 || !(d0 > 0.5D)) && (k <= 0 || !(d0 < 0.5D)) ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
            } else {
                return DoorHingeSide.LEFT;
            }
        } else {
            return DoorHingeSide.RIGHT;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext bpc) {
        BlockPos blockpos = bpc.getClickedPos();
        Level level = bpc.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(bpc)) {
            boolean flag = level.hasNeighborSignal(blockpos) || level.hasNeighborSignal(blockpos.above());
            return this.defaultBlockState().setValue(FACING, bpc.getHorizontalDirection()).setValue(HINGE, this.getHinge(bpc)).setValue(POWERED, flag).setValue(OPEN, flag).setValue(HALF, DoubleBlockHalf.LOWER).setValue(LOCKED,false);
        } else {
            return null;
        }
    }

    @Override
    public void appendHoverText(ItemStack is, @org.jetbrains.annotations.Nullable BlockGetter p_49817_, List<Component> lc, TooltipFlag tf) {
        lc.add(Component.translatable("block.thingamajigs.lockable_door.desc"));
    }
}
