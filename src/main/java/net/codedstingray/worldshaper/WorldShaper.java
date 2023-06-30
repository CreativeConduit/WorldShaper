package net.codedstingray.worldshaper;

import net.codedstingray.worldshaper.commands.CommandInitializer;
import net.codedstingray.worldshaper.event.listener.PlayerJoinListener;
import net.codedstingray.worldshaper.event.listener.SelectionWandListener;
import net.codedstingray.worldshaper.selection.PlayerSelectionMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

/**
 * Main class of the WorldShaper plugin.
 */
public class WorldShaper extends JavaPlugin {

    private static WorldShaper INSTANCE;

    private final PlayerSelectionMap playerSelectionMap = new PlayerSelectionMap();

    @Override
    public void onLoad() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        new CommandInitializer().initCommands(this);

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new SelectionWandListener(), this);

        getLogger().info("WorldShaper successfully initialized");
    }

    public PlayerSelectionMap getPlayerSelectionMap() {
        return playerSelectionMap;
    }

    public static WorldShaper getInstance() {
        return Objects.requireNonNull(INSTANCE);
    }
}
