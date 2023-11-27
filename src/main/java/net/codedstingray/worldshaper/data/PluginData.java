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

import net.codedstingray.worldshaper.area.*;
import net.codedstingray.worldshaper.selection.type.SelectionType;
import net.codedstingray.worldshaper.selection.type.SelectionTypeIndefinitePositions;
import net.codedstingray.worldshaper.selection.type.SelectionTypeTwoPositions;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A data class that holds various plugin related data, mostly through the use of maps.
 */
public class PluginData {

    public final SelectionType DEFAULT_SELECTION_TYPE;
    public final String DEFAULT_AREA_NAME = CuboidArea.NAME;

    private final Map<String, SelectionType> selectionTypesByName = new HashMap<>();
    private final Map<String, AreaFactory> areaFactoriesMap = new HashMap<>();

    private final Map<UUID, PlayerData> playerDataMap = new HashMap<>();

    public PluginData() {
        selectionTypesByName.put(SelectionTypeTwoPositions.NAME.toLowerCase(), new SelectionTypeTwoPositions());
        selectionTypesByName.put(SelectionTypeIndefinitePositions.NAME.toLowerCase(), new SelectionTypeIndefinitePositions());

        DEFAULT_SELECTION_TYPE = selectionTypesByName.get(SelectionTypeTwoPositions.NAME.toLowerCase());


        areaFactoriesMap.put(CuboidArea.NAME.toLowerCase(), new CuboidAreaFactory());
        areaFactoriesMap.put(PointsArea.NAME.toLowerCase(), new PointsAreaFactory());
    }

    public SelectionType getSelectionTypeByName(String name) {
        return selectionTypesByName.get(name.toLowerCase());
    }

    public AreaFactory getAreaFactory(String areaName) {
        return areaFactoriesMap.get(areaName.toLowerCase());
    }

    public Set<String> getAllRegisteredSelectionTypes() {
        return selectionTypesByName.values().stream().map(SelectionType::getName).collect(Collectors.toSet());
    }

    public Set<String> getAllRegisteredAreaTypes() {
        return areaFactoriesMap.keySet();
    }

    public PlayerData getPlayerDataForPlayer(UUID player) {
        return playerDataMap.get(player);
    }

    public void createPlayerDataIfNotPresent(UUID player) {
        playerDataMap.computeIfAbsent(
                player,
                k -> PlayerData.create(this, player)
        );
    }
}
