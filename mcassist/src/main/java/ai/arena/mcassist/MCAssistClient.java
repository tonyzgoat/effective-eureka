package ai.arena.mcassist;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class MCAssistClient implements ClientModInitializer {
    public static final String MOD_ID = "mcassist";
    public static final MCAssistConfig CONFIG = new MCAssistConfig();

    public static KeyBinding toggleHudKey;
    public static KeyBinding addWaypointKey;
    public static KeyBinding nextCrosshairKey;
    public static KeyBinding sortHotbarKey;

    @Override
    public void onInitializeClient() {
        toggleHudKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mcassist.toggle_hud",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.mcassist"
        ));

        addWaypointKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mcassist.add_waypoint",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_EQUAL,
                "category.mcassist"
        ));

        nextCrosshairKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mcassist.next_crosshair",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_BACKSLASH,
                "category.mcassist"
        ));

        sortHotbarKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mcassist.sort_hotbar",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                "category.mcassist"
        ));

        HudRenderer.init();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleHudKey.wasPressed()) {
                CONFIG.hudEnabled = !CONFIG.hudEnabled;
                ChatUtil.sendClientMessage(client, "MCAssist HUD " + (CONFIG.hudEnabled ? "enabled" : "disabled"));
            }
            while (addWaypointKey.wasPressed()) {
                WaypointManager.addCurrentLocation(client);
            }
            while (nextCrosshairKey.wasPressed()) {
                CONFIG.crosshairStyle = (CONFIG.crosshairStyle + 1) % 3;
                CONFIG.save();
                ChatUtil.sendClientMessage(client, "Crosshair style: " + CONFIG.crosshairStyle);
            }
            while (sortHotbarKey.wasPressed()) {
                InventoryHelper.sortHotbar(client);
            }
            while (adminPanelKey.wasPressed()) {
                KeybindScreens.openAdminPanel();
            }
            while (tntAssistKey.wasPressed()) {
                TntCartSetupAssistant.announceStatus(client);
            }
        });
    }
}
