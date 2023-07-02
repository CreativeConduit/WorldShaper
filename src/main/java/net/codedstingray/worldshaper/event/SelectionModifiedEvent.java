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

    public SelectionModifiedEvent(Selection selection, UUID player) {
        this.selection = selection;
        this.player = player;
    }

    public Selection getSelection() {
        return selection;
    }

    public UUID getPlayerUUID() {
        return player;
    }
}
