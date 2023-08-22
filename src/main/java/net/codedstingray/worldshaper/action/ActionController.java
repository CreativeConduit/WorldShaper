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

package net.codedstingray.worldshaper.action;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Objects;

public class ActionController {

    public void performAction(ActionStack actionStack, Action action) {
        actionStack.clearUndoStack();
        applyAction(action);
        actionStack.pushOnMainStack(action);
    }

    public void undoAction(ActionStack actionStack) {
        Action action = actionStack.popMainStack();
        undoAction(action);
        actionStack.pushOnUndoStack(action);
    }

    public void redoAction(ActionStack actionStack) {
        Action action = actionStack.popUndoStack();
        applyAction(action);
        actionStack.pushOnMainStack(action);
    }

    private void applyAction(Action action) {
        World world = Objects.requireNonNull(Bukkit.getWorld(action.worldUUID));

        for (Action.ActionItem actionItem: action) {
            Block block = world.getBlockAt(actionItem.location());
            block.setBlockData(actionItem.to());
        }
    }

    private void undoAction(Action action) {
        World world = Objects.requireNonNull(Bukkit.getWorld(action.worldUUID));

        for (Action.ActionItem actionItem: action) {
            Block block = world.getBlockAt(actionItem.location());
            block.setBlockData(actionItem.from());
        }
    }
}
