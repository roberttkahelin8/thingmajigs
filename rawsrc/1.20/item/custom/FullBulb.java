package net.rk.thingamajigs.item.custom;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.rk.thingamajigs.block.ThingamajigsBlocks;
import net.rk.thingamajigs.legacy.FullBulbPlacer;

public class FullBulb extends Item {
    private final Block pStandingBlock = ThingamajigsBlocks.GROUND_FULL_BULB.get();
    private final Block pWallBlock = ThingamajigsBlocks.WALL_FULL_BULB.get();

    public FullBulb(Properties pProperties) {
        super(pProperties);
    }

    @Override public InteractionResult useOn(UseOnContext context) {
        FullBulbPlacer.run(
                context.getLevel(),context.getClickedPos().getX(),context.getClickedPos().getY(),context.getClickedPos().getZ(),context.getClickedFace(),context.getPlayer(),context.getItemInHand()
        );
        return InteractionResult.SUCCESS;
    }

}
