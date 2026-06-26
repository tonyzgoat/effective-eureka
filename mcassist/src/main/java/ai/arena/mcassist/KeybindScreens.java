package ai.arena.mcassist;

import ai.arena.mcassist.admin.AdminCommandPanelScreen;
import net.minecraft.client.MinecraftClient;

public final class KeybindScreens {
    private KeybindScreens() {}

    public static void openAdminPanel() {
        MinecraftClient client = MinecraftClient.getInstance();
        client.setScreen(new AdminCommandPanelScreen(client.currentScreen));
    }
}
