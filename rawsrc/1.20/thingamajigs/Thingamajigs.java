package net.rk.thingamajigs;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.rk.thingamajigs.block.ThingamajigsBlocks;
import net.rk.thingamajigs.config.ThingamajigsServerConfigs;
import net.rk.thingamajigs.entity.ThingamajigsBlockEntities;
import net.rk.thingamajigs.events.ThingamajigsSoundEvents;
import net.rk.thingamajigs.fluid.ThingamajigsFluids;
import net.rk.thingamajigs.item.ThingamajigsCreativeTab;
import net.rk.thingamajigs.item.ThingamajigsItems;
import net.rk.thingamajigs.misc.ThingamajigsBlockTypes;
import net.rk.thingamajigs.painting.ThingamajigsPaintings;

@Mod(Thingamajigs.MOD_ID)
public class Thingamajigs {
    public static final String MOD_ID = "thingamajigs";
    private static final org.slf4j.Logger LOGGERV2 = LogUtils.getLogger();

    public Thingamajigs() {
        // Register the setup method for mod loading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // register creative mode tabs using new method for 1.20+
        ThingamajigsCreativeTab.register(eventBus);

        // register all OTHER classes from the mod using their eventBus 'getter' method
        ThingamajigsSoundEvents.register(eventBus); // use new system
        ThingamajigsItems.register(eventBus);
        ThingamajigsBlocks.register(eventBus);
        ThingamajigsPaintings.register(eventBus);
        ThingamajigsBlockEntities.register(eventBus);

        // Fluid and Fluid Type Registry
        ThingamajigsFluids.FLUID_TYPES.register(eventBus);
        ThingamajigsFluids.FLUIDS.register(eventBus);

        // setup listeners "...oh im, listening for your voice..."
        eventBus.addListener(this::setup);
        eventBus.addListener(this::setupClient);

        // setup MORE listeners "more!"
        eventBus.addListener(this::addCreative);

        // register client and server (common) configs
        LOGGERV2.info("Thingamajigs is doing final preparations.");

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ThingamajigsServerConfigs.CPSEC);

        // register mod for server and client
        try{
            MinecraftForge.EVENT_BUS.register(this);
        }
        catch (Exception e){
            LOGGERV2.error("Thingamajigs had an error during event bus registry phase (self error).");
        }
    }

    // New MC Tabs System
    private void addCreative(BuildCreativeModeTabContentsEvent event){
        if(event.getTab() == ThingamajigsCreativeTab.ALL_ITEMS_TAB_v2.get()){
            // ingredient items
            event.accept(ThingamajigsItems.THINGAMAJIG);
            event.accept(ThingamajigsItems.THINGAMAJIG_GLOB);
            event.accept(ThingamajigsItems.SIGN_GLOB);
            event.accept(ThingamajigsItems.DOOR_GLOB);
            event.accept(ThingamajigsItems.POOP_HORN);
            // food
            event.accept(ThingamajigsItems.GLOB_SANDWICH);
            // fluid items
            event.accept(ThingamajigsItems.PURIFYING_WATER_BUCKET);
            event.accept(ThingamajigsItems.SLUDGE_BUCKET);
            // plants and flowers
            event.accept(ThingamajigsBlocks.BULBY_FLOWER.get().asItem());
            event.accept(ThingamajigsBlocks.DROOPY_FLOWER.get().asItem());
            // interactive items (torch placers and other block items)
            event.accept(ThingamajigsItems.CLEAR_BULB_ITEM);
            event.accept(ThingamajigsItems.FULL_BULB_ITEM);
            event.accept(ThingamajigsItems.CLEAR_LANTERN_ITEM);
            event.accept(ThingamajigsItems.FULL_LANTERN_ITEM);
            event.accept(ThingamajigsItems.PAPER_LANTERN_ITEM);
            event.accept(ThingamajigsItems.RED_LANTERN_ITEM);
            // lanterns
            event.accept(ThingamajigsBlocks.SCULK_LANTERN.get().asItem());
            // start full blocks
            // Mini-City Blocks
            event.accept(ThingamajigsBlocks.MINI_ROAD.get().asItem());
            event.accept(ThingamajigsBlocks.MINI_RAIL.get().asItem());
            event.accept(ThingamajigsBlocks.MINI_RED_BUILDING.get().asItem());
            event.accept(ThingamajigsBlocks.MINI_YELLOW_BUILDING.get().asItem());
            event.accept(ThingamajigsBlocks.MINI_TALL_YELLOW_BUILDING.get().asItem());
            event.accept(ThingamajigsBlocks.MINI_GREEN_BUILDING.get().asItem());
            event.accept(ThingamajigsBlocks.MINI_BLUE_BUILDING.get().asItem());
            // Doors
            event.accept(ThingamajigsBlocks.WHITE_WOOD_DOOR.get().asItem());
            event.accept(ThingamajigsBlocks.SCREEN_DOOR.get().asItem());
            event.accept(ThingamajigsBlocks.FESTIVE_DOOR.get().asItem());
            event.accept(ThingamajigsBlocks.SNOWMAN_DOOR.get().asItem());
            event.accept(ThingamajigsBlocks.STONE_DOOR.get().asItem());
            event.accept(ThingamajigsBlocks.BUBBLE_DOOR.get().asItem());
            event.accept(ThingamajigsBlocks.METALLIC_DOOR.get().asItem());
            event.accept(ThingamajigsBlocks.ALARMED_DOOR.get().asItem());
            // chains
            event.accept(ThingamajigsBlocks.SCULK_CHAIN.get().asItem());
            // Misc. Begin Blocks
            event.accept(ThingamajigsBlocks.DECORATIVE_PORTAL.get().asItem());
            event.accept(ThingamajigsBlocks.NOT_QUITE_MENGER.get().asItem());
            event.accept(ThingamajigsBlocks.SPOOKY_STONE.get().asItem());
            event.accept(ThingamajigsBlocks.BLUEBERRY_STONE.get().asItem());
            event.accept(ThingamajigsBlocks.NETHERISH_STONE.get().asItem());
            event.accept(ThingamajigsBlocks.VOLCANIC_STONE.get().asItem());
            event.accept(ThingamajigsBlocks.CHARGED_VOLCANIC_STONE.get().asItem());
            // Techno Blocks
            event.accept(ThingamajigsBlocks.TECHNO_CORE.get().asItem());
            event.accept(ThingamajigsBlocks.TECHNO_PILLAR.get().asItem());
            event.accept(ThingamajigsBlocks.CHISELED_TECHNO_BLOCK.get().asItem());
            event.accept(ThingamajigsBlocks.NEON_BLOCK.get().asItem());
            event.accept(ThingamajigsBlocks.ALT_NEON_BLOCK.get().asItem());
            // other tech blocks
            event.accept(ThingamajigsBlocks.CIRCUITS.get().asItem());
            // Factory Blocks
            event.accept(ThingamajigsBlocks.METAL_SCAFFOLDING.get().asItem());
            event.accept(ThingamajigsBlocks.FAN_BLOCK_ULTRASONIC.get().asItem());
            event.accept(ThingamajigsBlocks.FAN_BLOCK_FAST.get().asItem());
            event.accept(ThingamajigsBlocks.FAN_BLOCK.get().asItem());
            event.accept(ThingamajigsBlocks.FAN_BLOCK_OFF.get().asItem());
            event.accept(ThingamajigsBlocks.TRANSPARENT_FAST_FAN_BLOCK.get().asItem());
            event.accept(ThingamajigsBlocks.TRANSPARENT_FAN_BLOCK.get().asItem());
            event.accept(ThingamajigsBlocks.TRANSPARENT_OFF_FAN_BLOCK.get().asItem());
            event.accept(ThingamajigsBlocks.GRATE.get().asItem());
            // misc other glass blocks
            event.accept(ThingamajigsBlocks.SCREEN.get().asItem());
            // Fancy Decoration Blocks
            event.accept(ThingamajigsBlocks.MYSTERIOUS_ONE_WOOL.get().asItem());
            event.accept(ThingamajigsBlocks.FIREOUS_GLAZED_TERRACOTTA.get().asItem());
            event.accept(ThingamajigsBlocks.DARK_FIREOUS_GLAZED_TERRACOTTA.get().asItem());
            event.accept(ThingamajigsBlocks.CRYSTAL_BLOCK.get().asItem());
            // Useful Blocks
            event.accept(ThingamajigsBlocks.NETHER_CHISELED_BOOKSHELF.get().asItem());
            // Railroad or Railway
            event.accept(ThingamajigsBlocks.PURPLE_RAIL.get().asItem());
            event.accept(ThingamajigsBlocks.PURPLE_POWERED_RAIL.get().asItem());
            event.accept(ThingamajigsBlocks.PURPLE_DETECTOR_RAIL.get().asItem());
            event.accept(ThingamajigsBlocks.PURPLE_ACTIVATOR_RAIL.get().asItem());
            // Road Blocks & Items
            event.accept(ThingamajigsItems.PAINT_BRUSH);
            event.accept(ThingamajigsItems.WHITE_PAINT_BRUSH);
            event.accept(ThingamajigsItems.YELLOW_PAINT_BRUSH);
            event.accept(ThingamajigsItems.BLUE_PAINT_BRUSH);
            //
            //
            event.accept(ThingamajigsBlocks.ASPHALT.get().asItem());
            event.accept(ThingamajigsBlocks.ASPHALT_OK.get().asItem());
            event.accept(ThingamajigsBlocks.ASPHALT_MEDIOCRE.get().asItem());
            event.accept(ThingamajigsBlocks.ASPHALT_OLD.get().asItem());
            event.accept(ThingamajigsBlocks.SIDEWALK.get().asItem());
            event.accept(ThingamajigsBlocks.SIDEWALK_CRACKED.get().asItem());
            event.accept(ThingamajigsBlocks.SIDEWALK_SECTIONED.get().asItem());
            event.accept(ThingamajigsBlocks.SIDEWALK_BLOCKED.get().asItem());
            // road slabs
            event.accept(ThingamajigsBlocks.ASPHALT_SLAB.get().asItem());
            event.accept(ThingamajigsBlocks.ASPHALT_OK_SLAB.get().asItem());
            event.accept(ThingamajigsBlocks.ASPHALT_MEDIOCRE_SLAB.get().asItem());
            event.accept(ThingamajigsBlocks.ASPHALT_OLD_SLAB.get().asItem());
            event.accept(ThingamajigsBlocks.SIDEWALK_SLAB.get().asItem());
            event.accept(ThingamajigsBlocks.CRACKED_SIDEWALK_SLAB.get().asItem());
            event.accept(ThingamajigsBlocks.SECTIONED_SIDEWALK_SLAB.get().asItem());
            event.accept(ThingamajigsBlocks.BLOCKED_SIDEWALK_SLAB.get().asItem());
            // painted road blocks
            event.accept(ThingamajigsBlocks.DOUBLE_WHITE_ASPHALT.get().asItem());
            event.accept(ThingamajigsBlocks.DOUBLE_YELLOW_ASPHALT.get().asItem());
            event.accept(ThingamajigsBlocks.DOUBLE_CORNER_WHITE_ASPHALT.get().asItem());
            event.accept(ThingamajigsBlocks.DOUBLE_CORNER_YELLOW_ASPHALT.get().asItem());
            // Poles and Generic Road Side Things
            event.accept(ThingamajigsBlocks.LIGHT_POLE.get().asItem());
            event.accept(ThingamajigsBlocks.HOLDER_POLE.get().asItem());
            event.accept(ThingamajigsBlocks.STRAIGHT_POLE.get().asItem());
            event.accept(ThingamajigsBlocks.STRAIGHT_HORIZONTAL_POLE.get().asItem());
            event.accept(ThingamajigsBlocks.L_POLE.get().asItem());
            event.accept(ThingamajigsBlocks.L_ONLY_POLE.get().asItem());
            event.accept(ThingamajigsBlocks.AXIS_POLE.get().asItem());
            event.accept(ThingamajigsBlocks.PLUS_POLE.get().asItem());
            event.accept(ThingamajigsBlocks.TL_CONNECTOR.get().asItem());
            event.accept(ThingamajigsBlocks.T_POLE.get().asItem());
            event.accept(ThingamajigsBlocks.T_POLE_B.get().asItem());
            event.accept(ThingamajigsBlocks.T_POLE_C.get().asItem());
            event.accept(ThingamajigsBlocks.VERTICAL_T_POLE.get().asItem());
            event.accept(ThingamajigsBlocks.TRI_POLE.get().asItem());
            event.accept(ThingamajigsBlocks.TRI_POLE_B.get().asItem());
            event.accept(ThingamajigsBlocks.T_HORZ_ONLY_POLE.get().asItem());
            event.accept(ThingamajigsBlocks.VERTICAL_POLE_REDSTONE.get().asItem());
            // railroad crossing stuff
            event.accept(ThingamajigsBlocks.RR_CANTILEVER.get().asItem());
            event.accept(ThingamajigsBlocks.RR_CANTILEVER_END.get().asItem());
            event.accept(ThingamajigsBlocks.RR_CANTILEVER_LIGHTS.get().asItem());
            event.accept(ThingamajigsBlocks.RAILROAD_CROSSING.get().asItem());
            event.accept(ThingamajigsBlocks.RAILROAD_CROSSING_LIGHTS.get().asItem());
            event.accept(ThingamajigsBlocks.BLUEY_MECHANICAL_BELL.get().asItem());
            event.accept(ThingamajigsBlocks.BLUEY_MECHANICAL_BELL_TWO.get().asItem());
            event.accept(ThingamajigsBlocks.EBELL_ONE.get().asItem());
            event.accept(ThingamajigsBlocks.EBELL_TWO.get().asItem());
            // international railroad crossing stuff
            event.accept(ThingamajigsBlocks.RAILROAD_CROSSING_BLOCKER.get().asItem());
            // other traffic control things
            event.accept(ThingamajigsBlocks.STOP_GATE.get().asItem());
            event.accept(ThingamajigsBlocks.ARROW_BOARD.get().asItem());
            // traffic control
            event.accept(ThingamajigsBlocks.TRAFFIC_CONTROL_BOX.get().asItem());
            // Traffic Signals
            event.accept(ThingamajigsBlocks.CROSSWALK_BUTTON.get().asItem());
            event.accept(ThingamajigsBlocks.PED_FLASHERS.get().asItem());
            event.accept(ThingamajigsBlocks.HAWK_SIGNAL.get().asItem());
            event.accept(ThingamajigsBlocks.PED_SIGNAL_WORDED.get().asItem());
            event.accept(ThingamajigsBlocks.PED_SIGNAL_SYMBOLS.get().asItem());
            event.accept(ThingamajigsBlocks.PED_SIGNAL_MAN_1.get().asItem());
            event.accept(ThingamajigsBlocks.TRAFFIC_SIGNAL_NORMAL_1.get().asItem());
            event.accept(ThingamajigsBlocks.TRAFFIC_SIGNAL_NORMAL_2.get().asItem());
            event.accept(ThingamajigsBlocks.TRAFFIC_SIGNAL_NORMAL_3.get().asItem());
            event.accept(ThingamajigsBlocks.TRAFFIC_SIGNAL_NORMAL_4.get().asItem());
            event.accept(ThingamajigsBlocks.TRAFFIC_SIGNAL_DOGHOUSE_1.get().asItem());
            event.accept(ThingamajigsBlocks.TRAFFIC_SIGNAL_DOGHOUSE_2.get().asItem());
            event.accept(ThingamajigsBlocks.TRAFFIC_SIGNAL_SYMBOL_1.get().asItem());
            event.accept(ThingamajigsBlocks.TRAFFIC_SIGNAL_YELLOW_FLASH.get().asItem());
            event.accept(ThingamajigsBlocks.TRAFFIC_SIGNAL_RED_FLASH.get().asItem());
            event.accept(ThingamajigsBlocks.HORIZONTAL_TRAFFIC_SIGNAL_1.get().asItem());
            event.accept(ThingamajigsBlocks.HORIZONTAL_TRAFFIC_SIGNAL_3.get().asItem());
            event.accept(ThingamajigsBlocks.HORIZONTAL_TRAFFIC_SIGNAL_2.get().asItem());
            event.accept(ThingamajigsBlocks.YELLOW_BEACON.get().asItem());
            event.accept(ThingamajigsBlocks.RED_BEACON.get().asItem());
            event.accept(ThingamajigsBlocks.ARROW_BEACON.get().asItem());
            event.accept(ThingamajigsBlocks.TRAFFIC_SIGNAL_ALLWAY_STOP_BEACON.get().asItem());
            // Road Construction Blocks
            event.accept(ThingamajigsBlocks.ROAD_PANEL.get().asItem());
            event.accept(ThingamajigsBlocks.ROAD_BARRIER_SMALL.get().asItem());
            event.accept(ThingamajigsBlocks.ROAD_BARRIER_SMALL_LIGHTED.get().asItem());
            event.accept(ThingamajigsBlocks.ROAD_BARRIER_LIGHTED.get().asItem());
            event.accept(ThingamajigsBlocks.ROAD_BARRIER_CLOSED.get().asItem());
            event.accept(ThingamajigsBlocks.ROAD_BARRIER_THRU_CLOSED.get().asItem());
            event.accept(ThingamajigsBlocks.ROAD_BARRIER_BRIDGE_CLOSED.get().asItem());
            event.accept(ThingamajigsBlocks.ROAD_BARRIER_BRIDGE_THRU_CLOSED.get().asItem());
            event.accept(ThingamajigsBlocks.BIG_ROAD_CONE.get().asItem());
            event.accept(ThingamajigsBlocks.ROAD_BARREL.get().asItem());
            event.accept(ThingamajigsBlocks.ROAD_CHANNELIZER.get().asItem());
            event.accept(ThingamajigsBlocks.CONCRETE_BARRIER.get().asItem());
            event.accept(ThingamajigsBlocks.REBAR_CONCRETE_BARRIER.get().asItem());
            event.accept(ThingamajigsBlocks.REINFORCED_CONCRETE_BARRIER.get().asItem());
            event.accept(ThingamajigsBlocks.BRIDGE_BARRIER.get().asItem());
            event.accept(ThingamajigsBlocks.ROAD_COVER.get().asItem());
            // car wash
            event.accept(ThingamajigsBlocks.CAR_WASH_SIGNAGE.get().asItem());
            event.accept(ThingamajigsBlocks.CAR_WASH_SIGNAL.get().asItem());
            event.accept(ThingamajigsBlocks.CAR_WASH_TIRE_SCRUBBER.get().asItem());
            event.accept(ThingamajigsBlocks.CAR_WASH_MIXED_BRUSH.get().asItem());
            event.accept(ThingamajigsBlocks.CAR_WASH_DRIPPER.get().asItem());
            event.accept(ThingamajigsBlocks.CAR_WASH_SPRAYER.get().asItem());
            event.accept(ThingamajigsBlocks.CAR_WASH_TRIFOAMER.get().asItem());
            event.accept(ThingamajigsBlocks.CAR_WASH_SOAPER.get().asItem());
            event.accept(ThingamajigsBlocks.CAR_WASH_WAXER.get().asItem());
            event.accept(ThingamajigsBlocks.CAR_WASH_BLUE_BRUSH.get().asItem());
            event.accept(ThingamajigsBlocks.CAR_WASH_YELLOW_BRUSH.get().asItem());
            event.accept(ThingamajigsBlocks.CAR_WASH_RED_BRUSH.get().asItem());
            event.accept(ThingamajigsBlocks.CAR_WASH_MITTER_CURTAIN.get().asItem());
            event.accept(ThingamajigsBlocks.CAR_WASH_DRYER.get().asItem());
            // telephone & cell service towers
            event.accept(ThingamajigsBlocks.PHONE_SWITCHER.get().asItem());
            event.accept(ThingamajigsBlocks.CELL_MULTI_ANGLED_TRANSMITTER.get().asItem());
            event.accept(ThingamajigsBlocks.CELL_MULTI_TRANSMITTER.get().asItem());
            event.accept(ThingamajigsBlocks.CELL_TRANSMITTER.get().asItem());
            event.accept(ThingamajigsBlocks.CELL_MICROWAVE_TRANSMITTER.get().asItem());
            event.accept(ThingamajigsBlocks.OLD_MICROWAVE_TRANSMITTER.get().asItem());
            // Tiling and Flooring Blocks
            event.accept(ThingamajigsBlocks.CRYSTALINE_STONE.get().asItem());
            event.accept(ThingamajigsBlocks.INDENTED_STONE.get().asItem());
            event.accept(ThingamajigsBlocks.PANEL_STONE.get().asItem());
            //
            event.accept(ThingamajigsBlocks.PANEL_STONE_BRICKS.get().asItem());
            event.accept(ThingamajigsBlocks.MOSSY_PANEL_STONE_BRICKS.get().asItem());
            event.accept(ThingamajigsBlocks.CRACKED_PANEL_STONE_BRICKS.get().asItem());
            event.accept(ThingamajigsBlocks.CHISELED_PANEL_STONE_BRICKS.get().asItem());
            // connected texture blocks
            event.accept(ThingamajigsBlocks.STONE_PILLAR.get().asItem());
            event.accept(ThingamajigsBlocks.STONE_BRICK_PILLAR.get().asItem());
            event.accept(ThingamajigsBlocks.CHISELED_STONE_BRICK_PILLAR.get().asItem());
            //
            event.accept(ThingamajigsBlocks.BRICK_SIDEWALK.get().asItem());
            event.accept(ThingamajigsBlocks.BRICK_SIDEWALK_HB.get().asItem());
            event.accept(ThingamajigsBlocks.BASIC_BATHROOM_TILE.get().asItem());
            event.accept(ThingamajigsBlocks.STORE_FLOORING.get().asItem());
            event.accept(ThingamajigsBlocks.BOWLING_FLOORING.get().asItem());
            event.accept(ThingamajigsBlocks.OAK_LANE.get().asItem());
            event.accept(ThingamajigsBlocks.OLD_TEAL_WOOL.get().asItem());
            event.accept(ThingamajigsBlocks.LOVE_SEAT_WOOL.get().asItem());
            event.accept(ThingamajigsBlocks.CHECKBOARD_WOOL.get().asItem());
            // Pathways and Carpets
            event.accept(ThingamajigsBlocks.BROWN_PATHWAY.get().asItem());
            // Bookshelves
            event.accept(ThingamajigsBlocks.BLANK_BOOKSHELF.get().asItem());
            event.accept(ThingamajigsBlocks.ABANDONED_BOOKSHELF.get().asItem());
            event.accept(ThingamajigsBlocks.BONE_BOOKSHELF.get().asItem());
            event.accept(ThingamajigsBlocks.BRICK_BOOKSHELF.get().asItem());
            event.accept(ThingamajigsBlocks.GLOWSTONE_BOOKSHELF.get().asItem());
            event.accept(ThingamajigsBlocks.EXPERIENCE_BOOKSHELF.get().asItem());
            event.accept(ThingamajigsBlocks.HISTORIAN_BOOKSHELF.get().asItem());
            event.accept(ThingamajigsBlocks.EXPLORER_BOOKSHELF.get().asItem());
            event.accept(ThingamajigsBlocks.POTION_BOOKSHELF.get().asItem());
            event.accept(ThingamajigsBlocks.EXPENSIVE_BOOKSHELF.get().asItem());
            event.accept(ThingamajigsBlocks.SCARY_BOOKSHELF.get().asItem());
            event.accept(ThingamajigsBlocks.RED_TOME_BOOKSHELF.get().asItem());
            event.accept(ThingamajigsBlocks.YELLOW_TOME_BOOKSHELF.get().asItem());
            event.accept(ThingamajigsBlocks.GREEN_TOME_BOOKSHELF.get().asItem());
            event.accept(ThingamajigsBlocks.BLUE_TOME_BOOKSHELF.get().asItem());
            event.accept(ThingamajigsBlocks.POOPSHELF.get().asItem());
            event.accept(ThingamajigsBlocks.ANCIENT_BOOKSHELF.get().asItem());
            // Sports & Games
            // outdoor and workout
            event.accept(ThingamajigsBlocks.PUNCHING_BAG.get().asItem());
            // mini golf
            event.accept(ThingamajigsBlocks.MINIGOLF_GRASS_BLOCK.get().asItem());
            event.accept(ThingamajigsBlocks.MINIGOLF_HOLE.get().asItem());
            event.accept(ThingamajigsBlocks.MINIGOLF_FLAG.get().asItem());
            // bowling pins
            event.accept(ThingamajigsBlocks.BOWLING_PIN.get().asItem());
            event.accept(ThingamajigsBlocks.RED_BOWLING_PIN.get().asItem());
            event.accept(ThingamajigsBlocks.GOLD_BOWLING_PIN.get().asItem());
            event.accept(ThingamajigsBlocks.DIAMOND_BOWLING_PIN.get().asItem());
            // bowling balls
            event.accept(ThingamajigsBlocks.BROWN_BOWLING_BALL.get().asItem());
            event.accept(ThingamajigsBlocks.YELLOW_BOWLING_BALL.get().asItem());
            event.accept(ThingamajigsBlocks.LIME_BOWLING_BALL.get().asItem());
            event.accept(ThingamajigsBlocks.GREEN_BOWLING_BALL.get().asItem());
            event.accept(ThingamajigsBlocks.LIGHT_BLUE_BOWLING_BALL.get().asItem());
            event.accept(ThingamajigsBlocks.BLUE_BOWLING_BALL.get().asItem());
            event.accept(ThingamajigsBlocks.PURPLE_BOWLING_BALL.get().asItem());
            event.accept(ThingamajigsBlocks.PINK_BOWLING_BALL.get().asItem());
            // bowling technical
            event.accept(ThingamajigsBlocks.BOWLING_ALLEY_OILER.get().asItem());
            event.accept(ThingamajigsBlocks.BOWLING_BALL_RETRIEVER.get().asItem());
            event.accept(ThingamajigsBlocks.BOWLING_GAME_CONTROLLER.get().asItem());
            event.accept(ThingamajigsBlocks.PIN_SETTER.get().asItem());
            // arcade machines
            event.accept(ThingamajigsBlocks.ARCADE_MACHINE.get().asItem());
            event.accept(ThingamajigsBlocks.ARCADE_MACHINE_OPENABLE.get().asItem());
            event.accept(ThingamajigsBlocks.BASKETBALL_MACHINE.get().asItem());
            event.accept(ThingamajigsBlocks.PINBALL_MACHINE.get().asItem());
            event.accept(ThingamajigsBlocks.LIGHTUP_MACHINE.get().asItem());
            event.accept(ThingamajigsBlocks.FOOSBALL_TABLE.get().asItem());
            event.accept(ThingamajigsBlocks.CLAW_MACHINE.get().asItem());
            event.accept(ThingamajigsBlocks.GUMBALL_MACHINE.get().asItem());
            event.accept(ThingamajigsBlocks.HAMMER_MACHINE.get().asItem());
            event.accept(ThingamajigsBlocks.WACK_MACHINE.get().asItem());
            event.accept(ThingamajigsBlocks.AIR_HOCKEY_TABLE.get().asItem());
            // Water Park
            event.accept(ThingamajigsBlocks.WATER_SLIDE.get().asItem());
            event.accept(ThingamajigsBlocks.DIVING_BOARD.get().asItem());
            // Commercial Use
            event.accept(ThingamajigsBlocks.BLUEYBOX.get().asItem());
            event.accept(ThingamajigsBlocks.REFRESHMENT_MACHINE.get().asItem());
            event.accept(ThingamajigsBlocks.RED_SODA_MACHINE.get().asItem());
            event.accept(ThingamajigsBlocks.BLUE_SODA_MACHINE.get().asItem());
            event.accept(ThingamajigsBlocks.RED_VENDING_MACHINE.get().asItem());
            event.accept(ThingamajigsBlocks.BLUE_VENDING_MACHINE.get().asItem());
            event.accept(ThingamajigsBlocks.CEILING_FAN.get().asItem());
            event.accept(ThingamajigsBlocks.OPEN_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.OFFICE_PHONE.get().asItem());
            event.accept(ThingamajigsBlocks.FAX_MACHINE.get().asItem());
            event.accept(ThingamajigsBlocks.WATER_FOUNTAIN.get().asItem());
            event.accept(ThingamajigsBlocks.ICECREAM_MACHINE.get().asItem());
            event.accept(ThingamajigsBlocks.FRIER.get().asItem());
            event.accept(ThingamajigsBlocks.CASH_REGISTER.get().asItem());
            event.accept(ThingamajigsBlocks.STORE_NUMBER_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.AISLE_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.STORE_STAND.get().asItem());
            event.accept(ThingamajigsBlocks.STORE_SHELF.get().asItem());
            event.accept(ThingamajigsBlocks.STORE_FREEZER.get().asItem());
            event.accept(ThingamajigsBlocks.CONVENIENCE_SHELF.get().asItem());
            event.accept(ThingamajigsBlocks.SHOPPING_CART_MOVER.get().asItem());
            event.accept(ThingamajigsBlocks.SHOPPING_CART.get().asItem());
            event.accept(ThingamajigsBlocks.SHOPPING_BASKET_PILE.get().asItem());
            event.accept(ThingamajigsBlocks.SHOPPING_BASKET.get().asItem());
            event.accept(ThingamajigsBlocks.PAYPHONE.get().asItem());
            event.accept(ThingamajigsBlocks.PAYPHONE_SEETHROUGH.get().asItem());
            event.accept(ThingamajigsBlocks.GAS_PUMP.get().asItem());
            event.accept(ThingamajigsBlocks.THEATER_SEAT.get().asItem());
            event.accept(ThingamajigsBlocks.THEATER_SEAT_CONTINUOUS.get().asItem());
            event.accept(ThingamajigsBlocks.POPCORN_MACHINE.get().asItem());
            event.accept(ThingamajigsBlocks.COTTON_CANDY_MAKER.get().asItem());
            event.accept(ThingamajigsBlocks.HOTDOG_ROTATOR.get().asItem());
            event.accept(ThingamajigsBlocks.SLUSHY_MACHINE.get().asItem());
            event.accept(ThingamajigsBlocks.TICKET_TELLER_WINDOW.get().asItem());
            event.accept(ThingamajigsBlocks.VELVET_ROPE_FENCE.get().asItem());
            event.accept(ThingamajigsBlocks.CARNIVAL_AWNING.get().asItem());
            event.accept(ThingamajigsBlocks.PORTA_POTTY.get().asItem());
            event.accept(ThingamajigsBlocks.CATWALK_CENTER.get().asItem());
            event.accept(ThingamajigsBlocks.CATWALK.get().asItem());
            event.accept(ThingamajigsBlocks.TEACHING_BOARD.get().asItem());
            event.accept(ThingamajigsBlocks.SCHOOL_DESK.get().asItem());
            event.accept(ThingamajigsBlocks.LOCKER.get().asItem());
            event.accept(ThingamajigsBlocks.LIBRARY_STOOL.get().asItem());
            // Electronics
            event.accept(ThingamajigsBlocks.CLASSIC_TV.get().asItem());
            event.accept(ThingamajigsBlocks.TV.get().asItem());
            event.accept(ThingamajigsBlocks.BIG_TV.get().asItem());
            event.accept(ThingamajigsBlocks.ULTRA_HD_TV.get().asItem());
            event.accept(ThingamajigsBlocks.OLD_PC_MONITOR.get().asItem());
            event.accept(ThingamajigsBlocks.MODERN_PC_MONITOR.get().asItem());
            event.accept(ThingamajigsBlocks.DVD_PLAYER.get().asItem());
            event.accept(ThingamajigsBlocks.VHS_PLAYER.get().asItem());
            event.accept(ThingamajigsBlocks.OLD_PC.get().asItem());
            event.accept(ThingamajigsBlocks.BROKEN_COMPUTER.get().asItem());
            event.accept(ThingamajigsBlocks.OLD_FLAT_COMPUTER.get().asItem());
            event.accept(ThingamajigsBlocks.BLUEY_DESKTOP_COMPUTER.get().asItem());
            event.accept(ThingamajigsBlocks.BLUEYTOSH_LAPTOP_OLD.get().asItem());
            event.accept(ThingamajigsBlocks.BLUEYDOWS_LAPTOP.get().asItem());
            event.accept(ThingamajigsBlocks.BLUEYTOSH_LAPTOP.get().asItem());
            event.accept(ThingamajigsBlocks.BLUEYTOSH_STUDIO.get().asItem());
            event.accept(ThingamajigsBlocks.BLUEYCUBE_CONSOLE.get().asItem());
            event.accept(ThingamajigsBlocks.BLUEMAN_CONSOLE.get().asItem());
            event.accept(ThingamajigsBlocks.BLUEYSNAP_BASE.get().asItem());
            event.accept(ThingamajigsBlocks.BLUEYSNAP_CONSOLE.get().asItem());
            event.accept(ThingamajigsBlocks.PRINTER.get().asItem());
            event.accept(ThingamajigsBlocks.PROJECTOR.get().asItem());
            event.accept(ThingamajigsBlocks.WHITE_TELEPHONE.get().asItem());
            event.accept(ThingamajigsBlocks.BLACK_TELEPHONE.get().asItem());
            event.accept(ThingamajigsBlocks.GENERAL_DIGITAL_PHONE.get().asItem());
            event.accept(ThingamajigsBlocks.FEATURED_CORDLESS_PHONE.get().asItem());
            event.accept(ThingamajigsBlocks.MOBILE_PHONE.get().asItem());
            event.accept(ThingamajigsBlocks.SMARTPHONE.get().asItem());
            event.accept(ThingamajigsBlocks.GRAPHICS_CARD.get().asItem());
            event.accept(ThingamajigsBlocks.HARD_DRIVE.get().asItem());
            event.accept(ThingamajigsBlocks.INTERNET_MODEM.get().asItem());
            event.accept(ThingamajigsBlocks.INTERNET_ROUTER.get().asItem());
            event.accept(ThingamajigsBlocks.NEWER_INTERNET_ROUTER.get().asItem());
            event.accept(ThingamajigsBlocks.WIFI_ROUTER.get().asItem());
            event.accept(ThingamajigsBlocks.AUDIO_CONTROLLER.get().asItem());
            // Utilities
            event.accept(ThingamajigsBlocks.AC_DUCT.get().asItem());
            event.accept(ThingamajigsBlocks.AC_DUCT_CORNER.get().asItem());
            event.accept(ThingamajigsBlocks.AC_DUCT_ALLWAY.get().asItem());
            event.accept(ThingamajigsBlocks.AIR_CONDITIONER.get().asItem());
            event.accept(ThingamajigsBlocks.AC_THERMOSTAT.get().asItem());
            event.accept(ThingamajigsBlocks.GAS_HEATER.get().asItem());
            event.accept(ThingamajigsBlocks.WATER_SOFTENER.get().asItem());
            event.accept(ThingamajigsBlocks.SALT_TANK.get().asItem());
            event.accept(ThingamajigsBlocks.SOLAR_PANEL.get().asItem());
            event.accept(ThingamajigsBlocks.SERVER_RACK.get().asItem());
            // power outlets and switches or buttons
            event.accept(ThingamajigsBlocks.HOME_BREAKER.get().asItem());
            event.accept(ThingamajigsBlocks.UNGROUNDED_US_OUTLET.get().asItem());
            event.accept(ThingamajigsBlocks.US_OUTLET.get().asItem());
            event.accept(ThingamajigsBlocks.T_US_OUTLET.get().asItem());
            event.accept(ThingamajigsBlocks.INTERNET_JACK_OUTLET.get().asItem());
            event.accept(ThingamajigsBlocks.USB_OUTLET.get().asItem());
            event.accept(ThingamajigsBlocks.UK_OUTLET.get().asItem());
            event.accept(ThingamajigsBlocks.GERMAN_OUTLET.get().asItem());
            event.accept(ThingamajigsBlocks.AUSTRALIAN_OUTLET.get().asItem());
            event.accept(ThingamajigsBlocks.BUTTON_SWITCH.get().asItem());
            event.accept(ThingamajigsBlocks.ROCKER_SWITCH.get().asItem());
            event.accept(ThingamajigsBlocks.DOOR_BELL.get().asItem());
            // fire prevention & protection
            event.accept(ThingamajigsBlocks.FIRE_EXTINGUISHER.get().asItem());
            event.accept(ThingamajigsBlocks.FIRE_DETECTOR.get().asItem());
            event.accept(ThingamajigsBlocks.BEEP_FIRE_ALARM.get().asItem());
            event.accept(ThingamajigsBlocks.HORN_FIRE_ALARM.get().asItem());
            event.accept(ThingamajigsBlocks.LOUD_FIRE_ALARM.get().asItem());
            event.accept(ThingamajigsBlocks.FIRE_ESCAPE_LADDER.get().asItem());
            // security
            event.accept(ThingamajigsBlocks.FILM_SECURITY_CAMERA.get().asItem());
            event.accept(ThingamajigsBlocks.ROBOT_SECURITY_CAMERA.get().asItem());
            event.accept(ThingamajigsBlocks.BOX_SECURITY_CAMERA.get().asItem());
            event.accept(ThingamajigsBlocks.SECURE_SECURITY_CAMERA.get().asItem());
            event.accept(ThingamajigsBlocks.DOME_SECURITY_CAMERA.get().asItem());
            event.accept(ThingamajigsBlocks.CHAINLINK_FENCE.get().asItem());
            // water management
            event.accept(ThingamajigsBlocks.CULVERT.get().asItem());
            event.accept(ThingamajigsBlocks.DIRT_CULVERT.get().asItem());
            event.accept(ThingamajigsBlocks.SAND_CULVERT.get().asItem());
            event.accept(ThingamajigsBlocks.SANDSTONE_CULVERT.get().asItem());
            event.accept(ThingamajigsBlocks.STONE_CULVERT.get().asItem());
            event.accept(ThingamajigsBlocks.TERRACOTTA_CULVERT.get().asItem());
            event.accept(ThingamajigsBlocks.BRICK_CULVERT.get().asItem());
            event.accept(ThingamajigsBlocks.STONE_BRICK_CULVERT.get().asItem());
            // hazard signs
            event.accept(ThingamajigsBlocks.BIO_HAZARD_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.BLAST_HAZARD_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.CRYO_HAZARD_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.HARDHAT_HAZARD_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.DEATH_HAZARD_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.FIRE_HAZARD_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.GENERAL_HAZARD_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.NOENTRY_HAZARD_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.FALLING_HAZARD_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.POISON_HAZARD_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.RADIATION_HAZARD_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.SHOCK_HAZARD_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.WORKERS_HAZARD_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.RADIOWAVES_HAZARD_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.OXYGEN_HAZARD_SIGN.get().asItem());
            // Home Appliances & Other
            // misc. home things
            event.accept(ThingamajigsBlocks.BUTTER_CHURNER.get().asItem());
            event.accept(ThingamajigsBlocks.GARDEN_GNOME.get().asItem());
            event.accept(ThingamajigsBlocks.GARDEN_HOSE.get().asItem());
            event.accept(ThingamajigsBlocks.DOG_HOUSE.get().asItem());
            event.accept(ThingamajigsBlocks.HOTTUB.get().asItem());
            event.accept(ThingamajigsBlocks.MAILBOX.get().asItem());
            event.accept(ThingamajigsBlocks.GREY_MAILBOX.get().asItem());
            event.accept(ThingamajigsBlocks.BLACK_MAILBOX.get().asItem());
            event.accept(ThingamajigsBlocks.LAWN_MOWER.get().asItem());
            event.accept(ThingamajigsBlocks.GENERATOR.get().asItem());
            event.accept(ThingamajigsBlocks.CEILING_FAN.get().asItem());
            event.accept(ThingamajigsBlocks.WHITE_FAN.get().asItem());
            event.accept(ThingamajigsBlocks.GRAY_FAN.get().asItem());
            event.accept(ThingamajigsBlocks.BLACK_FAN.get().asItem());
            event.accept(ThingamajigsBlocks.LAVA_LAMP.get().asItem());
            event.accept(ThingamajigsBlocks.STANDING_VACUUM.get().asItem());
            event.accept(ThingamajigsBlocks.SHOP_VACUUM.get().asItem());
            event.accept(ThingamajigsBlocks.STEAM_CLEANER.get().asItem());
            event.accept(ThingamajigsBlocks.CRIB.get().asItem());
            event.accept(ThingamajigsBlocks.BABY_CARRIAGE.get().asItem());
            event.accept(ThingamajigsBlocks.GAS_CAN.get().asItem());
            event.accept(ThingamajigsBlocks.SATELLITE_DISH.get().asItem());
            event.accept(ThingamajigsBlocks.ANTENNA.get().asItem());
            event.accept(ThingamajigsBlocks.OLD_WOODEN_PHONE.get().asItem());
            event.accept(ThingamajigsBlocks.WHEELBARROW.get().asItem());
            event.accept(ThingamajigsBlocks.RECYCLE_BIN.get().asItem());
            event.accept(ThingamajigsBlocks.THERMOMETER.get().asItem());
            event.accept(ThingamajigsBlocks.AIR_PURIFIER.get().asItem());
            event.accept(ThingamajigsBlocks.SPACE_HEATER.get().asItem());
            event.accept(ThingamajigsBlocks.VHS_COLLECTION.get().asItem());
            event.accept(ThingamajigsBlocks.DVD_COLLECTION.get().asItem());
            event.accept(ThingamajigsBlocks.TALL_LAMP.get().asItem());
            // eating utensils
            event.accept(ThingamajigsBlocks.PLATE.get().asItem());
            event.accept(ThingamajigsBlocks.CUP.get().asItem());
            event.accept(ThingamajigsBlocks.COOKIE_JAR.get().asItem());
            event.accept(ThingamajigsBlocks.EATING_UTENCILS.get().asItem());
            // furniture
            event.accept(ThingamajigsBlocks.STONE_TABLE.get().asItem());
            event.accept(ThingamajigsBlocks.QUARTZ_TABLE.get().asItem());
            event.accept(ThingamajigsBlocks.GOLD_TABLE.get().asItem());
            event.accept(ThingamajigsBlocks.NETHER_BRICK_TABLE.get().asItem());
            event.accept(ThingamajigsBlocks.PRISMARINE_TABLE.get().asItem());
            event.accept(ThingamajigsBlocks.PURPUR_TABLE.get().asItem());
            event.accept(ThingamajigsBlocks.SCULK_TABLE.get().asItem());
            event.accept(ThingamajigsBlocks.LOVE_SEAT.get().asItem());
            event.accept(ThingamajigsBlocks.LOVE_COUCH.get().asItem());
            event.accept(ThingamajigsBlocks.MYSTERIOUS_ONE_COUCH.get().asItem());
            // appliances
            event.accept(ThingamajigsBlocks.DRYER.get().asItem());
            event.accept(ThingamajigsBlocks.WASHER.get().asItem());
            event.accept(ThingamajigsBlocks.DISHWASHER_WALL.get().asItem());
            event.accept(ThingamajigsBlocks.PORTABLE_DISH_WASHER.get().asItem());
            event.accept(ThingamajigsBlocks.HUMIDIFIER.get().asItem());
            event.accept(ThingamajigsBlocks.DEHUMIDIFIER.get().asItem());
            event.accept(ThingamajigsBlocks.TOOL_STATION.get().asItem());
            event.accept(ThingamajigsBlocks.FREEZER.get().asItem());
            event.accept(ThingamajigsBlocks.FRIDGE.get().asItem());
            event.accept(ThingamajigsBlocks.MINI_FRIDGE.get().asItem());
            event.accept(ThingamajigsBlocks.STOVE_HOOD.get().asItem());
            event.accept(ThingamajigsBlocks.STOVE.get().asItem());
            event.accept(ThingamajigsBlocks.SEWING_MACHINE.get().asItem());
            event.accept(ThingamajigsBlocks.IRONING_TABLE.get().asItem());
            // Kitchen Stuff
            event.accept(ThingamajigsBlocks.KITCHEN_SINK.get().asItem());
            event.accept(ThingamajigsBlocks.MICROWAVE.get().asItem());
            event.accept(ThingamajigsBlocks.TOASTER_OVEN.get().asItem());
            event.accept(ThingamajigsBlocks.TOASTER.get().asItem());
            event.accept(ThingamajigsBlocks.OVEN.get().asItem());
            event.accept(ThingamajigsBlocks.PAPER_TOWEL.get().asItem());
            event.accept(ThingamajigsBlocks.FOOD_PROCESSOR.get().asItem());
            event.accept(ThingamajigsBlocks.BLENDER.get().asItem());
            event.accept(ThingamajigsBlocks.STAND_MIXER.get().asItem());
            event.accept(ThingamajigsBlocks.JUICER.get().asItem());
            event.accept(ThingamajigsBlocks.RICE_COOKER.get().asItem());
            event.accept(ThingamajigsBlocks.SLOW_COOKER.get().asItem());
            event.accept(ThingamajigsBlocks.INSTANT_POT.get().asItem());
            event.accept(ThingamajigsBlocks.BREAD_MACHINE.get().asItem());
            event.accept(ThingamajigsBlocks.WAFFLE_IRON.get().asItem());
            event.accept(ThingamajigsBlocks.PANINI_MAKER.get().asItem());
            event.accept(ThingamajigsBlocks.ICE_CREAM_MAKER.get().asItem());
            event.accept(ThingamajigsBlocks.YOGURT_MAKER.get().asItem());
            event.accept(ThingamajigsBlocks.COFFEE_GRINDER.get().asItem());
            event.accept(ThingamajigsBlocks.FRENCH_PRESS.get().asItem());
            event.accept(ThingamajigsBlocks.COFFEE_MACHINE.get().asItem());
            event.accept(ThingamajigsBlocks.FOOD_DEHYDRATOR.get().asItem());
            event.accept(ThingamajigsBlocks.SMOKER_GRILL.get().asItem());
            // Bathroom Stuff
            event.accept(ThingamajigsBlocks.SMALL_SINK.get().asItem());
            event.accept(ThingamajigsBlocks.FANCY_SINK.get().asItem());
            event.accept(ThingamajigsBlocks.SHOWER_HANDLES.get().asItem());
            event.accept(ThingamajigsBlocks.SHOWER_HEAD.get().asItem());
            event.accept(ThingamajigsBlocks.BATHTUB_NOZZLE.get().asItem());
            event.accept(ThingamajigsBlocks.TOILET.get().asItem());
            event.accept(ThingamajigsBlocks.TOILET_PAPER.get().asItem());
            // Toys & Kids Stuff
            event.accept(ThingamajigsBlocks.TOY_BOX.get().asItem());
            event.accept(ThingamajigsBlocks.WOOD_DUCK.get().asItem());
            event.accept(ThingamajigsBlocks.WOOD_CAR.get().asItem());
            event.accept(ThingamajigsBlocks.MRPUPPY.get().asItem());
            event.accept(ThingamajigsBlocks.REINDEER_PLUSHY.get().asItem());
            event.accept(ThingamajigsBlocks.SNOWMAN_PLUSHY.get().asItem());
            event.accept(ThingamajigsBlocks.STEVE_PLUSHY.get().asItem());
            event.accept(ThingamajigsBlocks.ZOMBIE_PLUSHIE.get().asItem());
            event.accept(ThingamajigsBlocks.CREEPER_PLUSHY.get().asItem());
            // Hospital and Health
            event.accept(ThingamajigsBlocks.HOSPITAL_COVER.get().asItem());
            event.accept(ThingamajigsBlocks.HOSPITAL_BED.get().asItem());
            event.accept(ThingamajigsBlocks.HOSPITAL_COMPUTER.get().asItem());
            event.accept(ThingamajigsBlocks.HEART_MONITOR.get().asItem());
            event.accept(ThingamajigsBlocks.IV.get().asItem());
            event.accept(ThingamajigsBlocks.OPERATION_TABLE.get().asItem());
            event.accept(ThingamajigsBlocks.OPERATION_TOOLS.get().asItem());
            // Science
            event.accept(ThingamajigsBlocks.MICROSCOPE.get().asItem());
            event.accept(ThingamajigsBlocks.CHEMICAL_TUBE.get().asItem());
            event.accept(ThingamajigsBlocks.BEAKER.get().asItem());
            event.accept(ThingamajigsBlocks.FLASK.get().asItem());
            // Packed & Bulk Items
            event.accept(ThingamajigsBlocks.WATER_BOTTLE_PACK.get().asItem());
            event.accept(ThingamajigsBlocks.BULK_PRODUCT.get().asItem());
            // Graveyards & Death
            event.accept(ThingamajigsBlocks.COFFIN.get().asItem());
            event.accept(ThingamajigsBlocks.CROSS_GRAVESTONE.get().asItem());
            event.accept(ThingamajigsBlocks.STANDARD_GRAVESTONE.get().asItem());
            event.accept(ThingamajigsBlocks.PLACARD_GRAVESTONE.get().asItem());
            // Seasonal (Christmas)
            event.accept(ThingamajigsBlocks.CHRISTMAS_FIREPLACE.get().asItem());
            event.accept(ThingamajigsBlocks.SLEIGH.get().asItem());
            event.accept(ThingamajigsBlocks.GINGERBREAD_HOUSE.get().asItem());
            event.accept(ThingamajigsBlocks.NUTCRACKER.get().asItem());
            event.accept(ThingamajigsBlocks.NORTH_POLE.get().asItem());
            event.accept(ThingamajigsBlocks.SANTA_STATUE.get().asItem());
            event.accept(ThingamajigsBlocks.SANTA_INFLATABLE.get().asItem());
            event.accept(ThingamajigsBlocks.SNOWMAN.get().asItem());
            event.accept(ThingamajigsBlocks.SNOWMAN_BLUEMAN_STATUE.get().asItem());
            event.accept(ThingamajigsBlocks.CHRISTMAS_WREATH.get().asItem());
            event.accept(ThingamajigsBlocks.SMALL_CHRISTMAS_TREE.get().asItem());
            event.accept(ThingamajigsBlocks.CHRISTMAS_TREE.get().asItem());
            event.accept(ThingamajigsBlocks.LIGHTED_CHRISTMAS_TREE.get().asItem());
            event.accept(ThingamajigsBlocks.PRESENT_PILE.get().asItem());
            event.accept(ThingamajigsBlocks.LIGHTED_DEER.get().asItem());
            event.accept(ThingamajigsBlocks.CHRISTMAS_LIGHTS.get().asItem());
            event.accept(ThingamajigsBlocks.CHRISTMAS_LIGHTS_ALT.get().asItem());
            event.accept(ThingamajigsBlocks.AMBER_STRING_LIGHTS.get().asItem());
            event.accept(ThingamajigsBlocks.BLUE_STRING_LIGHTS.get().asItem());
            // Food Related
            event.accept(ThingamajigsBlocks.PIZZA_BOX.get().asItem());
            // Misc. Junk
            event.accept(ThingamajigsBlocks.BIOHAZARD_BIN.get().asItem());
            event.accept(ThingamajigsBlocks.RADIOACTIVE_BARREL.get().asItem());
            event.accept(ThingamajigsBlocks.FIREWORKS_DISPLAY.get().asItem());
            event.accept(ThingamajigsBlocks.BLUEMAN_STATUE.get().asItem());
            event.accept(ThingamajigsBlocks.DUCK_STATUE.get().asItem());
            event.accept(ThingamajigsBlocks.REINDEER_WALL_HEAD.get().asItem());
            event.accept(ThingamajigsBlocks.WARDEN_TROPHY.get().asItem());
            event.accept(ThingamajigsBlocks.HEAD_CANDLE.get().asItem());
            event.accept(ThingamajigsBlocks.POOP.get().asItem());
            event.accept(ThingamajigsBlocks.FULL_POOP_BLOCK.get().asItem());
        }
        // all road signs tabs (makes it easier to find what you want)
        if(event.getTab() == ThingamajigsCreativeTab.ROAD_SIGNS_TAB.get()){
            // Road Name Signs
            event.accept(ThingamajigsItems.GREEN_ROADWAY_SIGN_ITEM.get().asItem());
            event.accept(ThingamajigsItems.RED_ROADWAY_SIGN_ITEM.get().asItem());
            event.accept(ThingamajigsItems.BLUE_ROADWAY_SIGN_ITEM.get().asItem());
            event.accept(ThingamajigsItems.BROWN_ROADWAY_SIGN_ITEM.get().asItem());
            // hanging road name signs
            event.accept(ThingamajigsItems.GREEN_HANGING_ROADWAY_SIGN_ITEM.get().asItem());
            // Road Signs
            // railroad crossing related signs
            event.accept(ThingamajigsBlocks.RR_AHEAD_OLD.get().asItem());
            event.accept(ThingamajigsBlocks.RR_AHEAD.get().asItem());
            event.accept(ThingamajigsBlocks.CATEYE_CROSSBUCK.get().asItem());
            event.accept(ThingamajigsBlocks.INVERTED_CATEYE_CROSSBUCK.get().asItem());
            event.accept(ThingamajigsBlocks.CROSSBUCK.get().asItem());
            // these signs were added because they were the most tastefully unique... (language correct MADE me add the 'tasteful' part)
            // international rr-related signs
            event.accept(ThingamajigsBlocks.CZECH_CROSSBUCK.get().asItem());
            event.accept(ThingamajigsBlocks.FINNISH_CROSSBUCK.get().asItem());
            event.accept(ThingamajigsBlocks.GERMAN_CROSSBUCK.get().asItem());
            event.accept(ThingamajigsBlocks.AUSTRALIA_CROSSBUCK.get().asItem());
            event.accept(ThingamajigsBlocks.CANADIAN_CROSSBUCK.get().asItem());
            event.accept(ThingamajigsBlocks.JAPAN_CROSSBUCK.get().asItem());
            // stop signs
            event.accept(ThingamajigsBlocks.STOP_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.YELLOW_STOP_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.GREEN_STOP_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.BLUE_STOP_SIGN.get().asItem());
            // speed limit ahead
            event.accept(ThingamajigsBlocks.SPEED_LIMIT_AHEAD.get().asItem());
            event.accept(ThingamajigsBlocks.SCHOOL_SPEED_LIMIT_AHEAD.get().asItem());
            // speed limit
            event.accept(ThingamajigsBlocks.SPEED_LIMIT_10.get().asItem());
            event.accept(ThingamajigsBlocks.SPEED_LIMIT_20.get().asItem());
            event.accept(ThingamajigsBlocks.SPEED_LIMIT_30.get().asItem());
            event.accept(ThingamajigsBlocks.SPEED_LIMIT_40.get().asItem());
            event.accept(ThingamajigsBlocks.SPEED_LIMIT_50.get().asItem());
            // other signs (sorry too lazy)
            event.accept(ThingamajigsBlocks.NO_PASSING_ZONE_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.YIELD_AHEAD.get().asItem());
            event.accept(ThingamajigsBlocks.YIELD_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.SLOW_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.CROSSING_AHEAD_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.CROSSING_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.EXIT_LEFT.get().asItem());
            event.accept(ThingamajigsBlocks.EXIT_RIGHT.get().asItem());
            event.accept(ThingamajigsBlocks.BUMP.get().asItem());
            event.accept(ThingamajigsBlocks.DIP.get().asItem());
            event.accept(ThingamajigsBlocks.DEAD_END.get().asItem());
            event.accept(ThingamajigsBlocks.EMERGENCY_SCENE_AHEAD.get().asItem());
            event.accept(ThingamajigsBlocks.BOMB_THREAT_AHEAD.get().asItem());
            event.accept(ThingamajigsBlocks.TNT_THREAT.get().asItem());
            event.accept(ThingamajigsBlocks.PILLAGER_SCENE.get().asItem());
            event.accept(ThingamajigsBlocks.CRIME_SCENE_AHEAD.get().asItem());
            event.accept(ThingamajigsBlocks.NO_OUTLET.get().asItem());
            event.accept(ThingamajigsBlocks.NO_PARKING.get().asItem());
            event.accept(ThingamajigsBlocks.PARKING_PERMITTED_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.SPEEDING_WORKERS_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.SPEED_HUMP_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.TOLL_AHEAD.get().asItem());
            event.accept(ThingamajigsBlocks.TWAS_DIP_SIGN.get().asItem());
            //
            event.accept(ThingamajigsBlocks.ANGLED_BIG_MERGE_LEFT.get().asItem());
            event.accept(ThingamajigsBlocks.ANGLED_BIG_MERGE_RIGHT.get().asItem());
            event.accept(ThingamajigsBlocks.BIG_LEFT_MERGES.get().asItem());
            event.accept(ThingamajigsBlocks.BIG_RIGHT_MERGES.get().asItem());
            event.accept(ThingamajigsBlocks.BIKE.get().asItem());
            event.accept(ThingamajigsBlocks.CAR.get().asItem());
            event.accept(ThingamajigsBlocks.TRUCK.get().asItem());
            event.accept(ThingamajigsBlocks.FIRE_TRUCK.get().asItem());
            event.accept(ThingamajigsBlocks.BUS_STOP_AHEAD.get().asItem());
            event.accept(ThingamajigsBlocks.CROSSING_SIGN_F.get().asItem());
            event.accept(ThingamajigsBlocks.CROSSING_AHEAD_F.get().asItem());
            event.accept(ThingamajigsBlocks.DEER.get().asItem());
            event.accept(ThingamajigsBlocks.EQUESTRIAN.get().asItem());
            event.accept(ThingamajigsBlocks.TRAM_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.MINECART_CROSSING.get().asItem());
            event.accept(ThingamajigsBlocks.GOLF_CART_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.FARMER_ORIGINAL.get().asItem());
            event.accept(ThingamajigsBlocks.TRACTOR.get().asItem());
            event.accept(ThingamajigsBlocks.TRUCK_SLOPE.get().asItem());
            event.accept(ThingamajigsBlocks.DIAGONAL_TRACK_LEFTORRIGHT.get().asItem());
            event.accept(ThingamajigsBlocks.DIAGONAL_TRACK_RIGHTORLEFT.get().asItem());
            event.accept(ThingamajigsBlocks.DIVIDER_START.get().asItem());
            event.accept(ThingamajigsBlocks.DIVIDER_ENDS.get().asItem());
            event.accept(ThingamajigsBlocks.FALLING_ROCKS.get().asItem());
            event.accept(ThingamajigsBlocks.FORWARD_OR_LEFT.get().asItem());
            event.accept(ThingamajigsBlocks.FORWARD_OR_RIGHT.get().asItem());
            event.accept(ThingamajigsBlocks.HIGH_TRACK.get().asItem());
            event.accept(ThingamajigsBlocks.HORIZONTAL_TRACK.get().asItem());
            event.accept(ThingamajigsBlocks.LANES.get().asItem());
            event.accept(ThingamajigsBlocks.LEFT_LANE_ENDS.get().asItem());
            event.accept(ThingamajigsBlocks.RIGHT_LANE_ENDS.get().asItem());
            event.accept(ThingamajigsBlocks.LEFT_MERGES.get().asItem());
            event.accept(ThingamajigsBlocks.RIGHT_MERGES.get().asItem());
            event.accept(ThingamajigsBlocks.SINGLE_TO_MULTI.get().asItem());
            event.accept(ThingamajigsBlocks.MULTI_TO_SINGLE.get().asItem());
            event.accept(ThingamajigsBlocks.PAVEMENT_ENDS_OLD.get().asItem());
            event.accept(ThingamajigsBlocks.NARROW_BRIDGE.get().asItem());
            event.accept(ThingamajigsBlocks.PED_DISABLED.get().asItem());
            event.accept(ThingamajigsBlocks.PLANT_ROAD_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.PLAYGROUND.get().asItem());
            event.accept(ThingamajigsBlocks.PLUS.get().asItem());
            event.accept(ThingamajigsBlocks.ROAD_TWISTY_LEFT.get().asItem());
            event.accept(ThingamajigsBlocks.ROAD_TWISTY_RIGHT.get().asItem());
            event.accept(ThingamajigsBlocks.ROUNDABOUT_OLD.get().asItem());
            event.accept(ThingamajigsBlocks.ROUNDABOUT.get().asItem());
            event.accept(ThingamajigsBlocks.SEATBELT.get().asItem());
            event.accept(ThingamajigsBlocks.THINK_BEFORE_YOU_THROW.get().asItem());
            event.accept(ThingamajigsBlocks.BIG_ARROW_ROAD_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.ARROW_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.SHOULDER_DROP_RIGHT.get().asItem());
            event.accept(ThingamajigsBlocks.SLIPPERY_WHEN_WET.get().asItem());
            event.accept(ThingamajigsBlocks.SMALL_TO_BIG_LEFT.get().asItem());
            event.accept(ThingamajigsBlocks.SMALL_TO_BIG_RIGHT.get().asItem());
            event.accept(ThingamajigsBlocks.SMILEY_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.STOP_AHEAD.get().asItem());
            event.accept(ThingamajigsBlocks.TRACK_LEFT_SIDE.get().asItem());
            event.accept(ThingamajigsBlocks.TRACK_RIGHT_SIDE.get().asItem());
            event.accept(ThingamajigsBlocks.TRAFFIC_LIGHT.get().asItem());
            event.accept(ThingamajigsBlocks.VERTICAL_TRACK.get().asItem());
            event.accept(ThingamajigsBlocks.EFE_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.TFF_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.CHEVRON.get().asItem());
            event.accept(ThingamajigsBlocks.DISABLED_PARKING_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.DISABLED_PARKING_SIGN_ALT.get().asItem());
            event.accept(ThingamajigsBlocks.DISABLED_PARKING_SIGN_ALT_TWO.get().asItem());
            event.accept(ThingamajigsBlocks.RESERVED_DISABLED_PARKING.get().asItem());
            event.accept(ThingamajigsBlocks.RESERVED_DISABLED_PARKING_ALT.get().asItem());
            event.accept(ThingamajigsBlocks.STOP_HERE_ON_RED.get().asItem());
            event.accept(ThingamajigsBlocks.LOW_POWER_LINES.get().asItem());
            event.accept(ThingamajigsBlocks.HAWK_SIGNAL_AHEAD.get().asItem());
            event.accept(ThingamajigsBlocks.NO_PEDS.get().asItem());
            event.accept(ThingamajigsBlocks.STAY_LEFT.get().asItem());
            event.accept(ThingamajigsBlocks.STAY_RIGHT.get().asItem());
            event.accept(ThingamajigsBlocks.INTERSTATE_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.RECTANGLE_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.BIG_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.BLUEYPASS_ONLY.get().asItem());
            event.accept(ThingamajigsBlocks.DIVIDER.get().asItem());
            event.accept(ThingamajigsBlocks.ONE_WAY_LEFT.get().asItem());
            event.accept(ThingamajigsBlocks.ONE_WAY_RIGHT.get().asItem());
            event.accept(ThingamajigsBlocks.DIVIDED_WAY.get().asItem());
            event.accept(ThingamajigsBlocks.GRID_SIGN.get().asItem());
            event.accept(ThingamajigsBlocks.WRONG_WAY.get().asItem());
            // half signs
            event.accept(ThingamajigsBlocks.AHEAD_HALF.get().asItem());
            event.accept(ThingamajigsBlocks.AHEAD_YELLOW_HALF.get().asItem());
            event.accept(ThingamajigsBlocks.ALL_DAY_HALF.get().asItem());
            event.accept(ThingamajigsBlocks.ALL_WAY_HALF.get().asItem());
            event.accept(ThingamajigsBlocks.FOUR_WAY_HALF.get().asItem());
            event.accept(ThingamajigsBlocks.THREE_WAY_HALF.get().asItem());
            event.accept(ThingamajigsBlocks.TWO_WAY_HALF.get().asItem());
            event.accept(ThingamajigsBlocks.ONE_WAY_HALF.get().asItem());
            event.accept(ThingamajigsBlocks.ENTER_AHEAD_HALF.get().asItem());
            event.accept(ThingamajigsBlocks.EXEMPT_HALF.get().asItem());
            event.accept(ThingamajigsBlocks.EXEMPT_YELLOW_HALF.get().asItem());
            event.accept(ThingamajigsBlocks.MON_FRI_HALF.get().asItem());
            event.accept(ThingamajigsBlocks.SAT_SUN_HALF.get().asItem());
            event.accept(ThingamajigsBlocks.ONLY_THRU_LEFT.get().asItem());
            event.accept(ThingamajigsBlocks.ONLY_THRU_RIGHT.get().asItem());
            event.accept(ThingamajigsBlocks.SCHOOL_HALF.get().asItem());
            event.accept(ThingamajigsBlocks.SCHOOL_Y_HALF.get().asItem());
            event.accept(ThingamajigsBlocks.TO_ROUTE_HALF.get().asItem());
            event.accept(ThingamajigsBlocks.TO_TOLL_HALF.get().asItem());
            event.accept(ThingamajigsBlocks.WHEN_FLASHING_HALF.get().asItem());
            // construction signs
            event.accept(ThingamajigsBlocks.ROAD_WORK_AHEAD.get().asItem());
            event.accept(ThingamajigsBlocks.BRIDGE_OUT_AHEAD.get().asItem());
            event.accept(ThingamajigsBlocks.CONST_ROAD_CLOSED_AHEAD.get().asItem());
            event.accept(ThingamajigsBlocks.BLASTING_ZONE_AHEAD.get().asItem());
            event.accept(ThingamajigsBlocks.FLAGGER_AHEAD.get().asItem());
            event.accept(ThingamajigsBlocks.WORKERS_PRESENT.get().asItem());
            event.accept(ThingamajigsBlocks.BPTS.get().asItem());
            event.accept(ThingamajigsBlocks.UTIL_WORK_AHEAD.get().asItem());
            event.accept(ThingamajigsBlocks.DETOUR_ARROW_LEFT.get().asItem());
            event.accept(ThingamajigsBlocks.DETOUR_ARROW_RIGHT.get().asItem());
            // uk signs
            event.accept(ThingamajigsBlocks.UK_STOP_ON_RED.get().asItem());
            event.accept(ThingamajigsBlocks.ASCENT.get().asItem());
            event.accept(ThingamajigsBlocks.DESCENT.get().asItem());
            event.accept(ThingamajigsBlocks.BENDS.get().asItem());
            event.accept(ThingamajigsBlocks.ROAD_CROSSES.get().asItem());
            event.accept(ThingamajigsBlocks.MERGES.get().asItem());
            event.accept(ThingamajigsBlocks.CHILDREN.get().asItem());
            event.accept(ThingamajigsBlocks.CROSSING_NO_GATES.get().asItem());
            event.accept(ThingamajigsBlocks.DANGER.get().asItem());
            event.accept(ThingamajigsBlocks.DUAL_ENDS.get().asItem());
            event.accept(ThingamajigsBlocks.ELDER.get().asItem());
            event.accept(ThingamajigsBlocks.GATED_CROSSING.get().asItem());
            event.accept(ThingamajigsBlocks.GIVE_WAY.get().asItem());
            event.accept(ThingamajigsBlocks.HUMP_BRIDGE.get().asItem());
            event.accept(ThingamajigsBlocks.MINECARTS.get().asItem());
            event.accept(ThingamajigsBlocks.NARROW_BOTH.get().asItem());
            event.accept(ThingamajigsBlocks.NARROW_SIDES.get().asItem());
            event.accept(ThingamajigsBlocks.OPEN_BRIDGE.get().asItem());
            event.accept(ThingamajigsBlocks.PEDS_AHEAD.get().asItem());
            event.accept(ThingamajigsBlocks.RIVERBANK.get().asItem());
            event.accept(ThingamajigsBlocks.ROAD_WORK.get().asItem());
            event.accept(ThingamajigsBlocks.ROUNDABOUT_UK.get().asItem());
            event.accept(ThingamajigsBlocks.SIDE_WIND.get().asItem());
            event.accept(ThingamajigsBlocks.SIGNAL_AHEAD.get().asItem());
            event.accept(ThingamajigsBlocks.SLIPPERY.get().asItem());
            event.accept(ThingamajigsBlocks.SOFT_VERGE.get().asItem());
            event.accept(ThingamajigsBlocks.STOP_OR_GIVEWAY_AHEAD.get().asItem());
            event.accept(ThingamajigsBlocks.TRY.get().asItem());
            event.accept(ThingamajigsBlocks.TUNNEL.get().asItem());
            event.accept(ThingamajigsBlocks.TWO_WAY.get().asItem());
            event.accept(ThingamajigsBlocks.UNEVEN.get().asItem());
            event.accept(ThingamajigsBlocks.WATER_COURSE.get().asItem());
            event.accept(ThingamajigsBlocks.ZEBRA.get().asItem());
            event.accept(ThingamajigsBlocks.ALL_PROHIBITED.get().asItem());
            event.accept(ThingamajigsBlocks.HORSE_PROHIBITED.get().asItem());
            event.accept(ThingamajigsBlocks.MOTOR_VEHICLES_PROHIBITED.get().asItem());
            event.accept(ThingamajigsBlocks.MOTORCYCLES_PROHIBITED.get().asItem());
            event.accept(ThingamajigsBlocks.NO_OVERTAKING.get().asItem());
            event.accept(ThingamajigsBlocks.NO_STOPPING.get().asItem());
            event.accept(ThingamajigsBlocks.NO_WAITING.get().asItem());
            event.accept(ThingamajigsBlocks.PEDS_PROHIBITED.get().asItem());
            event.accept(ThingamajigsBlocks.SPEED_10.get().asItem());
            event.accept(ThingamajigsBlocks.SPEED_20.get().asItem());
            event.accept(ThingamajigsBlocks.SPEED_30.get().asItem());
            event.accept(ThingamajigsBlocks.SPEED_40.get().asItem());
            event.accept(ThingamajigsBlocks.SPEED_50.get().asItem());
            event.accept(ThingamajigsBlocks.CATTLE_GRID.get().asItem());
            //
        }
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS){
            event.accept(ThingamajigsItems.THINGAMAJIG, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            // fixed ordering
            event.getEntries().putBefore(Items.EXPERIENCE_BOTTLE.getDefaultInstance(),ThingamajigsItems.THINGAMAJIG.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES){
            event.accept(ThingamajigsItems.PAINT_BRUSH);
            event.accept(ThingamajigsItems.WHITE_PAINT_BRUSH);
            event.accept(ThingamajigsItems.YELLOW_PAINT_BRUSH);
            event.accept(ThingamajigsItems.BLUE_PAINT_BRUSH);
            // fixed ordering
            event.getEntries().putAfter(Items.BRUSH.getDefaultInstance(),
                    ThingamajigsItems.PAINT_BRUSH.get().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(ThingamajigsItems.PAINT_BRUSH.get().getDefaultInstance(),
                    ThingamajigsItems.WHITE_PAINT_BRUSH.get().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(ThingamajigsItems.WHITE_PAINT_BRUSH.get().getDefaultInstance(),
                    ThingamajigsItems.YELLOW_PAINT_BRUSH.get().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(ThingamajigsItems.YELLOW_PAINT_BRUSH.get().getDefaultInstance(),
                    ThingamajigsItems.BLUE_PAINT_BRUSH.get().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }

        // config option can disable or enable these features in the creative tab (otherwise the give command will work)
        if(ThingamajigsServerConfigs.COMMON.opBlocksEnabled.get()){
            if(event.getTabKey() == CreativeModeTabs.OP_BLOCKS){
                if(event.hasPermissions()){
                    event.accept(ThingamajigsItems.WATER_SOURCE);
                    event.getEntries().putBefore(Items.COMMAND_BLOCK_MINECART.getDefaultInstance(),
                            ThingamajigsItems.WATER_SOURCE.get().getDefaultInstance(),
                            CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                    event.accept(ThingamajigsItems.NP_PLACEABLE);
                    event.getEntries().putAfter(ThingamajigsItems.WATER_SOURCE.get().getDefaultInstance(),
                            ThingamajigsItems.NP_PLACEABLE.get().getDefaultInstance(),
                            CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                    event.accept(ThingamajigsItems.EP_PLACEABLE);
                    event.getEntries().putAfter(ThingamajigsItems.NP_PLACEABLE.get().getDefaultInstance(),
                            ThingamajigsItems.EP_PLACEABLE.get().getDefaultInstance(),
                            CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                    event.accept(ThingamajigsItems.EG_PLACEABLE);
                    event.getEntries().putAfter(ThingamajigsItems.EP_PLACEABLE.get().getDefaultInstance(),
                            ThingamajigsItems.EG_PLACEABLE.get().getDefaultInstance(),
                            CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                }
            }
        }
        // end creative mode tab items setup
    }

    @OnlyIn(Dist.CLIENT)
    public static void setRenderTypes(){
        ItemBlockRenderTypes.setRenderLayer(ThingamajigsFluids.SLUDGE.get(),RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ThingamajigsFluids.SLUDGE_FLOWING.get(),RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ThingamajigsFluids.PURIFYING_WATER.get(),RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ThingamajigsFluids.PURIFYING_WATER_FLOWING.get(),RenderType.translucent());
    }

    private void setupClient(final FMLClientSetupEvent event){
        // set render types for client
        event.enqueueWork(Thingamajigs::setRenderTypes);

        // try wood type and sign renderer setup
        try{
            WoodType.register(ThingamajigsBlockTypes.GENERIC_ROAD_WOOD);
            WoodType.register(ThingamajigsBlockTypes.GENERIC_RED_ROAD_WOOD);
            WoodType.register(ThingamajigsBlockTypes.GENERIC_BLUE_ROAD_WOOD);
            WoodType.register(ThingamajigsBlockTypes.GENERIC_BROWN_ROAD_WOOD);
        }
        catch (Exception woodTypeError){
            LOGGERV2.error("Thingamajigs could not register new WoodTypes.");
        }

        try{
            // regular signs
            BlockEntityRenderers.register(ThingamajigsBlockEntities.GREEN_ROADWAY_SIGN_BLOCK_ENTITIES.get(), SignRenderer::new);
            BlockEntityRenderers.register(ThingamajigsBlockEntities.RED_ROADWAY_SIGN_BE.get(), SignRenderer::new);
            BlockEntityRenderers.register(ThingamajigsBlockEntities.BLUE_ROADWAY_SIGN_BE.get(), SignRenderer::new);
            BlockEntityRenderers.register(ThingamajigsBlockEntities.BROWN_ROADWAY_SIGN_BE.get(), SignRenderer::new);
            // hanging signs
            BlockEntityRenderers.register(ThingamajigsBlockEntities.GREEN_HANGING_SIGN.get(), HangingSignRenderer::new);
        }
        catch(Exception blockEntityRendererError){
            LOGGERV2.error("Thingamajigs could not register new BlockEntityRenderers.");
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
        // workers work for a sheet of cheetah paper
        event.enqueueWork(() -> {
            try{
                Sheets.addWoodType(ThingamajigsBlockTypes.GENERIC_ROAD_WOOD);
                Sheets.addWoodType(ThingamajigsBlockTypes.GENERIC_RED_ROAD_WOOD);
                Sheets.addWoodType(ThingamajigsBlockTypes.GENERIC_BLUE_ROAD_WOOD);
                Sheets.addWoodType(ThingamajigsBlockTypes.GENERIC_BROWN_ROAD_WOOD);
            }
            catch (java.lang.RuntimeException err){
                LOGGERV2.error("Cannot register sheets. Runtime Distance Error");
            }
        });
    }
}
