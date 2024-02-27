package net.rk.thingamajigs.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.gui.widget.ExtendedButton;
import net.neoforged.neoforge.client.gui.widget.ExtendedSlider;
import net.neoforged.neoforge.network.PacketDistributor;
import net.rk.thingamajigs.Thingamajigs;
import net.rk.thingamajigs.ThingamajigsColors;
import net.rk.thingamajigs.entity.blockentity.DJLaserLightBE;
import net.rk.thingamajigs.gui.widgets.DJLaserLightToggledButton;
import net.rk.thingamajigs.network.data.DJLaserLightUpdatePayload;

import java.util.HashMap;

public class DJLaserLightScreen extends AbstractContainerScreen<DJLaserLightMenu> {
    private final static HashMap<String, Object> guistate = DJLaserLightMenu.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;

    private DJLaserLightBE djllbe;

    public Button decreaseHeightButton;
    public Button increaseHeightButton;
    public Button decVertAngleButton;
    public Button incVertAngleButton;

    public ExtendedSlider redSlider;
    public ExtendedSlider greenSlider;
    public ExtendedSlider blueSlider;

    public ExtendedSlider oscAngleSlider;
    public ExtendedSlider mmSlider;

    public ExtendedSlider laserSizeSlider;

    public DJLaserLightToggledButton customColorToggle;

    public DJLaserLightToggledButton randomColorToggle;

    public ExtendedSlider voscAngleSlider;
    public ExtendedSlider vmmSlider;


    private static final ResourceLocation BG_TEXTURE = new ResourceLocation("thingamajigs:textures/gui/laser_light_menu.png");

    public DJLaserLightScreen(DJLaserLightMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 320;
        this.imageHeight = 240;

        this.minecraft = Minecraft.getInstance();
        this.font = this.minecraft.font;

        int widthx = (this.width - this.imageWidth) / 2;
        int heighty = (this.height - this.imageHeight) / 2;

        this.djllbe = (DJLaserLightBE)world.getBlockEntity(new BlockPos(x,y,z)); // access the BE at the current pos that the screen is opened at
    }

    @Override
    protected void init() {
        super.init();
        setupExtras();
        addRenderableWidget(decreaseHeightButton);
        addRenderableWidget(increaseHeightButton);
        addRenderableWidget(decVertAngleButton);
        addRenderableWidget(incVertAngleButton);
        addRenderableWidget(redSlider);
        addRenderableWidget(greenSlider);
        addRenderableWidget(blueSlider);
        addRenderableWidget(customColorToggle);
        addRenderableWidget(randomColorToggle);
        addRenderableWidget(oscAngleSlider);
        addRenderableWidget(mmSlider);
        addRenderableWidget(laserSizeSlider);
        addRenderableWidget(voscAngleSlider);
        addRenderableWidget(vmmSlider);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics,pMouseX,pMouseY,pPartialTick);
        this.renderBg(pGuiGraphics,pPartialTick,pMouseX,pMouseY);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        Component modelabel = Component.translatable("container.thingamajigs.laser_light.mode_title")
                .withStyle(ChatFormatting.WHITE);
        Component extraInfolabel = Component.translatable("container.thingamajigs.laser_light.mode_unused");

        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY,
                ThingamajigsColors.getWhite(), false);

        if(djllbe.lightMode == 0){
            extraInfolabel = Component.translatable("container.thingamajigs.laser_light.mode_zero_label");
        }

        pGuiGraphics.drawString(this.font,
                Component.literal(modelabel.getString() + " " + djllbe.lightMode + " (" + extraInfolabel.getString() + ")"),
                208, 110,
                ThingamajigsColors.getWhite(),
                false);
    }

    // fun
    @Override
    public void renderBackground(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderCustomBg(pGuiGraphics);
        if (this.minecraft.level != null) {
            net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(new net.neoforged.neoforge.client.event.ScreenEvent.BackgroundRendered(this, pGuiGraphics));
        }
    }

    public void renderCustomBg(GuiGraphics guigraph) {
        ResourceLocation BG_LOC = new ResourceLocation(Thingamajigs.MODID,"textures/block/sidewalk_new.png");

        guigraph.setColor((float)redSlider.getValue(), (float)greenSlider.getValue(), (float)blueSlider.getValue(), 0.5F);

        guigraph.blit(BG_LOC, this.leftPos - 18, this.topPos + 107, 0, 0.0F, 0.0F,
                16, 16, 16, 16);

        guigraph.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(
                new net.neoforged.neoforge.client.event.ScreenEvent.BackgroundRendered(this, guigraph));
    }



    @Override
    protected void renderBg(GuiGraphics ggraph, float ptick, int mousx, int mousy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, BG_TEXTURE);
        ggraph.blit(BG_TEXTURE,
                this.leftPos,this.topPos,0,0,
                this.imageWidth,this.imageHeight,this.imageWidth,this.imageHeight);
        RenderSystem.disableBlend();
    }

    @Override
    public void containerTick() {
        super.containerTick();
    }

    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(key, b, c);
    }

    public void onHeightAmtChanged(String str){

    }

    // send to server all updated BE elements so the BE can be updated and saved and synced properly to all clients
    private void setupExtras(){
        //
        //    int color,float angle
        //         int lightmode

        int smallSliderwidth = 72;
        int sliderheight = 16;
        int mediumSliderwidth = 102;

        //

        //oscAngleSlider
        oscAngleSlider = new ExtendedSlider(this.leftPos + 208,this.topPos + 50,mediumSliderwidth,sliderheight,
                Component.translatable("slider.thingamajigs.laser_light.osc_angle_slider"),Component.empty(),
                0,180,djllbe.oscAngle,1,1,true)
        {
            @Override
            protected void applyValue() {
                int oscVal = (int)getValue();

                PacketDistributor.SERVER.noArg().send(new DJLaserLightUpdatePayload(
                        new BlockPos(x,y,z),
                        djllbe.useCustomColor,djllbe.randomlyGenerateColor,
                        djllbe.height,djllbe.color,
                        djllbe.angle,djllbe.verticalAngle,
                        oscVal,djllbe.degreeOfMotionMultiplier,
                        djllbe.laserSize,
                        djllbe.red,
                        djllbe.green,
                        djllbe.blue,
                        djllbe.lightMode,djllbe.vertOscAngle,djllbe.dOfMotMultiVert
                ));
                setFocused(false);
            }
        };

        mmSlider = new ExtendedSlider(oscAngleSlider.getX(),oscAngleSlider.getY() + oscAngleSlider.getHeight() + 2,mediumSliderwidth,sliderheight,
                Component.translatable("slider.thingamajigs.laser_light.mm_slider"),Component.empty(),
                0,32,djllbe.degreeOfMotionMultiplier,1,1,true)
        {
            @Override
            protected void applyValue() {
                int val = (int)getValue();

                PacketDistributor.SERVER.noArg().send(new DJLaserLightUpdatePayload(
                        new BlockPos(x,y,z),
                        djllbe.useCustomColor,djllbe.randomlyGenerateColor,
                        djllbe.height,djllbe.color,
                        djllbe.angle,djllbe.verticalAngle,
                        djllbe.oscAngle, val,
                        djllbe.laserSize,
                        djllbe.red,
                        djllbe.green,
                        djllbe.blue,
                        djllbe.lightMode,djllbe.vertOscAngle,djllbe.dOfMotMultiVert
                ));
                setFocused(false);
            }
        };

        voscAngleSlider = new ExtendedSlider(this.leftPos + 209,this.topPos + 148,mediumSliderwidth,sliderheight,
                Component.translatable("slider.thingamajigs.laser_light.vosc_angle_slider"),Component.empty(),
                0,180,djllbe.vertOscAngle,1,1,true)
        {
            @Override
            protected void applyValue() {
                int voscVal = (int)getValue();

                PacketDistributor.SERVER.noArg().send(new DJLaserLightUpdatePayload(
                        new BlockPos(x,y,z),
                        djllbe.useCustomColor,djllbe.randomlyGenerateColor,
                        djllbe.height,djllbe.color,
                        djllbe.angle,djllbe.verticalAngle,
                        djllbe.oscAngle,djllbe.degreeOfMotionMultiplier,
                        djllbe.laserSize,
                        djllbe.red,
                        djllbe.green,
                        djllbe.blue,
                        djllbe.lightMode,voscVal,djllbe.dOfMotMultiVert
                ));
                setFocused(false);
            }
        };

        vmmSlider = new ExtendedSlider(voscAngleSlider.getX(),voscAngleSlider.getY() + voscAngleSlider.getHeight() + 2,mediumSliderwidth,sliderheight,
                Component.translatable("slider.thingamajigs.laser_light.vmm_slider"),Component.empty(),
                0,32,djllbe.dOfMotMultiVert,1,1,true)
        {
            @Override
            protected void applyValue() {
                int val = (int)getValue();

                PacketDistributor.SERVER.noArg().send(new DJLaserLightUpdatePayload(
                        new BlockPos(x,y,z),
                        djllbe.useCustomColor,djllbe.randomlyGenerateColor,
                        djllbe.height,djllbe.color,
                        djllbe.angle,djllbe.verticalAngle,
                        djllbe.oscAngle, djllbe.degreeOfMotionMultiplier,
                        djllbe.laserSize,
                        djllbe.red,
                        djllbe.green,
                        djllbe.blue,
                        djllbe.lightMode,djllbe.vertOscAngle,val
                ));
                setFocused(false);
            }
        };

        laserSizeSlider = new ExtendedSlider(mmSlider.getX(),mmSlider.getY() + mmSlider.getHeight() + 2,mediumSliderwidth,sliderheight,
                Component.translatable("slider.thingamajigs.laser_light.lasersizeslider"),Component.empty(),
                0.01,3.0,djllbe.laserSize,0.01,4,true)
        {
            @Override
            protected void applyValue() {
                float val = (float)getValue();

                PacketDistributor.SERVER.noArg().send(new DJLaserLightUpdatePayload(
                        new BlockPos(x,y,z),
                        djllbe.useCustomColor,djllbe.randomlyGenerateColor,
                        djllbe.height,djllbe.color,
                        djllbe.angle,djllbe.verticalAngle,
                        djllbe.oscAngle,djllbe.degreeOfMotionMultiplier,
                        val,
                        djllbe.red,
                        djllbe.green,
                        djllbe.blue,
                        djllbe.lightMode,djllbe.vertOscAngle,djllbe.dOfMotMultiVert
                ));
                setFocused(false);
            }
        };

        decreaseHeightButton = new ExtendedButton(this.leftPos + 23, this.topPos + 24,
                52, 16, Component.translatable("button.thingamajigs.laser_light.height_dec"),
                (b0s) -> {
                    PacketDistributor.SERVER.noArg().send(new DJLaserLightUpdatePayload(
                            new BlockPos(x,y,z),
                            djllbe.useCustomColor,djllbe.randomlyGenerateColor,
                            djllbe.height - 1,djllbe.color,
                            djllbe.angle,djllbe.verticalAngle,
                            djllbe.oscAngle,djllbe.degreeOfMotionMultiplier,
                            djllbe.laserSize,
                            djllbe.red,
                            djllbe.green,
                            djllbe.blue,
                            djllbe.lightMode,djllbe.vertOscAngle,djllbe.dOfMotMultiVert
                            ));
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,0.8F));
                });

        increaseHeightButton = new ExtendedButton(decreaseHeightButton.getX() + decreaseHeightButton.getWidth() + 2, decreaseHeightButton.getY(),
                52, 16, Component.translatable("button.thingamajigs.laser_light.height_inc"),
                (b0s) -> {
                    PacketDistributor.SERVER.noArg().send(new DJLaserLightUpdatePayload(
                            new BlockPos(x,y,z),
                            djllbe.useCustomColor,djllbe.randomlyGenerateColor,
                            djllbe.height + 1,djllbe.color,
                            djllbe.angle,djllbe.verticalAngle,
                            djllbe.oscAngle,djllbe.degreeOfMotionMultiplier,
                            djllbe.laserSize,
                            djllbe.red,
                            djllbe.green,
                            djllbe.blue,
                            djllbe.lightMode,djllbe.vertOscAngle,djllbe.dOfMotMultiVert
                    ));
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,1.0F));
                });

        decVertAngleButton = new ExtendedButton(this.leftPos + 23, decreaseHeightButton.getY() + decreaseHeightButton.getHeight() + 2,
                52, 16, Component.translatable("button.thingamajigs.laser_light.vert_ang_dec"),
                (b0s) -> {
                    PacketDistributor.SERVER.noArg().send(new DJLaserLightUpdatePayload(
                            new BlockPos(x,y,z),
                            djllbe.useCustomColor,djllbe.randomlyGenerateColor,
                            djllbe.height,djllbe.color,
                            djllbe.angle,djllbe.verticalAngle - 1,
                            djllbe.oscAngle,djllbe.degreeOfMotionMultiplier,
                            djllbe.laserSize,
                            djllbe.red,
                            djllbe.green,
                            djllbe.blue,
                            djllbe.lightMode,djllbe.vertOscAngle,djllbe.dOfMotMultiVert
                    ));
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,1.0F));
                });

        incVertAngleButton = new ExtendedButton(decVertAngleButton.getX() + decVertAngleButton.getWidth() + 2, increaseHeightButton.getY() + increaseHeightButton.getHeight() + 2,
                52, 16, Component.translatable("button.thingamajigs.laser_light.vert_ang_inc"),
                (b0s) -> {
                    PacketDistributor.SERVER.noArg().send(new DJLaserLightUpdatePayload(
                            new BlockPos(x,y,z),
                            djllbe.useCustomColor,djllbe.randomlyGenerateColor,
                            djllbe.height,djllbe.color,
                            djllbe.angle,djllbe.verticalAngle + 1,
                            djllbe.oscAngle,djllbe.degreeOfMotionMultiplier,
                            djllbe.laserSize,
                            djllbe.red,
                            djllbe.green,
                            djllbe.blue,
                            djllbe.lightMode,djllbe.vertOscAngle,djllbe.dOfMotMultiVert
                    ));
                    Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_PICKUP,1.0F));
                });

        redSlider = new ExtendedSlider(this.leftPos + 11,this.topPos + 88,mediumSliderwidth,sliderheight,
                Component.translatable("slider.thingamajigs.laser_light.red_slider"),Component.empty(),
                0.0,1.0,djllbe.red,0.1,1,true)
        {
            @Override
            protected void applyValue() {
                float redVal = (float)getValue();

                PacketDistributor.SERVER.noArg().send(new DJLaserLightUpdatePayload(
                        new BlockPos(x,y,z),
                        djllbe.useCustomColor,djllbe.randomlyGenerateColor,
                        djllbe.height,djllbe.color,
                        djllbe.angle,djllbe.verticalAngle,
                        djllbe.oscAngle,djllbe.degreeOfMotionMultiplier,
                        djllbe.laserSize,
                        redVal,
                        djllbe.green,
                        djllbe.blue,
                        djllbe.lightMode,djllbe.vertOscAngle,djllbe.dOfMotMultiVert
                ));
                setFocused(false);
            }
        };

        greenSlider = new ExtendedSlider(this.leftPos + 11,redSlider.getY() + redSlider.getHeight() + 2,mediumSliderwidth,sliderheight,
                Component.translatable("slider.thingamajigs.laser_light.green_slider"),Component.empty(),
                0.0,1.0,djllbe.green,0.1,1,true)
        {
            @Override
            protected void applyValue() {
                float greenVal = (float)getValue();

                PacketDistributor.SERVER.noArg().send(new DJLaserLightUpdatePayload(
                        new BlockPos(x,y,z),
                        djllbe.useCustomColor,djllbe.randomlyGenerateColor,
                        djllbe.height,djllbe.color,
                        djllbe.angle,djllbe.verticalAngle,
                        djllbe.oscAngle,djllbe.degreeOfMotionMultiplier,
                        djllbe.laserSize,
                        djllbe.red,
                        greenVal,
                        djllbe.blue,
                        djllbe.lightMode,djllbe.vertOscAngle,djllbe.dOfMotMultiVert
                ));
                setFocused(false);
            }
        };

        blueSlider = new ExtendedSlider(this.leftPos + 11,greenSlider.getY() + greenSlider.getHeight() + 2,mediumSliderwidth,sliderheight,
                Component.translatable("slider.thingamajigs.laser_light.blue_slider"),Component.empty(),
                0.0,1.0,djllbe.blue,0.1,1,true)
        {
            @Override
            protected void applyValue() {
                float blueVal = (float)getValue();

                PacketDistributor.SERVER.noArg().send(new DJLaserLightUpdatePayload(
                        new BlockPos(x,y,z),
                        djllbe.useCustomColor,djllbe.randomlyGenerateColor,
                        djllbe.height,djllbe.color,
                        djllbe.angle,djllbe.verticalAngle,
                        djllbe.oscAngle,djllbe.degreeOfMotionMultiplier,
                        djllbe.laserSize,
                        djllbe.red,
                        djllbe.green,
                        blueVal,
                        djllbe.lightMode,djllbe.vertOscAngle,djllbe.dOfMotMultiVert
                ));
                setFocused(false);
            }
        };

        Component addonOn = Component.empty();
        if(djllbe.useCustomColor){
            addonOn = CommonComponents.OPTION_ON;
        }
        else{
            addonOn = CommonComponents.OPTION_OFF;
        }

        Component addonRGCOn = Component.empty();
        if(djllbe.randomlyGenerateColor){
            addonRGCOn = CommonComponents.OPTION_ON;
        }
        else{
            addonRGCOn = CommonComponents.OPTION_OFF;
        }

        customColorToggle = new DJLaserLightToggledButton(this.leftPos + 252,this.topPos + 5,
                djllbe.useCustomColor,"tooltip.thingamajigs.laser_light.cct",addonOn){
            @Override
            public void onRelease(double pMouseX, double pMouseY) {
                boolean activated = djllbe.useCustomColor;
                if(activated){
                    activated = false;
                    setStateTriggered(false);
                }
                else{
                    activated = true;
                    setStateTriggered(true);
                }
                PacketDistributor.SERVER.noArg().send(new DJLaserLightUpdatePayload(
                        new BlockPos(x,y,z),
                        activated,djllbe.randomlyGenerateColor,
                        djllbe.height,djllbe.color,
                        djllbe.angle,djllbe.verticalAngle,
                        djllbe.oscAngle,djllbe.degreeOfMotionMultiplier,
                        djllbe.laserSize,
                        djllbe.red,
                        djllbe.green,
                        djllbe.blue,
                        djllbe.lightMode,djllbe.vertOscAngle,djllbe.dOfMotMultiVert
                ));
                setFocused(false);
            }
        };

        randomColorToggle = new DJLaserLightToggledButton(
                customColorToggle.getX() + customColorToggle.getWidth() + 2,
                this.topPos + 5,
                djllbe.randomlyGenerateColor,"tooltip.thingamajigs.laser_light.rct",addonRGCOn){
            @Override
            public void onRelease(double pMouseX, double pMouseY) {
                boolean activated = djllbe.randomlyGenerateColor;
                if(activated){
                    activated = false;
                    setStateTriggered(false);
                }
                else{
                    activated = true;
                    setStateTriggered(true);
                }
                PacketDistributor.SERVER.noArg().send(new DJLaserLightUpdatePayload(
                        new BlockPos(x,y,z),
                        djllbe.useCustomColor,activated,
                        djllbe.height,djllbe.color,
                        djllbe.angle,djllbe.verticalAngle,
                        djllbe.oscAngle,djllbe.degreeOfMotionMultiplier,
                        djllbe.laserSize,
                        djllbe.red,
                        djllbe.green,
                        djllbe.blue,
                        djllbe.lightMode,djllbe.vertOscAngle,djllbe.dOfMotMultiVert
                ));
                setFocused(false);
            }
        };

    }
}
