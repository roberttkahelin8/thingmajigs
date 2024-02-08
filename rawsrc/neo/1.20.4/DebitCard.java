package net.rk.thingamajigs.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.rk.thingamajigs.block.ATM;
import net.rk.thingamajigs.block.InsetATM;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecated")
public class DebitCard extends Item {
    private int moneyAmt = 0; // the item instance and ref has this redundant counter for tracking
    private final int maxMoney = 999999; // the maximum money allowed on the card

    public DebitCard(Properties p) {
        super(p.stacksTo(1).setNoRepair().rarity(Rarity.EPIC).fireResistant());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.thingamajigs.debit_card.desc").withStyle(ChatFormatting.GRAY));
        if(stack.hasTag()){
            pTooltipComponents.add(Component.translatable("item.thingamajigs.debit_card.stored_money.title", stack.getTag().getInt("money"))
                    .withStyle(ChatFormatting.GREEN));
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        LevelAccessor levelAccessor = ctx.getLevel();
        Level level = ctx.getLevel();
        BlockPos positionClicked = ctx.getClickedPos();
        Block blockClicked = level.getBlockState(positionClicked).getBlock();
        ItemStack stack = ctx.getItemInHand();
        Player ply = ctx.getPlayer();
        InteractionHand ih = ctx.getHand();
        ItemStack invitem = ItemStack.EMPTY;

        boolean shifting = ply.isShiftKeyDown();

        if(ih == InteractionHand.MAIN_HAND){
            if(blockClicked instanceof ATM || blockClicked instanceof InsetATM){
                CompoundTag moneyTag = new CompoundTag();
                if(shifting){
                    if(stack.hasTag()){
                        // handle atm money
                        if(stack.getTag() != null){
                            if(stack.getTag().getInt("money") > 0){
                                if(stack.getTag().getInt("money") > 4){
                                    stack.getTag().putInt("money", stack.getTag().getInt("money") - 4);
                                    moneyAmt = stack.getTag().getInt("money");
                                    level.playSound(null,positionClicked,
                                            SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,1.0f,1.0f);
                                    ply.displayClientMessage(
                                            Component.translatable("item.thingamajigs.debit_card.card_take_funds", 4),true);
                                    // add money to inventory if able or drop it otherwise
                                    if(!ply.getInventory().add(new ItemStack(TItems.MONEY.asItem(),1))){
                                        ply.drop(new ItemStack(TItems.MONEY.asItem(),1), false);
                                    }
                                }
                                else{
                                    stack.getTag().putInt("money", stack.getTag().getInt("money") - 1);
                                    moneyAmt = stack.getTag().getInt("money");
                                    level.playSound(null,positionClicked,
                                            SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,1.0f,1.0f);
                                    ply.displayClientMessage(
                                            Component.translatable("item.thingamajigs.debit_card.card_take_funds", 1),true);
                                    // add coins to inventory if able or drop it otherwise
                                    if(!ply.getInventory().add(new ItemStack(TItems.COIN.asItem(),1))){
                                        ply.drop(new ItemStack(TItems.COIN.asItem(),1), false);
                                    }
                                }
                                return InteractionResult.sidedSuccess(ctx.getLevel().isClientSide);
                            }
                            else {
                                ply.displayClientMessage(
                                        Component.translatable("item.thingamajigs.debit_card.empty_card"),true);
                                level.playSound(null,positionClicked,
                                        SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,1.0f,0.5f);
                            }
                        }
                    }
                    else{
                        moneyTag.putInt("money",0);
                        stack.setTag(moneyTag);
                    }
                }
                else{
                    if(stack.hasTag()){
                        boolean hasMoney = false;
                        boolean hasCoin = false;
                        int invsizeply = ply.getInventory().getContainerSize();
                        Inventory plyinv = ply.getInventory();

                        // loop over all the player's inventory
                        // except the offhand slot
                        for(int i = 0; i < invsizeply - 1; i++){
                            if(plyinv.getItem(i) != ItemStack.EMPTY){
                                invitem = plyinv.getItem(i);
                                if(invitem.is(TItems.MONEY.asItem())){
                                    hasMoney = true;
                                    break;
                                }
                                else if(invitem.is(TItems.COIN.asItem())) {
                                    hasCoin = true;
                                    break;
                                }
                            }
                        }

                        // logic stuffs
                        if(hasMoney){
                            // handle overflow and money tag
                            if (stack.getTag() != null){
                                // if the money is off, do the simple thing and absorb it to correct errors
                                if(stack.getTag().getInt("money") + 4 >= maxMoney){
                                    invitem.shrink(1);
                                }

                                stack.getTag().putInt("money", stack.getTag().getInt("money") + 4);

                                if(stack.getTag().getInt("money") >= maxMoney){
                                    stack.getTag().putInt("money", maxMoney);
                                    ply.displayClientMessage(
                                            Component.translatable("item.thingamajigs.debit_card.full_card"),true);
                                    level.playSound(null,positionClicked,
                                            SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,1.0f,0.5f);
                                    moneyAmt = stack.getTag().getInt("money");
                                }
                                else{
                                    invitem.shrink(1);
                                    level.playSound(null,positionClicked,
                                            SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,1.0f,1.0f);
                                    moneyAmt = stack.getTag().getInt("money");
                                    ply.displayClientMessage(
                                            Component.translatable("item.thingamajigs.debit_card.card_add_funds",4),true);
                                }
                                return InteractionResult.sidedSuccess(ctx.getLevel().isClientSide);
                            }
                        }
                        else if(hasCoin){
                            // handle overflow and money tag
                            if (stack.getTag() != null){
                                // if the money is off, do the simple thing and absorb it to correct errors
                                if(stack.getTag().getInt("money") + 1 >= maxMoney){
                                    invitem.shrink(1);
                                }

                                stack.getTag().putInt("money", stack.getTag().getInt("money") + 1);
                                if(stack.getTag().getInt("money") >= maxMoney){
                                    stack.getTag().putInt("money", maxMoney);
                                    ply.displayClientMessage(
                                            Component.translatable("item.thingamajigs.debit_card.full_card"),true);
                                    level.playSound(null,positionClicked,
                                            SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,1.0f,0.5f);
                                    moneyAmt = stack.getTag().getInt("money");
                                }
                                else{
                                    invitem.shrink(1);
                                    level.playSound(null,positionClicked,
                                            SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,1.0f,1.0f);
                                    moneyAmt = stack.getTag().getInt("money");
                                    ply.displayClientMessage(
                                            Component.translatable("item.thingamajigs.debit_card.card_add_funds",1),true);
                                }
                                return InteractionResult.sidedSuccess(ctx.getLevel().isClientSide);
                            }
                        }
                    }
                    else{
                        moneyTag.putInt("money",0);
                        stack.setTag(moneyTag);
                        return InteractionResult.sidedSuccess(ctx.getLevel().isClientSide);
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BRUSH;
    }
}
