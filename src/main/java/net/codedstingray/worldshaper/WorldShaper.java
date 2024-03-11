/*
 * WorldShaper: a powerful in-game map editor for Minecraft
 * Copyright (C) 2023 CodedStingray
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.codedstingray.worldshaper;

import net.codedstingray.worldshaper.action.ActionController;
import net.codedstingray.worldshaper.commands.CommandInitializer;
import net.codedstingray.worldshaper.data.PluginData;
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

    private PluginData pluginData;

    private ActionController actionController;

    @Override
    public void onLoad() {
        INSTANCE = this;

        saveDefaultConfig();

        pluginData = new PluginData(getConfig());

        actionController = new ActionController();
    }

    @Override
    public void onEnable() {
        new CommandInitializer().initCommands(this);

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new SelectionWandListener(), this);
        getServer().getPluginManager().registerEvents(new AreaModificationHandler(), this);

        getLogger().info("WorldShaper successfully initialized");
    }

    public PluginData getPluginData() {
        return pluginData;
    }

    public ActionController getActionController() {
        return actionController;
    }

    public static WorldShaper getInstance() {
        return Objects.requireNonNull(INSTANCE);
    }
}
