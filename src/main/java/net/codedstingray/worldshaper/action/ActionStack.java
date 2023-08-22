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

import java.util.Stack;

public class ActionStack {

    private final Stack<Action> performedActions = new Stack<>();
    private final Stack<Action> undoneActions = new Stack<>();


    public void pushOnMainStack(Action action) {
        performedActions.push(action);
    }

    public void pushOnUndoStack(Action action) {
        undoneActions.push(action);
    }

    public void clearMainStack() {
        performedActions.clear();
    }

    public void clearUndoStack() {
        undoneActions.clear();
    }

    public Action popMainStack() {
        return performedActions.pop();
    }

    public Action popUndoStack() {
        return undoneActions.pop();
    }

    public Action peekMainStack() {
        return performedActions.peek();
    }

    public Action peekUndoStack() {
        return undoneActions.peek();
    }

    public boolean isMainStackEmpty() {
        return performedActions.empty();
    }

    public boolean isUndoStackEmpty() {
        return undoneActions.empty();
    }
}
