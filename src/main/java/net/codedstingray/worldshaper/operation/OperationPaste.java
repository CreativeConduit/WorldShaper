package net.codedstingray.worldshaper.operation;

import net.codedstingray.worldshaper.action.Action;
import net.codedstingray.worldshaper.action.Action.ActionItem;
import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.clipboard.Clipboard;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3ii;
import net.codedstingray.worldshaper.util.world.LocationUtils;
import net.codedstingray.worldshaper.util.world.PositionedBlockData;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
        List<ActionItem> actionItems = new LinkedList<>();
        Vector3i offset = clipboard.getOriginPosition().add(LocationUtils.locationToBlockVector(playerLocation));

        clipboard.forEach(positionedBlockData ->
                createActionItem(world, offset, positionedBlockData).ifPresent(actionItems::add));

        return new Action(world.getUID(), actionItems);
    }

    private static Optional<ActionItem> createActionItem(World world, Vector3i offset, PositionedBlockData positionedBlockData) {
        BlockData blockDataTo = positionedBlockData.blockData();
        Vector3ii position = positionedBlockData.position();

        if (blockDataTo == null) {
            return Optional.empty();
        }

        Location blockLocation = LocationUtils.vectorToLocation(position.add(offset), world);
        BlockData blockDataFrom = world.getBlockData(blockLocation);

        return Optional.of(new ActionItem(blockLocation, blockDataFrom, blockDataTo));
    }
}
