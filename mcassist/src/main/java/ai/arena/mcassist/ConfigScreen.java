package ai.arena.mcassist;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {
    private final Screen parent;

    public ConfigScreen(Screen parent) {
        super(Text.literal("MCAssist Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int y = this.height / 4;

        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(MCAssistClient.CONFIG.hudEnabled)
                .build(centerX - 100, y, 200, 20, Text.literal("HUD Enabled"), (button, value) -> {
                    MCAssistClient.CONFIG.hudEnabled = value;
                    MCAssistClient.CONFIG.save();
                }));
        y += 24;

        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(MCAssistClient.CONFIG.showCoords)
                .build(centerX - 100, y, 200, 20, Text.literal("Show Coordinates"), (button, value) -> {
                    MCAssistClient.CONFIG.showCoords = value;
                    MCAssistClient.CONFIG.save();
                }));
        y += 24;

        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(MCAssistClient.CONFIG.showFps)
                .build(centerX - 100, y, 200, 20, Text.literal("Show FPS"), (button, value) -> {
                    MCAssistClient.CONFIG.showFps = value;
                    MCAssistClient.CONFIG.save();
                }));
        y += 24;

        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(MCAssistClient.CONFIG.showBiome)
                .build(centerX - 100, y, 200, 20, Text.literal("Show Biome"), (button, value) -> {
                    MCAssistClient.CONFIG.showBiome = value;
                    MCAssistClient.CONFIG.save();
                }));
        y += 24;

        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(MCAssistClient.CONFIG.showClock)
                .build(centerX - 100, y, 200, 20, Text.literal("Show Clock"), (button, value) -> {
                    MCAssistClient.CONFIG.showClock = value;
                    MCAssistClient.CONFIG.save();
                }));
        y += 24;

        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(MCAssistClient.CONFIG.showWaypoints)
                .build(centerX - 100, y, 200, 20, Text.literal("Show Waypoints"), (button, value) -> {
                    MCAssistClient.CONFIG.showWaypoints = value;
                    MCAssistClient.CONFIG.save();
                }));
        y += 24;

        this.addDrawableChild(CyclingButtonWidget.builder(value -> Text.literal("Crosshair Style: " + value))
                .values(0, 1, 2)
                .initially(MCAssistClient.CONFIG.crosshairStyle)
                .build(centerX - 100, y, 200, 20, Text.literal("Crosshair Style"), (button, value) -> {
                    MCAssistClient.CONFIG.crosshairStyle = value;
                    MCAssistClient.CONFIG.save();
                }));
        y += 32;

        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> this.close())
                .dimensions(centerX - 100, y, 200, 20)
                .build());
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }
}
