package net.rk.thingamajigs.xtrablock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;
import net.rk.thingamajigs.block.custom.ThingamajigsDecorativeBlock;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ClockRadioBlock extends ThingamajigsDecorativeBlock {
    public static final VoxelShape NORTHSOUTH = Block.box(2, 0, 6, 14, 6, 10);
    public static final VoxelShape EASTWEST = Block.box(6, 0, 2, 10, 6, 14);
    public static final IntegerProperty TIME = IntegerProperty.create("time",0,24);

    public ClockRadioBlock(Properties p) {
        super(p.sound(SoundType.LANTERN).strength(1F));
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
                .setValue(TIME,0));
    }

    @Override
    public VoxelShape getShape(BlockState bs, BlockGetter bg, BlockPos bp, CollisionContext cc) {
        switch(bs.getValue(FACING)){
            case NORTH:
            case SOUTH:
                return NORTHSOUTH;
            case EAST:
            case WEST:
                return EASTWEST;
            default: return Shapes.block();
        }
    }

    @Override
    public void onPlace(BlockState bs, Level lvl, BlockPos bp, BlockState bs2, boolean b1) {
        lvl.scheduleTick(bp,this,10, TickPriority.LOW); // initial tick
    }

    @Override
    public void tick(BlockState bs, ServerLevel sl, BlockPos bp, RandomSource rs) {
        try{
            long l1 = sl.getDayTime(); // get the current time in long format
            int time1 = Integer.parseInt(Long.toString(l1)); // convert time to integer from string
            int nTime1 = time1 / 1000; // 1000 becomes 1, 24000 becomes 24
            // potential error correction
            if(nTime1 < 0){
                nTime1 = 0;
            }
            else if(nTime1 > 24){
                nTime1 = 24;
            }
            // set time after doing maths (and blockstate)
            sl.setBlock(bp,bs.setValue(TIME,nTime1),3);
        }
        catch (Exception exc){
            // do nothing, skip
            //
        }
        sl.scheduleTick(bp,this,80,TickPriority.LOW); // repetitive ticks
    }

    @Override
    public InteractionResult use(BlockState bs, Level lvl, BlockPos bp, Player p, InteractionHand h, BlockHitResult bhr) {
        long l1 = lvl.getDayTime();
        try{
            p.displayClientMessage(Component.literal("Time (in long): " + String.valueOf(l1)),true);
            return InteractionResult.SUCCESS;
        }
        catch (Exception e){
            return InteractionResult.FAIL;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING,WATERLOGGED,TIME);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER)
                .setValue(TIME,0);
    }

    @Override
    public void appendHoverText(ItemStack it, @Nullable BlockGetter bg, List<Component> listc, TooltipFlag tf) {
        listc.add(Component.translatable("block.thingamajigs.clock_radio.desc"));
    }
}
