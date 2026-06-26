package ai.arena.mcassist;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class MCAssistConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("mcassist.json");

    public boolean hudEnabled = true;
    public boolean showCoords = true;
    public boolean showFps = true;
    public boolean showBiome = true;
    public boolean showClock = true;
    public boolean showWaypoints = true;
    public int crosshairStyle = 0;

    public static MCAssistConfig load() {
        try {
            if (Files.exists(CONFIG_PATH)) {
                try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                    MCAssistConfig loaded = GSON.fromJson(reader, MCAssistConfig.class);
                    if (loaded != null) {
                        loaded.save();
                        return loaded;
                    }
                }
            }
        } catch (Exception ignored) {
        }

        MCAssistConfig config = new MCAssistConfig();
        config.save();
        return config;
    }

    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException ignored) {
        }
    }
}
