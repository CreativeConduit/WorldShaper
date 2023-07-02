package net.codedstingray.worldshaper.data;

import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.area.AreaFactory;
import net.codedstingray.worldshaper.area.CuboidArea;
import net.codedstingray.worldshaper.area.CuboidAreaFactory;
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
    public final String DEFAULT_AREA_NAME = CuboidArea.NAME;

    private final PlayerSelectionMap playerSelectionMap = new PlayerSelectionMap();
    private final Map<String, SelectionType> selectionTypeMap = new HashMap<>();
    private final Map<UUID, SelectionType> playerSelectionTypeMap = new HashMap<>();

    private final Map<String, AreaFactory> areaFactoriesMap = new HashMap<>();
    private final Map<UUID, Area> playerAreaMap = new HashMap<>();

    public PlayerData() {
        selectionTypeMap.put(SelectionTypeTwoPositions.NAME, new SelectionTypeTwoPositions());
        selectionTypeMap.put(SelectionTypeIndefinitePositions.NAME, new SelectionTypeIndefinitePositions());

        DEFAULT_SELECTION_TYPE = selectionTypeMap.get(SelectionTypeTwoPositions.NAME);


        areaFactoriesMap.put(CuboidArea.NAME, new CuboidAreaFactory());
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

    public Area getAreaForPlayer(UUID player) {
        return playerAreaMap.get(player);
    }

    public boolean setAreaForPlayer(UUID player, String areaName) {
        AreaFactory factory = areaFactoriesMap.get(areaName);
        if (factory == null) {
            return false;
        }

        Area area = factory.create();
        playerAreaMap.put(player, area);
        area.updateArea(playerSelectionMap.getSelection(player));
        return true;
    }
}
