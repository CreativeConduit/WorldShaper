package net.codedstingray.worldshaper;

import net.codedstingray.worldshaper.commands.CommandInitializer;
import net.codedstingray.worldshaper.event.listener.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of the WorldShaper plugin.
 */
public class WorldShaper extends JavaPlugin {

    @Override
    public void onEnable() {
        new CommandInitializer().initCommands(this);

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

        getLogger().info("WorldShaper successfully initialized");
    }
}
