package net.codedstingray.worldshaper.operation;

import net.codedstingray.worldshaper.action.Action;
import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.clipboard.Clipboard;
import net.codedstingray.worldshaper.clipboard.ClipboardUtils;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import net.codedstingray.worldshaper.util.world.Direction;
import net.codedstingray.worldshaper.util.world.LocationUtils;
import org.bukkit.Location;
import org.bukkit.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.LinkedList;
import java.util.List;

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

        Clipboard clipboard = Clipboard.createFromArea(world, area,
                LocationUtils.locationToEntityVector(playerLocation),
                LocationUtils.locationToBlockVector(playerLocation));
        clipboard.applyTransform(false, true);

        List<Action.ActionItem> actionItems = new LinkedList<>();
        for (int i = 1; i <= amount; i++) {
            Vector3i offset = direction.baseVector.toMutable()
                    .scale(distance * i)
                    .add(clipboard.getAppliedOriginBlockOffset())
                    .add(LocationUtils.locationToBlockVector(playerLocation));

            clipboard.forEach(positionedBlockData ->
                    ClipboardUtils.createActionItem(world, offset, positionedBlockData).ifPresent(actionItems::add));
        }

        return new Action(world.getUID(), actionItems);
    }
}
