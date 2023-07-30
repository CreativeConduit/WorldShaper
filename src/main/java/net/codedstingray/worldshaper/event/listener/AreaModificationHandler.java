package net.codedstingray.worldshaper.event.listener;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.data.PlayerData;
import net.codedstingray.worldshaper.event.SelectionModifiedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AreaModificationHandler implements Listener {

    @EventHandler
    public void onSelectionModified(SelectionModifiedEvent event) {
        PlayerData playerData = WorldShaper.getInstance().getPluginData().getPlayerDataForPlayer(event.getPlayerUUID());
        Area area = playerData.getArea();
        area.updateArea(event.getSelection());
    }
}
