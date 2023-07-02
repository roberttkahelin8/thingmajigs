package net.rk.thingamajigs.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.rk.thingamajigs.item.ThingamajigsItems;

import java.util.List;

public class AsphaltDynamicBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final IntegerProperty AGE = IntegerProperty.create("age",0,3);
    public static final IntegerProperty COLOR = IntegerProperty.create("type",0,2);
    public static final IntegerProperty MARKING = IntegerProperty.create("type",0,5);
    // 0 = straight double, 1 = straight single, 2 = dashed single, 3 = road edge, 4 = parking edge, 5 = sidewalk line

    public AsphaltDynamicBlock(Properties p) {
        super(p.strength(1.5F).sound(SoundType.TUFF).requiresCorrectToolForDrops());
    }

    // too complex! Sorry!

    @Override
    public void appendHoverText(ItemStack itemstack, BlockGetter world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("tooltip.thingamajigs.placeholder"));
    }

    @Override
    public InteractionResult use(BlockState bs, Level lvl, BlockPos bp, Player pl, InteractionHand ih, BlockHitResult bhr) {
        if(!lvl.isClientSide()){
            if(pl.isShiftKeyDown()){
                lvl.setBlock(bp,bs.cycle(AGE),2);
                lvl.playSound(null,bp, SoundEvents.STONE_HIT, SoundSource.BLOCKS,1.0F,1.0F);
                return InteractionResult.SUCCESS;
            }
            else{
                if(pl.getItemInHand(ih).is(Items.WHITE_DYE)){
                    lvl.setBlock(bp,bs.setValue(COLOR,0),3);
                    lvl.playSound(null,bp, SoundEvents.DYE_USE, SoundSource.BLOCKS,1.0F,1.0F);
                    return InteractionResult.SUCCESS;
                }
                else if(pl.getItemInHand(ih).is(Items.YELLOW_DYE)){
                    lvl.setBlock(bp,bs.setValue(COLOR,1),3);
                    lvl.playSound(null,bp, SoundEvents.DYE_USE, SoundSource.BLOCKS,1.0F,1.0F);
                    return InteractionResult.SUCCESS;
                }
                else if(pl.getItemInHand(ih).is(Items.BLUE_DYE)){
                    lvl.setBlock(bp,bs.setValue(COLOR,2),3);
                    lvl.playSound(null,bp, SoundEvents.DYE_USE, SoundSource.BLOCKS,1.0F,1.0F);
                    return InteractionResult.SUCCESS;
                }
                else if(pl.getItemInHand(ih).is(ThingamajigsItems.PAINT_BRUSH.get())){
                    lvl.setBlock(bp,bs.cycle(MARKING),2);
                    lvl.playSound(null,bp, SoundEvents.ITEM_FRAME_ROTATE_ITEM, SoundSource.BLOCKS,1.0F,1.0F);
                    return InteractionResult.SUCCESS;
                }
                else{
                    return InteractionResult.PASS;
                }
            }
        }
        return InteractionResult.PASS;
    }
}
