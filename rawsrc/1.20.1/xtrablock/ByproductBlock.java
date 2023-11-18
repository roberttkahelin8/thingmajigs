package net.rk.thingamajigs.xtrablock;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class ByproductBlock extends Block {
    public ByproductBlock(Properties p) {
        super(p.strength(0.5f,1.75f)
                .mapColor(MapColor.COLOR_BROWN)
                .sound(SoundType.ROOTED_DIRT));
    }

    @Override
    public void appendHoverText(ItemStack is, @Nullable BlockGetter bg, List<Component> l, TooltipFlag tf) {
        l.add(Component.translatable("block.thingamajigs.byproduct.desc")
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void stepOn(Level lvl, BlockPos bp, BlockState bs, Entity ent) {
        Random r = new Random();

        double rdx = (double)ent.getX() + 0.5D + lvl.getRandom().nextDouble() / 4.0D *
                (double)(lvl.getRandom().nextBoolean() ? 1 : -1);

        double dy = (double)ent.getY() + 0.4D;
        double rdz = (double)ent.getZ() + 0.5D + lvl.getRandom().nextDouble() / 4.0D *
                (double)(lvl.getRandom().nextBoolean() ? 1 : -1);

        if(ent instanceof LivingEntity){
            lvl.addParticle(ParticleTypes.SNEEZE,
                    rdx,dy,rdz,
                    0.0D,0.07D,0.0D);
            ((LivingEntity) ent).addEffect(new MobEffectInstance(
                    MobEffects.POISON,
                    100,
                    1,
                    true,
                    false,
                    false));
            ((LivingEntity) ent).addEffect(new MobEffectInstance(
                    MobEffects.CONFUSION,
                    200,
                    0,
                    true,
                    false,
                    false));
        }
    }
}
