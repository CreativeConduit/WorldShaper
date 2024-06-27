package net.codedstingray.worldshaper.operation;

import net.codedstingray.worldshaper.action.Action;
import net.codedstingray.worldshaper.action.Action.ActionItem;
import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.clipboard.Clipboard;
import net.codedstingray.worldshaper.clipboard.ClipboardUtils;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import net.codedstingray.worldshaper.util.world.LocationUtils;
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
        Clipboard clipboard = Clipboard.createFromArea(world, area,
                LocationUtils.locationToEntityVector(playerLocation),
                LocationUtils.locationToBlockVector(playerLocation));
        clipboard.applyTransform(false, true);

        List<ActionItem> actionItems = new LinkedList<>();
        Set<Location> pasteLocations = new HashSet<>();
        Vector3i offset = clipboard.getAppliedOriginBlockOffset().add(LocationUtils.locationToBlockVector(originLocation));

        clipboard.forEach(positionedBlockData -> ClipboardUtils.createActionItem(world, offset, positionedBlockData).ifPresent(
                actionItem -> {
                    actionItems.add(actionItem);
                    pasteLocations.add(actionItem.location());
                }));

        area.forEach(position -> cutBlockFromArea(world, pasteLocations, position).ifPresent(actionItems::add));

        return new Action(world.getUID(), actionItems);
    }

    private Optional<ActionItem> cutBlockFromArea(World world, Set<Location> pasteLocations, Vector3i position) {
        Location location = LocationUtils.vectorToLocation(position, world);
        if (pasteLocations.contains(location)) {
            return Optional.empty();
        }
        Block block = world.getBlockAt(location);
        BlockData from = block.getBlockData();

        return Optional.of(new ActionItem(location, from, Bukkit.createBlockData(Material.AIR)));
    }
}
