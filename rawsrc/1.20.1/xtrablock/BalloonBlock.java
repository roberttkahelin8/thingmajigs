package net.rk.thingamajigs.xtrablock;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.ticks.TickPriority;
import net.rk.thingamajigs.events.ThingamajigsSoundEvents;

public class BalloonBlock extends Block {
    public BalloonBlock(Properties p) {
        super(p.strength(0.5f,0.25f).noOcclusion().sound(SoundType.WOOL)
                .instrument(NoteBlockInstrument.FLUTE).pushReaction(PushReaction.DESTROY)
                .mapColor(MapColor.WOOL).jumpFactor(1.45f));
    }

    @Override
    public boolean isValidSpawn(BlockState state, BlockGetter level, BlockPos pos, SpawnPlacements.Type type, EntityType<?> entityType) {
        return false;
    }

    @Override
    public boolean isPathfindable(BlockState bs, BlockGetter bg, BlockPos bp, PathComputationType pct) {
        return false;
    }

    @Override
    public void onPlace(BlockState bs1, Level l, BlockPos bp, BlockState bs2, boolean b1) {
        boolean bsn = l.getBlockState(bp.north()).getBlock() == Blocks.LAVA;
        boolean bss = l.getBlockState(bp.south()).getBlock() == Blocks.LAVA;
        boolean bse = l.getBlockState(bp.east()).getBlock() == Blocks.LAVA;
        boolean bsw = l.getBlockState(bp.west()).getBlock() == Blocks.LAVA;
        boolean bsd = l.getBlockState(bp.below()).getBlock() == Blocks.LAVA;
        boolean bsu = l.getBlockState(bp.above()).getBlock() == Blocks.LAVA;
        if(bsn || bss || bse || bsw || bsd || bsu){
            l.playSound(null,bp.above(), ThingamajigsSoundEvents.POP.get(), SoundSource.BLOCKS,1.0f,1.0f);
            l.removeBlock(bp,false);
            return;
        }
        l.scheduleTick(bp,this,2, TickPriority.LOW);
    }

    @Override
    public void tick(BlockState bs, ServerLevel sl, BlockPos bp, RandomSource rs) {
        if(bp.getY() >= 255){
            sl.setBlock(bp,Blocks.AIR.defaultBlockState(),3);
        }
        if(sl.getBlockState(bp.above()).is(Blocks.WATER)){
            BlockState tempBs = sl.getBlockState(bp.above());
            sl.setBlock(bp.above(),this.defaultBlockState(),3);
            sl.setBlock(bp,tempBs,3);
        }
        sl.scheduleTick(bp,this,1,TickPriority.LOW);
    }

    // from slime block, edited to be nicer to hard falls with sneaking on
    public void fallOn(Level lvl, BlockState bs, BlockPos bp, Entity ent1, float f1) {
        if (ent1.isShiftKeyDown()) {
            ent1.causeFallDamage(f1, 0.5F, lvl.damageSources().fall());
        }
        else {
            if(ent1 instanceof ItemEntity){
                ent1.move(MoverType.PISTON,new Vec3(0.0D,0.75D,0.0D));
            }
            else{
                ent1.causeFallDamage(f1, 0.0F, lvl.damageSources().fall());
            }
        }
    }

    // decorated pot but without the fancy stuff that is not in 1.20
    @Override
    public void onProjectileHit(Level lvl, BlockState bs, BlockHitResult bhr, Projectile p) {
        BlockPos bp = bhr.getBlockPos();
        if (!lvl.isClientSide && p.mayInteract(lvl, bp)) {
            if(p instanceof AbstractArrow || p instanceof FireworkRocketEntity || p instanceof FishingHook){
                double x = p.getDeltaMovement().x;
                double y = p.getDeltaMovement().y;
                double z = p.getDeltaMovement().z;
                float xrot = p.getXRot();
                float yrot = p.getYRot();

                p.setDeltaMovement(x * -1.0D,y * -1.0D,z * -1.0D);
                p.setXRot(xrot * -1.0f);
                p.setYRot(yrot * -1.0f);

                lvl.playSound(null,bp,ThingamajigsSoundEvents.POP.get(),SoundSource.BLOCKS,2.0f,1.0f);
                lvl.removeBlock(bp,false);
            }
        }
    }

    // from slime block, but extremely bouncy! not so bouncy for other entity types
    public void updateEntityAfterFallOn(BlockGetter bg, Entity ent) {
        if (ent.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(bg, ent);
        }
        else {
            Vec3 vec3 = ent.getDeltaMovement();
            if (vec3.y < 0.0D) {
                double d0 = ent instanceof LivingEntity ? 1.0D : 0.5D;
                ent.setDeltaMovement(vec3.x, -vec3.y * d0, vec3.z);
            }
        }
    }
}
