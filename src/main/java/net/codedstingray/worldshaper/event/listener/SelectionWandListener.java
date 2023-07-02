package net.codedstingray.worldshaper.event.listener;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.items.SelectionWand;
import net.codedstingray.worldshaper.selection.type.SelectionType;
import net.codedstingray.worldshaper.util.chat.ChatFormattingUtils;
import net.codedstingray.worldshaper.util.world.LocationUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.joml.Vector3i;

import java.util.Objects;

public class SelectionWandListener implements Listener {

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Action action = event.getAction();
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        if(item == null || clickedBlock == null || !item.isSimilar(SelectionWand.SELECTION_WAND)) {
            return;
        }

        SelectionType selectionType = Objects.requireNonNullElse(
                WorldShaper.getInstance().getPlayerSelectionTypeMap().get(player.getUniqueId()),
                WorldShaper.getInstance().getDefaultSelectionType()
        );

        Vector3i clickedPosition = LocationUtils.locationToBlockVector(clickedBlock.getLocation());

        int index;
        switch (action) {
            case LEFT_CLICK_BLOCK -> index = selectionType.onLeftClick(player, clickedPosition);
            case RIGHT_CLICK_BLOCK -> index = selectionType.onRightClick(player, clickedPosition);
            default -> {
                return;
            }
        }

        ChatFormattingUtils.sendWorldShaperMessage(player, ChatFormattingUtils.positionSetMessage(index, clickedPosition));

        event.setCancelled(true);
    }
}
