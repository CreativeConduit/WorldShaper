package net.codedstingray.worldshaper.commands;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.commands.utility.CommandWand;

import java.util.Objects;

/**
 * Utility class for command initialization.
 */
public class CommandInitializer {

    /**
     * Initializes all WorldShaper Commands.
     *
     * @param plugin The WorldShaper plugin, necessary for command registration
     */
    public void initCommands(WorldShaper plugin) {
        Objects.requireNonNull(plugin.getCommand("wand")).setExecutor(new CommandWand());
    }
}
