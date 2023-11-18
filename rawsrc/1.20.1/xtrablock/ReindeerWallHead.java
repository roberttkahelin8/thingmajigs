package net.rk.thingamajigs.xtrablock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.rk.thingamajigs.block.custom.ThingamajigsDecorativeBlock;

import java.util.stream.Stream;

public class ReindeerWallHead extends ThingamajigsDecorativeBlock implements Equipable {
    public static final VoxelShape NORTH_SHAPE = Stream.of(
            Block.box(4, 0, 15, 12, 8, 16),
            Block.box(5.5, 1, 11, 10.5, 7, 15),
            Block.box(4, 3, 4, 12, 12, 11),
            Block.box(6, 2, 0, 10, 8, 4)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape EAST_SHAPE = Stream.of(
            Block.box(0, 0, 4, 1, 8, 12),
            Block.box(1, 1, 5.5, 5, 7, 10.5),
            Block.box(5, 3, 4, 12, 12, 12),
            Block.box(12, 2, 6, 16, 8, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape WEST_SHAPE = Stream.of(
            Block.box(15, 0, 4, 16, 8, 12),
            Block.box(11, 1, 5.5, 15, 7, 10.5),
            Block.box(4, 3, 4, 11, 12, 12),
            Block.box(0, 2, 6, 4, 8, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape SOUTH_SHAPE = Stream.of(
            Block.box(4, 0, 0, 12, 8, 1),
            Block.box(5.5, 1, 1, 10.5, 7, 5),
            Block.box(4, 3, 5, 12, 12, 12),
            Block.box(6, 2, 12, 10, 8, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public ReindeerWallHead(Properties properties) {
        super(properties.strength(0.85f,2f).mapColor(MapColor.COLOR_BROWN));
    }

    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.BAMBOO_WOOD_HIT;
    }

    @Override
    public VoxelShape getShape(BlockState bs, BlockGetter bg, BlockPos bp, CollisionContext cc) {
        Direction direction = bs.getValue(FACING);
        switch(direction){
            case NORTH:
                return NORTH_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case EAST:
                return EAST_SHAPE;
            case WEST:
                return WEST_SHAPE;
            default:
                return Shapes.block();
        }
    }
}
