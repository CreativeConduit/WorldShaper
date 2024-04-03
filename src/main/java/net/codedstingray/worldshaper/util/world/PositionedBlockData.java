package net.codedstingray.worldshaper.util.world;

import net.codedstingray.worldshaper.util.vector.vector3.Vector3ii;
import org.bukkit.block.data.BlockData;

public record PositionedBlockData(Vector3ii position, BlockData blockData) {

}
