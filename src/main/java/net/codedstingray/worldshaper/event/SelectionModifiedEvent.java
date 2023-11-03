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

package net.codedstingray.worldshaper.event;
import net.codedstingray.worldshaper.selection.Selection;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

@ParametersAreNonnullByDefault
public class SelectionModifiedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    private final Selection selection;
    private final UUID player;
    private final boolean didChange;

    public SelectionModifiedEvent(Selection selection, UUID player, boolean didChange) {
        this.selection = selection;
        this.player = player;
        this.didChange = didChange;
    }

    public Selection getSelection() {
        return selection;
    }

    public UUID getPlayerUUID() {
        return player;
    }

    public boolean didChange() {
        return didChange;
    }
}
