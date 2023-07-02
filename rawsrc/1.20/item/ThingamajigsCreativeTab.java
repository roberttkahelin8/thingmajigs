package net.rk.thingamajigs.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.rk.thingamajigs.Thingamajigs;

@Mod.EventBusSubscriber(modid = Thingamajigs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ThingamajigsCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CMTS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Thingamajigs.MOD_ID);

    @Deprecated
    public static CreativeModeTab ALL_ITEMS_TAB; // legacy stuff has ended in 1.20, leading this variable to be older than the newer one that is now used in it's place...
    // future versions of Thingamajigs may delete this unused tab completely...

    // tabs and bg image deprecated due to tabs image not working anymore
    // do they exist? yes! But tabs will be out of normal usage for a long time... otherwise, the bg still works fine if enabled...
    public static RegistryObject<CreativeModeTab> ALL_ITEMS_TAB_v2 = CMTS.register("thingamajigs",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ThingamajigsItems.THINGAMAJIG.get()))
                    .title(Component.translatable("itemGroup.Thingamajigs"))
                    //.withBackgroundLocation(new ResourceLocation("thingamajigs:textures/gui/thingamajigsitems.png"))
                    //.withTabsImage(new ResourceLocation("thingamajigs:textures/gui/thingamajigstabs.png"))
                    .build());

    public static void register(IEventBus eventBus){
        CMTS.register(eventBus);
    }
}
