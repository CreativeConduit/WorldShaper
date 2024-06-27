package net.codedstingray.worldshaper.operation;

import net.codedstingray.worldshaper.action.Action;
import net.codedstingray.worldshaper.action.Action.ActionItem;
import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.clipboard.Clipboard;
import net.codedstingray.worldshaper.clipboard.ClipboardUtils;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import net.codedstingray.worldshaper.util.world.LocationUtils;
import org.bukkit.Location;
import org.bukkit.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.LinkedList;
import java.util.List;

@ParametersAreNonnullByDefault
public class OperationPaste implements Operation {

    private final Clipboard clipboard;
    private final Location playerLocation;

    public OperationPaste(Clipboard clipboard, Location playerLocation) {
        this.clipboard = clipboard;
        this.playerLocation = playerLocation;
    }

    @Override
    public Action performOperation(Area area, World world) {
        clipboard.applyTransform();

        List<ActionItem> actionItems = new LinkedList<>();
        Vector3i offset = clipboard.getAppliedOriginBlockOffset().add(LocationUtils.locationToBlockVector(playerLocation));

        clipboard.forEach(positionedBlockData ->
                ClipboardUtils.createActionItem(world, offset, positionedBlockData).ifPresent(actionItems::add));

        return new Action(world.getUID(), actionItems);
    }
}
