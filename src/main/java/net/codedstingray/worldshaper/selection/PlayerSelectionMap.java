package net.codedstingray.worldshaper.selection;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A Mapping class that maps players to selections.
 */
@ParametersAreNonnullByDefault
public class PlayerSelectionMap {
    private final Map<UUID, Selection> playerMappedSelections = new HashMap<>();

    /**
     * Adds a new {@link Selection} for the given player.
     *
     * @param player The player which shall be mapped to a new selection
     */
    public void addNewPlayerSelection(UUID player) {
        playerMappedSelections.put(player, new Selection());
    }

    /**
     * Gets the {@link Selection} the given player is mapped to.
     *
     * @param player The player for which to get the selection
     * @return The selection the given player is mapped to
     */
    public Selection getSelection(UUID player) {
        return playerMappedSelections.get(player);
    }
}
