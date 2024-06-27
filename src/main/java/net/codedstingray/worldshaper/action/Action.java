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

import org.bukkit.Location;
import org.bukkit.block.data.BlockData;

import java.util.*;

/**
 * An Action is a collection of ActionItems.<br>
 * An {@link ActionItem} is used to track a change on a single block.<br>
 * Thus, an Action is used to track changes over a set of blocks. These are used by the {@link ActionController} to
 * apply a change to a world or to undo a change.
 */
public class Action implements Iterable<Action.ActionItem> {

    public final UUID worldUUID;
    private final List<ActionItem> actionItems;

    public Action(UUID worldUUID, List<ActionItem> actionItems) {
        this.worldUUID = worldUUID;
        this.actionItems = Collections.unmodifiableList(actionItems);
        verifyActionItems();
    }

    private void verifyActionItems() {
        for (ActionItem actionItem: actionItems) {
            if (actionItem.location.getWorld() == null || !worldUUID.equals(actionItem.location.getWorld().getUID())) {
                throw new IllegalArgumentException("Action Item location did not match action world; Action Item: " +
                        actionItem + "; world uuid: " + worldUUID);
            }
        }
    }

    @Override
    public Iterator<ActionItem> iterator() {
        return actionItems.iterator();
    }

    /**
     * Represents a block change at a specific location.
     *
     * @param location The location of the change
     * @param from The {@link BlockData} before the change
     * @param to The {@link BlockData} after the change
     */
    public record ActionItem(Location location, BlockData from, BlockData to) {
        @Override
        public String toString() {
            return "ActionItem {Location: " + location + "; from: " + from + "; to: " + to + "}";
        }
    }
}
