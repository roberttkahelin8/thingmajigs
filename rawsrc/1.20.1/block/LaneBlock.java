package net.rk.thingamajigs.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LaneBlock extends Block {
    private static final VoxelShape LANE_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public LaneBlock(Properties p) {
        super(p.strength(1f,20f).sound(SoundType.WOOD)
                .friction(0.8F)
                .speedFactor(1.1F));
    }

    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return LANE_SHAPE;
    }

    public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return Shapes.block();
    }

    public VoxelShape getVisualShape(BlockState pState, BlockGetter pReader, BlockPos pPos, CollisionContext pContext) {
        return Shapes.block();
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 75;
    }

    @Override
    public void stepOn(Level lvl, BlockPos bp, BlockState bs, Entity e) {
        if(e instanceof ItemEntity){
            e.move(MoverType.PISTON,new Vec3(
                    e.getDeltaMovement().x * 1.1D,
                    e.getDeltaMovement().y * 0.01D,
                    e.getDeltaMovement().z * 1.1D));

            if(e.getDeltaMovement().x > 3.2D){
                e.setDeltaMovement(3.2D,e.getDeltaMovement().y,e.getDeltaMovement().z);
            }
            else if(e.getDeltaMovement().x < -3.2D){
                e.setDeltaMovement(-3.2D,e.getDeltaMovement().y,e.getDeltaMovement().z);
            }

            if(e.getDeltaMovement().z > 3.2D){
                e.setDeltaMovement(e.getDeltaMovement().x,e.getDeltaMovement().y,3.2D);
            }
            else if(e.getDeltaMovement().z < -3.2D){
                e.setDeltaMovement(e.getDeltaMovement().x,e.getDeltaMovement().y,-3.2D);
            }
        }
    }

    @Override
    public void fallOn(Level lvl, BlockState bs, BlockPos bp, Entity e, float f1) {
        if(e instanceof ItemEntity){
            e.setDeltaMovement(
                    e.getDeltaMovement().x * 1.12D,
                    e.getDeltaMovement().y,
                    e.getDeltaMovement().z * 1.12D);

            if(e.getDeltaMovement().x > 3.2D){
                e.setDeltaMovement(3.2D,e.getDeltaMovement().y,e.getDeltaMovement().z);
            }
            else if(e.getDeltaMovement().x < -3.2D){
                e.setDeltaMovement(-3.2D,e.getDeltaMovement().y,e.getDeltaMovement().z);
            }

            if(e.getDeltaMovement().z > 3.2D){
                e.setDeltaMovement(e.getDeltaMovement().x,e.getDeltaMovement().y,3.2D);
            }
            else if(e.getDeltaMovement().z < -3.2D){
                e.setDeltaMovement(e.getDeltaMovement().x,e.getDeltaMovement().y,-3.2D);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
}
