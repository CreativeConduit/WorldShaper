package net.codedstingray.worldshaper.operation;

import net.codedstingray.worldshaper.action.Action;
import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.clipboard.Clipboard;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3ii;
import net.codedstingray.worldshaper.util.world.LocationUtils;
import net.codedstingray.worldshaper.util.world.PositionedBlockData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
public class OperationMove implements Operation {

    private final Location playerLocation;
    private final Location originLocation;

    public OperationMove(Location playerLocation, Vector3i moveVector) {
        this.playerLocation = playerLocation;
        originLocation = LocationUtils.vectorToLocation(
                LocationUtils.locationToBlockVector(playerLocation).add(moveVector),
                playerLocation.getWorld());
    }

    @Override
    public Action performOperation(Area area, World world) {
        Clipboard clipboard = Clipboard.createFromArea(world, area, LocationUtils.locationToBlockVector(playerLocation));

        List<Action.ActionItem> actionItems = new LinkedList<>();
        Set<Location> pasteLocations = new HashSet<>();
        Vector3i offset = clipboard.getOriginPosition().add(LocationUtils.locationToBlockVector(originLocation));

        clipboard.forEach(positionedBlockData -> createActionItem(world, offset, positionedBlockData).ifPresent(
                actionItem -> {
                    actionItems.add(actionItem);
                    pasteLocations.add(actionItem.location());
                }));

        for (Vector3i position: area) {
            Location location = LocationUtils.vectorToLocation(position, world);
            if (pasteLocations.contains(location)) {
                continue;
            }
            Block block = world.getBlockAt(location);
            BlockData from = block.getBlockData();

            Action.ActionItem actionItem = new Action.ActionItem(location, from, Bukkit.createBlockData(Material.AIR));
            actionItems.add(actionItem);
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
