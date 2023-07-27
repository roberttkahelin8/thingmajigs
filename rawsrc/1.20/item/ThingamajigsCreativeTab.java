package net.rk.thingamajigs.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.rk.thingamajigs.Thingamajigs;
import net.rk.thingamajigs.block.ThingamajigsBlocks;

@Mod.EventBusSubscriber(modid = Thingamajigs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ThingamajigsCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CMTS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Thingamajigs.MOD_ID);
    public static final ResourceLocation ITEMS = new ResourceLocation("thingamajigs:textures/gui/thingamajigsitems.png");
    public static final ResourceLocation ITEMS_LONGER = new ResourceLocation("thingamajigs:textures/gui/thingamajigssignitems.png");
    public static final ResourceLocation TABS = new ResourceLocation("thingamajigs:textures/gui/thingamajigstabs.png");


    public static RegistryObject<CreativeModeTab> ALL_ITEMS_TAB_v2 = CMTS.register("thingamajigs",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ThingamajigsItems.THINGAMAJIG.get()))
                    .title(Component.translatable("itemGroup.Thingamajigs"))
                    .withBackgroundLocation(ITEMS)
                    .withTabsImage(TABS)
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .build());

    public static RegistryObject<CreativeModeTab> ROAD_SIGNS_TAB = CMTS.register("thingamajigs_road_signs",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ThingamajigsBlocks.STOP_SIGN.get()))
                    .title(Component.translatable("itemGroup.ThingamajigsRoadSigns"))
                    .withTabsBefore(ThingamajigsCreativeTab.ALL_ITEMS_TAB_v2.getKey())
                    .withBackgroundLocation(ITEMS_LONGER)
                    .withTabsImage(TABS)
                    .build());

    public static void register(IEventBus eventBus){
        CMTS.register(eventBus);
    }
}
