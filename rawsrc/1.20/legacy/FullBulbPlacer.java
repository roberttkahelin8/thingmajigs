package net.rk.thingamajigs.legacy;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.ForgeRegistries;
import net.rk.thingamajigs.block.ThingamajigsBlocks;

public class FullBulbPlacer {
    // what is this hack and slashing? YES! Stinky old code that had been changed here and there 100s of times
    // it is from an old version of the mod (rewritten here and there to prevent errors)
    // it originally used poor practices in order to not shrink the stack when in creative mode...
    public static void run(LevelAccessor world, double x, double y, double z, Direction direction, Entity entity, ItemStack itemstack) {
        if(direction == null || entity == null){
            return;
        }
        if(entity instanceof LivingEntity _entity) _entity.swing(InteractionHand.MAIN_HAND, true);if (!(new Object(){
            public boolean checkGamemode(Entity _ent){
                if(_ent instanceof ServerPlayer _serverPlayer) {
                    return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
                } else if(_ent instanceof Player _player) {
                    return _player.isCreative();
                }
                return false;
            }
        }.checkGamemode(entity))) {(itemstack).shrink(1);}if (direction == Direction.UP) {if ((world.getBlockState(new BlockPos((int)x,(int)(y+1),(int)z))).getBlock() == Blocks.AIR) {world.setBlock(new BlockPos((int)x,(int)(y+1),(int)z), ThingamajigsBlocks.GROUND_FULL_BULB.get().defaultBlockState(),3);if(world instanceof Level _level) {
            if(!_level.isClientSide()) {
                _level.playSound(null, new BlockPos((int) x, (int) y, (int) z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.metal.place")), SoundSource.BLOCKS, 1, 1);
            } else {
                _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.metal.place")), SoundSource.BLOCKS, 1, 1, false);
            }
        }}}else if (direction == Direction.NORTH) {if ((world.getBlockState(new BlockPos((int)x,(int)y,(int)(z-1)))).getBlock() == Blocks.AIR) {world.setBlock(new BlockPos((int)x,(int)y,(int)(z-1)), ThingamajigsBlocks.WALL_FULL_BULB.get().defaultBlockState(), 3);{
            Direction _dir = Direction.NORTH;
            BlockPos _pos = new BlockPos((int)x,(int)y,(int)(z-1));
            BlockState _bs = world.getBlockState(_pos);
            Property<?> _property = _bs.getBlock().getStateDefinition().getProperty("facing");
            if (_property instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(_dir)) {
                world.setBlock(_pos, _bs.setValue(_dp, _dir), 3);
            } else {
                _property = _bs.getBlock().getStateDefinition().getProperty("axis");
                if (_property instanceof EnumProperty _ap && _ap.getPossibleValues().contains(_dir.getAxis()))
                    world.setBlock(_pos, _bs.setValue(_ap, _dir.getAxis()), 3);
            }
        }if(world instanceof Level _level) {
            if(!_level.isClientSide()) {
                _level.playSound(null, new BlockPos((int) x, (int) y, (int) z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.metal.place")),
                        SoundSource.BLOCKS, 1, 1);
            } else {
                _level.playLocalSound(x, y, z,
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.metal.place")),
                        SoundSource.BLOCKS, 1, 1, false);
            }
        }}}else if (direction == Direction.SOUTH) {if ((world.getBlockState(new BlockPos((int)x,(int)y,(int)(z+1)))).getBlock() == Blocks.AIR) {world.setBlock(new BlockPos((int)x,(int)y,(int)(z+1)), ThingamajigsBlocks.WALL_FULL_BULB.get().defaultBlockState(),3);{
            Direction _dir = Direction.SOUTH;
            BlockPos _pos = new BlockPos((int)x,(int)y,(int)(z+1));
            BlockState _bs = world.getBlockState(_pos);
            Property<?> _property = _bs.getBlock().getStateDefinition().getProperty("facing");
            if (_property instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(_dir)) {
                world.setBlock(_pos, _bs.setValue(_dp, _dir), 3);
            } else {
                _property = _bs.getBlock().getStateDefinition().getProperty("axis");
                if (_property instanceof EnumProperty _ap && _ap.getPossibleValues().contains(_dir.getAxis()))
                    world.setBlock(_pos, _bs.setValue(_ap, _dir.getAxis()), 3);
            }
        }if(world instanceof Level _level) {
            if(!_level.isClientSide()) {
                _level.playSound(null, new BlockPos((int) x, (int) y, (int) z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.metal.place")),
                        SoundSource.BLOCKS, 1, 1);
            } else {_level.playLocalSound(x, y, z,
                    ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.metal.place")),
                    SoundSource.BLOCKS, 1, 1, false);
            }
        }}}else if (direction == Direction.WEST) {if ((world.getBlockState(new BlockPos((int)(x-1),(int)y,(int)z))).getBlock() == Blocks.AIR) {world.setBlock(new BlockPos((int)(x-1),(int)y,(int)z), ThingamajigsBlocks.WALL_FULL_BULB.get().defaultBlockState(),3);{
            Direction _dir = Direction.WEST;
            BlockPos _pos = new BlockPos((int)(x-1),(int)y,(int)z);
            BlockState _bs = world.getBlockState(_pos);
            Property<?> _property = _bs.getBlock().getStateDefinition().getProperty("facing");
            if (_property instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(_dir)) {
                world.setBlock(_pos, _bs.setValue(_dp, _dir), 3);
            } else {
                _property = _bs.getBlock().getStateDefinition().getProperty("axis");
                if (_property instanceof EnumProperty _ap && _ap.getPossibleValues().contains(_dir.getAxis()))
                    world.setBlock(_pos, _bs.setValue(_ap, _dir.getAxis()), 3);
            }
        }if(world instanceof Level _level) {
            if(!_level.isClientSide()) {
                _level.playSound(null, new BlockPos((int) x, (int) y, (int) z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.metal.place")),
                        SoundSource.BLOCKS, 1, 1);
            } else {
                _level.playLocalSound(x, y, z,
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.metal.place")),
                        SoundSource.BLOCKS, 1, 1, false);
            }
        }}}else if (direction == Direction.EAST) {if ((world.getBlockState(new BlockPos((int)(x+1),(int)y,(int)z))).getBlock() == Blocks.AIR) {world.setBlock(new BlockPos((int)(x+1),(int)y,(int)z), ThingamajigsBlocks.WALL_FULL_BULB.get().defaultBlockState(),3);{
            Direction _dir = Direction.EAST;
            BlockPos _pos = new BlockPos((int)(x+1),(int)y,(int)z);
            BlockState _bs = world.getBlockState(_pos);
            Property<?> _property = _bs.getBlock().getStateDefinition().getProperty("facing");
            if (_property instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(_dir)) {
                world.setBlock(_pos, _bs.setValue(_dp, _dir), 3);
            } else {
                _property = _bs.getBlock().getStateDefinition().getProperty("axis");
                if (_property instanceof EnumProperty _ap && _ap.getPossibleValues().contains(_dir.getAxis()))
                    world.setBlock(_pos, _bs.setValue(_ap, _dir.getAxis()), 3);
            }
        }if(world instanceof Level _level) {
            if(!_level.isClientSide()) {
                _level.playSound(null, new BlockPos((int) x, (int) y, (int) z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.metal.place")),
                        SoundSource.BLOCKS, 1, 1);
            } else {
                _level.playLocalSound(x, y, z,
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.metal.place")),
                        SoundSource.BLOCKS, 1, 1, false);
            }
        }}}
    }
}
