/*
 * WorldShaper, a powerful in-game map editing and terraforming tool for Minecraft.
 * Copyright (C) 2023-2024 CreativeConduit
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

import net.codedstingray.worldshaper.selection.Selection;
import net.codedstingray.worldshaper.selection.type.SelectionType;
import net.codedstingray.worldshaper.util.vector.VectorUtils;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3i;
import net.codedstingray.worldshaper.util.vector.vector3.Vector3ii;
import net.codedstingray.worldshaper.util.world.Direction;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An Area is a collection of blocks based on a {@link Selection}. The Area gets calculated based on the blocks in the
 * Selection and typically represents a collection of blocks that is to be modified through WorldShaper
 * {@link net.codedstingray.worldshaper.operation.Operation Operations}.
 */
@ParametersAreNonnullByDefault
public interface Area extends Iterable<Vector3i> {

    /**
     * The name of the area. This is used for commands like /area.
     *
     * @return The name of the area
     */
    @Nonnull
    String getName();

    /**
     * The default {@link SelectionType} for this area. Used primarily to change the selection type based on the area
     * when a player changes their area.
     *
     * @return This Area's default {@link SelectionType}
     */
    @Nonnull
    SelectionType getDefaultSelectionType();


    /**
     * Returns if this Area is valid or not. An area might have certain conditions like a minimum number of points in
     * its {@link Selection}.
     *
     * @return {@code true} if this Area is valid, {@code false} otherwise
     */
    boolean isValid();

    /**
     * Recalculates the block collection represented by this area based on the given Selection.
     *
     * @param selection The {@link Selection} upon which this Area should be recalculated
     */
    void updateArea(Selection selection);

    /**
     * Returns if a given block position is withing the area.
     *
     * @param position The position to be checked
     * @return {@code true} if the position is contained within the area, {@code false} otherwise
     */
    boolean isInArea(Vector3i position);


    /**
     * Returns the min-position of the area's bounding box. The bounding box is important for commands like /stack and
     * /copy.
     *
     * @return The min-position of the bounding box
     */
    Vector3ii getBoundingBoxMin();

    /**
     * Returns the max-position of the area's bounding box. The bounding box is important for commands like /stack and
     * /copy.
     *
     * @return The max-position of the bounding box
     */

    Vector3ii getBoundingBoxMax();
    /**
     * Returns the size of the area's bounding box. The bounding box is important for commands like /stack and
     * /copy.
     *
     * @return The size of the bounding box, represented as a vector that holds the size in the x-, y- and z-axis
     */
    default Vector3ii getBoundingBixSize() {
        return getBoundingBoxMax().toMutable().sub(getBoundingBoxMin()).add(VectorUtils.ONE).toImmutable();
    }


    /**
     * Moves the area in the given {@link Direction} by the given distance.
     *
     * @param direction The {@link Direction} to move in
     * @param distance The distance by which to move
     */
    void move(Direction direction, int distance);

    /**
     * Expands the area in the given {@link Direction} by the given amount.
     *
     * @param direction The {@link Direction} to expand in
     * @param amount The amount by which to expand
     */
    void expand(Direction direction, int amount);

    /**
     * Retracts the area in the given {@link Direction} by the given amount.
     *
     * @param direction The {@link Direction} to retract in
     * @param amount The amount by which to retract
     */
    void retract(Direction direction, int amount);
}
