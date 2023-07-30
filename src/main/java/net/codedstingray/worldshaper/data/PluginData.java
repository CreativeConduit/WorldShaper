package net.codedstingray.worldshaper.data;

import net.codedstingray.worldshaper.area.AreaFactory;
import net.codedstingray.worldshaper.area.CuboidArea;
import net.codedstingray.worldshaper.area.CuboidAreaFactory;
import net.codedstingray.worldshaper.selection.type.SelectionType;
import net.codedstingray.worldshaper.selection.type.SelectionTypeIndefinitePositions;
import net.codedstingray.worldshaper.selection.type.SelectionTypeTwoPositions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
        selectionTypesByName.put(SelectionTypeTwoPositions.NAME, new SelectionTypeTwoPositions());
        selectionTypesByName.put(SelectionTypeIndefinitePositions.NAME, new SelectionTypeIndefinitePositions());

        DEFAULT_SELECTION_TYPE = selectionTypesByName.get(SelectionTypeTwoPositions.NAME);


        areaFactoriesMap.put(CuboidArea.NAME, new CuboidAreaFactory());
    }

    public SelectionType getSelectionTypeByName(String name) {
        return selectionTypesByName.get(name);
    }

    public AreaFactory getAreaFactory(String areaName) {
        return areaFactoriesMap.get(areaName);
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
