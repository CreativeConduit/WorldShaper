/*
 * WorldShaper, a powerful in-game map editor for Minecraft
 * Copyright (C) 2023-2024 CodedStingray
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

/**
 * Manages {@link Action Actions},
 */
public class ActionController {

    /**
     * Applies a new {@link Action} to the world. This clears the undo-stack in the given {@link ActionStack} and pushes
     * the given action to the main stack.
     *
     * @param actionStack The {@link ActionStack} to apply the action to
     * @param action The {@link Action} to be applied
     */
    public void performAction(ActionStack actionStack, Action action) {
        actionStack.clearUndoStack();
        applyAction(action);
        actionStack.pushOnMainStack(action);
    }

    /**
     * Undoes the last performed {@link Action} in the given {@link ActionStack}. Moves that action from the main stack
     * to the undo-stack.
     *
     * @param actionStack The {@link ActionStack} to undo the action from
     */
    public void undoAction(ActionStack actionStack) {
        Action action = actionStack.popMainStack();
        undoAction(action);
        actionStack.pushOnUndoStack(action);
    }

    /**
     * Redoes the last undone {@link Action} in the given {@link ActionStack}. Moves that action from the undo-stack
     * to the main stack.
     *
     * @param actionStack The {@link ActionStack} to redo the action from
     */
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
