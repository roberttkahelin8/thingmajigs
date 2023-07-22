package net.rk.thingamajigs.xtrablock.redstoneblocks;

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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import net.rk.thingamajigs.block.ThingamajigsBlocks;
import net.rk.thingamajigs.block.custom.Pole;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VerticalPoleRedstone extends Block {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public VerticalPoleRedstone(Properties properties) {
        super(properties.strength(1F,5F));
        this.registerDefaultState(this.defaultBlockState().setValue(POWERED, false));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter bg, List<Component> list, TooltipFlag ttf) {
        list.add(Component.translatable("block.thingamajigs.vertical_pole_redstone.desc"));
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Pole.VERTICAL_ALL;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }

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
                    lvl.scheduleTick(bp.above(),this,3,TickPriority.LOW);
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
        builder.add(POWERED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(POWERED, false);
    }
}
