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

package net.codedstingray.worldshaper.area;

import net.codedstingray.worldshaper.WorldShaper;
import net.codedstingray.worldshaper.selection.Selection;
import net.codedstingray.worldshaper.selection.type.SelectionType;
import net.codedstingray.worldshaper.selection.type.SelectionTypeTwoPositions;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3ii;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3im;
import net.codedstingray.worldshaper.util.world.Direction;
import net.codedstingray.worldshaper.util.vector.VectorUtils;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Iterator;
import java.util.NoSuchElementException;

@ParametersAreNonnullByDefault
public class CuboidArea implements Area {

    public static final String NAME = "cuboid";

    private Vector3im minPos;
    private Vector3im maxPos;

    private boolean isValid;

    @Override
    @Nonnull
    public String getName() {
        return NAME;
    }

    @Override
    @Nonnull
    public SelectionType getDefaultSelectionType() {
        return WorldShaper.getInstance().getPluginData().getSelectionTypeByName(SelectionTypeTwoPositions.NAME);
    }

    @Override
    public boolean isValid() {
        return isValid;
    }

    @Override
    public void updateArea(Selection selection) {
        Vector3i pos1 = selection.getControlPosition(0);
        Vector3i pos2 = selection.getControlPosition(1);

        if (pos1 != null && pos2 != null) {
            minPos = new Vector3im(
                    Math.min(pos1.getX(), pos2.getX()),
                    Math.min(pos1.getY(), pos2.getY()),
                    Math.min(pos1.getZ(), pos2.getZ())
            );
            maxPos = new Vector3im(
                    Math.max(pos1.getX(), pos2.getX()),
                    Math.max(pos1.getY(), pos2.getY()),
                    Math.max(pos1.getZ(), pos2.getZ())
            );
            isValid = true;
        } else {
            minPos = null;
            maxPos = null;
            isValid = false;
        }
    }

    @Override
    public void move(Direction direction, int distance) {
        minPos.add(direction.baseVector.scale(distance));
        maxPos.add(direction.baseVector.scale(distance));
    }

    @Override
    public void expand(Direction direction, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot expand area by a negative amount");
        }

        switch (direction) {
            case NORTH -> minPos.add(0, 0, -amount);
            case SOUTH -> maxPos.add(0, 0, amount);
            case EAST -> maxPos.add(amount, 0, 0);
            case WEST -> minPos.add(-amount, 0, 0);
            case UP -> maxPos.add(0, amount, 0);
            case DOWN -> minPos.add(0, -amount, 0);
            default -> {
            }
        }
    }

    @Override
    public void retract(Direction direction, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot retract area by a negative amount");
        }

        switch (direction) {
            case NORTH -> {
                minPos.add(0, 0, amount);
                if (minPos.z > maxPos.z) {
                    minPos.z = maxPos.z;
                }
            }
            case SOUTH -> {
                maxPos.add(0, 0, -amount);
                if (maxPos.z < minPos.z) {
                    maxPos.z = minPos.z;
                }
            }
            case EAST -> {
                maxPos.add(-amount, 0, 0);
                if (maxPos.x < minPos.x) {
                    maxPos.x = minPos.x;
                }
            }
            case WEST -> {
                minPos.add(amount, 0, 0);
                if (minPos.x > maxPos.x) {
                    minPos.x = maxPos.x;
                }
            }
            case UP -> {
                maxPos.add(0, -amount, 0);
                if (maxPos.y < minPos.y) {
                    maxPos.y = minPos.y;
                }
            }
            case DOWN -> {
                minPos.add(0, amount, 0);
                if (minPos.y > maxPos.y) {
                    minPos.y = maxPos.y;
                }
            }
            default -> {
            }
        }
    }

    @Override
    public Iterator<Vector3i> iterator() {
        return new Iterator<>() {
            private final Vector3im current = new Vector3im(minPos);

            @Override
            public boolean hasNext() {
                return current.y <= maxPos.y;
            }

            @Override
            public Vector3i next() {
                if(!hasNext()) throw new NoSuchElementException();
                Vector3i result = new Vector3ii(current);

                //move pointer
                current.add(VectorUtils.BASE_X);
                if(current.x > maxPos.x) {
                    current.x = minPos.x;
                    current.add(VectorUtils.BASE_Z);
                    if(current.z > maxPos.z) {
                        current.z = minPos.z;
                        current.add(VectorUtils.BASE_Y);
                    }
                }

                return result;
            }
        };
    }
}
