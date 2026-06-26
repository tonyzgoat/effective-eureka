package ai.arena.mcassist;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class HudRenderer {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private HudRenderer() {}

    public static void init() {
        HudRenderCallback.EVENT.register(HudRenderer::render);
    }

    private static void render(DrawContext context, net.minecraft.client.render.RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!MCAssistClient.CONFIG.hudEnabled || client.player == null || client.options.hudHidden) {
            return;
        }

        TextRenderer tr = client.textRenderer;
        int x = 6;
        int y = 6;
        int color = 0xFFFFFF;

        if (MCAssistClient.CONFIG.showCoords) {
            var p = client.player.getPos();
            context.drawText(tr, Text.literal(String.format("XYZ: %.1f / %.1f / %.1f", p.x, p.y, p.z)), x, y, color, true);
            y += 10;
        }

        if (MCAssistClient.CONFIG.showFps) {
            context.drawText(tr, Text.literal("FPS: " + client.getCurrentFps()), x, y, color, true);
            y += 10;
        }

        if (MCAssistClient.CONFIG.showBiome) {
            RegistryEntry<Biome> biome = client.world.getBiome(client.player.getBlockPos());
            Identifier id = client.world.getRegistryManager().getOrThrow(net.minecraft.registry.RegistryKeys.BIOME).getId(biome.value());
            String biomeName = id == null ? "unknown" : id.getPath();
            context.drawText(tr, Text.literal("Biome: " + biomeName), x, y, color, true);
            y += 10;
        }

        if (MCAssistClient.CONFIG.showClock) {
            context.drawText(tr, Text.literal("Time: " + LocalTime.now().format(TIME_FORMAT)), x, y, color, true);
            y += 10;
        }

        if (MCAssistClient.CONFIG.showWaypoints) {
            for (WaypointManager.Waypoint waypoint : WaypointManager.getWaypoints()) {
                double dx = waypoint.pos().getX() + 0.5 - client.player.getX();
                double dz = waypoint.pos().getZ() + 0.5 - client.player.getZ();
                int dist = (int) Math.sqrt(dx * dx + dz * dz);
                context.drawText(tr, Text.literal(waypoint.name() + ": " + dist + "m"), x, y, 0x55FF55, true);
                y += 10;
            }
        }

        if (client.options.useKey.isPressed()) {
            context.drawText(tr, ai.arena.mcassist.tnt.TntCartSetupAssistant.getOverlayText(client), x, y + 4, 0xFFAA00, true);
        }

        renderCrosshair(context, client);
    }

    private static void renderCrosshair(DrawContext context, MinecraftClient client) {
        int style = MCAssistClient.CONFIG.crosshairStyle;
        if (style == 0) return;

        int cx = client.getWindow().getScaledWidth() / 2;
        int cy = client.getWindow().getScaledHeight() / 2;
        int color = style == 1 ? 0xFFFFFFFF : 0xFF00FF00;

        context.fill(cx - 1, cy - 6, cx + 1, cy - 2, color);
        context.fill(cx - 1, cy + 2, cx + 1, cy + 6, color);
        context.fill(cx - 6, cy - 1, cx - 2, cy + 1, color);
        context.fill(cx + 2, cy - 1, cx + 6, cy + 1, color);
        if (style == 2) {
            context.fill(cx - 1, cy - 1, cx + 1, cy + 1, color);
        }
    }
}
