package net.codedstingray.worldshaper.data;

import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.area.AreaFactory;
import net.codedstingray.worldshaper.selection.Selection;
import net.codedstingray.worldshaper.selection.type.SelectionType;

import java.util.UUID;

/**
 * An object that holds various data for one player.
 */
public class PlayerData {

    private final PluginData pluginData;

    private final Selection selection;
    private SelectionType selectionType;
    private Area area;

    private PlayerData(PluginData pluginData, Selection selection, SelectionType selectionType, String areaName) {
        this.pluginData = pluginData;

        this.selection = selection;
        this.selectionType = selectionType;
        setArea(areaName);
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

    public static PlayerData create(PluginData pluginData, UUID player) {
        return new PlayerData(
                pluginData,
                new Selection(player),
                pluginData.DEFAULT_SELECTION_TYPE,
                pluginData.DEFAULT_AREA_NAME);
    }
}
