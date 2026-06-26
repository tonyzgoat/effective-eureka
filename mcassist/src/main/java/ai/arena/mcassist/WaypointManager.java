package ai.arena.mcassist;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public final class WaypointManager {
    public record Waypoint(String name, BlockPos pos) {}

    private static final List<Waypoint> WAYPOINTS = new ArrayList<>();

    private WaypointManager() {}

    public static void addCurrentLocation(MinecraftClient client) {
        if (client.player == null) return;
        BlockPos pos = client.player.getBlockPos();
        String name = "WP" + (WAYPOINTS.size() + 1);
        WAYPOINTS.add(new Waypoint(name, pos));
        ChatUtil.sendClientMessage(client, "Added waypoint " + name + " at " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
    }

    public static List<Waypoint> getWaypoints() {
        return WAYPOINTS;
    }
}
