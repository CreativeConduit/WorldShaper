package net.codedstingray.worldshaper;

import net.codedstingray.worldshaper.event.listener.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldShaper extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

        getLogger().info("WorldShaper successfully initialized");
    }
}
