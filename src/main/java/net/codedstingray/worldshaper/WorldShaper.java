package net.codedstingray.worldshaper;

import net.codedstingray.worldshaper.commands.CommandInitializer;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.event.listener.AreaModificationHandler;
import net.codedstingray.worldshaper.event.listener.PlayerJoinListener;
import net.codedstingray.worldshaper.event.listener.SelectionWandListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

/**
 * Main class of the WorldShaper plugin.
 */
public class WorldShaper extends JavaPlugin {

    private static WorldShaper INSTANCE;

    private PlayerData playerData;

    @Override
    public void onLoad() {
        INSTANCE = this;

        playerData = new PlayerData();
    }

    @Override
    public void onEnable() {
        new CommandInitializer().initCommands(this);

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new SelectionWandListener(), this);
        getServer().getPluginManager().registerEvents(new AreaModificationHandler(), this);

        getLogger().info("WorldShaper successfully initialized");
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public static WorldShaper getInstance() {
        return Objects.requireNonNull(INSTANCE);
    }
}
