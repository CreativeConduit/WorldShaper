package net.codedstingray.worldshaper.operation;

import net.codedstingray.worldshaper.action.Action;
import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.clipboard.Clipboard;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3ii;
import net.codedstingray.worldshaper.util.world.Direction;
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
public class OperationStack implements Operation {

    private final Location playerLocation;
    private final int amount;
    private final Direction direction;

    public OperationStack(Location playerLocation, int amount, Direction direction) {
        this.playerLocation = playerLocation;
        this.amount = amount;
        this.direction = direction;
    }

    @Override
    public Action performOperation(Area area, World world) {
        int distance = switch (direction) {
            case EAST, WEST -> area.getBoundingBixSize().x;
            case UP, DOWN -> area.getBoundingBixSize().y;
            case NORTH, SOUTH -> area.getBoundingBixSize().z;
        };

        Clipboard clipboard = Clipboard.createFromArea(world, area, LocationUtils.locationToBlockVector(playerLocation));

        List<Action.ActionItem> actionItems = new LinkedList<>();
        for (int i = 1; i <= amount; i++) {
            Vector3i offset = direction.baseVector.toMutable()
                    .scale(distance * i)
                    .add(clipboard.getOriginPosition())
                    .add(LocationUtils.locationToBlockVector(playerLocation));
            clipboard.forEach(positionedBlockData ->
                    createActionItem(world, offset, positionedBlockData).ifPresent(actionItems::add));
        }

        return new Action(world.getUID(), actionItems);
    }

    private static Optional<Action.ActionItem> createActionItem(World world, Vector3i offset, PositionedBlockData positionedBlockData) {
        BlockData blockDataTo = positionedBlockData.blockData();
        Vector3ii position = positionedBlockData.position();

        if (blockDataTo == null) {
            return Optional.empty();
        }

        Location blockLocation = LocationUtils.vectorToLocation(position.add(offset), world);
        BlockData blockDataFrom = world.getBlockData(blockLocation);

        return Optional.of(new Action.ActionItem(blockLocation, blockDataFrom, blockDataTo));
    }
}
