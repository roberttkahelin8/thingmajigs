package net.rk.thingamajigs.xtrablock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.ticks.TickPriority;
import net.rk.thingamajigs.block.ThingamajigsBlocks;
import net.rk.thingamajigs.block.custom.Sign;
import org.jetbrains.annotations.Nullable;

public class CrossbuckRedstone extends Sign {
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");

    public CrossbuckRedstone(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(POWERED,false));
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }

    @Override
    public void neighborChanged(BlockState bs, Level lvl, BlockPos bp, Block blk, BlockPos bp2, boolean p_55671_) {
        BlockState similarBlock = ThingamajigsBlocks.VERTICAL_POLE_REDSTONE.get().defaultBlockState();
        BlockState similarBlock2 = ThingamajigsBlocks.RAILROAD_CROSSING_LIGHTS.get().defaultBlockState();
        Block sbBlock = similarBlock.getBlock();
        Block sbBlock2 = similarBlock2.getBlock();

        if (!lvl.isClientSide) {
            if(lvl.getBlockState(bp.below()).is(sbBlock)){
                if(lvl.getBlockState(bp.below()).getValue(POWERED) == true){
                    lvl.setBlock(bp,bs.setValue(POWERED,true),3);
                }
                else if(lvl.getBlockState(bp.below()).getValue(POWERED) == false){
                    lvl.setBlock(bp,bs.setValue(POWERED,false),3);
                }
            }
            else if(lvl.getBlockState(bp.below()).is(sbBlock2)){
                if(lvl.getBlockState(bp.below()).getValue(POWERED) == true){
                    lvl.setBlock(bp,bs.setValue(POWERED,true),3);
                }
                else if(lvl.getBlockState(bp.below()).getValue(POWERED) == false){
                    lvl.setBlock(bp,bs.setValue(POWERED,false),3);
                }
            }
            else{
                if(lvl.hasNeighborSignal(bp) == true){
                    lvl.setBlock(bp,bs.setValue(POWERED,true),3);
                    lvl.scheduleTick(bp.above(),this,3, TickPriority.LOW);
                }
                else if(lvl.hasNeighborSignal(bp) == false){
                    lvl.setBlock(bp,bs.setValue(POWERED,false),3);
                    lvl.scheduleTick(bp.above(),this,3,TickPriority.LOW);
                }
            }
        }
    }

    @Override
    public void tick(BlockState bs, ServerLevel sl, BlockPos bp, RandomSource rs) {
        sl.updateNeighborsAt(bp.above(),this);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING,POWERED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(POWERED,false);
    }
}
