package net.rk.thingamajigs.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.ticks.TickPriority;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RailroadCrossingLights extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");

    //public static final IntegerProperty TYPE = IntegerProperty.create("type",0,3);
    // 0 = single, 1 = double, 2 = triple, 3 = angled (towards another street)

    public RailroadCrossingLights(Properties p) {
        super(p.noOcclusion());
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(POWERED, false));
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> bs) {
        super.createBlockStateDefinition(bs.add(FACING,POWERED));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(POWERED,false);
    }

    // old deprecated code (does not have fancy features)
    /*
    public void neighborChanged(BlockState bs, Level lvl, BlockPos bp, Block blk, BlockPos bp2, boolean bl1) {
        if (!lvl.isClientSide) {
            boolean flag = bs.getValue(ON);
            if (flag != lvl.hasNeighborSignal(bp)) {
                if (flag) {
                    lvl.scheduleTick(bp, this, 4);
                } else {
                    lvl.setBlock(bp, bs.cycle(ON), 2);
                }
            }
        }
    }

    public void tick(BlockState bs, ServerLevel slvl, BlockPos bp2, RandomSource rnds) {
        if (bs.getValue(ON) && !slvl.hasNeighborSignal(bp2)) {
            slvl.setBlock(bp2, bs.cycle(ON), 2);
        }
    }
    */

    @Override
    public void neighborChanged(BlockState bs, Level lvl, BlockPos bp, Block blk, BlockPos bp2, boolean p_55671_) {
        BlockState similarBlock = ThingamajigsBlocks.VERTICAL_POLE_REDSTONE.get().defaultBlockState();
        Block sbBlock = similarBlock.getBlock();
        if (!lvl.isClientSide) {
            if(lvl.getBlockState(bp.below()).is(sbBlock)){
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
    public RenderShape getRenderShape(BlockState bs) {
        return RenderShape.MODEL;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, BlockGetter world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("block.rr_lights.desc"));
    }
}
