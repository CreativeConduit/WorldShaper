package net.codedstingray.worldshaper.data;

import net.codedstingray.worldshaper.selection.PlayerSelectionMap;
import net.codedstingray.worldshaper.selection.type.SelectionType;
import net.codedstingray.worldshaper.selection.type.SelectionTypeIndefinitePositions;
import net.codedstingray.worldshaper.selection.type.SelectionTypeTwoPositions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A data class that holds various player related data, mostly through the use of maps.
 */
public class PlayerData {

    public final SelectionType DEFAULT_SELECTION_TYPE;

    private final PlayerSelectionMap playerSelectionMap = new PlayerSelectionMap();
    private final Map<String, SelectionType> selectionTypeMap = new HashMap<>();
    private final Map<UUID, SelectionType> playerSelectionTypeMap = new HashMap<>();

    public PlayerData() {
        selectionTypeMap.put(SelectionTypeTwoPositions.NAME, new SelectionTypeTwoPositions());
        selectionTypeMap.put(SelectionTypeIndefinitePositions.NAME, new SelectionTypeIndefinitePositions());

        DEFAULT_SELECTION_TYPE = selectionTypeMap.get(SelectionTypeTwoPositions.NAME);
    }

    public Map<UUID, SelectionType> getPlayerSelectionTypeMap() {
        return playerSelectionTypeMap;
    }

    public SelectionType getSelectionTypeByName(String name) {
        return selectionTypeMap.get(name);
    }

    public PlayerSelectionMap getPlayerSelectionMap() {
        return playerSelectionMap;
    }
}
