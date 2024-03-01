package net.rk.thingamajigs.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.rk.thingamajigs.Thingamajigs;
import net.rk.thingamajigs.block.custom.*;
import net.rk.thingamajigs.block.custom.blocks.*;
import net.rk.thingamajigs.block.custom.specialblocks.ThingamajigsCustomLeavesBlock;
import net.rk.thingamajigs.block.custom.specialblocks.ThingamajigsFlammableNormalBlock;
import net.rk.thingamajigs.block.custom.specialblocks.ThingamajigsFlammableRotatedPillarBlock;
import net.rk.thingamajigs.entity.customblock.*;
import net.rk.thingamajigs.entity.customblock.hangingblock.GreenHangingSignBlock;
import net.rk.thingamajigs.entity.customblock.hangingblock.GreenWallHangingSignBlock;
import net.rk.thingamajigs.events.ThingamajigsSoundEvents;
import net.rk.thingamajigs.events.ThingamajigsSoundTypes;
import net.rk.thingamajigs.fluid.fluidblocks.PurifyingWaterBlock;
import net.rk.thingamajigs.fluid.fluidblocks.SludgeBlock;
import net.rk.thingamajigs.interfacing.WeatheringCopperChair;
import net.rk.thingamajigs.item.ThingamajigsItems;
import net.rk.thingamajigs.misc.ThingamajigsBlockTypes;
import net.rk.thingamajigs.world.tree.RubberTreeGrower;
import net.rk.thingamajigs.xtrablock.*;
import net.rk.thingamajigs.xtrablock.Mirror;
import net.rk.thingamajigs.xtrablock.custompumpkins.*;
import net.rk.thingamajigs.xtrablock.redstoneblocks.RailroadCrossingCantilever;
import net.rk.thingamajigs.xtrablock.redstoneblocks.RailroadCrossingCantileverLights;
import net.rk.thingamajigs.xtrablock.redstoneblocks.VerticalPoleRedstone;
import net.rk.thingamajigs.xtrablock.stagedbasedblocks.*;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;


public class ThingamajigsBlocks {
    // Blocks WOOOOO!

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Thingamajigs.MOD_ID);

    // Miscellaneous Blocks
    public static final RegistryObject<Block> BASIC_BATHROOM_TILE = registerBlock("basic_bathroom_tile",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1f).requiresCorrectToolForDrops().sound(SoundType.CALCITE)), 0);
    public static final RegistryObject<Block> MINIGOLF_GRASS_BLOCK = registerBlock("minigolf_grass_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).strength(1.1f).sound(SoundType.GRASS)), 0);
    public static final RegistryObject<Block> MINIGOLF_HOLE = registerBlock("minigolf_hole",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).strength(1.1f).sound(SoundType.GRASS)), 0);

    public static final RegistryObject<Block> FAN_BLOCK_ULTRASONIC = registerBlock("fan_block_ultrasonic",
            () -> new FanBlock(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> FAN_BLOCK_FAST = registerBlock("fan_block_fast",
            () -> new FanBlock(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> FAN_BLOCK = registerBlock("fan_block",
            () -> new FanBlock(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> FAN_BLOCK_OFF = registerBlock("fan_block_off",
            () -> new FanBlock(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops().sound(SoundType.METAL)), 0);

    public static final RegistryObject<Block> TECHNO_PILLAR = registerBlock("techno_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().strength(1.2f,24F)
                    .requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)
                    .emissiveRendering(ThingamajigsBlocks::always)), 0);
    public static final RegistryObject<Block> TECHNO_CORE = registerBlock("techno_core",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(1.5f,32F).requiresCorrectToolForDrops()
                    .sound(SoundType.NETHERITE_BLOCK)
                    .emissiveRendering(ThingamajigsBlocks::always)), 0);


    public static final RegistryObject<Block> NEON_BLOCK = registerBlock("neon_block",
            () -> new NeonFloorBlock(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops().sound(SoundType.METAL).lightLevel(customLitBlockEmission(15))), 0);
    public static final RegistryObject<Block> ALT_NEON_BLOCK = registerBlock("alternative_neon_block",
            () -> new NeonFloorBlock(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops().sound(SoundType.METAL).lightLevel(customLitBlockEmission(15))), 0);
    public static final RegistryObject<Block> OLD_TEAL_WOOL = registerBlock("old_teal_wool",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).strength(0.5f).sound(SoundType.WOOL)), 0);
    public static final RegistryObject<Block> LOVE_SEAT_WOOL = registerBlock("love_seat_wool",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).strength(0.5f).sound(SoundType.WOOL)), 0);
    public static final RegistryObject<Block> OAK_LANE = registerBlock("oak_lane",
            () -> new LaneBlock(BlockBehaviour.Properties.of().ignitedByLava()), 0);
    public static final RegistryObject<Block> GRATE = registerBlock("grate",
            () -> new GlassLikeBlock(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops().sound(SoundType.POLISHED_DEEPSLATE)), 0);
    public static final RegistryObject<Block> BRICK_SIDEWALK = registerBlock("brick_sidewalk",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS).strength(1f).requiresCorrectToolForDrops().sound(SoundType.STONE)), 0);
    public static final RegistryObject<Block> BRICK_SIDEWALK_HB = registerBlock("brick_sidewalk_hb",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS).strength(1f).requiresCorrectToolForDrops().sound(SoundType.STONE)), 0);
    public static final RegistryObject<Block> STORE_FLOORING = registerBlock("store_flooring",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1f).requiresCorrectToolForDrops().sound(SoundType.STONE)), 0);



    // Torch-Like Blocks
    public static final RegistryObject<Block> GROUND_CLEAR_BULB = customRegisterBlock("standing_clear_bulb",
            () -> new GroundClearBulbBlock(BlockBehaviour.Properties.copy(Blocks.TORCH).lightLevel(s -> 12),ParticleTypes.FLAME));
    public static final RegistryObject<Block> WALL_CLEAR_BULB = customRegisterBlock("wall_clear_bulb",
            () -> new WallClearBulbBlock(BlockBehaviour.Properties.copy(Blocks.WALL_TORCH).lightLevel(s -> 12),ParticleTypes.FLAME));

    public static final RegistryObject<Block> GROUND_FULL_BULB = customRegisterBlock("standing_full_bulb",
            () -> new GroundFullBulbBlock(BlockBehaviour.Properties.copy(Blocks.TORCH).lightLevel(s -> 15),ParticleTypes.FLAME));
    public static final RegistryObject<Block> WALL_FULL_BULB = customRegisterBlock("wall_full_bulb",
            () -> new WallFullBulbBlock(BlockBehaviour.Properties.copy(Blocks.WALL_TORCH).lightLevel(s -> 15),ParticleTypes.FLAME));

    public static final RegistryObject<Block> GROUND_CLEAR_LANTERN = customRegisterBlock("standing_clear_lantern",
            () -> new GroundClearLanternBlock(BlockBehaviour.Properties.copy(Blocks.TORCH).lightLevel(s -> 13),ParticleTypes.FLAME));
    public static final RegistryObject<Block> WALL_CLEAR_LANTERN = customRegisterBlock("wall_clear_lantern",
            () -> new WallClearLanternBlock(BlockBehaviour.Properties.copy(Blocks.WALL_TORCH).lightLevel(s -> 13), ParticleTypes.FLAME));

    public static final RegistryObject<Block> GROUND_FULL_LANTERN = customRegisterBlock("standing_full_lantern",
            () -> new GroundFullLanternBlock(BlockBehaviour.Properties.copy(Blocks.TORCH).lightLevel(s -> 14),ParticleTypes.FLAME));
    public static final RegistryObject<Block> WALL_FULL_LANTERN = customRegisterBlock("wall_full_lantern",
            () -> new WallFullLanternBlock(BlockBehaviour.Properties.copy(Blocks.WALL_TORCH).lightLevel(s -> 14),ParticleTypes.FLAME));


    // Decorative Blocks
    // Appliances and Electronics Decorative Blocks
    public static final RegistryObject<Block> TV = registerBlock("tv",
            () -> new TV(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2f).sound(SoundType.WOOD).noOcclusion()), 0);
    public static final RegistryObject<Block> BIG_TV = registerBlock("big_tv",
            () -> new BigTV(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.LANTERN).noOcclusion()), 0);
    public static final RegistryObject<Block> AIR_CONDITIONER = registerBlock("air_conditioner",
            () -> new AirConditioner(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> VHS_PLAYER = registerBlock("vhs_player",
            () -> new VhsPlayer(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)), 0);
    public static final RegistryObject<Block> DVD_PLAYER = registerBlock("dvd_player",
            () -> new DvdPlayer(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)), 0);
    public static final RegistryObject<Block> BLUEMAN_CONSOLE = registerBlock("blueman_console",
            () -> new BluemanConsole(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> WHITE_FAN = registerBlock("white_fan",
            () -> new StandingFanBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)), 0);
    public static final RegistryObject<Block> GRAY_FAN = registerBlock("gray_fan",
            () -> new StandingFanBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)), 0);
    public static final RegistryObject<Block> BLACK_FAN = registerBlock("black_fan",
            () -> new StandingFanBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)), 0);

    // Movie Theater Decorative Blocks
    public static final RegistryObject<Block> THEATER_SEAT = registerBlock("theater_seat",
            () -> new TheaterSeat(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD)), 0);
    public static final RegistryObject<Block> THEATER_SEAT_CONTINUOUS = registerBlock("theater_seat_cont",
            () -> new TheaterSeat(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD)), 0);
    public static final RegistryObject<Block> POPCORN_MACHINE = registerBlock("popcorn_machine",
            () -> new DoubleTallDecorationBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).lightLevel(s -> 5)), 0);
    public static final RegistryObject<Block> TICKET_TELLER_WINDOW = registerBlock("ticket_teller_window",
            () -> new TicketTellerWindowBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> VELVET_ROPE_FENCE = registerBlock("velvet_rope_fence",
            () -> new CustomFenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOL).strength(1.1F,2.5F)), 0);

    // General Purpose Decorative Blocks
    public static final RegistryObject<Block> REFRESHMENT_MACHINE = registerBlock("refreshment_machine",
            () -> new DoubleTallDecorationBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).lightLevel(s -> 7)), 0);
    public static final RegistryObject<Block> BLUEYBOX = registerBlock("blueybox",
            () -> new BlueyBoxBlock(BlockBehaviour.Properties.of().sound(SoundType.NETHERITE_BLOCK).lightLevel(s -> 12)), 0);
    public static final RegistryObject<Block> BLUE_SODA_MACHINE = registerBlock("blue_soda_machine",
            () -> new DoubleTallDecorationBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).lightLevel(s -> 12)), 0);
    public static final RegistryObject<Block> RED_SODA_MACHINE = registerBlock("red_soda_machine",
            () -> new DoubleTallDecorationBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).lightLevel(s -> 12)), 0);
    public static final RegistryObject<Block> CASH_REGISTER = registerBlock("cash_register",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.NETHERITE_BLOCK).lightLevel(s -> 5)), 0);
    public static final RegistryObject<Block> BLUE_VENDING_MACHINE = registerBlock("blue_vending_machine",
            () -> new VendingMachine(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).lightLevel(s -> 12)), 0);
    public static final RegistryObject<Block> RED_VENDING_MACHINE = registerBlock("red_vending_machine",
            () -> new VendingMachine(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).lightLevel(s -> 12)), 0);
    public static final RegistryObject<Block> COFFEE_MACHINE = registerBlock("coffee_machine",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)), 0);
    public static final RegistryObject<Block> PAYPHONE = registerBlock("payphone",
            () -> new Payphone(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> PAYPHONE_SEETHROUGH = registerBlock("payphone_seethrough",
            () -> new Payphone(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> GAS_PUMP = registerBlock("gas_pump",
            () -> new GasPump(BlockBehaviour.Properties.of().sound(SoundType.NETHERITE_BLOCK)), 0);
    public static final RegistryObject<Block> GAS_CAN = registerBlock("gas_can",
            () -> new GasCan(BlockBehaviour.Properties.of().sound(SoundType.CANDLE)), 0);
    public static final RegistryObject<Block> STORE_SHELF = registerBlock("store_shelf",
            () -> new StoreShelfBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)), 0);
    public static final RegistryObject<Block> STORE_FREEZER = registerBlock("store_freezer",
            () -> new StoreFreezer(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)), 0);
    public static final RegistryObject<Block> FRIER = registerBlock("frier",
            () -> new ToggledStateBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)), 0);
    public static final RegistryObject<Block> BLACK_TELEPHONE = registerBlock("black_telephone",
            () -> new OldTelephone(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)), 0);
    public static final RegistryObject<Block> WHITE_TELEPHONE = registerBlock("white_telephone",
            () -> new OldTelephone(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)), 0);
    public static final RegistryObject<Block> STORE_NUMBER_SIGN = registerBlock("store_number_sign",
            () -> new StoreNumberSign(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).lightLevel(s -> 15)), 0);
    public static final RegistryObject<Block> FREEZER = registerBlock("freezer",
            () -> new Freezer(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> FRENCH_PRESS = registerBlock("french_press",
            () -> new FrenchPress(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)), 0);
    public static final RegistryObject<Block> FRIDGE = registerBlock("fridge",
            () -> new FridgeBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> GRAPHICS_CARD = registerBlock("graphics_card",
            () -> new GraphicsCard(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> MOBILE_PHONE = registerBlock("mobile_phone",
            () -> new MobilePhone(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> HARD_DRIVE = registerBlock("hard_drive",
            () -> new GraphicsCard(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)), 0);
    public static final RegistryObject<Block> DRYER = registerBlock("dryer",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> WASHER = registerBlock("washer",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> HOTTUB = registerBlock("hottub",
            () -> new Hottub(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> SATELLITE_DISH = registerBlock("satellite_dish",
            () -> new SatelliteBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> ANTENNA = registerBlock("antenna",
            () -> new DoubleTallDecorationBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> BLUEYTOSH_LAPTOP = registerBlock("blueytosh_laptop",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noCollission()), 0);
    public static final RegistryObject<Block> BLUEYTOSH_LAPTOP_OLD = registerBlock("blueytosh_laptop_old",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noCollission()), 0);
    public static final RegistryObject<Block> BLUEYDOWS_LAPTOP = registerBlock("blueydows_laptop",
            () -> new BlueydlaptopBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noCollission()), 0);
    public static final RegistryObject<Block> TOASTER = registerBlock("toaster",
            () -> new Toaster(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)), 0);
    public static final RegistryObject<Block> MICROWAVE = registerBlock("microwave",
            () -> new Microwave(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)), 0);
    public static final RegistryObject<Block> WHEELBARROW = registerBlock("wheelbarrow",
            () -> new Wheelbarrow(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> STOVE_HOOD = registerBlock("stove_hood",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).lightLevel(s -> 5).noCollission()), 0);
    public static final RegistryObject<Block> STOVE = registerBlock("stove",
            () -> new Stove(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)), 0);
    public static final RegistryObject<Block> SOLAR_PANEL = registerBlock("solar_panel",
            () -> new DoubleTallDecorationBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> STORE_STAND = registerBlock("store_stand",
            () -> new DoubleTallDecorationBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> OLD_FLAT_COMPUTER = registerBlock("old_flat_computer",
            () -> new OldFlatComputer(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> SHOPPING_CART_MOVER = registerBlock("shopping_cart_mover",
            () -> new Wheelbarrow(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> SHOPPING_CART = registerBlock("shopping_cart",
            () -> new Wheelbarrow(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> MINIGOLF_FLAG = registerBlock("minigolf_flag",
            () -> new DoubleTallDecorationBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).noCollission()), 0);
    public static final RegistryObject<Block> OVEN = registerBlock("oven",
            () -> new Oven(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> ICECREAM_MACHINE = registerBlock("icecream_machine",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> INTERNET_MODEM = registerBlock("internet_modem",
            () -> new InternetRouter(BlockBehaviour.Properties.of().lightLevel(s -> 5)), 0);
    public static final RegistryObject<Block> INTERNET_ROUTER = registerBlock("internet_router",
            () -> new InternetRouter(BlockBehaviour.Properties.of().lightLevel(s -> 5)), 0);
    public static final RegistryObject<Block> NEWER_INTERNET_ROUTER = registerBlock("internet_router_newer",
            () -> new InternetRouter(BlockBehaviour.Properties.of().lightLevel(s -> 5)), 0);
    public static final RegistryObject<Block> WIFI_ROUTER = registerBlock("wifi_router",
            () -> new OldFlatComputer(BlockBehaviour.Properties.of().lightLevel(s -> 5)), 0);
    public static final RegistryObject<Block> OPEN_SIGN = registerBlock("open_sign",
            () -> new OpenSign(BlockBehaviour.Properties.of()
                    .lightLevel(openSignLitEmission(15))), 0);
    public static final RegistryObject<Block> RECYCLE_BIN = registerBlock("recycle_bin",
            () -> new RecycleBin(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE).strength(1F,1F)), 0);
    public static final RegistryObject<Block> SERVER_RACK = registerBlock("server_rack",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> MODERN_PC_MONITOR = registerBlock("pc_screen",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> OLD_PC_MONITOR = registerBlock("pc_monitor",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> OLD_PC = registerBlock("old_pc",
            () -> new OldPC(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> PAPER_TOWEL = registerBlock("paper_towel",
            () -> new HazardSignBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).sound(SoundType.WOOL)), 0);
    public static final RegistryObject<Block> AISLE_SIGN = registerBlock("aisle_sign",
            () -> new AisleSign(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)), 0);
    public static final RegistryObject<Block> TRAFFIC_CONTROL_BOX = registerBlock("traffic_control_box",
            () -> new TrafficControlBox(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> CELL_MICROWAVE_TRANSMITTER = registerBlock("microwave_transmitter",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> CELL_TRANSMITTER = registerBlock("cell_transmitter",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> CELL_MULTI_TRANSMITTER = registerBlock("cell_multi_transmitter",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> CELL_MULTI_ANGLED_TRANSMITTER = registerBlock("cell_multi_angled_transmitter",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> OLD_MICROWAVE_TRANSMITTER = registerBlock("old_microwave_transmitter",
            () -> new MicrowaveTransmitter(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> STONE_TABLE = registerBlock("stone_table",
            () -> new ConnectedTableBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)), 0);
    public static final RegistryObject<Block> GOLD_TABLE = registerBlock("gold_table",
            () -> new ConnectedTableBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> SCULK_TABLE = registerBlock("sculk_table",
            () -> new ConnectedTableBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.SCULK_CATALYST)), 0);
    public static final RegistryObject<Block> NETHER_BRICK_TABLE = registerBlock("nether_brick_table",
            () -> new ConnectedTableBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)), 0);
    public static final RegistryObject<Block> PRISMARINE_TABLE = registerBlock("prismarine_table",
            () -> new ConnectedTableBlock(BlockBehaviour.Properties.copy(Blocks.PRISMARINE)), 0);
    public static final RegistryObject<Block> PURPUR_TABLE = registerBlock("purpur_table",
            () -> new ConnectedTableBlock(BlockBehaviour.Properties.copy(Blocks.PURPUR_BLOCK)), 0);
    public static final RegistryObject<Block> QUARTZ_TABLE = registerBlock("quartz_table",
            () -> new ConnectedTableBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK)), 0);



    // Podiums and Statues
    public static final RegistryObject<Block> DUCK_STATUE = registerBlock("duck_statue",
            () -> new Podium(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)), 0);


    // Bathroom/Restroom Stuff
    public static final RegistryObject<Block> TOILET = registerBlock("toilet",
            () -> new Toilet(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> SMALL_SINK = registerBlock("small_sink",
            () -> new SmallSink(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> FANCY_SINK = registerBlock("fancy_sink",
            () -> new FancySink(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> SHOWER_HANDLES = registerBlock("shower_handles",
            () -> new ShowerHandles(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SHOWER_HEAD = registerBlock("shower_head",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().noCollission()), 0);
    public static final RegistryObject<Block> TOILET_PAPER = registerBlock("toilet_paper",
            () -> new HazardSignBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).sound(SoundType.WOOL)), 0);


    // Misc
    public static final RegistryObject<Block> DOG_HOUSE = registerBlock("dog_house",
            () -> new Doghouse(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD)), 0);
    public static final RegistryObject<Block> LOVE_SEAT = registerBlock("love_seat",
            () -> new LoveSeat(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD)), 0);
    public static final RegistryObject<Block> LOVE_COUCH = registerBlock("love_couch",
            () -> new LoveCouch(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD)), 0);
    public static final RegistryObject<Block> BLUEMAN_STATUE = registerBlock("blueman_statue",
            () -> new BluemanStatue(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD)), 0);
    public static final RegistryObject<Block> MRPUPPY = registerBlock("mrpuppy",
            () -> new MrPuppy(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL)), 0);
    public static final RegistryObject<Block> HOME_BREAKER = registerBlock("home_breaker",
            () -> new HomeBreaker(BlockBehaviour.Properties.of().strength(1f).sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> PRINTER = registerBlock("printer",
            () -> new Printer(BlockBehaviour.Properties.of().strength(1f).sound(SoundType.LANTERN)), 0);
    public static final RegistryObject<Block> PROJECTOR = registerBlock("projector",
            () -> new Projector(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)), 0);
    public static final RegistryObject<Block> MINI_FRIDGE = registerBlock("mini_fridge",
            () -> new ToggledStateBlock(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> CRIB = registerBlock("crib",
            () -> new Crib(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> THERMOMETER = registerBlock("thermometer",
            () -> new Thermometer(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)), 0);
    public static final RegistryObject<Block> CHEMICAL_TUBE = registerBlock("chemical_tube",
            () -> new ChemicalTube(BlockBehaviour.Properties.copy(Blocks.GLASS).strength(0.5F,1F).sound(SoundType.GLASS).noOcclusion()), 0);
    public static final RegistryObject<Block> WATER_FOUNTAIN = registerBlock("water_fountain",
            () -> new WaterFountain(BlockBehaviour.Properties.of().strength(1f).sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> TOASTER_OVEN = registerBlock("toaster_oven",
            () -> new ToasterOven(BlockBehaviour.Properties.of().strength(1f).sound(SoundType.LANTERN)), 0);
    public static final RegistryObject<Block> DISHWASHER_WALL = registerBlock("dishwasher_wall",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(1F,10F)), 0);
    public static final RegistryObject<Block> OFFICE_PHONE = registerBlock("office_phone",
            () -> new OfficePhone(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).strength(1F,2F)), 0);
    public static final RegistryObject<Block> PORTABLE_DISH_WASHER = registerBlock("portable_dish_washer",
            () -> new PortableDishwasher(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).strength(0.95F,5F)), 0);
    public static final RegistryObject<Block> STANDING_VACUUM = registerBlock("vacuum_standing",
            () -> new StandingVacuum(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE).strength(0.5F,1F)), 0);
    public static final RegistryObject<Block> SHOP_VACUUM = registerBlock("shop_vac",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE).strength(1F,1F)), 0);
    public static final RegistryObject<Block> BLENDER = registerBlock("blender",
            () -> new Blender(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE).strength(1F,1F)), 0);
    public static final RegistryObject<Block> FOOD_PROCESSOR = registerBlock("food_processor",
            () -> new Blender(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE).strength(1F,1F)), 0);
    public static final RegistryObject<Block> INSTANT_POT = registerBlock("instant_pot",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).strength(1.1F,2.5F)), 0);
    public static final RegistryObject<Block> RICE_COOKER = registerBlock("rice_cooker",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).strength(1.05F,1.5F)), 0);
    public static final RegistryObject<Block> SLOW_COOKER = registerBlock("slow_cooker",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).strength(1F,1.25F)), 0);
    public static final RegistryObject<Block> STAND_MIXER = registerBlock("stand_mixer",
            () -> new StandMixer(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> JUICER = registerBlock("juicer",
            () -> new StandMixer(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> WAFFLE_IRON = registerBlock("waffle_iron",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).strength(1F,1F).noCollission()), 0);
    public static final RegistryObject<Block> BREAD_MACHINE = registerBlock("bread_machine",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).strength(1F,1F)), 0);
    public static final RegistryObject<Block> ICE_CREAM_MAKER = registerBlock("ice_cream_maker",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).strength(1F,1F)), 0);
    public static final RegistryObject<Block> YOGURT_MAKER = registerBlock("yogurt_maker",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).strength(1F,1F)), 0);
    public static final RegistryObject<Block> COFFEE_GRINDER = registerBlock("coffee_grinder",
            () -> new Blender(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).strength(1F,1F)), 0);
    public static final RegistryObject<Block> PANINI_MAKER = registerBlock("panini_maker",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).strength(1F,1F)), 0);
    public static final RegistryObject<Block> FOOD_DEHYDRATOR = registerBlock("food_dehydrator",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).strength(1F,1F)), 0);
    public static final RegistryObject<Block> KITCHEN_SINK = registerBlock("kitchen_sink",
            () -> new KitchenSink(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> GARDEN_GNOME = registerBlock("garden_gnome",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE).strength(1.25F)), 0);
    public static final RegistryObject<Block> WATER_SOFTENER = registerBlock("water_softener",
            () -> new DoubleTallDecorationBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(1F,2F)), 0);
    public static final RegistryObject<Block> SALT_TANK = registerBlock("salt_tank",
            () -> new SaltTank(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(1F,2F)), 0);
    public static final RegistryObject<Block> SEWING_MACHINE = registerBlock("sewing_machine",
            () -> new StandMixer(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> AUDIO_CONTROLLER = registerBlock("audio_controller",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1F,2.25F)), 0);
    public static final RegistryObject<Block> GENERATOR = registerBlock("generator",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).strength(1.1F,5.25F)), 0);
    public static final RegistryObject<Block> IRONING_TABLE = registerBlock("ironing_table",
            () -> new OperationTable(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).strength(1F)), 0);
    public static final RegistryObject<Block> STEAM_CLEANER = registerBlock("steam_cleaner",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).strength(1F)), 0);
    public static final RegistryObject<Block> HUMIDIFIER = registerBlock("humidifier",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE_TILES).strength(1F)), 0);
    public static final RegistryObject<Block> DEHUMIDIFIER = registerBlock("dehumidifier",
            () -> new Dehumidifier(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> AIR_PURIFIER = registerBlock("air_purifier",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE_TILES).strength(1F)), 0);
    public static final RegistryObject<Block> SPACE_HEATER = registerBlock("space_heater",
            () -> new SpaceHeater(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE_TILES).strength(1F).lightLevel(s -> 7)), 0);
    public static final RegistryObject<Block> CEILING_FAN = registerBlock("ceiling_fan",
            () -> new CeilingFan(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE).strength(1F,2F)), 0);
    public static final RegistryObject<Block> SMOKER_GRILL = registerBlock("smoker_grill",
            () -> new SmokerGrill(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE_BRICKS).strength(1.25F,2F).lightLevel(enabledLitBlockEmission(10))), 0);
    public static final RegistryObject<Block> COTTON_CANDY_MAKER = registerBlock("cotton_candy_maker",
            () -> new DoubleTallDecorationBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).strength(1F)), 0);
    public static final RegistryObject<Block> CARNIVAL_AWNING = registerBlock("carnival_awning",
            () -> new CarnivalAwning(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL)), 0);
    public static final RegistryObject<Block> PORTA_POTTY = registerBlock("porta_potty",
            () -> new PortaPotty(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL)), 0);
    public static final RegistryObject<Block> WARDEN_TROPHY = registerBlock("warden_trophy",
            () -> new WardenTrophy(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);

    // Finale Update #1 Thingamajigs Features
    public static final RegistryObject<Block> FOOSBALL_TABLE = registerBlock("foosball_table",
            () -> new AirConditioner(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> CLAW_MACHINE = registerBlock("claw_machine",
            () -> new DoubleTallDecorationBlock(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> GUMBALL_MACHINE = registerBlock("gumball_machine",
            () -> new DoubleTallDecorationBlock(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> HAMMER_MACHINE = registerBlock("hammer_machine",
            () -> new DoubleTallDecorationBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)), 0);
    public static final RegistryObject<Block> WACK_MACHINE = registerBlock("wack_machine",
            () -> new DoubleTallDecorationBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)), 0);
    public static final RegistryObject<Block> AIR_HOCKEY_TABLE = registerBlock("air_hockey_table",
            () -> new AirConditioner(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)), 0);
    public static final RegistryObject<Block> BUTTER_CHURNER = registerBlock("butter_churner",
            () -> new DoubleTallDecorationBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> FIRE_ESCAPE_LADDER = registerBlock("fire_escape_ladder",
            () -> new FireEscapeLadder(BlockBehaviour.Properties.of().noOcclusion()), 0);
    public static final RegistryObject<Block> CATWALK_CENTER = registerBlock("catwalk_center",
            () -> new CatWalkCenter(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> CATWALK = registerBlock("catwalk",
            () -> new CatWalk(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> MYSTERIOUS_ONE_COUCH = registerBlock("mysterious_couch",
            () -> new LoveCouch(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL)), 0);
    public static final RegistryObject<Block> GENERAL_DIGITAL_PHONE = registerBlock("general_digital_phone",
            () -> new OfficePhone(BlockBehaviour.Properties.copy(Blocks.SNOW_BLOCK)), 0);
    public static final RegistryObject<Block> ZOMBIE_PLUSHIE = registerBlock("zombie_plushie",
            () -> new ReindeerPlushy(BlockBehaviour.Properties.copy(Blocks.SNOW_BLOCK).sound(SoundType.WOOL)), 0);
    public static final RegistryObject<Block> STEVE_PLUSHY = registerBlock("steve_plushy",
            () -> new ReindeerPlushy(BlockBehaviour.Properties.copy(Blocks.SNOW_BLOCK).sound(SoundType.WOOL)), 0);
    public static final RegistryObject<Block> VHS_COLLECTION = registerBlock("vhs_collection",
            () -> new VHSCollection(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1F,5F)), 0);

    // Finale update #2 features
    public static final RegistryObject<Block> DVD_COLLECTION = registerBlock("dvd_collection",
            () -> new VHSCollection(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1F,5F)), 0);
    public static final RegistryObject<Block> SHOPPING_BASKET = registerBlock("shopping_basket",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1F)), 0);
    public static final RegistryObject<Block> SHOPPING_BASKET_PILE = registerBlock("shopping_basket_pile",
            () -> new DoubleTallDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.25F)), 0);
    public static final RegistryObject<Block> BLUEYSNAP_CONSOLE = registerBlock("blueysnap_console",
            () -> new Blueysnapblock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1F)), 0);
    public static final RegistryObject<Block> BLUEYSNAP_BASE = registerBlock("blueysnap_base",
            () -> new Blueysnapblock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1F)), 0);
    public static final RegistryObject<Block> BEAKER = registerBlock("beaker",
            () -> new SmallGlassStorageThing(BlockBehaviour.Properties.copy(Blocks.GLASS)), 0);
    public static final RegistryObject<Block> FLASK = registerBlock("flask",
            () -> new SmallGlassStorageThing(BlockBehaviour.Properties.copy(Blocks.GLASS)), 0);
    public static final RegistryObject<Block> MICROSCOPE = registerBlock("microscope",
            () -> new StandMixer(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1F)), 0);
    public static final RegistryObject<Block> OLD_WOODEN_PHONE = registerBlock("old_wooden_phone",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1F,5F)), 0);
    public static final RegistryObject<Block> BATHTUB_NOZZLE = registerBlock("bathtub_nozzle",
            () -> new BathtubNozzle(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> GARDEN_HOSE = registerBlock("garden_hose",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.CALCITE).noCollission()), 0);
    public static final RegistryObject<Block> WOOD_DUCK = registerBlock("wood_duck",
            () -> new ReindeerPlushy(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noCollission()), 0);
    public static final RegistryObject<Block> WOOD_CAR = registerBlock("wood_car",
            () -> new ReindeerPlushy(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noCollission()), 0);
    public static final RegistryObject<Block> PHONE_SWITCHER = registerBlock("phone_switcher",
            () -> new PhoneSwitcher(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> HOTDOG_ROTATOR = registerBlock("hotdog_rotator",
            () -> new HotdogRotator(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> LAWN_MOWER = registerBlock("lawn_mower",
            () -> new Wheelbarrow(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> DIVING_BOARD = registerBlock("diving_board",
            () -> new DivingBoard(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> WATER_SLIDE = registerBlock("water_slide",
            () -> new WaterSlide(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SLUSHY_MACHINE = registerBlock("slushy_machine",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).strength(1F,2F)), 0);
    public static final RegistryObject<Block> TOY_BOX = registerBlock("toy_box",
            () -> new AirConditioner(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD)), 0);
    public static final RegistryObject<Block> BABY_CARRIAGE = registerBlock("baby_carriage",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.CALCITE)), 0);
    public static final RegistryObject<Block> CONVENIENCE_SHELF = registerBlock("convenience_shelf",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE_BRICKS)), 0);
    public static final RegistryObject<Block> CREEPER_PLUSHY = registerBlock("creeper_plushy",
            () -> new ReindeerPlushy(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).sound(SoundType.WOOL).noCollission()), 0);

    // end of finale updates (not the actual final updates per-say, just final listed features list update features) features

    // Start Features 1.19.3+
    public static final RegistryObject<Block> FEATURED_CORDLESS_PHONE = registerBlock("featured_cordless_phone",
            () -> new CordlessPhoneBase(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.LANTERN)), 0);
    public static final RegistryObject<Block> SMARTPHONE = registerBlock("smartphone",
            () -> new MobilePhone(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.CALCITE)), 0);
    public static final RegistryObject<Block> POOPSHELF = registerBlock("poopshelf",
            () -> new PoopBookshelf(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);

    public static final RegistryObject<Block> FULL_POOP_BLOCK = registerBlock("full_poop_block",
            () -> new FullPoopBlock(BlockBehaviour.Properties.copy(Blocks.MOSS_BLOCK)), 0);
    //
    public static final RegistryObject<Block> FIRE_DETECTOR = registerBlock("fire_detector",
            () -> new FireDetector(BlockBehaviour.Properties.of()), 0);

    public static final RegistryObject<Block> FIRE_EXTINGUISHER = registerBlock("fire_extinguisher",
            () -> new FireExtinguisher(BlockBehaviour.Properties.of()), 0);

    public static final RegistryObject<Block> HISTORIAN_BOOKSHELF = registerBlock("historian_bookshelf",
            () -> new CustomBookshelf(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    //
    public static final RegistryObject<Block> CHECKBOARD_WOOL = registerBlock("checkerboard_wool",
            () -> new CheckerboardWool(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).mapColor(MapColor.TERRACOTTA_WHITE)), 0);
    //
    public static final RegistryObject<Block> STONE_PILLAR = registerBlock("stone_pillar",
            () -> new ConnectingVerticalPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)), 0);
    public static final RegistryObject<Block> STONE_BRICK_PILLAR = registerBlock("stone_brick_pillar",
            () -> new ConnectingVerticalPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)), 0);
    public static final RegistryObject<Block> CHISELED_STONE_BRICK_PILLAR = registerBlock("chiseled_stone_brick_pillar",
            () -> new ConnectingVerticalPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)), 0);

    //
    public static final RegistryObject<Block> DOUBLE_WHITE_ASPHALT = registerBlock("double_white_asphalt",
            () -> new MarkedAsphalt(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops()), 0);
    public static final RegistryObject<Block> DOUBLE_YELLOW_ASPHALT = registerBlock("double_yellow_asphalt",
            () -> new MarkedAsphalt(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops()), 0);
    public static final RegistryObject<Block> DOUBLE_CORNER_WHITE_ASPHALT = registerBlock("double_corner_white_asphalt",
            () -> new MarkedAsphalt(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops()), 0);
    public static final RegistryObject<Block> DOUBLE_CORNER_YELLOW_ASPHALT = registerBlock("double_corner_yellow_asphalt",
            () -> new MarkedAsphalt(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops()), 0);


    //
    public static final RegistryObject<Block> CRYSTALINE_STONE = registerBlock("crystaline_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F)
            ), 0);
    public static final RegistryObject<Block> INDENTED_STONE = registerBlock("indented_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F)
            ), 0);
    public static final RegistryObject<Block> PANEL_STONE = registerBlock("panel_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F)
            ), 0);
    //
    public static final RegistryObject<Block> PANEL_STONE_BRICKS = registerBlock("panel_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F)
            ), 0);
    public static final RegistryObject<Block> MOSSY_PANEL_STONE_BRICKS = registerBlock("mossy_panel_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F)
            ), 0);
    public static final RegistryObject<Block> CRACKED_PANEL_STONE_BRICKS = registerBlock("cracked_panel_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F)
            ), 0);
    public static final RegistryObject<Block> CHISELED_PANEL_STONE_BRICKS = registerBlock("chiseled_panel_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F)
            ), 0);

    //

    public static final RegistryObject<Block> SIDEWALK_SLAB = registerBlock("sidewalk_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(1F)), 0);
    public static final RegistryObject<Block> CRACKED_SIDEWALK_SLAB = registerBlock("cracked_sidewalk_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(1F)), 0);
    public static final RegistryObject<Block> SECTIONED_SIDEWALK_SLAB = registerBlock("sectioned_sidewalk_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(1F)), 0);
    public static final RegistryObject<Block> BLOCKED_SIDEWALK_SLAB = registerBlock("blocked_sidewalk_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().strength(1F)), 0);

    //
    public static final RegistryObject<Block> ASPHALT_SLAB = registerBlock("asphalt_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(2f)
                    .requiresCorrectToolForDrops()
                    .friction(0.4f)
                    .sound(SoundType.TUFF)), 0);
    public static final RegistryObject<Block> ASPHALT_OK_SLAB = registerBlock("ok_asphalt_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(1.9f)
                    .requiresCorrectToolForDrops()
                    .friction(0.45f)
                    .sound(SoundType.TUFF)), 0);
    public static final RegistryObject<Block> ASPHALT_MEDIOCRE_SLAB = registerBlock("mediocre_asphalt_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(1.5f)
                    .requiresCorrectToolForDrops()
                    .friction(0.5f)
                    .sound(SoundType.TUFF)), 0);
    public static final RegistryObject<Block> ASPHALT_OLD_SLAB = registerBlock("old_asphalt_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(1.2f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.TUFF)), 0);

    // 1.19.3+ signs
    public static final RegistryObject<Block> DIVIDER = registerBlock("divider",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> BPTS = registerBlock("bpts",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> UTIL_WORK_AHEAD = registerBlock("util_work_ahead",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> DETOUR_ARROW_LEFT = registerBlock("detour_arrow_left",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> DETOUR_ARROW_RIGHT = registerBlock("detour_arrow_right",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> ONE_WAY_LEFT = registerBlock("one_way_left",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> ONE_WAY_RIGHT = registerBlock("one_way_right",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> BLASTING_ZONE_AHEAD = registerBlock("blasting_zone_ahead",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);

    public static final RegistryObject<Block> DIVIDED_WAY = registerBlock("divided_way",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);


    // 1.19.3+ other
    public static final RegistryObject<Block> STOP_GATE = registerBlock("stop_gate",
            () -> new StopGate(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> ARROW_BOARD = registerBlock("arrow_board",
            () -> new ArrowBoard(BlockBehaviour.Properties.of().lightLevel(modeLitBlockEmission(5))), 0);


    // 1.19.3+ traffic signals
    public static final RegistryObject<Block> HAWK_SIGNAL = registerBlock("hawk_signal",
            () -> new HawkSignal(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> YELLOW_BEACON = registerBlock("yellow_beacon",
            () -> new TrafficBeacon(BlockBehaviour.Properties.of().lightLevel(s -> 10)), 0);
    public static final RegistryObject<Block> RED_BEACON = registerBlock("red_beacon",
            () -> new TrafficBeacon(BlockBehaviour.Properties.of().lightLevel(s -> 10)), 0);
    public static final RegistryObject<Block> ARROW_BEACON = registerBlock("arrow_beacon",
            () -> new ArrowBeacon(BlockBehaviour.Properties.of().lightLevel(s -> 10)), 0);


    // horizontal traffic signals
    // main signal (green to red & red to green)
    public static final RegistryObject<Block> HORIZONTAL_TRAFFIC_SIGNAL_1 = registerBlock("horizontal_traffic_signal",
            () -> new HorizontalTrafficSignal(BlockBehaviour.Properties.of().lightLevel(s -> 10)), 0);
    // flashing red variant
    public static final RegistryObject<Block> HORIZONTAL_TRAFFIC_SIGNAL_2 = registerBlock("horizontal_traffic_signal_fr",
            () -> new HorizontalTrafficSignal(BlockBehaviour.Properties.of().lightLevel(s -> 10)), 0);
    // flashing yellow variant
    public static final RegistryObject<Block> HORIZONTAL_TRAFFIC_SIGNAL_3 = registerBlock("horizontal_traffic_signal_fy",
            () -> new HorizontalTrafficSignal(BlockBehaviour.Properties.of().lightLevel(s -> 10)), 0);



    // 1.19.3+ decorative full or other blocks
    public static final RegistryObject<Block> FIREOUS_GLAZED_TERRACOTTA = registerBlock("fireous_glazed_terracotta",
            () -> new GlazedTerracottaBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.4F).requiresCorrectToolForDrops()), 0);
    public static final RegistryObject<Block> DARK_FIREOUS_GLAZED_TERRACOTTA = registerBlock("dark_fireous_glazed_terracotta",
            () -> new GlazedTerracottaBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.4F).requiresCorrectToolForDrops()), 0);
    public static final RegistryObject<Block> SCREEN = registerBlock("screen",
            () -> new GlassLikeBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.SCAFFOLDING).strength(1.1F)), 0);

    // 1.19.3+ plants and flowers
    public static final RegistryObject<Block> BULBY_FLOWER = registerBlock("bulby_flower",
            () -> new FlowerBlock(MobEffects.INVISIBILITY, 5, BlockBehaviour.Properties.copy(Blocks.POPPY).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)),0);
    public static final RegistryObject<Block> DROOPY_FLOWER = registerBlock("droopy_flower",
            () -> new DroopyFlower(MobEffects.CONFUSION, 10, BlockBehaviour.Properties.copy(Blocks.BLUE_ORCHID).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)),0);

    // potted plants and flowers (DONT REGISTER WITH CREATIVE MODE TABS)
    public static final RegistryObject<Block> POTTED_BULBY_FLOWER = registerBlock("potted_bulby_flower",
            () -> new FlowerPotBlock(ThingamajigsBlocks.BULBY_FLOWER.get(),BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY).instabreak().noOcclusion()),0);
    public static final RegistryObject<Block> POTTED_DROOPY_FLOWER = registerBlock("potted_droopy_flower",
            () -> new FlowerPotBlock(ThingamajigsBlocks.DROOPY_FLOWER.get(),BlockBehaviour.Properties.copy(Blocks.POTTED_BLUE_ORCHID).instabreak().noOcclusion()),0);
    //
    public static final RegistryObject<Block> LIBRARY_STOOL = registerBlock("library_stool",
            () -> new LibraryStool(BlockBehaviour.Properties.of()), 0);

    //
    public static final RegistryObject<Block> BIOHAZARD_BIN = registerBlock("biohazard_bin",
            () -> new BiohazardBin(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> RADIOACTIVE_BARREL = registerBlock("radioactive_barrel",
            () -> new RadioactiveBarrel(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> TEACHING_BOARD = registerBlock("teaching_board",
            () -> new TeachingBoard(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> LOCKER = registerBlock("locker",
            () -> new Locker(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SCHOOL_DESK = registerBlock("school_desk",
            () -> new SchoolDesk(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> TALL_LAMP = registerBlock("tall_lamp",
            () -> new TallLamp(BlockBehaviour.Properties.of().lightLevel(onLitBlockEmission(10))), 0);
    public static final RegistryObject<Block> PUNCHING_BAG = registerBlock("punching_bag",
            () -> new PunchingBag(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL)), 0);

    // More TVs
    public static final RegistryObject<Block> CLASSIC_TV = registerBlock("classic_tv",
            () -> new ClassicTv(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    // end 1.19.3+ features list

    // Special (Railroad Crossing Related Blocks)
    public static final RegistryObject<Block> RAILROAD_CROSSING = registerBlock("railroad_crossing",
            () -> new RailroadCrossing(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> RAILROAD_CROSSING_LIGHTS = registerBlock("railroad_crossing_lights",
            () -> new RailroadCrossingLights(BlockBehaviour.Properties.of()
                    .lightLevel(rrCrossingLightsEmission(15))), 0);

    public static final RegistryObject<Block> BLUEY_MECHANICAL_BELL = registerBlock("bluey_bell",
            () -> new BlueyMechanicalBell(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> BLUEY_MECHANICAL_BELL_TWO = registerBlock("bluey_bell_two",
            () -> new BlueyMechanicalBellTwo(BlockBehaviour.Properties.of()), 0);

    public static final RegistryObject<Block> VERTICAL_T_POLE = registerBlock("vertical_t_pole",
            () -> new VerticalTPole(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> TL_CONNECTOR = registerBlock("tl_connector",
            () -> new PlusPole(BlockBehaviour.Properties.of()), 0);

    public static final RegistryObject<Block> TRANSPARENT_OFF_FAN_BLOCK = registerBlock("transparent_off_fan_block",
            () -> new TransparentFanBlock(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> TRANSPARENT_FAN_BLOCK = registerBlock("transparent_fan_block",
            () -> new TransparentFanBlock(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> TRANSPARENT_FAST_FAN_BLOCK = registerBlock("transparent_fast_fan_block",
            () -> new TransparentFanBlock(BlockBehaviour.Properties.of()), 0);

    public static final RegistryObject<Block> METAL_SCAFFOLDING = registerBlock("metal_scaffolding",
            () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().noOcclusion()), 0);

    public static final RegistryObject<Block> ULTRA_HD_TV = registerBlock("uhd_tv",
            () -> new UltraHDTV(BlockBehaviour.Properties.of()), 0);

    public static final RegistryObject<Block> RAILROAD_CROSSING_BLOCKER = registerBlock("rr_blocker",
            () -> new RailroadCrossingBlocker(BlockBehaviour.Properties.of()), 0);

    public static final RegistryObject<Block> CHISELED_TECHNO_BLOCK = registerBlock("chiseled_techno_block",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().sound(SoundType.NETHERITE_BLOCK).strength(1F,75F).requiresCorrectToolForDrops()), 0);

    // WHAT!?!? THEY'RE BACK?!? INTERNATIONAL CROSSBUCKS?!? Oh yes. They are back because I nearly forgot about them...
    public static final RegistryObject<Block> CZECH_CROSSBUCK = registerBlock("czech_crossbuck",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> FINNISH_CROSSBUCK = registerBlock("finnish_crossbuck",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> GERMAN_CROSSBUCK = registerBlock("german_crossbuck",
            () -> new ToggledStateSign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> AUSTRALIA_CROSSBUCK = registerBlock("australia_crossbuck",
            () -> new ToggledStateSign(BlockBehaviour.Properties.of()), 0);

    // Pathways
    public static final RegistryObject<Block> BROWN_PATHWAY = registerBlock("brown_pathway",
            () -> new Pathway(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).sound(SoundType.CANDLE)), 0);

    // not quite menger... but it stores many thingamajigs
    public static final RegistryObject<Block> NOT_QUITE_MENGER = registerBlock("not_quite",
            () -> new NotQuiteMengerBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)), 0);
    public static final RegistryObject<Block> FAX_MACHINE = registerBlock("fax_machine",
            () -> new FaxMachine(BlockBehaviour.Properties.of()), 0);


    public static final RegistryObject<Block> NETHER_CHISELED_BOOKSHELF = registerBlock("nether_chiseled_bookshelf",
            () -> new NetherChiseledBookshelf(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.NETHER)
                    .strength(1.5F,5.5F)
                    .requiresCorrectToolForDrops()), 0);

    public static final RegistryObject<Block> CANADIAN_CROSSBUCK = registerBlock("canadian_crossbuck",
            () -> new CrossbuckRedstone(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)));

    public static final RegistryObject<Block> JAPAN_CROSSBUCK = registerBlock("japan_crossbuck",
            () -> new Sign(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)));

    public static final RegistryObject<Block> CATEYE_CROSSBUCK = registerBlock("cateye_crossbuck",
            () -> new CrossbuckRedstone(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)));

    public static final RegistryObject<Block> INVERTED_CATEYE_CROSSBUCK = registerBlock("inverted_cateye_crossbuck",
            () -> new CrossbuckRedstone(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)));

    // uk signs
    public static final RegistryObject<Block> ASCENT = registerBlock("ascent",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> DESCENT = registerBlock("descent",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> BENDS = registerBlock("bends",
            () -> new BendsSign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> ROAD_CROSSES = registerBlock("road_crosses",
            () -> new RoadCrossesSign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> MERGES = registerBlock("merges",
            () -> new MergeSign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> CHILDREN = registerBlock("children",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> CROSSING_NO_GATES = registerBlock("crossing_no_gates",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> DANGER = registerBlock("danger",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> DUAL_ENDS = registerBlock("dual_ends",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> ELDER = registerBlock("elder",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> GATED_CROSSING = registerBlock("gated_crossing",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> GIVE_WAY = registerBlock("give_way",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> HUMP_BRIDGE = registerBlock("hump_bridge",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> MINECARTS = registerBlock("minecarts",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> NARROW_BOTH = registerBlock("narrow_both",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> NARROW_SIDES = registerBlock("narrow_sides",
            () -> new RoadSideEndsSign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> OPEN_BRIDGE = registerBlock("open_bridge",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> PEDS_AHEAD = registerBlock("peds_ahead",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> RIVERBANK = registerBlock("riverbank",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> ROAD_WORK = registerBlock("road_work",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> ROUNDABOUT_UK = registerBlock("roundabout_uk",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> SIDE_WIND = registerBlock("side_wind",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> SIGNAL_AHEAD = registerBlock("signal_ahead",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> SLIPPERY = registerBlock("slippery",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> SOFT_VERGE = registerBlock("soft_verge",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> STOP_OR_GIVEWAY_AHEAD = registerBlock("stop_or_giveway_ahead",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> TRY = registerBlock("try",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> TUNNEL = registerBlock("tunnel",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> TWO_WAY = registerBlock("two_way",
            () -> new TwoWaySign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> UNEVEN = registerBlock("uneven",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> WATER_COURSE = registerBlock("water_course",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> ZEBRA = registerBlock("zebra",
            () -> new Sign(BlockBehaviour.Properties.of()));
    // uk round signs
    public static final RegistryObject<Block> ALL_PROHIBITED = registerBlock("all_prohibited",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> HORSE_PROHIBITED = registerBlock("horse_prohibited",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> MOTOR_VEHICLES_PROHIBITED = registerBlock("motor_vehicles_prohibited",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> MOTORCYCLES_PROHIBITED = registerBlock("motorcycles_prohibited",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> NO_OVERTAKING = registerBlock("no_overtaking",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> NO_STOPPING = registerBlock("no_stopping",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> NO_WAITING = registerBlock("no_waiting",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> PEDS_PROHIBITED = registerBlock("peds_prohibited",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> SPEED_50 = registerBlock("speed_50",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> SPEED_40 = registerBlock("speed_40",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> SPEED_30 = registerBlock("speed_30",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> SPEED_20 = registerBlock("speed_20",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> SPEED_10 = registerBlock("speed_10",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> CRYSTAL_BLOCK = registerBlock("crystal_block",
            () -> new AncientRelicCrystalBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> CIRCUITS = registerBlock("circuits",
            () -> new Block(BlockBehaviour.Properties.of().strength(1F,10F).sound(SoundType.METAL)));
    public static final RegistryObject<Block> MYSTERIOUS_ONE_WOOL = registerBlock("mysterious_one_wool",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.PINK_WOOL)));
    public static final RegistryObject<Block> PIZZA_BOX = registerBlock("pizza_box",
            () -> new PizzaBox(BlockBehaviour.Properties.copy(Blocks.BAMBOO_BLOCK)));
    public static final RegistryObject<Block> CATTLE_GRID = registerBlock("cattle_grid",
            () -> new Sign(BlockBehaviour.Properties.of()));

    // more US signs
    public static final RegistryObject<Block> GRID_SIGN = registerBlock("grid_sign",
            () -> new Sign(BlockBehaviour.Properties.of()));

    // joke pc
    public static final RegistryObject<Block> EXPLODING_PC = registerBlock("olde_pc",
            () -> new ExplodingPC(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> PLATE = registerBlock("plate",
            () -> new Plate(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> CUP = registerBlock("cup",
            () -> new Cup(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> DECORATIVE_PORTAL = registerBlock("decorative_portal",
            () -> new DecorativePortalBlock(BlockBehaviour.Properties.of()
                    .lightLevel(s -> 11)
                    .hasPostProcess(ThingamajigsBlocks::always)
                    .emissiveRendering(ThingamajigsBlocks::always)));

    public static final RegistryObject<Block> COOKIE_JAR = registerBlock("cookie_jar",
            () -> new Cup(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> EATING_UTENCILS = registerBlock("eating_utencils",
            () -> new EatingUtencils(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> GREEN_HANGING_SIGN = customRegisterBlock("green_hanging_sign",
            () -> new GreenHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_HANGING_SIGN),ThingamajigsBlockTypes.GENERIC_ROAD_WOOD));
    public static final RegistryObject<Block> GREEN_WALL_HANGING_SIGN = customRegisterBlock("green_wall_hanging_sign",
            () -> new GreenWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_WALL_HANGING_SIGN),ThingamajigsBlockTypes.GENERIC_ROAD_WOOD));

    // redstone connective poles
    public static final RegistryObject<Block> VERTICAL_POLE_REDSTONE = registerBlock("vertical_pole_redstone",
            () -> new VerticalPoleRedstone(BlockBehaviour.Properties.of()));

    // start 1.20.1 features list
    public static final RegistryObject<Block> BLUEYTOSH_STUDIO = registerBlock("blueytosh_studio",
            () -> new BlueytoshStudioBlock(BlockBehaviour.Properties.of()));

    // more rr blocks
    public static final RegistryObject<Block> RR_CANTILEVER = registerBlock("rr_cantilever",
            () -> new RailroadCrossingCantilever(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> RR_CANTILEVER_LIGHTS = registerBlock("rr_cantilever_lights",
            () -> new RailroadCrossingCantileverLights(BlockBehaviour.Properties.of()
                    .lightLevel(rrCrossingCantileverLightEmission(15))));

    public static final RegistryObject<Block> RR_CANTILEVER_END = registerBlock("rr_cantilever_end",
            () -> new RailroadCrossingCantilever(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> WRONG_WAY = registerBlock("wrong_way",
            () -> new Sign(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> EBELL_ONE = registerBlock("ebell_one",
            () -> new EBellOne(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> EBELL_TWO = registerBlock("ebell_two",
            () -> new EBellTwo(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> FANCY_QUARTZ_PILLAR = registerBlock("fancy_quartz_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_PILLAR).noOcclusion()));
    public static final RegistryObject<Block> TENTH_ANNIVERSARY_CAKE = registerBlock("tenth_anniversary_cake",
            () -> new TenthAnniversaryCake(BlockBehaviour.Properties.copy(Blocks.CAKE).noOcclusion()));
    public static final RegistryObject<Block> TNT_SLAB = registerBlock("tnt_slab",
            () -> new TNTSlabBlock(BlockBehaviour.Properties.copy(Blocks.TNT)));
    public static final RegistryObject<Block> CHEESE_BLOCK = registerBlock("cheese",
            () -> new CheeseBlock(BlockBehaviour.Properties.copy(Blocks.CAKE).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> GLOWING_CHEESE_BLOCK = registerBlock("glowing_cheese",
            () -> new GlowingCheeseBlock(BlockBehaviour.Properties.copy(Blocks.CAKE).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> FLOWERING_LILY_PAD = registerBlock("flowering_lily_pad",
            () -> new FloweringLilyPadBlock(BlockBehaviour.Properties.copy(Blocks.LILY_PAD)));
    public static final RegistryObject<Block> TRIPLE_LILY_PAD = registerBlock("triple_lily_pad",
            () -> new TripleLilyPadBlock(BlockBehaviour.Properties.copy(Blocks.LILY_PAD)));

    public static final RegistryObject<Block> COLORED_GLASS = registerBlock("colored_glass",
            () -> new ColoredGlass(BlockBehaviour.Properties.of()
                    .isRedstoneConductor(ThingamajigsBlocks::never)
                    .isViewBlocking(ThingamajigsBlocks::never)
                    .isSuffocating(ThingamajigsBlocks::never)));



    // end 1.20.1 features list

    // custom chest blocks


    // custom signs
    // green road sign
    public static final RegistryObject<Block> GREEN_WALL_SIGN = customRegisterBlock("green_wall_sign",
            () -> new RoadwayWallSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_WALL_SIGN)
                    .sound(SoundType.LANTERN)
                    .mapColor(MapColor.COLOR_LIGHT_GREEN),
                    ThingamajigsBlockTypes.GENERIC_ROAD_WOOD));

    public static final RegistryObject<Block> GREEN_STANDING_SIGN = customRegisterBlock("green_standing_sign",
            () -> new RoadwayStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_SIGN)
                    .sound(SoundType.LANTERN)
                    .mapColor(MapColor.COLOR_LIGHT_GREEN),
                    ThingamajigsBlockTypes.GENERIC_ROAD_WOOD));

    public static final RegistryObject<Block> RED_WALL_SIGN = customRegisterBlock("red_wall_sign",
            () -> new RedRWWallSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_WALL_SIGN)
                    .sound(SoundType.LANTERN)
                    .mapColor(MapColor.COLOR_RED),
                    ThingamajigsBlockTypes.GENERIC_RED_ROAD_WOOD));

    public static final RegistryObject<Block> RED_STANDING_SIGN = customRegisterBlock("red_standing_sign",
            () -> new RedRWStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_SIGN)
                    .sound(SoundType.LANTERN)
                    .mapColor(MapColor.COLOR_RED),
                    ThingamajigsBlockTypes.GENERIC_RED_ROAD_WOOD));

    public static final RegistryObject<Block> BLUE_WALL_SIGN = customRegisterBlock("blue_wall_sign",
            () -> new BlueRWWallSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_WALL_SIGN)
                    .sound(SoundType.LANTERN)
                    .mapColor(MapColor.COLOR_BLUE),
                    ThingamajigsBlockTypes.GENERIC_BLUE_ROAD_WOOD));

    public static final RegistryObject<Block> BLUE_STANDING_SIGN = customRegisterBlock("blue_standing_sign",
            () -> new BlueRWStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_SIGN)
                    .sound(SoundType.LANTERN)
                    .mapColor(MapColor.COLOR_BLUE),
                    ThingamajigsBlockTypes.GENERIC_BLUE_ROAD_WOOD));

    public static final RegistryObject<Block> BROWN_WALL_SIGN = customRegisterBlock("brown_wall_sign",
            () -> new BrownRWWallSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_WALL_SIGN)
                    .sound(SoundType.LANTERN)
                    .mapColor(MapColor.COLOR_BROWN),
                    ThingamajigsBlockTypes.GENERIC_BROWN_ROAD_WOOD));

    public static final RegistryObject<Block> BROWN_STANDING_SIGN = customRegisterBlock("brown_standing_sign",
            () -> new BrownRWStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_SIGN)
                    .sound(SoundType.LANTERN)
                    .mapColor(MapColor.COLOR_BROWN),
                    ThingamajigsBlockTypes.GENERIC_BROWN_ROAD_WOOD));

    // Fluid Blocks

    public static final RegistryObject<Block> SLUDGE = registerBlock("sludge",
            () -> new SludgeBlock());
    public static final RegistryObject<Block> PURIFYING_WATER = registerBlock("purifying_water",
            () -> new PurifyingWaterBlock());

    // Lava Lamp Blocks
    public static final RegistryObject<Block> LAVA_LAMP = registerBlock("lava_lamp",
            () -> new LavaLamp(BlockBehaviour.Properties.of()), 0);


    // Blocks that have BlockEntities associated with them (attached)
    public static final RegistryObject<Block> MAILBOX = registerBlock("mailbox",
            () -> new BasicMailbox(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.LANTERN)), 0);

    public static final RegistryObject<Block> BLACK_MAILBOX = registerBlock("black_mailbox",
            () -> new BasicMailbox(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.LANTERN)), 0);

    public static final RegistryObject<Block> GREY_MAILBOX = registerBlock("grey_mailbox",
            () -> new BasicMailbox(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.LANTERN)), 0);


    // NOTE! Some block entities use vanilla menus, which may cause issues with other mods that change menu behavior for the vanilla game

    //

    // Gothic (alternative meaning) or Death Related Decorative Blocks
    public static final RegistryObject<Block> CROSS_GRAVESTONE = registerBlock("cross_gravestone",
            () -> new CrossGravestone(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)), 0);
    public static final RegistryObject<Block> STANDARD_GRAVESTONE = registerBlock("standard_gravestone",
            () -> new StandardGravestone(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)), 0);
    public static final RegistryObject<Block> PLACARD_GRAVESTONE = registerBlock("placard_gravestone",
            () -> new PlacardGravestone(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE).noCollission()), 0);
    public static final RegistryObject<Block> COFFIN = registerBlock("coffin",
            () -> new CoffinBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD)), 0);

    // Railway or Railroad Decorative Blocks
    public static final RegistryObject<Block> PURPLE_RAIL = registerBlock("purple_rail",
            () -> new PurpleRailBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).noCollission().strength(0.7F).sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> PURPLE_POWERED_RAIL = registerBlock("purple_powered_rail",
            () -> new PurplePoweredRail(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).noCollission().strength(0.7F).sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> PURPLE_ACTIVATOR_RAIL = registerBlock("purple_activator_rail",
            () -> new PurpleActivatorRail(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).noCollission().strength(0.7F).sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> PURPLE_DETECTOR_RAIL = registerBlock("purple_detector_rail",
            () -> new PurpleDetectorRail(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).noCollission().strength(0.7F).sound(SoundType.METAL)), 0);


    // Car Wash Decorative Blocks
    public static final RegistryObject<Block> CAR_WASH_DRYER = registerBlock("car_wash_dryer",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).noCollission()), 0);
    public static final RegistryObject<Block> CAR_WASH_MITTER_CURTAIN = registerBlock("car_wash_mitter_curtain",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).noCollission()), 0);
    public static final RegistryObject<Block> CAR_WASH_MIXED_BRUSH = registerBlock("car_wash_mixed_brush",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).noCollission()), 0);
    public static final RegistryObject<Block> CAR_WASH_SIGNAGE = registerBlock("car_wash_signage",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).noCollission()), 0);
    public static final RegistryObject<Block> CAR_WASH_SIGNAL = registerBlock("car_wash_signal",
            () -> new CarWashSignal(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> CAR_WASH_DRIPPER = registerBlock("car_wash_dripper",
            () -> new CarWashDripper(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> CAR_WASH_SPRAYER = registerBlock("car_wash_sprayer",
            () -> new CarWashSprayer(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> CAR_WASH_SOAPER = registerBlock("car_wash_soaper",
            () -> new CarWashSoaper(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> CAR_WASH_WAXER = registerBlock("car_wash_waxer",
            () -> new CarWashWaxer(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> CAR_WASH_TRIFOAMER = registerBlock("car_wash_trifoamer",
            () -> new CarWashTriFoamSprayer(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> CAR_WASH_BLUE_BRUSH = registerBlock("car_wash_blue_brush",
            () -> new DoubleTallDecorationBlock(BlockBehaviour.Properties.of().noCollission()), 0);
    public static final RegistryObject<Block> CAR_WASH_RED_BRUSH = registerBlock("car_wash_red_brush",
            () -> new DoubleTallDecorationBlock(BlockBehaviour.Properties.of().noCollission()), 0);
    public static final RegistryObject<Block> CAR_WASH_YELLOW_BRUSH = registerBlock("car_wash_yellow_brush",
            () -> new DoubleTallDecorationBlock(BlockBehaviour.Properties.of().noCollission()), 0);
    public static final RegistryObject<Block> CAR_WASH_TIRE_SCRUBBER = registerBlock("car_wash_tire_scrubber",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().noCollission()), 0);


    // Utility Decorative Blocks
    public static final RegistryObject<Block> AC_DUCT = registerBlock("ac_duct",
            () -> new AcDuctStraight(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> AC_DUCT_CORNER = registerBlock("ac_duct_corner",
            () -> new AcDuctCorner(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> AC_DUCT_ALLWAY = registerBlock("ac_duct_allway",
            () -> new AcDuctAllWay(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> US_OUTLET = registerBlock("us_outlet",
            () -> new WallOutletBlock(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> T_US_OUTLET = registerBlock("t_us_outlet",
            () -> new WallOutletBlock(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> UNGROUNDED_US_OUTLET = registerBlock("ungrounded_us_outlet",
            () -> new WallOutletBlock(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> USB_OUTLET = registerBlock("usb_outlet",
            () -> new WallOutletBlock(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> INTERNET_JACK_OUTLET = registerBlock("internet_jack_outlet",
            () -> new WallOutletBlock(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> UK_OUTLET = registerBlock("uk_outlet",
            () -> new WallOutletBlock(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> AUSTRALIAN_OUTLET = registerBlock("australian_outlet",
            () -> new WallOutletBlock(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> GERMAN_OUTLET = registerBlock("german_outlet",
            () -> new WallOutletBlock(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);

    // Switches & Buttons
    public static final RegistryObject<Block> BUTTON_SWITCH = registerBlock("button_switch",
            () -> new CustomLeverBlock(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> ROCKER_SWITCH = registerBlock("rocker_switch",
            () -> new CustomLeverBlock(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);

    //
    public static final RegistryObject<Block> CHAINLINK_FENCE = registerBlock("chainlink_fence",
            () -> new ChainlinkFence(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> CULVERT = registerBlock("culvert",
            () -> new CulvertBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> STONE_CULVERT = registerBlock("stone_culvert",
            () -> new CulvertBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE)), 0);
    public static final RegistryObject<Block> DIRT_CULVERT = registerBlock("dirt_culvert",
            () -> new CulvertBlock(BlockBehaviour.Properties.of().sound(SoundType.GRAVEL)), 0);
    public static final RegistryObject<Block> SAND_CULVERT = registerBlock("sand_culvert",
            () -> new CulvertBlock(BlockBehaviour.Properties.of().sound(SoundType.SAND)), 0);
    public static final RegistryObject<Block> SANDSTONE_CULVERT = registerBlock("sandstone_culvert",
            () -> new CulvertBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE)), 0);
    public static final RegistryObject<Block> BRICK_CULVERT = registerBlock("brick_culvert",
            () -> new CulvertBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE)), 0);
    public static final RegistryObject<Block> STONE_BRICK_CULVERT = registerBlock("stone_brick_culvert",
            () -> new CulvertBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE)), 0);
    public static final RegistryObject<Block> TERRACOTTA_CULVERT = registerBlock("terracotta_culvert",
            () -> new CulvertBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE)), 0);

    // Alarms
    public static final RegistryObject<Block> HORN_FIRE_ALARM = registerBlock("horn_fire_alarm",
            () -> new HornFireAlarm(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> BEEP_FIRE_ALARM = registerBlock("beep_fire_alarm",
            () -> new BeepingFireAlarm(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> LOUD_FIRE_ALARM = registerBlock("loud_fire_alarm",
            () -> new LoudFireAlarm(BlockBehaviour.Properties.of()), 0);

    // Security Cameras
    public static final RegistryObject<Block> FILM_SECURITY_CAMERA = registerBlock("film_cam",
            () -> new SecurityCameraMultidirectional(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SECURE_SECURITY_CAMERA = registerBlock("secure_cam",
            () -> new SecurityCameraMultidirectional(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> BOX_SECURITY_CAMERA = registerBlock("box_cam",
            () -> new SecurityCameraQuintDirectional(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> DOME_SECURITY_CAMERA = registerBlock("dome_cam",
            () -> new DomeSecurityCamera(BlockBehaviour.Properties.of().sound(SoundType.LANTERN).noCollission()), 0);
    public static final RegistryObject<Block> ROBOT_SECURITY_CAMERA = registerBlock("robot_cam",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)), 0);


    // Hazard Signs
    public static final RegistryObject<Block> GENERAL_HAZARD_SIGN = registerBlock("general_hazard",
            () -> new HazardSignBlock(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> FALLING_HAZARD_SIGN = registerBlock("falling_hazard",
            () -> new HazardSignBlock(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> HARDHAT_HAZARD_SIGN = registerBlock("hardhat_hazard",
            () -> new HazardSignBlock(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> POISON_HAZARD_SIGN = registerBlock("poison_hazard",
            () -> new HazardSignBlock(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> DEATH_HAZARD_SIGN = registerBlock("death_hazard",
            () -> new HazardSignBlock(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> FIRE_HAZARD_SIGN = registerBlock("fire_hazard",
            () -> new HazardSignBlock(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> RADIATION_HAZARD_SIGN = registerBlock("radiation_hazard",
            () -> new HazardSignBlock(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> BIO_HAZARD_SIGN = registerBlock("bio_hazard",
            () -> new HazardSignBlock(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> CRYO_HAZARD_SIGN = registerBlock("cryo_hazard",
            () -> new HazardSignBlock(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> BLAST_HAZARD_SIGN = registerBlock("blast_hazard",
            () -> new HazardSignBlock(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SHOCK_HAZARD_SIGN = registerBlock("shock_hazard",
            () -> new HazardSignBlock(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> NOENTRY_HAZARD_SIGN = registerBlock("noentry_hazard",
            () -> new HazardSignBlock(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> WORKERS_HAZARD_SIGN = registerBlock("workers_hazard",
            () -> new HazardSignBlock(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> RADIOWAVES_HAZARD_SIGN = registerBlock("radiowaves_hazard",
            () -> new HazardSignBlock(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> OXYGEN_HAZARD_SIGN = registerBlock("oxygen_hazard",
            () -> new HazardSignBlock(BlockBehaviour.Properties.of()), 0);


    // Arcade Decorative Blocks
    public static final RegistryObject<Block> BASKETBALL_MACHINE = registerBlock("basketball_machine",
            () -> new BasketballMachine(BlockBehaviour.Properties.of().sound(SoundType.METAL).lightLevel(s -> 12)), 0);
    public static final RegistryObject<Block> PINBALL_MACHINE = registerBlock("pinball_machine",
            () -> new BasketballMachine(BlockBehaviour.Properties.of().sound(SoundType.METAL).lightLevel(s -> 12)), 0);
    public static final RegistryObject<Block> LIGHTUP_MACHINE = registerBlock("lightup_machine",
            () -> new LightupMachine(BlockBehaviour.Properties.of().sound(SoundType.METAL).lightLevel(s -> 12)), 0);

    // Bowling Alley Decorative Blocks
    public static final RegistryObject<Block> PINK_BOWLING_BALL = registerBlock("pink_bowling_ball",
            () -> new BowlingBall(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)), 0);
    public static final RegistryObject<Block> PURPLE_BOWLING_BALL = registerBlock("purple_bowling_ball",
            () -> new BowlingBall(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)), 0);
    public static final RegistryObject<Block> BROWN_BOWLING_BALL = registerBlock("brown_bowling_ball",
            () -> new BowlingBall(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)), 0);
    public static final RegistryObject<Block> YELLOW_BOWLING_BALL = registerBlock("yellow_bowling_ball",
            () -> new BowlingBall(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)), 0);
    public static final RegistryObject<Block> BLUE_BOWLING_BALL = registerBlock("blue_bowling_ball",
            () -> new BowlingBall(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)), 0);
    public static final RegistryObject<Block> LIGHT_BLUE_BOWLING_BALL = registerBlock("light_blue_bowling_ball",
            () -> new BowlingBall(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)), 0);
    public static final RegistryObject<Block> GREEN_BOWLING_BALL = registerBlock("green_bowling_ball",
            () -> new BowlingBall(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)), 0);
    public static final RegistryObject<Block> LIME_BOWLING_BALL = registerBlock("lime_bowling_ball",
            () -> new BowlingBall(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)), 0);
    public static final RegistryObject<Block> BOWLING_FLOORING = registerBlock("bowling_flooring",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(0.5f).sound(SoundType.WOOL)), 0);
    public static final RegistryObject<Block> BOWLING_ALLEY_OILER = registerBlock("bowling_alley_oiler",
            () -> new BowlingAlleyOiler(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> PIN_SETTER = registerBlock("pin_setter",
            () -> new BowlingAlleyPinSetter(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> BOWLING_PIN = registerBlock("bowling_pin",
            () -> new BowlingPin(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> RED_BOWLING_PIN = registerBlock("red_bowling_pin",
            () -> new BowlingPin(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> DIAMOND_BOWLING_PIN = registerBlock("diamond_bowling_pin",
            () -> new BowlingPin(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> GOLD_BOWLING_PIN = registerBlock("gold_bowling_pin",
            () -> new BowlingPin(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> BOWLING_BALL_RETRIEVER = registerBlock("bowling_ball_retriever",
            () -> new BowlingBallRetriever(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> BOWLING_GAME_CONTROLLER = registerBlock("bowling_game_controller",
            () -> new BowlingBallController(BlockBehaviour.Properties.of()), 0);



    // Christmas Decorative Blocks
    public static final RegistryObject<Block> SANTA_STATUE = registerBlock("santa_statue",
            () -> new SantaStatue(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).sound(SoundType.WOOD)), 0);
    public static final RegistryObject<Block> SANTA_INFLATABLE = registerBlock("santa_inflatable",
            () -> new SantaStatue(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).sound(SoundType.WOOL)), 0);
    public static final RegistryObject<Block> SNOWMAN = registerBlock("snowman",
            () -> new Snowman(BlockBehaviour.Properties.copy(Blocks.SNOW_BLOCK).sound(SoundType.POWDER_SNOW)), 0);
    public static final RegistryObject<Block> REINDEER_PLUSHY = registerBlock("reindeer_plushy",
            () -> new ReindeerPlushy(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).sound(SoundType.WOOL).noCollission()), 0);
    public static final RegistryObject<Block> CHRISTMAS_TREE = registerBlock("christmas_tree",
            () -> new ChristmasTree(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1.25F,10F).sound(SoundType.AZALEA_LEAVES).noOcclusion().lightLevel(s -> 10)), 0);
    public static final RegistryObject<Block> CHRISTMAS_WREATH = registerBlock("christmas_wreath",
            () -> new ChristmasWreathBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).sound(SoundType.AZALEA_LEAVES).noCollission()), 0);
    public static final RegistryObject<Block> AMBER_STRING_LIGHTS = registerBlock("amber_string_lights",
            () -> new FlatWallPlaneBlock(BlockBehaviour.Properties.of().instabreak().sound(SoundType.LADDER).noCollission().lightLevel(s -> 3)
                    .hasPostProcess(ThingamajigsBlocks::always).emissiveRendering(ThingamajigsBlocks::always)), 0);
    public static final RegistryObject<Block> BLUE_STRING_LIGHTS = registerBlock("blue_string_lights",
            () -> new FlatWallPlaneBlock(BlockBehaviour.Properties.of().instabreak().sound(SoundType.LADDER).noCollission().lightLevel(s -> 3)
                    .hasPostProcess(ThingamajigsBlocks::always).emissiveRendering(ThingamajigsBlocks::always)), 0);
    public static final RegistryObject<Block> CHRISTMAS_LIGHTS = registerBlock("christmas_lights",
            () -> new FlatWallPlaneBlock(BlockBehaviour.Properties.of().instabreak().sound(SoundType.LADDER).noCollission().lightLevel(s -> 4)
                    .hasPostProcess(ThingamajigsBlocks::always).emissiveRendering(ThingamajigsBlocks::always)), 0);
    public static final RegistryObject<Block> CHRISTMAS_LIGHTS_ALT = registerBlock("christmas_lights_alt",
            () -> new FlatWallPlaneBlock(BlockBehaviour.Properties.of().instabreak().sound(SoundType.LADDER).noCollission().lightLevel(s -> 4)
                    .hasPostProcess(ThingamajigsBlocks::always).emissiveRendering(ThingamajigsBlocks::always)), 0);
    public static final RegistryObject<Block> NORTH_POLE = registerBlock("north_pole",
            () -> new NorthPole(BlockBehaviour.Properties.of().strength(1f,2f).sound(SoundType.STONE).noCollission().noOcclusion()), 0);
    public static final RegistryObject<Block> LIGHTED_DEER = registerBlock("lighted_deer",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of().strength(1.5f,2f).sound(SoundType.LADDER).noCollission().lightLevel(s -> 7)), 0);
    public static final RegistryObject<Block> LIGHTED_CHRISTMAS_TREE = registerBlock("lighted_christmas_tree",
            () -> new ChristmasTree(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.AZALEA_LEAVES).noOcclusion().strength(1.25F,10F).lightLevel(s -> 10)), 0);
    public static final RegistryObject<Block> SMALL_CHRISTMAS_TREE = registerBlock("small_christmas_tree",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.AZALEA_LEAVES).noOcclusion().strength(1.15F,8F).lightLevel(s -> 7)), 0);
    public static final RegistryObject<Block> CHRISTMAS_FIREPLACE = registerBlock("christmas_fireplace",
            () -> new DoubleTallDecorationBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS)), 0);
    public static final RegistryObject<Block> SLEIGH = registerBlock("sleigh",
            () -> new Wheelbarrow(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> SNOWMAN_BLUEMAN_STATUE = registerBlock("blueman_snowman",
            () -> new BluemanStatueNoToggle(BlockBehaviour.Properties.copy(Blocks.SNOW_BLOCK)), 0);
    public static final RegistryObject<Block> PRESENT_PILE = registerBlock("present_pile",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noCollission().sound(SoundType.WOOL).strength(1.0F,2.0F)), 0);
    public static final RegistryObject<Block> NUTCRACKER = registerBlock("nutcracker",
            () -> new Nutcracker(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> GINGERBREAD_HOUSE = registerBlock("gingerbread_house",
            () -> new GingerbreadHouse(BlockBehaviour.Properties.copy(Blocks.CAKE)), 0);
    public static final RegistryObject<Block> SNOWMAN_PLUSHY = registerBlock("snowman_plushy",
            () -> new ReindeerPlushy(BlockBehaviour.Properties.copy(Blocks.SNOW_BLOCK).sound(SoundType.WOOL)), 0);

    // end of christmas features list



    // Misc Decorative Blocks
    public static final RegistryObject<Block> BROKEN_COMPUTER = registerBlock("broken_computer",
            () -> new BrokenComputer(BlockBehaviour.Properties.of().sound(SoundType.METAL).lightLevel(s -> 5)), 0);
    public static final RegistryObject<Block> HEAD_CANDLE = registerBlock("head_candle",
            () -> new HeadCandle(BlockBehaviour.Properties.copy(Blocks.STONE).lightLevel(s -> 15)), 0);
    public static final RegistryObject<Block> DOOR_BELL = registerBlock("door_bell",
            () -> new Doorbell(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> REINDEER_WALL_HEAD = registerBlock("reindeer_wall_head",
            () -> new ReindeerWallHead(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> GAS_HEATER = registerBlock("gas_heater",
            () -> new DoubleTallDecorationBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> AC_THERMOSTAT = registerBlock("ac_thermostat",
            () -> new AcThermostat(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> BLUEY_DESKTOP_COMPUTER = registerBlock("bluey_desktop_computer",
            () -> new BlueyDesktopComputer(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> BLUEYCUBE_CONSOLE = registerBlock("blueycube_console",
            () -> new BlueyCubeConsole(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)), 0);
    public static final RegistryObject<Block> BULK_PRODUCT = registerBlock("bulk_product",
            () -> new BulkProduct(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD)), 0);
    // poop block
    public static final RegistryObject<Block> POOP = registerBlock("poop",
            () -> new PoopBlock(BlockBehaviour.Properties.copy(Blocks.GRASS).noCollission().noOcclusion()), 0);
    public static final RegistryObject<Block> TOOL_STATION = registerBlock("tool_station",
            () -> new ToolStation(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)), 0);
    public static final RegistryObject<Block> WATER_BOTTLE_PACK = registerBlock("water_bottle_pack",
            () -> new WaterBottlePack(BlockBehaviour.Properties.of().strength(1.25f).sound(SoundType.CALCITE)), 0);




    // Hospital Decorative Blocks
    public static final RegistryObject<Block> IV = registerBlock("iv",
            () -> new IVBlock(BlockBehaviour.Properties.of().sound(SoundType.COPPER)), 0);
    public static final RegistryObject<Block> HOSPITAL_BED = registerBlock("hospital_bed",
            () -> new ThingamajigsBedBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD)), 0);
    public static final RegistryObject<Block> HOSPITAL_COVER = registerBlock("hospital_cover",
            () -> new HospitalCover(BlockBehaviour.Properties.copy(Blocks.LIGHT_BLUE_WOOL).sound(SoundType.CALCITE).noCollission()), 0);
    public static final RegistryObject<Block> HEART_MONITOR = registerBlock("heart_monitor",
            () -> new HospitalPortables(BlockBehaviour.Properties.of().sound(SoundType.COPPER)), 0);
    public static final RegistryObject<Block> HOSPITAL_COMPUTER = registerBlock("hospital_computer",
            () -> new HospitalPortables(BlockBehaviour.Properties.of().sound(SoundType.COPPER)), 0);
    public static final RegistryObject<Block> OPERATION_TABLE = registerBlock("operation_table",
            () -> new OperationTable(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> OPERATION_TOOLS = registerBlock("operation_tools",
            () -> new OperationToolsBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)), 0);

    // Road Barriers Decorative Blocks
    public static final RegistryObject<Block> ROAD_BARRIER_CLOSED = registerBlock("road_barrier_closed",
            () -> new RoadBarrier(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> ROAD_BARRIER_THRU_CLOSED = registerBlock("road_barrier_thru_closed",
            () -> new RoadBarrier(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> ROAD_BARRIER_BRIDGE_CLOSED = registerBlock("road_barrier_bridge_closed",
            () -> new RoadBarrier(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> ROAD_BARRIER_BRIDGE_THRU_CLOSED = registerBlock("road_barrier_bridge_thru_closed",
            () -> new RoadBarrier(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> CONCRETE_BARRIER = registerBlock("concrete_barrier",
            () -> new BasicConcreteBarrier(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> REINFORCED_CONCRETE_BARRIER = registerBlock("reinforced_concrete_barrier",
            () -> new BasicConcreteBarrier(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> REBAR_CONCRETE_BARRIER = registerBlock("rebar_concrete_barrier",
            () -> new BasicConcreteBarrier(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> BRIDGE_BARRIER = registerBlock("bridge_barrier",
            () -> new ConcreteBarrier(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> ROAD_COVER = registerBlock("road_cover",
            () -> new RoadCoveringBlock(BlockBehaviour.Properties.of().noOcclusion().noCollission()), 0);
    public static final RegistryObject<Block> BIG_ROAD_CONE = registerBlock("big_road_cone",
            () -> new BigRoadCone(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> ROAD_BARREL = registerBlock("road_barrel",
            () -> new RoadBarrel(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> ROAD_CHANNELIZER = registerBlock("road_channelizer",
            () -> new Channelizer(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> ROAD_BARRIER_LIGHTED = registerBlock("road_barrier_lighted",
            () -> new RoadBarrier(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> ROAD_BARRIER_SMALL = registerBlock("road_barrier_small",
            () -> new SmallRoadBarrier(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> ROAD_BARRIER_SMALL_LIGHTED = registerBlock("road_barrier_small_lighted",
            () -> new SmallRoadBarrier(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> ROAD_PANEL = registerBlock("road_panel",
            () -> new RoadPanel(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);


    // Intractable Blocks
    public static final RegistryObject<Block> FIREWORKS_DISPLAY = registerBlock("firework_display",
            () -> new FireworksDisplay(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).strength(2F,1F).noOcclusion()), 0);
    public static final RegistryObject<Block> ARCADE_MACHINE_OPENABLE = registerBlock("arcade_machine_openable",
            () -> new ArcadeMachineOpenable(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> ARCADE_MACHINE = registerBlock("arcade_machine",
            () -> new ArcadeMachineMultipleTypesBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).lightLevel(s -> 12)), 0);
    public static final RegistryObject<Block> CROSSWALK_BUTTON = registerBlock("crosswalk_button",
            () -> new CrosswalkButton(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);


    // Traffic Signals
    public static final RegistryObject<Block> TRAFFIC_SIGNAL_NORMAL_1 = registerBlock("traffic_signal_normal_1",
            () -> new TrafficSignalBase(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> TRAFFIC_SIGNAL_NORMAL_2 = registerBlock("traffic_signal_left",
            () -> new TrafficSignalBase(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> TRAFFIC_SIGNAL_NORMAL_3 = registerBlock("traffic_signal_right",
            () -> new TrafficSignalBase(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> TRAFFIC_SIGNAL_NORMAL_4 = registerBlock("u_turn_signal",
            () -> new TrafficSignalBase(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> TRAFFIC_SIGNAL_SYMBOL_1 = registerBlock("no_left_turn_signal",
            () -> new TrafficSignalBase(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> TRAFFIC_SIGNAL_DOGHOUSE_1 = registerBlock("traffic_signal_doghouse_left",
            () -> new TrafficSignalBase(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> TRAFFIC_SIGNAL_DOGHOUSE_2 = registerBlock("traffic_signal_doghouse_right",
            () -> new TrafficSignalBase(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> TRAFFIC_SIGNAL_RED_FLASH = registerBlock("red_signal_flash",
            () -> new TrafficSignalBase(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> TRAFFIC_SIGNAL_YELLOW_FLASH = registerBlock("yellow_signal_flash",
            () -> new TrafficSignalBase(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> TRAFFIC_SIGNAL_ALLWAY_STOP_BEACON = registerBlock("red_allway",
            () -> new RedStopAllwayBeacon(BlockBehaviour.Properties.of().strength(1f).sound(SoundType.METAL)), 0);


    // Pedestrian Signals
    public static final RegistryObject<Block> PED_SIGNAL_SYMBOLS = registerBlock("ped_signal",
            () -> new TrafficSignalBase(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> PED_SIGNAL_WORDED = registerBlock("ped_signal_worded",
            () -> new TrafficSignalBase(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> PED_SIGNAL_MAN_1 = registerBlock("ped_signal_man_1",
            () -> new TrafficSignalBase(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> PED_FLASHERS = registerBlock("ped_flashers",
            () -> new PedFlashers(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops().sound(SoundType.METAL)), 0);



    // Poles
    public static final RegistryObject<Block> STRAIGHT_POLE = registerBlock("straight_pole",
            () -> new VerticalPole(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> STRAIGHT_HORIZONTAL_POLE = registerBlock("straight_horizontal_pole",
            () -> new HorizontalPole(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> L_POLE = registerBlock("l_pole",
            () -> new LPole(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> AXIS_POLE = registerBlock("axis_pole",
            () -> new AxisPole(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> HOLDER_POLE = registerBlock("holder_pole",
            () -> new Pole(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> PLUS_POLE = registerBlock("plus_pole",
            () -> new PlusPole(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> T_POLE = registerBlock("t_pole",
            () -> new TPole(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> T_POLE_B = registerBlock("t_pole_b",
            () -> new InvertTPole(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> T_POLE_C = registerBlock("t_pole_c",
            () -> new TPoleC(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> TRI_POLE = registerBlock("tri_pole",
            () -> new LPole(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> L_ONLY_POLE = registerBlock("l_only_pole",
            () -> new LOnlyPole(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> T_HORZ_ONLY_POLE = registerBlock("t_horz_only_pole",
            () -> new THorizontalOnlyPole(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> TRI_POLE_B = registerBlock("tri_pole_b",
            () -> new TriPoleB(BlockBehaviour.Properties.of()), 0);

    // Light Source Poles
    public static final RegistryObject<Block> LIGHT_POLE = registerBlock("light_pole",
            () -> new Pole(BlockBehaviour.Properties.of().lightLevel(s -> 15)), 0);

    // Signs
    public static final RegistryObject<Block> STOP_SIGN = registerBlock("stop_sign",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> YELLOW_STOP_SIGN = registerBlock("yellow_stop_sign",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> NO_PASSING_ZONE_SIGN = registerBlock("no_passing_zone_sign",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> YIELD_SIGN = registerBlock("yield_sign",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SLOW_SIGN = registerBlock("slow_sign",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> CROSSING_AHEAD_SIGN = registerBlock("crossing_ahead_sign",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> CROSSING_SIGN = registerBlock("crossing_sign",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> EXIT_LEFT = registerBlock("exit_left",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> EXIT_RIGHT = registerBlock("exit_right",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> BLUE_STOP_SIGN = registerBlock("blue_stop_sign",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> BRIDGE_OUT_AHEAD = registerBlock("bridge_out_ahead",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> BUMP = registerBlock("bump",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> DEAD_END = registerBlock("dead_end",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> DIP = registerBlock("dip",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> EMERGENCY_SCENE_AHEAD = registerBlock("es_ahead",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> GREEN_STOP_SIGN = registerBlock("green_stop_sign",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> NO_OUTLET = registerBlock("no_outlet",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> NO_PARKING = registerBlock("no_parking",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> CROSSBUCK = registerBlock("crossbuck",
            () -> new CrossbuckRedstone(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> CONST_ROAD_CLOSED_AHEAD = registerBlock("const_rca",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> RR_AHEAD_OLD = registerBlock("rr_ahead_old",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> RR_AHEAD = registerBlock("rr_ahead",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SPEED_HUMP_SIGN = registerBlock("speed_hump_sign",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SPEED_LIMIT_AHEAD = registerBlock("speed_limit_ahead",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SPEEDING_WORKERS_SIGN = registerBlock("speeding_workers_sign",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> UK_STOP_ON_RED = registerBlock("uk_stop_on_red",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> TOLL_AHEAD = registerBlock("toll_ahead",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> TWAS_DIP_SIGN = registerBlock("twas_dip",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> PARKING_PERMITTED_SIGN = registerBlock("parking_permitted",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SCHOOL_SPEED_LIMIT_AHEAD = registerBlock("school_speed_limit_ahead",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);


    // Speed Limit Signs
    public static final RegistryObject<Block> SPEED_LIMIT_10 = registerBlock("sl_ten",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SPEED_LIMIT_20 = registerBlock("sl_twenty",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SPEED_LIMIT_30 = registerBlock("sl_thirty",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SPEED_LIMIT_40 = registerBlock("sl_forty",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SPEED_LIMIT_50 = registerBlock("sl_fifty",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);

    // Half Signs
    public static final RegistryObject<Block> AHEAD_HALF = registerBlock("ahead_half",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> AHEAD_YELLOW_HALF = registerBlock("ahead_yellow_half",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> ALL_DAY_HALF = registerBlock("all_day_half",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> ALL_WAY_HALF = registerBlock("all_way_half",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> ENTER_AHEAD_HALF = registerBlock("enter_ahead_half",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> EXEMPT_HALF = registerBlock("exempt_half",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> EXEMPT_YELLOW_HALF = registerBlock("exempt_yellow_half",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> FOUR_WAY_HALF = registerBlock("four_way_half",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> MON_FRI_HALF = registerBlock("monfri_half",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> ONE_WAY_HALF = registerBlock("one_way_half",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> ONLY_THRU_LEFT = registerBlock("only_thru_left",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> ONLY_THRU_RIGHT = registerBlock("only_thru_right",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SAT_SUN_HALF = registerBlock("satsun_half",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SCHOOL_HALF = registerBlock("school_half",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SCHOOL_Y_HALF = registerBlock("school_y_half",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> THREE_WAY_HALF = registerBlock("three_way_half",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> TO_ROUTE_HALF = registerBlock("to_route",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> TO_TOLL_HALF = registerBlock("to_toll",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> TWO_WAY_HALF = registerBlock("two_way_half",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> WHEN_FLASHING_HALF = registerBlock("when_flashing",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);

    // symbol signs
    public static final RegistryObject<Block> ANGLED_BIG_MERGE_LEFT = registerBlock("angled_big_merge_left",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> ANGLED_BIG_MERGE_RIGHT = registerBlock("angled_big_merge_right",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> BIG_LEFT_MERGES = registerBlock("big_left_merges",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> BIG_RIGHT_MERGES = registerBlock("big_right_merges",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> BIKE = registerBlock("bike",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> BOMB_THREAT_AHEAD = registerBlock("bomb_threat_ahead",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> BUS_STOP_AHEAD = registerBlock("bus_stop_ahead",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> CAR = registerBlock("car",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> CRIME_SCENE_AHEAD = registerBlock("crime_scene_ahead",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> CROSSING_AHEAD_F = registerBlock("crossing_ahead_f",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> CROSSING_SIGN_F = registerBlock("crossing_sign_f",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> DEER = registerBlock("deer",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> DIAGONAL_TRACK_LEFTORRIGHT = registerBlock("diagonal_track_leftright_sign",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> DIAGONAL_TRACK_RIGHTORLEFT = registerBlock("diagonal_track_rightleft_sign",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> DIVIDER_ENDS = registerBlock("divider_ends",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> DIVIDER_START = registerBlock("divider_start",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> EQUESTRIAN = registerBlock("equestrian",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> FALLING_ROCKS = registerBlock("falling_rocks",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> FARMER_ORIGINAL = registerBlock("farmer_original",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> FIRE_TRUCK = registerBlock("fire_truck",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> FORWARD_OR_LEFT = registerBlock("forward_or_left",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> FORWARD_OR_RIGHT = registerBlock("forward_or_right",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> GOLF_CART_SIGN = registerBlock("golf_cart_sign",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> HIGH_TRACK = registerBlock("high_track",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> HORIZONTAL_TRACK = registerBlock("horizontal_track",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> LANES = registerBlock("lanes",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> LEFT_LANE_ENDS = registerBlock("left_lane_ends",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> LEFT_MERGES = registerBlock("left_merges",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> MINECART_CROSSING = registerBlock("minecart_crossing",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> MULTI_TO_SINGLE = registerBlock("multi_to_single",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> NARROW_BRIDGE = registerBlock("narrow_bridge",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> PAVEMENT_ENDS_OLD = registerBlock("pavement_ends_old",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> PED_DISABLED = registerBlock("ped_disabled",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> PILLAGER_SCENE = registerBlock("pillager_scene",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> PLANT_ROAD_SIGN = registerBlock("plant_road_sign",
            () -> new PlantRoadSign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> PLAYGROUND = registerBlock("playground",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> PLUS = registerBlock("plus",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> RIGHT_LANE_ENDS = registerBlock("right_lane_ends",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> RIGHT_MERGES = registerBlock("right_merges",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> ROAD_TWISTY_LEFT = registerBlock("road_twisty_left",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> ROAD_TWISTY_RIGHT = registerBlock("road_twisty_right",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> ROUNDABOUT_OLD = registerBlock("roundabout_old",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> ROUNDABOUT = registerBlock("roundabout",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SEATBELT = registerBlock("seatbelt",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> BIG_ARROW_ROAD_SIGN = registerBlock("big_arrow_road_sign",
            () -> new BigArrowRoadSign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SHOULDER_DROP_RIGHT = registerBlock("shoulder_drop_right",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SINGLE_TO_MULTI = registerBlock("single_to_multi",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SLIPPERY_WHEN_WET = registerBlock("slip",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SMALL_TO_BIG_LEFT = registerBlock("stbl",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SMALL_TO_BIG_RIGHT = registerBlock("stbr",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> SMILEY_SIGN = registerBlock("smile",
            () -> new SmileySign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> STOP_AHEAD = registerBlock("stop_ahead",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> TNT_THREAT = registerBlock("tnt_threat",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> TRACK_LEFT_SIDE = registerBlock("track_left_side",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> TRACK_RIGHT_SIDE = registerBlock("track_right_side",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> TRACTOR = registerBlock("tractor",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> TRAFFIC_LIGHT = registerBlock("traffic_light",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> TRAM_SIGN = registerBlock("tram_sign",
            () -> new TramSign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> TRUCK_SLOPE = registerBlock("truck_slope",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> TRUCK = registerBlock("truck",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> VERTICAL_TRACK = registerBlock("vertical_track",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> YIELD_AHEAD = registerBlock("yield_ahead",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> EFE_SIGN = registerBlock("efe_sign",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> TFF_SIGN = registerBlock("tff_sign",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> CHEVRON = registerBlock("chevron",
            () -> new Chevron(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> DISABLED_PARKING_SIGN = registerBlock("disabled_parking_sign",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> DISABLED_PARKING_SIGN_ALT = registerBlock("disabled_parking_sign_alt",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> RESERVED_DISABLED_PARKING = registerBlock("reserved_disabled_parking",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> RESERVED_DISABLED_PARKING_ALT = registerBlock("reserved_disabled_parking_alt",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> ARROW_SIGN = registerBlock("arrow_sign",
            () -> new ArrowSign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> THINK_BEFORE_YOU_THROW = registerBlock("think_before_you_throw",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> STOP_HERE_ON_RED = registerBlock("stop_here_on_red",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> LOW_POWER_LINES = registerBlock("low_power_lines",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> HAWK_SIGNAL_AHEAD = registerBlock("hawk_signal_ahead",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> NO_PEDS = registerBlock("no_peds",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> STAY_RIGHT = registerBlock("stay_right",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> STAY_LEFT = registerBlock("stay_left",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);


    // Misc and or Random Signs
    public static final RegistryObject<Block> INTERSTATE_SIGN = registerBlock("interstate_sign",
            () -> new InterstateSign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> RECTANGLE_SIGN = registerBlock("rectangle_sign",
            () -> new RectangleSign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> BIG_SIGN = registerBlock("big_sign",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> BLUEYPASS_ONLY = registerBlock("blueypass_only",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> DISABLED_PARKING_SIGN_ALT_TWO = registerBlock("disabled_parking_sign_alt_two",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);

    // Construction-related Signs
    public static final RegistryObject<Block> FLAGGER_AHEAD = registerBlock("flagger_ahead",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> ROAD_WORK_AHEAD = registerBlock("road_work_ahead",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);
    public static final RegistryObject<Block> WORKERS_PRESENT = registerBlock("workers_present",
            () -> new Sign(BlockBehaviour.Properties.of()), 0);

    // Mini-City Blocks
    public static final RegistryObject<Block> MINI_ROAD = registerBlock("mini_road",
            () -> new MiniRoad(BlockBehaviour.Properties.of().sound(SoundType.DEEPSLATE_TILES)), 0);
    public static final RegistryObject<Block> MINI_RAIL = registerBlock("mini_rail",
            () -> new MiniRail(BlockBehaviour.Properties.of().sound(SoundType.METAL)), 0);
    public static final RegistryObject<Block> MINI_BLUE_BUILDING = registerBlock("blue_building",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> MINI_RED_BUILDING = registerBlock("red_building",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> MINI_GREEN_BUILDING = registerBlock("green_building",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> MINI_YELLOW_BUILDING = registerBlock("yellow_building",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);
    public static final RegistryObject<Block> MINI_TALL_YELLOW_BUILDING = registerBlock("tall_yellow_building",
            () -> new DoubleTallDecorationBlock(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);

    // Custom Doors
    public static final RegistryObject<Block> FESTIVE_DOOR = registerBlock("festive_door",
            () -> new FestiveDoor(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR)), 0);
    public static final RegistryObject<Block> METALLIC_DOOR = registerBlock("metallic_door",
            () -> new MetallicDoor(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR)), 0);
    public static final RegistryObject<Block> BUBBLE_DOOR = registerBlock("bubble_door",
            () -> new BubbleDoor(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR)), 0);
    public static final RegistryObject<Block> STONE_DOOR = registerBlock("stone_door",
            () -> new StoneDoor(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR)), 0);
    public static final RegistryObject<Block> WHITE_WOOD_DOOR = registerBlock("white_wood_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).strength(3.0F).sound(SoundType.WOOD).noOcclusion(), BlockSetType.OAK), 0);
    public static final RegistryObject<Block> ALARMED_DOOR = registerBlock("alarmed_door",
            () -> new AlarmedDoor(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR)), 0);
    public static final RegistryObject<Block> SCREEN_DOOR = registerBlock("screen_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.BAMBOO_DOOR).noOcclusion().strength(1F,2F), BlockSetType.BAMBOO), 0);
    public static final RegistryObject<Block> SNOWMAN_DOOR = registerBlock("snowman_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_DOOR).strength(3F,3F), BlockSetType.OAK), 0);

    // Bookshelves
    public static final RegistryObject<Block> BLANK_BOOKSHELF = registerBlock("blank_bookshelf",
            () -> new BlankBookshelf(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> ABANDONED_BOOKSHELF = registerBlock("abandoned_bookshelf",
            () -> new CustomBookshelf(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> BONE_BOOKSHELF = registerBlock("bone_bookshelf",
            () -> new CustomBookshelf(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> BRICK_BOOKSHELF = registerBlock("brick_bookshelf",
            () -> new BrickshelfBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> GLOWSTONE_BOOKSHELF = registerBlock("glowstone_bookshelf",
            () -> new CustomBookshelf(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> EXPERIENCE_BOOKSHELF = registerBlock("experience_bookshelf",
            () -> new CustomBookshelf(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> EXPLORER_BOOKSHELF = registerBlock("explorer_bookshelf",
            () -> new CustomBookshelf(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> SCARY_BOOKSHELF = registerBlock("scary_bookshelf",
            () -> new CustomBookshelf(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> RED_TOME_BOOKSHELF = registerBlock("red_tome_bookshelf",
            () -> new CustomBookshelf(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> BLUE_TOME_BOOKSHELF = registerBlock("blue_tome_bookshelf",
            () -> new CustomBookshelf(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> GREEN_TOME_BOOKSHELF = registerBlock("green_tome_bookshelf",
            () -> new CustomBookshelf(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> YELLOW_TOME_BOOKSHELF = registerBlock("yellow_tome_bookshelf",
            () -> new CustomBookshelf(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> EXPENSIVE_BOOKSHELF = registerBlock("expensive_bookshelf",
            () -> new CustomBookshelf(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);
    public static final RegistryObject<Block> POTION_BOOKSHELF = registerBlock("potion_bookshelf",
            () -> new CustomBookshelf(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), 0);

    public static final RegistryObject<Block> ANCIENT_BOOKSHELF = registerBlock("ancient_bookshelf",
            () -> new AncientBookshelf(BlockBehaviour.Properties.copy(Blocks.STONE)), 0);

    // Road Themed Blocks
    public static final RegistryObject<Block> ASPHALT = registerBlock("asphalt",
            () -> new Asphalt(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f).requiresCorrectToolForDrops().friction(0.4F).sound(SoundType.TUFF)), 0);

    public static final RegistryObject<Block> ASPHALT_OK = registerBlock("ok_asphalt",
            () -> new Asphalt(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.9f).requiresCorrectToolForDrops().friction(0.45F).sound(SoundType.TUFF)), 0);

    public static final RegistryObject<Block> ASPHALT_MEDIOCRE = registerBlock("mediocre_asphalt",
            () -> new Asphalt(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.5f).requiresCorrectToolForDrops().friction(0.5F).sound(SoundType.TUFF)), 0);

    public static final RegistryObject<Block> ASPHALT_OLD = registerBlock("old_asphalt",
            () -> new Asphalt(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.2f).requiresCorrectToolForDrops().sound(SoundType.TUFF)), 0);

    // Sidewalk Blocks
    public static final RegistryObject<Block> SIDEWALK = registerBlock("sidewalk",
            () -> new Sidewalk(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f).requiresCorrectToolForDrops().sound(SoundType.TUFF)), 0);
    public static final RegistryObject<Block> SIDEWALK_CRACKED = registerBlock("cracked_sidewalk",
            () -> new Sidewalk(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f).requiresCorrectToolForDrops().sound(SoundType.TUFF)), 0);
    public static final RegistryObject<Block> SIDEWALK_BLOCKED = registerBlock("blocked_sidewalk",
            () -> new Sidewalk(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f).requiresCorrectToolForDrops().sound(SoundType.TUFF)), 0);
    public static final RegistryObject<Block> SIDEWALK_SECTIONED = registerBlock("sectioned_sidewalk",
            () -> new Sidewalk(BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f).requiresCorrectToolForDrops().sound(SoundType.TUFF)), 0);


    // Road Lines, Symbols & Words
    public static final RegistryObject<Block> WHITE_ROAD_MARKING = customRegisterBlock("white_road_marking",
            () -> new WhiteRoadMarking(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> BLUE_ROAD_MARKING = customRegisterBlock("blue_road_marking",
            () -> new BlueRoadMarking(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> YELLOW_ROAD_MARKING = customRegisterBlock("yellow_road_marking",
            () -> new YellowRoadMarking(BlockBehaviour.Properties.copy(Blocks.STONE)));


    // custom torch like blocks (that use the vanilla system)

    public static final RegistryObject<Block> RED_LANTERN = customRegisterBlock("red_lantern",
            () -> new RedLanternBlock(BlockBehaviour.Properties.copy(Blocks.TORCH).lightLevel(s -> 13), ParticleTypes.END_ROD));
    public static final RegistryObject<Block> WALL_RED_LANTERN = customRegisterBlock("wall_red_lantern",
            () -> new RedLanternWallBlock(BlockBehaviour.Properties.copy(Blocks.WALL_TORCH).lightLevel(s -> 13), ParticleTypes.END_ROD));

    public static final RegistryObject<Block> PAPER_LANTERN = customRegisterBlock("paper_lantern",
            () -> new PaperLanternBlock(BlockBehaviour.Properties.copy(Blocks.TORCH).lightLevel(s -> 12), ParticleTypes.ELECTRIC_SPARK));
    public static final RegistryObject<Block> WALL_PAPER_LANTERN = customRegisterBlock("wall_paper_lantern",
            () -> new PaperLanternWallBlock(BlockBehaviour.Properties.copy(Blocks.WALL_TORCH).lightLevel(s -> 12), ParticleTypes.ELECTRIC_SPARK));

    // chains
    public static final RegistryObject<Block> SCULK_CHAIN = registerBlock("sculk_chain",
            () -> new ChainBlock(BlockBehaviour.Properties.copy(Blocks.CHAIN).sound(SoundType.SCULK)));

    // lantern like blocks (using vanilla system)
    public static final RegistryObject<Block> SCULK_LANTERN = registerBlock("sculk_lantern",
            () -> new LanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN).lightLevel(s -> 15).sound(SoundType.SCULK_CATALYST)));

    // misc stone blocks
    public static final RegistryObject<Block> SPOOKY_STONE = registerBlock("spooky_stone",
            () -> new SpookyStone(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()
                    .mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistryObject<Block> BLUEBERRY_STONE = registerBlock("blueberry_stone",
            () -> new BlueberryStone(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()
                    .mapColor(MapColor.COLOR_BLUE)));

    public static final RegistryObject<Block> NETHERISH_STONE = registerBlock("netherish_stone",
            () -> new NetherishStone(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()
                    .mapColor(MapColor.NETHER)));

    public static final RegistryObject<Block> VOLCANIC_STONE = registerBlock("volcanic_stone",
            () -> new VolcanicStone(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()
                    .mapColor(MapColor.COLOR_RED)));

    public static final RegistryObject<Block> CHARGED_VOLCANIC_STONE = registerBlock("charged_volcanic_stone",
            () -> new ChargedVolcanicStone(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()
                    .mapColor(MapColor.COLOR_RED)));

    // chairs
    public static final RegistryObject<Block> STONE_CHAIR = registerBlock("stone_chair",
            () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.STONE),
                    SoundEvents.STONE_STEP));
    public static final RegistryObject<Block> GOLD_CHAIR = registerBlock("gold_chair",
            () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK),
                    SoundEvents.METAL_STEP));
    public static final RegistryObject<Block> QUARTZ_CHAIR = registerBlock("quartz_chair",
            () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK),
                    SoundEvents.STONE_STEP));
    public static final RegistryObject<Block> NETHER_BRICK_CHAIR = registerBlock("nether_brick_chair",
            () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS),
                    SoundEvents.NETHER_BRICKS_STEP));
    public static final RegistryObject<Block> PRISMARINE_CHAIR = registerBlock("prismarine_chair",
            () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.PRISMARINE),
                    SoundEvents.STONE_STEP));
    public static final RegistryObject<Block> PURPUR_CHAIR = registerBlock("purpur_chair",
            () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.PURPUR_BLOCK),
                    SoundEvents.STONE_STEP));
    public static final RegistryObject<Block> SCULK_CHAIR = registerBlock("sculk_chair",
            () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.SCULK_CATALYST),
                    SoundEvents.SCULK_CATALYST_STEP));


    // dyed pumpkins
    public static final RegistryObject<Block> WHITE_PUMPKIN = registerBlock("white_pumpkin",
            () -> new WhitePumpkinBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> LIGHT_GRAY_PUMPKIN = registerBlock("light_gray_pumpkin",
            () -> new LightGrayPumpkinBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> WHITE_CARVED_PUMPKIN = registerBlock("white_carved_pumpkin",
            () -> new DyedEquipableCarvedPumpkinBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_WHITE)));

    public static final RegistryObject<Block> LIGHT_GRAY_CARVED_PUMPKIN = registerBlock("light_gray_carved_pumpkin",
            () -> new DyedEquipableCarvedPumpkinBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));

    public static final RegistryObject<Block> WHITE_PUMPKIN_STEM = customRegisterBlock("white_pumpkin_stem",
            () -> new StemBlock((StemGrownBlock)ThingamajigsBlocks.WHITE_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.WHITE_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.PUMPKIN_STEM)));
    public static final RegistryObject<Block> ATTATCHED_WHITE_PUMPKIN_STEM = customRegisterBlock("attached_white_pumpkin_stem",
            () -> new AttachedStemBlock((StemGrownBlock)ThingamajigsBlocks.WHITE_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.WHITE_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.ATTACHED_PUMPKIN_STEM)));

    public static final RegistryObject<Block> LIGHT_GRAY_PUMPKIN_STEM = customRegisterBlock("light_gray_pumpkin_stem",
            () -> new StemBlock((StemGrownBlock)ThingamajigsBlocks.LIGHT_GRAY_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.LIGHT_GRAY_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.PUMPKIN_STEM)));
    public static final RegistryObject<Block> ATTATCHED_LIGHT_GRAY_PUMPKIN_STEM = customRegisterBlock("attached_light_gray_pumpkin_stem",
            () -> new AttachedStemBlock((StemGrownBlock)ThingamajigsBlocks.LIGHT_GRAY_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.LIGHT_GRAY_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.ATTACHED_PUMPKIN_STEM)));

    // gray
    public static final RegistryObject<Block> GRAY_PUMPKIN = registerBlock("gray_pumpkin",
            () -> new GrayPumpkinBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> GRAY_CARVED_PUMPKIN = registerBlock("gray_carved_pumpkin",
            () -> new DyedEquipableCarvedPumpkinBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_GRAY)));

    public static final RegistryObject<Block> GRAY_PUMPKIN_STEM = customRegisterBlock("gray_pumpkin_stem",
            () -> new StemBlock((StemGrownBlock)ThingamajigsBlocks.GRAY_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.GRAY_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.PUMPKIN_STEM)));

    public static final RegistryObject<Block> ATTATCHED_GRAY_PUMPKIN_STEM = customRegisterBlock("attached_gray_pumpkin_stem",
            () -> new AttachedStemBlock((StemGrownBlock)ThingamajigsBlocks.GRAY_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.GRAY_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.ATTACHED_PUMPKIN_STEM)));

    // black
    public static final RegistryObject<Block> BLACK_PUMPKIN = registerBlock("black_pumpkin",
            () -> new BlackPumpkinBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> BLACK_CARVED_PUMPKIN = registerBlock("black_carved_pumpkin",
            () -> new DyedEquipableCarvedPumpkinBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_BLACK)));

    public static final RegistryObject<Block> BLACK_PUMPKIN_STEM = customRegisterBlock("black_pumpkin_stem",
            () -> new StemBlock((StemGrownBlock)ThingamajigsBlocks.BLACK_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.BLACK_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.PUMPKIN_STEM)));

    public static final RegistryObject<Block> ATTATCHED_BLACK_PUMPKIN_STEM = customRegisterBlock("attached_black_pumpkin_stem",
            () -> new AttachedStemBlock((StemGrownBlock)ThingamajigsBlocks.BLACK_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.BLACK_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.ATTACHED_PUMPKIN_STEM)));

    // brown
    public static final RegistryObject<Block> BROWN_PUMPKIN = registerBlock("brown_pumpkin",
            () -> new BrownPumpkinBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> BROWN_CARVED_PUMPKIN = registerBlock("brown_carved_pumpkin",
            () -> new DyedEquipableCarvedPumpkinBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_BROWN)));

    public static final RegistryObject<Block> BROWN_PUMPKIN_STEM = customRegisterBlock("brown_pumpkin_stem",
            () -> new StemBlock((StemGrownBlock)ThingamajigsBlocks.BROWN_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.BROWN_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.PUMPKIN_STEM)));

    public static final RegistryObject<Block> ATTATCHED_BROWN_PUMPKIN_STEM = customRegisterBlock("attached_brown_pumpkin_stem",
            () -> new AttachedStemBlock((StemGrownBlock)ThingamajigsBlocks.BROWN_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.BROWN_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.ATTACHED_PUMPKIN_STEM)));

    // red
    public static final RegistryObject<Block> RED_PUMPKIN = registerBlock("red_pumpkin",
            () -> new RedPumpkinBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> RED_CARVED_PUMPKIN = registerBlock("red_carved_pumpkin",
            () -> new DyedEquipableCarvedPumpkinBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_RED)));

    public static final RegistryObject<Block> RED_PUMPKIN_STEM = customRegisterBlock("red_pumpkin_stem",
            () -> new StemBlock((StemGrownBlock)ThingamajigsBlocks.RED_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.RED_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.PUMPKIN_STEM)));

    public static final RegistryObject<Block> ATTATCHED_RED_PUMPKIN_STEM = customRegisterBlock("attached_red_pumpkin_stem",
            () -> new AttachedStemBlock((StemGrownBlock)ThingamajigsBlocks.RED_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.RED_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.ATTACHED_PUMPKIN_STEM)));

    // yellow
    public static final RegistryObject<Block> YELLOW_PUMPKIN = registerBlock("yellow_pumpkin",
            () -> new YellowPumpkinBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> YELLOW_CARVED_PUMPKIN = registerBlock("yellow_carved_pumpkin",
            () -> new DyedEquipableCarvedPumpkinBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_YELLOW)));

    public static final RegistryObject<Block> YELLOW_PUMPKIN_STEM = customRegisterBlock("yellow_pumpkin_stem",
            () -> new StemBlock((StemGrownBlock)ThingamajigsBlocks.YELLOW_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.YELLOW_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.PUMPKIN_STEM)));

    public static final RegistryObject<Block> ATTATCHED_YELLOW_PUMPKIN_STEM = customRegisterBlock("attached_yellow_pumpkin_stem",
            () -> new AttachedStemBlock((StemGrownBlock)ThingamajigsBlocks.YELLOW_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.YELLOW_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.ATTACHED_PUMPKIN_STEM)));

    // lime
    public static final RegistryObject<Block> LIME_PUMPKIN = registerBlock("lime_pumpkin",
            () -> new LimePumpkinBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> LIME_CARVED_PUMPKIN = registerBlock("lime_carved_pumpkin",
            () -> new DyedEquipableCarvedPumpkinBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GREEN)));

    public static final RegistryObject<Block> LIME_PUMPKIN_STEM = customRegisterBlock("lime_pumpkin_stem",
            () -> new StemBlock((StemGrownBlock)ThingamajigsBlocks.LIME_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.LIME_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.PUMPKIN_STEM)));

    public static final RegistryObject<Block> ATTATCHED_LIME_PUMPKIN_STEM = customRegisterBlock("attached_lime_pumpkin_stem",
            () -> new AttachedStemBlock((StemGrownBlock)ThingamajigsBlocks.LIME_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.LIME_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.ATTACHED_PUMPKIN_STEM)));

    // green
    public static final RegistryObject<Block> GREEN_PUMPKIN = registerBlock("green_pumpkin",
            () -> new GreenPumpkinBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> GREEN_CARVED_PUMPKIN = registerBlock("green_carved_pumpkin",
            () -> new DyedEquipableCarvedPumpkinBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GREEN)));

    public static final RegistryObject<Block> GREEN_PUMPKIN_STEM = customRegisterBlock("green_pumpkin_stem",
            () -> new StemBlock((StemGrownBlock)ThingamajigsBlocks.GREEN_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.GREEN_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.PUMPKIN_STEM)));

    public static final RegistryObject<Block> ATTATCHED_GREEN_PUMPKIN_STEM = customRegisterBlock("attached_green_pumpkin_stem",
            () -> new AttachedStemBlock((StemGrownBlock)ThingamajigsBlocks.GREEN_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.GREEN_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.ATTACHED_PUMPKIN_STEM)));

    // cyan
    public static final RegistryObject<Block> CYAN_PUMPKIN = registerBlock("cyan_pumpkin",
            () -> new CyanPumpkinBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> CYAN_CARVED_PUMPKIN = registerBlock("cyan_carved_pumpkin",
            () -> new DyedEquipableCarvedPumpkinBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_CYAN)));

    public static final RegistryObject<Block> CYAN_PUMPKIN_STEM = customRegisterBlock("cyan_pumpkin_stem",
            () -> new StemBlock((StemGrownBlock)ThingamajigsBlocks.CYAN_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.CYAN_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.PUMPKIN_STEM)));

    public static final RegistryObject<Block> ATTATCHED_CYAN_PUMPKIN_STEM = customRegisterBlock("attached_cyan_pumpkin_stem",
            () -> new AttachedStemBlock((StemGrownBlock)ThingamajigsBlocks.CYAN_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.CYAN_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.ATTACHED_PUMPKIN_STEM)));

    // light blue
    public static final RegistryObject<Block> LIGHT_BLUE_PUMPKIN = registerBlock("light_blue_pumpkin",
            () -> new LightBluePumpkinBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> LIGHT_BLUE_CARVED_PUMPKIN = registerBlock("light_blue_carved_pumpkin",
            () -> new DyedEquipableCarvedPumpkinBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_BLUE)));

    public static final RegistryObject<Block> LIGHT_BLUE_PUMPKIN_STEM = customRegisterBlock("light_blue_pumpkin_stem",
            () -> new StemBlock((StemGrownBlock)ThingamajigsBlocks.LIGHT_BLUE_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.LIGHT_BLUE_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.PUMPKIN_STEM)));

    public static final RegistryObject<Block> ATTATCHED_LIGHT_BLUE_PUMPKIN_STEM = customRegisterBlock("attached_light_blue_pumpkin_stem",
            () -> new AttachedStemBlock((StemGrownBlock)ThingamajigsBlocks.LIGHT_BLUE_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.LIGHT_BLUE_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.ATTACHED_PUMPKIN_STEM)));

    // blue
    public static final RegistryObject<Block> BLUE_PUMPKIN = registerBlock("blue_pumpkin",
            () -> new BluePumpkinBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> BLUE_CARVED_PUMPKIN = registerBlock("blue_carved_pumpkin",
            () -> new DyedEquipableCarvedPumpkinBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLUE)));

    public static final RegistryObject<Block> BLUE_PUMPKIN_STEM = customRegisterBlock("blue_pumpkin_stem",
            () -> new StemBlock((StemGrownBlock)ThingamajigsBlocks.BLUE_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.BLUE_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.PUMPKIN_STEM)));

    public static final RegistryObject<Block> ATTATCHED_BLUE_PUMPKIN_STEM = customRegisterBlock("attached_blue_pumpkin_stem",
            () -> new AttachedStemBlock((StemGrownBlock)ThingamajigsBlocks.BLUE_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.BLUE_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.ATTACHED_PUMPKIN_STEM)));

    // purple
    public static final RegistryObject<Block> PURPLE_PUMPKIN = registerBlock("purple_pumpkin",
            () -> new PurplePumpkinBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> PURPLE_CARVED_PUMPKIN = registerBlock("purple_carved_pumpkin",
            () -> new DyedEquipableCarvedPumpkinBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistryObject<Block> PURPLE_PUMPKIN_STEM = customRegisterBlock("purple_pumpkin_stem",
            () -> new StemBlock((StemGrownBlock)ThingamajigsBlocks.PURPLE_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.PURPLE_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.PUMPKIN_STEM)));

    public static final RegistryObject<Block> ATTATCHED_PURPLE_PUMPKIN_STEM = customRegisterBlock("attached_purple_pumpkin_stem",
            () -> new AttachedStemBlock((StemGrownBlock)ThingamajigsBlocks.PURPLE_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.PURPLE_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.ATTACHED_PUMPKIN_STEM)));

    // magenta
    public static final RegistryObject<Block> MAGENTA_PUMPKIN = registerBlock("magenta_pumpkin",
            () -> new MagentaPumpkinBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> MAGENTA_CARVED_PUMPKIN = registerBlock("magenta_carved_pumpkin",
            () -> new DyedEquipableCarvedPumpkinBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_MAGENTA)));

    public static final RegistryObject<Block> MAGENTA_PUMPKIN_STEM = customRegisterBlock("magenta_pumpkin_stem",
            () -> new StemBlock((StemGrownBlock)ThingamajigsBlocks.MAGENTA_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.MAGENTA_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.PUMPKIN_STEM)));

    public static final RegistryObject<Block> ATTATCHED_MAGENTA_PUMPKIN_STEM = customRegisterBlock("attached_magenta_pumpkin_stem",
            () -> new AttachedStemBlock((StemGrownBlock)ThingamajigsBlocks.MAGENTA_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.MAGENTA_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.ATTACHED_PUMPKIN_STEM)));

    // pink
    public static final RegistryObject<Block> PINK_PUMPKIN = registerBlock("pink_pumpkin",
            () -> new PinkPumpkinBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> PINK_CARVED_PUMPKIN = registerBlock("pink_carved_pumpkin",
            () -> new DyedEquipableCarvedPumpkinBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_PINK)));

    public static final RegistryObject<Block> PINK_PUMPKIN_STEM = customRegisterBlock("pink_pumpkin_stem",
            () -> new StemBlock((StemGrownBlock)ThingamajigsBlocks.PINK_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.PINK_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.PUMPKIN_STEM)));

    public static final RegistryObject<Block> ATTATCHED_PINK_PUMPKIN_STEM = customRegisterBlock("attached_pink_pumpkin_stem",
            () -> new AttachedStemBlock((StemGrownBlock)ThingamajigsBlocks.PINK_PUMPKIN.get(),
                    () -> {return ThingamajigsItems.PINK_PUMPKIN_SEEDS.get();},
                    BlockBehaviour.Properties.copy(Blocks.ATTACHED_PUMPKIN_STEM)));

    // jack o lanterns
    public static final RegistryObject<Block> WHITE_JOL = registerBlock("white_jol",
            () -> new CustomJackOLanternBlock(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN)
                    .lightLevel(s -> 15)));
    public static final RegistryObject<Block> LIGHT_GRAY_JOL = registerBlock("light_gray_jol",
            () -> new CustomJackOLanternBlock(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN)
                    .lightLevel(s -> 15)));
    public static final RegistryObject<Block> GRAY_JOL = registerBlock("gray_jol",
            () -> new CustomJackOLanternBlock(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN)
                    .lightLevel(s -> 15)));
    public static final RegistryObject<Block> BLACK_JOL = registerBlock("black_jol",
            () -> new BlackJackOLanternBlock(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN)
                    .lightLevel(s -> 15)));
    public static final RegistryObject<Block> BROWN_JOL = registerBlock("brown_jol",
            () -> new CustomJackOLanternBlock(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN)
                    .lightLevel(s -> 15)));

    public static final RegistryObject<Block> RED_JOL = registerBlock("red_jol",
            () -> new CustomJackOLanternBlock(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN)
                    .lightLevel(s -> 15)));
    public static final RegistryObject<Block> YELLOW_JOL = registerBlock("yellow_jol",
            () -> new CustomJackOLanternBlock(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN)
                    .lightLevel(s -> 15)));
    public static final RegistryObject<Block> LIME_JOL = registerBlock("lime_jol",
            () -> new CustomJackOLanternBlock(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN)
                    .lightLevel(s -> 15)));
    public static final RegistryObject<Block> GREEN_JOL = registerBlock("green_jol",
            () -> new CustomJackOLanternBlock(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN)
                    .lightLevel(s -> 15)));
    public static final RegistryObject<Block> CYAN_JOL = registerBlock("cyan_jol",
            () -> new CustomJackOLanternBlock(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN)
                    .lightLevel(s -> 15)));
    public static final RegistryObject<Block> LIGHT_BLUE_JOL = registerBlock("light_blue_jol",
            () -> new CustomJackOLanternBlock(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN)
                    .lightLevel(s -> 15)));
    public static final RegistryObject<Block> BLUE_JOL = registerBlock("blue_jol",
            () -> new CustomJackOLanternBlock(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN)
                    .lightLevel(s -> 15)));
    public static final RegistryObject<Block> PURPLE_JOL = registerBlock("purple_jol",
            () -> new CustomJackOLanternBlock(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN)
                    .lightLevel(s -> 15)));
    public static final RegistryObject<Block> MAGENTA_JOL = registerBlock("magenta_jol",
            () -> new CustomJackOLanternBlock(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN)
                    .lightLevel(s -> 15)));
    public static final RegistryObject<Block> PINK_JOL = registerBlock("pink_jol",
            () -> new CustomJackOLanternBlock(BlockBehaviour.Properties.copy(Blocks.JACK_O_LANTERN)
                    .lightLevel(s -> 15)));


    // end of pumpkins list

    public static final RegistryObject<Block> LOCKABLE_DOOR = registerBlock("lockable_door",
            () -> new LockableDoor(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR)), 0);

    //

    public static final RegistryObject<Block> ROAST_TURKEY = registerBlock("roast_turkey",
            () -> new RoastTurkey(BlockBehaviour.Properties.of().strength(1f,5f)), 0);

    public static final RegistryObject<Block> POOP_CHAIR = registerBlock("poop_chair",
            () -> new ChairBlock(BlockBehaviour.Properties.of().strength(0.75F,2F).sound(ThingamajigsSoundTypes.POOP),
                    ThingamajigsSoundEvents.POOP_STEP.get()), 0);

    public static final RegistryObject<Block> COMPUTER_CONTROLS = registerBlock("computer_controls",
            () -> new ComputerControls(BlockBehaviour.Properties.of()), 0);

    public static final RegistryObject<Block> PC_CONTROLS = registerBlock("pc_controls",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of()
                    .sound(SoundType.LANTERN)
                    .strength(0.95F,2F)), 0);

    public static final RegistryObject<Block> RGB_PC_CONTROLS = registerBlock("rgb_pc_controls",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.of()
                    .sound(SoundType.LANTERN)
                    .strength(0.95F,2F)), 0);

    public static final RegistryObject<Block> DARKENED_STONE = registerBlock("darkened_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> GRADIENT_DARKENED_STONE = registerBlock("gradient_darkened_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> DARK_DARKENED_STONE = registerBlock("dark_darkened_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> SUPER_SPONGE = registerBlock("super_sponge",
            () -> new SuperSpongeBlock(BlockBehaviour.Properties.of())); // op-only sponge block

    public static final RegistryObject<Block> CORNER_COMPUTER = registerBlock("corner_computer",
            () -> new CornerComputer(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> CORNER_COMPUTER_WM = registerBlock("corner_computer_wm",
            () -> new CornerComputerMonitorized(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> AQUARIUM = registerBlock("aquarium",
            () -> new Aquarium(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> DIAMOND_CHAIR = registerBlock("diamond_chair",
            () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK),SoundEvents.METAL_HIT));

    public static final RegistryObject<Block> IRON_CHAIR = registerBlock("iron_chair",
            () -> new ChairBlock(BlockBehaviour.Properties.of().strength(1.1f,2f),SoundEvents.METAL_HIT));


    public static final RegistryObject<Block> COPPER_CHAIR = registerBlock("copper_chair",
            () -> new WeatheringCopperChairBlock(
                    WeatheringCopperChair.RustState.UNAFFECTED,
                    BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE)));
    public static final RegistryObject<Block> EXPOSED_COPPER_CHAIR = registerBlock("exposed_copper_chair",
            () -> new WeatheringCopperChairBlock(
                    WeatheringCopperChair.RustState.EXPOSED,
                    BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryObject<Block> WEATHERED_COPPER_CHAIR = registerBlock("weathered_copper_chair",
            () -> new WeatheringCopperChairBlock(
                    WeatheringCopperChair.RustState.WEATHERED,
                    BlockBehaviour.Properties.of().mapColor(MapColor.WARPED_STEM)));
    public static final RegistryObject<Block> OXIDIZED_COPPER_CHAIR = registerBlock("oxidized_copper_chair",
            () -> new WeatheringCopperChairBlock(
                    WeatheringCopperChair.RustState.OXIDIZED,
                    BlockBehaviour.Properties.of().mapColor(MapColor.WARPED_NYLIUM)));


    public static final RegistryObject<Block> WAXED_COPPER_CHAIR = registerBlock("waxed_copper_chair",
            () -> new WaxedCopperChairBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).requiresCorrectToolForDrops()
                            .strength(3.0F, 6.0F)
                            .sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WAXED_EXPOSED_COPPER_CHAIR = registerBlock("waxed_exposed_copper_chair",
            () -> new WaxedCopperChairBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).requiresCorrectToolForDrops()
                            .strength(3.0F, 6.0F)
                            .sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WAXED_WEATHERED_COPPER_CHAIR = registerBlock("waxed_weathered_copper_chair",
            () -> new WaxedCopperChairBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WARPED_STEM).requiresCorrectToolForDrops()
                            .strength(3.0F, 6.0F)
                            .sound(SoundType.COPPER)));
    public static final RegistryObject<Block> WAXED_OXIDIZED_COPPER_CHAIR = registerBlock("waxed_oxidized_copper_chair",
            () -> new WaxedCopperChairBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WARPED_NYLIUM).requiresCorrectToolForDrops()
                            .strength(3.0F, 6.0F)
                            .sound(SoundType.COPPER)));

    public static final RegistryObject<Block> WINE_BOTTLE = registerBlock("wine_bottle",
            () -> new WineBottle(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> CLOCK_RADIO = registerBlock("clock_radio",
            () -> new ClockRadioBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> DARK_CRYSTAL_BLOCK = registerBlock("dark_crystal_block",
            () -> new DarkCrystalBlock(BlockBehaviour.Properties.of()));

    // 1.6.7 features
    public static final RegistryObject<Block> OPEN_SIGN_ALT = registerBlock("open_sign_alt",
            () -> new OpenSign(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN)
                    .lightLevel(openSignLitEmission(15))));
    public static final RegistryObject<Block> OPEN_SIGN_ALT_TWO = registerBlock("open_sign_alt_two",
            () -> new OpenSign(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE)
                    .lightLevel(openSignLitEmission(15))));

    public static final RegistryObject<Block> BASKETBALL_HOOP = registerBlock("basketball_hoop",
            () -> new BasketballHoop(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> BYPRODUCT = registerBlock("byproduct",
            () -> new ByproductBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> SLUDGE_CONVERTER = registerBlock("sludge_converter",
            () -> new SludgeConverter(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> PURIFYING_BLOCK = registerBlock("purifying_block",
            () -> new PurifyingBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> BALLOON_BLOCK = customRegisterBlock("balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> LIGHT_GRAY_BALLOON_BLOCK = customRegisterBlock("light_gray_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> GRAY_BALLOON_BLOCK = customRegisterBlock("gray_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> BLACK_BALLOON_BLOCK = customRegisterBlock("black_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> BROWN_BALLOON_BLOCK = customRegisterBlock("brown_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> RED_BALLOON_BLOCK = customRegisterBlock("red_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> ORANGE_BALLOON_BLOCK = customRegisterBlock("orange_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> YELLOW_BALLOON_BLOCK = customRegisterBlock("yellow_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> LIME_BALLOON_BLOCK = customRegisterBlock("lime_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> GREEN_BALLOON_BLOCK = customRegisterBlock("green_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> CYAN_BALLOON_BLOCK = customRegisterBlock("cyan_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> LIGHT_BLUE_BALLOON_BLOCK = customRegisterBlock("light_blue_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> BLUE_BALLOON_BLOCK = customRegisterBlock("blue_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> PURPLE_BALLOON_BLOCK = customRegisterBlock("purple_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> MAGENTA_BALLOON_BLOCK = customRegisterBlock("magenta_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> PINK_BALLOON_BLOCK = customRegisterBlock("pink_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> TEAL_BALLOON_BLOCK = customRegisterBlock("teal_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> ACACIA_LANE = registerBlock("acacia_lane",
            () -> new LaneBlock(BlockBehaviour.Properties.of().ignitedByLava()));
    public static final RegistryObject<Block> BIRCH_LANE = registerBlock("birch_lane",
            () -> new LaneBlock(BlockBehaviour.Properties.of().ignitedByLava()));
    public static final RegistryObject<Block> SPRUCE_LANE = registerBlock("spruce_lane",
            () -> new LaneBlock(BlockBehaviour.Properties.of().ignitedByLava()));
    public static final RegistryObject<Block> DARK_OAK_LANE = registerBlock("dark_oak_lane",
            () -> new LaneBlock(BlockBehaviour.Properties.of().ignitedByLava()));
    public static final RegistryObject<Block> WARPED_LANE = registerBlock("warped_lane",
            () -> new FireproofLaneBlock(BlockBehaviour.Properties.of().sound(SoundType.NETHER_WOOD)));
    public static final RegistryObject<Block> CRIMSON_LANE = registerBlock("crimson_lane",
            () -> new FireproofLaneBlock(BlockBehaviour.Properties.of().sound(SoundType.NETHER_WOOD)));
    public static final RegistryObject<Block> CHERRY_LANE = registerBlock("cherry_lane",
            () -> new LaneBlock(BlockBehaviour.Properties.of().ignitedByLava().sound(SoundType.CHERRY_WOOD)));
    public static final RegistryObject<Block> MANGROVE_LANE = registerBlock("mangrove_lane",
            () -> new LaneBlock(BlockBehaviour.Properties.of().ignitedByLava()));
    public static final RegistryObject<Block> JUNGLE_LANE = registerBlock("jungle_lane",
            () -> new LaneBlock(BlockBehaviour.Properties.of().ignitedByLava()));

    // 1.6.8 features
    public static final RegistryObject<Block> VERTICAL_AXIS_POLE = registerBlock("vertical_axis_pole",
            () -> new VerticalAxisPole(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> THREE_WAY_POLE = registerBlock("three_way_pole",
            () -> new ThreeWayPole(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> ALL_WAY_POLE = registerBlock("all_way_pole",
            () -> new AllWayPole(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> AIRDUCT_VENT = registerBlock("airduct_vent",
            () -> new AirductVent(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> ESCALATOR = registerBlock("escalator",
            () -> new EscalatorBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> ESCALATOR_DOWN = registerBlock("escalator_down",
            () -> new EscalatorDownBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> BRITISH_RAILWAY_LIGHTS = registerBlock("railway_lights",
            () -> new BritCrossingLightOffBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> BRITISH_RAILWAY_LIGHTS_AMBER = customRegisterBlock("railway_lights_amber",
            () -> new BritCrossingLightAmberBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> BRITISH_RAILWAY_LIGHTS_ON = customRegisterBlock("railway_lights_on",
            () -> new BritCrossingLightOnBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> BRITISH_RAILWAY_ALARM = registerBlock("railway_alarm",
            () -> new BritCrossingAlarm(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> TRI_RAILWAY_LIGHTS = registerBlock("tri_railway_lights",
            () -> new TriRailwayLights(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> DUAL_RAILWAY_LIGHTS = registerBlock("dual_railway_lights",
            () -> new DualRailwayLights(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> THINGAMAJIG_STATE_CELL = registerBlock("thingamajig_state_cell",
            () -> new TwoStateToggledBlock(BlockBehaviour.Properties.of()
                    .sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ALT_US_OUTLET = registerBlock("alt_us_outlet",
            () -> new WallOutletBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> REINFORCED_GLASS = registerBlock("reinforced_glass",
            () -> new GlassLikeBlock(BlockBehaviour.Properties.of().strength(2f,100f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> DARKENED_STONE_BRICKS = registerBlock("darkened_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> PANEL_DARKENED_STONE_BRICKS = registerBlock("panel_darkened_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> CHISELED_PANEL_DARKENED_STONE_BRICKS = registerBlock("chiseled_panel_darkened_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));

    // 1.6.9 features

    public static final RegistryObject<Block> SWIRLY_TECHNO_BLOCK = registerBlock("swirly_techno",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(1.5f,32F).requiresCorrectToolForDrops()
                    .sound(SoundType.NETHERITE_BLOCK)
                    .emissiveRendering(ThingamajigsBlocks::always)), 0);

    public static final RegistryObject<Block> SUNSTONE_BLOCK = registerBlock("sunstone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> MOONSTONE_BLOCK = registerBlock("moonstone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> RUNICSTONE_BLOCK = registerBlock("runicstone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> RUNICSTONE_BRICKS = registerBlock("runicstone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> EXPOSED_RUNICSTONE_BLOCK = registerBlock("exposed_runicstone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));

    public static final RegistryObject<Block> TRI_CANDLE_HOLDER_BLOCK = registerBlock("tri_candle_holder",
            () -> new TriCandleHolder(BlockBehaviour.Properties.of().lightLevel(tricandleblkem(12))));

    public static final RegistryObject<Block> GEARS_BLOCK = registerBlock("gears_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> MOVING_GEARS_BLOCK = registerBlock("moving_gears_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> TISSUE_BOX = registerBlock("tissue_box",
            () -> new TissueBox(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> METAL_VENTS = registerBlock("metal_vents",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> YELLOW_CAUTION = registerBlock("yellow_caution",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ORANGE_CAUTION = registerBlock("orange_caution",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ALT_ORANGE_CAUTION = registerBlock("alt_orange_caution",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> GREEN_CAUTION = registerBlock("green_caution",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> LIGHT_BLUE_CAUTION = registerBlock("light_blue_caution",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> RED_CAUTION = registerBlock("red_caution",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));


    public static final RegistryObject<Block> FRENCH_BRICK = registerBlock("french_brick",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS)));
    public static final RegistryObject<Block> ALT_FRENCH_BRICK = registerBlock("alt_french_brick",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS)));

    public static final RegistryObject<Block> PURIFYING_BOOKSHELF = registerBlock("purifying_bookshelf",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));

    public static final RegistryObject<Block> ALT_ROAD_COVER = registerBlock("alt_road_cover",
            () -> new RoadCoveringBlock(BlockBehaviour.Properties.of().noOcclusion().noCollission()), 0);
    public static final RegistryObject<Block> ROAD_PANEL_COVER = registerBlock("road_panel_cover",
            () -> new RoadCoveringBlock(BlockBehaviour.Properties.of().noOcclusion().noCollission()), 0);
    public static final RegistryObject<Block> ALT_ROAD_PANEL_COVER = registerBlock("alt_road_panel_cover",
            () -> new RoadCoveringBlock(BlockBehaviour.Properties.of().noOcclusion().noCollission()), 0);

    public static final RegistryObject<Block> SCROLLING_YELLOW_CAUTION = registerBlock("scrolling_yellow_caution",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> MIRROR = registerBlock("mirror",
            () -> new Mirror(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> CALENDAR = registerBlock("calendar",
            () -> new Calendar(BlockBehaviour.Properties.of()));


    // hawk signal state blocks
    public static final RegistryObject<Block> HAWK_SIGNAL_YELLOW = customRegisterBlock("hawk_signal_y",
            () -> new HawkYellow(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> HAWK_SIGNAL_FLASHING_YELLOW = customRegisterBlock("hawk_signal_fy",
            () -> new HawkFlashingYellow(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> HAWK_SIGNAL_RED = customRegisterBlock("hawk_signal_r",
            () -> new HawkRed(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> HAWK_SIGNAL_FLASHING_RED = customRegisterBlock("hawk_signal_fr",
            () -> new HawkFlashingRed(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> BARREL_KEG = registerBlock("barrel_keg",
            () -> new BarrelKeg(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> POOPOO = customRegisterBlock("poopoo",
            () -> new BetterPoop(BlockBehaviour.Properties.copy(Blocks.GRASS)
                    .sound(ThingamajigsSoundTypes.POOP).noCollission().noOcclusion()));
    public static final RegistryObject<Block> PICNIC_TABLE = registerBlock("picnic_table",
            () -> new PicnicTable(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> POWDER_KEG = registerBlock("powder_keg",
            () -> new PowderKeg(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> VERTICAL_REDSTONE_SIDEWALK = registerBlock("vertical_redstone_sidewalk",
            () -> new VerticalBlockRedstone(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_GRAY)
                    .strength(2f).requiresCorrectToolForDrops().sound(SoundType.TUFF)));

    public static final RegistryObject<Block> NO_LEFT_TURN = registerBlock("no_left_turn",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> NO_RIGHT_TURN = registerBlock("no_right_turn",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> NO_STRAIGHT = registerBlock("no_straight",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> NO_U_TURN = registerBlock("no_u_turn",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> DO_NOT_ENTER = registerBlock("do_not_enter",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> HAZARDOUS_MATERIALS = registerBlock("hm",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> NO_HAZARDOUS_MATERIALS = registerBlock("no_hm",
            () -> new Sign(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> ONLY_LEFT = registerBlock("only_left",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> ONLY_RIGHT = registerBlock("only_right",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> ONLY_UP = registerBlock("only_up",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> HOV_ONLY = registerBlock("hov_only",
            () -> new Sign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> HOV_ENDS = registerBlock("hov_ends",
            () -> new Sign(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> MALE_BATHROOM_SIGN = registerBlock("male_bathroom_sign",
            () -> new BathroomSign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> FEMALE_BATHROOM_SIGN = registerBlock("female_bathroom_sign",
            () -> new BathroomSign(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> BOTH_BATHROOM_SIGN = registerBlock("both_bathroom_sign",
            () -> new BathroomSign(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> FAN_BLOCK_SPARK = registerBlock("fan_block_spark",
            () -> new FanBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> SCRAP_PANELS = registerBlock("scrap_panels",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.COPPER)));

    // rubber tree blocks
    public static final RegistryObject<Block> RUBBER_LOG = registerBlock("rubber_log",
            () -> new ThingamajigsFlammableRotatedPillarBlock(
                    BlockBehaviour.Properties.copy(Blocks.SPRUCE_LOG).strength(3.5f)));
    public static final RegistryObject<Block> RUBBER_WOOD = registerBlock("rubber_wood",
            () -> new ThingamajigsFlammableRotatedPillarBlock(
                    BlockBehaviour.Properties.copy(Blocks.SPRUCE_WOOD).strength(3.5f)));
    public static final RegistryObject<Block> STRIPPED_RUBBER_LOG = registerBlock("stripped_rubber_log",
            () -> new ThingamajigsFlammableRotatedPillarBlock(
                    BlockBehaviour.Properties.copy(Blocks.STRIPPED_SPRUCE_LOG).strength(3.5f)));
    public static final RegistryObject<Block> STRIPPED_RUBBER_WOOD = registerBlock("stripped_rubber_wood",
            () -> new ThingamajigsFlammableRotatedPillarBlock(
                    BlockBehaviour.Properties.copy(Blocks.STRIPPED_SPRUCE_WOOD).strength(3.5f)));
    public static final RegistryObject<Block> RUBBER_PLANKS = registerBlock("rubber_planks",
            () -> new ThingamajigsFlammableNormalBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_PLANKS)));
    public static final RegistryObject<Block> RUBBER_LEAVES = registerBlock("rubber_leaves",
            () -> new ThingamajigsCustomLeavesBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_LEAVES)));

    public static final RegistryObject<Block> RUBBER_WOOD_SLAB = registerBlock("rubber_wood_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_SLAB)));

    public static final RegistryObject<Block> RUBBER_WOOD_STAIRS = registerBlock("rubber_wood_stairs",
            () -> new StairBlock(ThingamajigsBlocks.RUBBER_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.SPRUCE_STAIRS)));

    public static final RegistryObject<Block> RUBBER_WOOD_DOOR = registerBlock("rubber_wood_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_DOOR),BlockSetType.SPRUCE));
    public static final RegistryObject<Block> RUBBER_WOOD_TRAPDOOR = registerBlock("rubber_wood_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_TRAPDOOR),BlockSetType.SPRUCE));

    public static final RegistryObject<Block> RUBBER_WOOD_BUTTON = registerBlock("rubber_wood_button",
            () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_BUTTON)
                    .noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY),
                    BlockSetType.SPRUCE,25,true));

    public static final RegistryObject<Block> RUBBER_WOOD_FENCE = registerBlock("rubber_wood_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_FENCE)));

    public static final RegistryObject<Block> RUBBER_WOOD_FENCE_GATE = registerBlock("rubber_wood_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_FENCE_GATE),
                    SoundEvents.FENCE_GATE_OPEN,SoundEvents.FENCE_GATE_CLOSE));

    public static final RegistryObject<Block> RUBBER_WOOD_PRESSURE_PLATE = registerBlock("rubber_wood_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
                    BlockBehaviour.Properties.copy(Blocks.SPRUCE_PRESSURE_PLATE)
                            .mapColor(MapColor.TERRACOTTA_YELLOW),
                    BlockSetType.SPRUCE));

    public static final RegistryObject<Block> RUBBER_SAPLING = registerBlock("rubber_sapling",
            () -> new SaplingBlock(new RubberTreeGrower(), BlockBehaviour.Properties.copy(Blocks.SPRUCE_SAPLING)));
    // end rubber stuff

    public static final RegistryObject<Block> TINY_CROSSING = registerBlock("tiny_crossing",
            () -> new DirectionalFlatTwoSidedBlock(BlockBehaviour.Properties.of()
                    .strength(0.2f,1f).sound(SoundType.DEEPSLATE_TILES)
                    .mapColor(MapColor.METAL).instabreak().noCollission()));


    public static final RegistryObject<Block> OUTLET_BLOCK = registerBlock("outlet",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> PAPER_WALL_BLOCK = registerBlock("paper_wall",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BAMBOO_MOSAIC)));

    public static final RegistryObject<Block> PAPER_FLOWER_WALL_BLOCK = registerBlock("paper_flower_wall",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BAMBOO_MOSAIC)));

    public static final RegistryObject<Block> WHITE_PARKING_ASPHALT = registerBlock("white_parking_asphalt",
            () -> new MarkedAsphalt(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> YELLOW_PARKING_ASPHALT = registerBlock("yellow_parking_asphalt",
            () -> new MarkedAsphalt(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> BLUE_PARKING_ASPHALT = registerBlock("blue_parking_asphalt",
            () -> new MarkedAsphalt(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops()));


    public static final RegistryObject<Block> DUMPSTER = registerBlock("dumpster",
            () -> new DumpsterBlock(BlockBehaviour.Properties.copy(Blocks.CAULDRON).noOcclusion()));

    public static final RegistryObject<Block> COMMERCIAL_LIQUID_DISPENSER = registerBlock("commercial_liquid_dispenser",
            () -> new CommercialLiquidDispenser(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> COMMERCIAL_CONDIMENT_DISPENSER = registerBlock("commercial_condiment_dispenser",
            () -> new CommercialCondimentDispenser(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<Block> COMMERCIAL_JUICE_DISPENSER = registerBlock("commercial_juice_dispenser",
            () -> new CommercialJuiceDispenser(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> COMMERCIAL_UTENCIL_DISPENSER = registerBlock("commercial_utencil_dispenser",
            () -> new CommercialUtencilDispenser(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    // 1.7.0 features
    public static final RegistryObject<Block> METALLIC_DOOR_BELL = registerBlock("metallic_door_bell",
            () -> new Doorbell(ThingamajigsSoundEvents.METALLIC.get(),BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> OLD_DOOR_BELL = registerBlock("old_door_bell",
            () -> new Doorbell(ThingamajigsSoundEvents.OLD.get(),BlockBehaviour.Properties.copy(Blocks.CRACKED_STONE_BRICKS)));
    public static final RegistryObject<Block> PLUCK_DOOR_BELL = registerBlock("pluck_door_bell",
            () -> new Doorbell(ThingamajigsSoundEvents.PLUCK.get(),BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));

    public static final RegistryObject<Block> ATM = registerBlock("atm",
            () -> new ATM(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .sound(SoundType.NETHERITE_BLOCK).lightLevel((s) -> 5)));

    public static final RegistryObject<Block> INSET_ATM = registerBlock("inset_atm",
            () -> new ThingamajigsDecorativeBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .sound(SoundType.NETHERITE_BLOCK).lightLevel((s) -> 5)));

    // 1.7.1 features
    public static final RegistryObject<Block> TRASH_CAN = registerBlock("trash_can",
            () -> new TrashCan(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> CHANGE_MACHINE = registerBlock("change_machine",
            () -> new ChangeMachine(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> PARTICULAR_STATUE = registerBlock("particular_statue",
            () -> new Podium(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> SPRING = customRegisterBlock("spring",
            () -> new SpringBlock(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> VIDEO_CAMERA = registerBlock("video_camera",
            () -> new VideoCamera(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> PROFESSIONAL_TV_CAMERA = registerBlock("professional_tv_camera",
            () -> new ProfessionalTVCamera(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> STUDIO_CAMERA = registerBlock("studio_camera",
            () -> new StudioCamera(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> COMMERCIAL_WASHER = registerBlock("commercial_washer",
            () -> new ToggledStateBlock(BlockBehaviour.Properties.of()
                    .sound(SoundType.LANTERN).strength(1f,5f).noOcclusion()));
    public static final RegistryObject<Block> COMMERCIAL_DRYER = registerBlock("commercial_dryer",
            () -> new ToggledStateBlock(BlockBehaviour.Properties.of()
                    .sound(SoundType.LANTERN).strength(1f,5f).noOcclusion()));

    public static final RegistryObject<Block> GOBO_LIGHT = registerBlock("gobo_light",
            () -> new DJGoboLight(BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> TURNTABLE = registerBlock("turntable",
            () -> new Turntable(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> ITEM_DISPLAY_BLOCK = registerBlock("item_displayer",
            () -> new ItemDisplayBlock(BlockBehaviour.Properties.of().lightLevel((s) -> 15)));

    // glow block
    public static final RegistryObject<Block> GLOW_BLOCK = registerBlock("glow_block",
            () -> new Block(BlockBehaviour.Properties.of().hasPostProcess(ThingamajigsBlocks::always).emissiveRendering(ThingamajigsBlocks::always)
                    .sound(SoundType.COPPER).mapColor(MapColor.WOOL).strength(1.1f,2.5f)));
    public static final RegistryObject<Block> LIGHT_GRAY_GLOW_BLOCK = registerBlock("light_gray_glow_block",
            () -> new Block(BlockBehaviour.Properties.of().hasPostProcess(ThingamajigsBlocks::always).emissiveRendering(ThingamajigsBlocks::always)
                    .sound(SoundType.COPPER).mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.1f,2.5f)));
    public static final RegistryObject<Block> GRAY_GLOW_BLOCK = registerBlock("gray_glow_block",
            () -> new Block(BlockBehaviour.Properties.of().hasPostProcess(ThingamajigsBlocks::always).emissiveRendering(ThingamajigsBlocks::always)
                    .sound(SoundType.COPPER).mapColor(MapColor.COLOR_GRAY).strength(1.1f,2.5f)));
    public static final RegistryObject<Block> BLACK_GLOW_BLOCK = registerBlock("black_glow_block",
            () -> new Block(BlockBehaviour.Properties.of().hasPostProcess(ThingamajigsBlocks::always).emissiveRendering(ThingamajigsBlocks::always)
                    .sound(SoundType.COPPER).mapColor(MapColor.COLOR_BLACK).strength(1.1f,2.5f)));
    public static final RegistryObject<Block> BROWN_GLOW_BLOCK = registerBlock("brown_glow_block",
            () -> new Block(BlockBehaviour.Properties.of().hasPostProcess(ThingamajigsBlocks::always).emissiveRendering(ThingamajigsBlocks::always)
                    .sound(SoundType.COPPER).mapColor(MapColor.COLOR_BROWN).strength(1.1f,2.5f)));
    public static final RegistryObject<Block> RED_GLOW_BLOCK = registerBlock("red_glow_block",
            () -> new Block(BlockBehaviour.Properties.of().hasPostProcess(ThingamajigsBlocks::always).emissiveRendering(ThingamajigsBlocks::always)
                    .sound(SoundType.COPPER).mapColor(MapColor.COLOR_RED).strength(1.1f,2.5f)));
    public static final RegistryObject<Block> ORANGE_GLOW_BLOCK = registerBlock("orange_glow_block",
            () -> new Block(BlockBehaviour.Properties.of().hasPostProcess(ThingamajigsBlocks::always).emissiveRendering(ThingamajigsBlocks::always)
                    .sound(SoundType.COPPER).mapColor(MapColor.COLOR_ORANGE).strength(1.1f,2.5f)));
    public static final RegistryObject<Block> YELLOW_GLOW_BLOCK = registerBlock("yellow_glow_block",
            () -> new Block(BlockBehaviour.Properties.of().hasPostProcess(ThingamajigsBlocks::always).emissiveRendering(ThingamajigsBlocks::always)
                    .sound(SoundType.COPPER).mapColor(MapColor.COLOR_YELLOW).strength(1.1f,2.5f)));
    public static final RegistryObject<Block> LIME_GLOW_BLOCK = registerBlock("lime_glow_block",
            () -> new Block(BlockBehaviour.Properties.of().hasPostProcess(ThingamajigsBlocks::always).emissiveRendering(ThingamajigsBlocks::always)
                    .sound(SoundType.COPPER).mapColor(MapColor.COLOR_LIGHT_GREEN).strength(1.1f,2.5f)));
    public static final RegistryObject<Block> GREEN_GLOW_BLOCK = registerBlock("green_glow_block",
            () -> new Block(BlockBehaviour.Properties.of().hasPostProcess(ThingamajigsBlocks::always).emissiveRendering(ThingamajigsBlocks::always)
                    .sound(SoundType.COPPER).mapColor(MapColor.COLOR_GREEN).strength(1.1f,2.5f)));
    public static final RegistryObject<Block> CYAN_GLOW_BLOCK = registerBlock("cyan_glow_block",
            () -> new Block(BlockBehaviour.Properties.of().hasPostProcess(ThingamajigsBlocks::always).emissiveRendering(ThingamajigsBlocks::always)
                    .sound(SoundType.COPPER).mapColor(MapColor.COLOR_CYAN).strength(1.1f,2.5f)));
    public static final RegistryObject<Block> LIGHT_BLUE_GLOW_BLOCK = registerBlock("light_blue_glow_block",
            () -> new Block(BlockBehaviour.Properties.of().hasPostProcess(ThingamajigsBlocks::always).emissiveRendering(ThingamajigsBlocks::always)
                    .sound(SoundType.COPPER).mapColor(MapColor.COLOR_LIGHT_BLUE).strength(1.1f,2.5f)));
    public static final RegistryObject<Block> BLUE_GLOW_BLOCK = registerBlock("blue_glow_block",
            () -> new Block(BlockBehaviour.Properties.of().hasPostProcess(ThingamajigsBlocks::always).emissiveRendering(ThingamajigsBlocks::always)
                    .sound(SoundType.COPPER).mapColor(MapColor.COLOR_BLUE).strength(1.1f,2.5f)));
    public static final RegistryObject<Block> PURPLE_GLOW_BLOCK = registerBlock("purple_glow_block",
            () -> new Block(BlockBehaviour.Properties.of().hasPostProcess(ThingamajigsBlocks::always).emissiveRendering(ThingamajigsBlocks::always)
                    .sound(SoundType.COPPER).mapColor(MapColor.COLOR_PURPLE).strength(1.1f,2.5f)));
    public static final RegistryObject<Block> MAGENTA_GLOW_BLOCK = registerBlock("magenta_glow_block",
            () -> new Block(BlockBehaviour.Properties.of().hasPostProcess(ThingamajigsBlocks::always).emissiveRendering(ThingamajigsBlocks::always)
                    .sound(SoundType.COPPER).mapColor(MapColor.COLOR_MAGENTA).strength(1.1f,2.5f)));
    public static final RegistryObject<Block> PINK_GLOW_BLOCK = registerBlock("pink_glow_block",
            () -> new Block(BlockBehaviour.Properties.of().hasPostProcess(ThingamajigsBlocks::always).emissiveRendering(ThingamajigsBlocks::always)
                    .sound(SoundType.COPPER).mapColor(MapColor.COLOR_PINK).strength(1.1f,2.5f)));

    // lab blocks
    public static final RegistryObject<Block> GRAY_SCREEN = registerBlock("gray_screen",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .mapColor(MapColor.COLOR_GRAY).lightLevel((s) -> 5)));
    public static final RegistryObject<Block> BLUE_SCREEN = registerBlock("blue_screen",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .mapColor(MapColor.COLOR_BLUE).lightLevel((s) -> 5)));

    public static final RegistryObject<Block> RUBBER_LANE = registerBlock("rubber_lane",
            () -> new LaneBlock(BlockBehaviour.Properties.of().ignitedByLava()));

    public static final RegistryObject<Block> BRAMBLE = registerBlock("bramble",
            () -> new DeadBushBlock(BlockBehaviour.Properties.copy(Blocks.DEAD_BUSH)){
                @Override
                protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
                    if(pState.is(BlockTags.DEAD_BUSH_MAY_PLACE_ON)){
                        return true;
                    }
                    return false;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 80;
                }
            });


    public static final RegistryObject<Block> POTTED_BRAMBLE = registerBlock("potted_bramble",
            () -> new FlowerPotBlock(ThingamajigsBlocks.BRAMBLE.get(),
                    BlockBehaviour.Properties.copy(Blocks.POTTED_DEAD_BUSH)
                            .instabreak().noOcclusion()));

    public static final RegistryObject<Block> DJ_LASER_LIGHT = registerBlock("laser_light",
            () -> new DJLaserLight(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).mapColor(MapColor.METAL)));




    // end of blocks list

    // TESTING BLOCKS

    /*
    public static final RegistryObject<Block> TEST_BLOCK = registerBlock("test_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    */

    // END TESTING BLOCKS LIST

    // additional things (may use Mojang Mappings)
    // states
    private static ToIntFunction<BlockState> openSignLitEmission(int pLightValue) {
        return (properties) -> {
            return properties.getValue(OpenSign.TOGGLED) ? 0 : pLightValue;
        };
    }

    private static ToIntFunction<BlockState> tricandleblkem(int lightvalue) {
        return (properties) -> {
            return properties.getValue(TriCandleHolder.LIT) ? lightvalue : 0;
        };
    }

    private static boolean always(BlockState bs, BlockGetter bg, BlockPos bp) {
        return true;
    }

    private static boolean never(BlockState bs, BlockGetter bg, BlockPos bp) {
        return false;
    }



    private static ToIntFunction<BlockState> customLitBlockEmission(int pLightValue) {
        return (properties) -> {
            return properties.getValue(BlockStateProperties.LIT) ? pLightValue : 0;
        };
    }

    private static ToIntFunction<BlockState> enabledLitBlockEmission(int i) {
        return (properties) -> {
            return properties.getValue(BlockStateProperties.ENABLED) ? i : 0;
        };
    }

    private static ToIntFunction<BlockState> modeLitBlockEmission(int i) {
        return (properties) -> {
            return properties.getValue(ArrowBoard.MODE);
        };
    }

    private static ToIntFunction<BlockState> onLitBlockEmission(int i) {
        return (properties) -> {
            return properties.getValue(TallLamp.ON) ? i : 0;
        };
    }

    private static ToIntFunction<BlockState> rrCrossingLightsEmission(int i) {
        return (properties) -> {
            return properties.getValue(RailroadCrossingLights.POWERED) ? i : 0;
        };
    }

    private static ToIntFunction<BlockState> rrCrossingCantileverLightEmission(int i) {
        return (properties) -> {
            return properties.getValue(RailroadCrossingCantilever.POWERED) ? i : 0;
        };
    }

    // Block Registration Methods

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, String tooltipKey) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tooltipKey);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, String tooltipKey) {
        ThingamajigsItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()) {
            @Override
            public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn) {
                tooltip.add(Component.translatable(tooltipKey));
                super.appendHoverText(stack, level, tooltip, flagIn);
            }
        });
    }

    // this method is used for old blocks, new blocks don't use this method anymore, use @registerBlock(name,block) instead
    // removed that ANNOYING creative mode tab condition and replaced it with a placeholder integer
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, int placeholder) {
        // changed how blocks handle c mode tabs by removing them entirely from this part of the code
        // the xtra variable is never used except for being a placeholder
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, 0);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, int idk2) {
        ThingamajigsItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    // This is only used if a block shouldn't be in the inventory at ANY point in Gameplay
    private static <T extends Block>RegistryObject<T> customRegisterBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ThingamajigsItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
