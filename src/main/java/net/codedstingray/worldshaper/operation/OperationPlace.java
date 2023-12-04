/*
 * WorldShaper: a powerful in-game map editor for Minecraft
 * Copyright (C) 2023 CodedStingray
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.codedstingray.worldshaper.operation;

import net.codedstingray.worldshaper.action.Action;
import net.codedstingray.worldshaper.area.Area;
import net.codedstingray.worldshaper.block.mask.Mask;
import net.codedstingray.worldshaper.block.pattern.Pattern;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3ii;
import net.codedstingray.worldshaper.util.world.LocationUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@ParametersAreNonnullByDefault
public class OperationPlace implements Operation {

    private final Pattern pattern;
    private final Mask mask;
    private final Modifier modifier;

    public OperationPlace(Pattern pattern) {
        this(pattern, Mask.MASK_ALL, Modifier.ALL);
    }

    public OperationPlace(Pattern pattern, Mask mask) {
        this(pattern, mask, Modifier.ALL);
    }

    public OperationPlace(Pattern pattern, Modifier modifier) {
        this(pattern, Mask.MASK_ALL, modifier);
    }

    public OperationPlace(Pattern pattern, Mask mask, Modifier modifier) {
        this.pattern = pattern;
        this.mask = mask;
        this.modifier = modifier;
    }

    @Override
    public Action performOperation(Area area, World world) {
        List<Action.ActionItem> actionItems = new LinkedList<>();
        for(Vector3i position: area) {
            if (!modifier.isBlockInModification(area, position) ||
                    !mask.matches(LocationUtils.vectorToLocation(position, world))) {
                continue;
            }

            Optional<BlockData> toOpt = pattern.getRandomBlockData();
            if (toOpt.isEmpty()) {
                continue;
            }

            Location location = LocationUtils.vectorToLocation(position, world);
            Block block = world.getBlockAt(location);
            BlockData from = block.getBlockData();
            BlockData to = toOpt.get();

            Action.ActionItem actionItem = new Action.ActionItem(location, from, to);
            actionItems.add(actionItem);
        }

        return new Action(world.getUID(), actionItems);
    }

    public enum Modifier {
        ALL((area, position) -> true),
        WALLS((area, position) -> {
            Vector3ii positionImmutable = position.toImmutable();
            return !(area.isInArea(positionImmutable.add(1, 0, 0)) &&
                     area.isInArea(positionImmutable.add(-1, 0, 0)) &&
                     area.isInArea(positionImmutable.add(0, 0, 1)) &&
                     area.isInArea(positionImmutable.add(0, 0, -1)));
        }),
        CEILING((area, position) -> {
            Vector3ii positionImmutable = position.toImmutable();
            return !area.isInArea(positionImmutable.add(0, 1, 0));
        }),
        FLOOR((area, position) -> {
            Vector3ii positionImmutable = position.toImmutable();
            return !area.isInArea(positionImmutable.add(0, -1, 0));
        }),
        HULL((area,  position) -> {
            Vector3ii positionImmutable = position.toImmutable();
            return !(area.isInArea(positionImmutable.add(1, 0, 0)) &&
                    area.isInArea(positionImmutable.add(-1, 0, 0)) &&
                    area.isInArea(positionImmutable.add(0, 1, 0)) &&
                    area.isInArea(positionImmutable.add(0, -1, 0)) &&
                    area.isInArea(positionImmutable.add(0, 0, 1)) &&
                    area.isInArea(positionImmutable.add(0, 0, -1)));
        });


        private final BiFunction<Area, Vector3i, Boolean> conditionFunction;

        Modifier(BiFunction<Area, Vector3i, Boolean> conditionFunction) {
            this.conditionFunction = conditionFunction;
        }

        public boolean isBlockInModification(Area area, Vector3i position) {
            return conditionFunction.apply(area, position);
        }
    }
}
