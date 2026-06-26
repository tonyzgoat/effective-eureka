package ai.arena.mcassist.admin;

import ai.arena.mcassist.ChatUtil;
import ai.arena.mcassist.MCAssistClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class AdminCommandPanelScreen extends Screen {
    private final Screen parent;
    private final List<ButtonWidget> presetButtons = new ArrayList<>();
    private TextFieldWidget previewBox;
    private int selectedIndex = -1;

    public AdminCommandPanelScreen(Screen parent) {
        super(Text.literal("Admin Command Panel"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int y = 40;

        previewBox = new TextFieldWidget(this.textRenderer, centerX - 150, y, 300, 20, Text.literal("Preview"));
        previewBox.setEditable(false);
        this.addDrawableChild(previewBox);
        y += 28;

        List<PresetCommand> presets = MCAssistClient.PRESET_COMMANDS;
        for (int i = 0; i < presets.size(); i++) {
            int index = i;
            PresetCommand preset = presets.get(i);
            ButtonWidget button = ButtonWidget.builder(Text.literal(preset.name()), b -> selectPreset(index))
                    .dimensions(centerX - 150, y, 300, 20)
                    .build();
            presetButtons.add(button);
            this.addDrawableChild(button);
            y += 24;
        }

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Prefill Chat"), b -> prefillChat())
                .dimensions(centerX - 150, y + 8, 145, 20)
                .build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Copy First Command"), b -> copyFirstCommand())
                .dimensions(centerX + 5, y + 8, 145, 20)
                .build());

        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, b -> close())
                .dimensions(centerX - 100, this.height - 28, 200, 20)
                .build());
    }

    private void selectPreset(int index) {
        selectedIndex = index;
        PresetCommand preset = MCAssistClient.PRESET_COMMANDS.get(index);
        previewBox.setText(String.join(" ; ", preset.commands()));
    }

    private boolean hasPermissionLikeStatus(MinecraftClient client) {
        return client.player != null && (client.player.hasPermissionLevel(2) || client.player.isCreativeLevelTwoOp());
    }

    private void prefillChat() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        if (!hasPermissionLikeStatus(client)) {
            ChatUtil.sendClientMessage(client, "No operator-like permission detected.");
            return;
        }
        if (selectedIndex < 0) {
            ChatUtil.sendClientMessage(client, "Select a preset first.");
            return;
        }

        PresetCommand preset = MCAssistClient.PRESET_COMMANDS.get(selectedIndex);
        if (preset.commands().isEmpty()) return;
        client.setScreen(new net.minecraft.client.gui.screen.ChatScreen(preset.commands().get(0)));
    }

    private void copyFirstCommand() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (selectedIndex < 0) {
            ChatUtil.sendClientMessage(client, "Select a preset first.");
            return;
        }
        PresetCommand preset = MCAssistClient.PRESET_COMMANDS.get(selectedIndex);
        if (preset.commands().isEmpty()) return;
        client.keyboard.setClipboard(preset.commands().get(0));
        ChatUtil.sendClientMessage(client, "Copied first command to clipboard.");
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
        context.drawTextWithShadow(this.textRenderer, Text.literal("Manual-use only: this screen never auto-sends commands."), this.width / 2 - 150, this.height - 52, 0xAAAAAA);
        super.render(context, mouseX, mouseY, delta);
    }
}
