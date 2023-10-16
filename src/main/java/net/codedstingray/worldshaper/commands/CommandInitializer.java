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

package net.codedstingray.worldshaper.commands;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.commands.action.CommandRedo;
import net.codedstingray.worldshaper.commands.action.CommandUndo;
import net.codedstingray.worldshaper.commands.area.CommandAreaType;
import net.codedstingray.worldshaper.commands.area.CommandExpand;
import net.codedstingray.worldshaper.commands.area.CommandMoveArea;
import net.codedstingray.worldshaper.commands.area.CommandRetract;
import net.codedstingray.worldshaper.commands.area.operations.CommandReplace;
import net.codedstingray.worldshaper.commands.area.operations.CommandSet;
import net.codedstingray.worldshaper.commands.selection.*;
import net.codedstingray.worldshaper.commands.utility.CommandWand;
import net.codedstingray.worldshaper.commands.utility.CommandWorldShaper;

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
        Objects.requireNonNull(plugin.getCommand("worldshaper")).setExecutor(new CommandWorldShaper());
        Objects.requireNonNull(plugin.getCommand("wand")).setExecutor(new CommandWand());

        Objects.requireNonNull(plugin.getCommand("undo")).setExecutor(new CommandUndo());
        Objects.requireNonNull(plugin.getCommand("redo")).setExecutor(new CommandRedo());

        Objects.requireNonNull(plugin.getCommand("pos")).setExecutor(new CommandPos());
        Objects.requireNonNull(plugin.getCommand("positions")).setExecutor(new CommandPositions());
        Objects.requireNonNull(plugin.getCommand("removepos")).setExecutor(new CommandRemovePos());
        Objects.requireNonNull(plugin.getCommand("clearpositions")).setExecutor(new CommandClearPositions());

        Objects.requireNonNull(plugin.getCommand("selectiontype")).setExecutor(new CommandSelectionType());
        Objects.requireNonNull(plugin.getCommand("areatype")).setExecutor(new CommandAreaType());

        Objects.requireNonNull(plugin.getCommand("movearea")).setExecutor(new CommandMoveArea());
        Objects.requireNonNull(plugin.getCommand("expand")).setExecutor(new CommandExpand());
        Objects.requireNonNull(plugin.getCommand("retract")).setExecutor(new CommandRetract());

        Objects.requireNonNull(plugin.getCommand("set")).setExecutor(new CommandSet());
        Objects.requireNonNull(plugin.getCommand("replace")).setExecutor(new CommandReplace());
    }
}
