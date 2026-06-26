package ai.arena.mcassist;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public final class ChatUtil {
    private ChatUtil() {}

    public static void sendClientMessage(MinecraftClient client, String message) {
        if (client.player != null) {
            client.player.sendMessage(Text.literal("[MCAssist] " + message), false);
        }
    }
}
