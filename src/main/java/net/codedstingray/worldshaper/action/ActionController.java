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
import java.util.Stack;

public class ActionController {

    private final Stack<Action> performedActions = new Stack<>();
    private final Stack<Action> undoneActions = new Stack<>();

    public void performAction(Action action) {
        undoneActions.clear();
        applyAction(action);
        performedActions.push(action);
    }

    public void undoAction() {
        Action action = performedActions.pop();
        undoAction(action);
        undoneActions.push(action);
    }

    public void redoAction() {
        Action action = undoneActions.pop();
        applyAction(action);
        performedActions.push(action);
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

    public boolean isMainStackEmpty() {
        return performedActions.empty();
    }

    public Action peekMainStack() {
        return performedActions.peek();
    }

    public boolean isUndoStackEmpty() {
        return undoneActions.empty();
    }

    public Action peekUndoStack() {
        return undoneActions.peek();
    }
}
