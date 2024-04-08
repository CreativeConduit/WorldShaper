package net.codedstingray.worldshaper.util.world;

import net.codedstingray.worldshaper.util.vector.vector3.Vector3ii;
import org.bukkit.block.data.BlockData;

/**
 * Associates a {@link BlockData} instance with a grid position.
 *
 * @param position The grid position
 * @param blockData The BlockData instance
 */
public record PositionedBlockData(Vector3ii position, BlockData blockData) {
}
