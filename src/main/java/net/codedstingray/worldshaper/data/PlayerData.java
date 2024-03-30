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

package net.codedstingray.worldshaper.data;

import net.codedstingray.worldshaper.action.ActionStack;
import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.area.AreaFactory;
import net.codedstingray.worldshaper.clipboard.Clipboard;
import net.codedstingray.worldshaper.selection.Selection;
import net.codedstingray.worldshaper.selection.type.SelectionType;

import java.util.Optional;
import java.util.UUID;

/**
 * An object that holds various data for one player.
 */
public class PlayerData {

    //back reference
    private final PluginData pluginData;

    private final Selection selection;
    private SelectionType selectionType;
    private Area area;

    private Clipboard clipboard = null;

    private final ActionStack actionStack;

    private PlayerData(PluginData pluginData, Selection selection, SelectionType selectionType, String areaName, ActionStack actionStack) {
        this.pluginData = pluginData;

        this.selection = selection;
        this.selectionType = selectionType;
        setArea(areaName);

        this.actionStack = actionStack;
    }

    public Selection getSelection() {
        return selection;
    }

    public SelectionType getSelectionType() {
        return selectionType;
    }

    public void setSelectionType(SelectionType selectionType) {
        this.selectionType = selectionType;
    }

    public Area getArea() {
        return area;
    }

    public boolean setArea(String areaName) {
        AreaFactory factory = pluginData.getAreaFactory(areaName);
        if (factory == null) {
            return false;
        }

        area = factory.create();
        area.updateArea(selection);
        return true;
    }

    public Optional<Clipboard> getClipboard() {
        return Optional.ofNullable(clipboard);
    }

    public void setClipboard(Clipboard clipboard) {
        this.clipboard = clipboard;
    }

    public ActionStack getActionStack() {
        return actionStack;
    }

    public static PlayerData create(PluginData pluginData, UUID player) {
        return new PlayerData(
                pluginData,
                new Selection(player),
                pluginData.DEFAULT_SELECTION_TYPE,
                pluginData.DEFAULT_AREA_NAME,
                new ActionStack()
        );
    }
}
