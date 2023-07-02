package net.codedstingray.worldshaper;

import net.codedstingray.worldshaper.commands.CommandInitializer;
import net.codedstingray.worldshaper.event.listener.PlayerJoinListener;
import net.codedstingray.worldshaper.event.listener.SelectionWandListener;
import net.codedstingray.worldshaper.selection.PlayerSelectionMap;
import net.codedstingray.worldshaper.selection.type.SelectionType;
import net.codedstingray.worldshaper.selection.type.SelectionTypeIndefinitePositions;
import net.codedstingray.worldshaper.selection.type.SelectionTypeTwoPositions;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Main class of the WorldShaper plugin.
 */
public class WorldShaper extends JavaPlugin {

    private static WorldShaper INSTANCE;

    private SelectionType DEFAULT_SELECTION_TYPE;

    private final PlayerSelectionMap playerSelectionMap = new PlayerSelectionMap();
    private final Map<String, SelectionType> selectionTypeMap = new HashMap<>();
    private final Map<UUID, SelectionType> playerSelectionTypeMap = new HashMap<>();

    @Override
    public void onLoad() {
        INSTANCE = this;

        selectionTypeMap.put(SelectionTypeTwoPositions.NAME, new SelectionTypeTwoPositions());
        selectionTypeMap.put(SelectionTypeIndefinitePositions.NAME, new SelectionTypeIndefinitePositions());

        DEFAULT_SELECTION_TYPE = selectionTypeMap.get(SelectionTypeTwoPositions.NAME);
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

    public Map<UUID, SelectionType> getPlayerSelectionTypeMap() {
        return playerSelectionTypeMap;
    }

    public SelectionType getSelectionTypeByName(String name) {
        return selectionTypeMap.get(name);
    }

    public SelectionType getDefaultSelectionType() {
        return DEFAULT_SELECTION_TYPE;
    }

    public static WorldShaper getInstance() {
        return Objects.requireNonNull(INSTANCE);
    }
}
