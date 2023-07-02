package net.rk.thingamajigs.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class KitchenSink extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty FULL = BooleanProperty.create("full");
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    public KitchenSink(Properties p) {
        super(p.strength(1.52F,5.25F).sound(SoundType.COPPER).noOcclusion().mapColor(MapColor.METAL));
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(FULL,false).setValue(OPEN,false));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, BlockGetter world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("tooltip.thingamajigs.kitchen_sink"));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult bhr) {
        if(!level.isClientSide()){
            boolean open = state.getValue(OPEN);
            boolean full = state.getValue(FULL);
            if(hand == InteractionHand.MAIN_HAND){
                if(player.isShiftKeyDown()){
                    if (open){
                        open = false;
                        level.setBlock(pos, state.setValue(OPEN, open), 0);
                    }
                    else {
                        open = true;
                        level.setBlock(pos, state.setValue(OPEN, open), 0);
                    }
                }
                else if(!player.isShiftKeyDown()){
                    if(player.getMainHandItem().getItem() == Items.BUCKET.getDefaultInstance().getItem()){
                        if (full){
                            full = false;
                            level.setBlock(pos, state.setValue(FULL, full), 0);
                        }
                        else {
                            full = true;
                            level.setBlock(pos, state.setValue(FULL, full), 0);
                        }
                        player.swing(hand);
                        level.playSound(null,pos,SoundEvents.BUCKET_FILL,SoundSource.BLOCKS,1.0F,1.0F);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            else {
                return InteractionResult.CONSUME;
            }
        }
        return super.use(state, level, pos, player, hand, bhr);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING,FULL,OPEN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(FULL,false).setValue(OPEN,false);
    }
}
