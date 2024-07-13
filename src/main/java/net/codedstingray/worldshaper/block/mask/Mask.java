/*
 * WorldShaper, a powerful in-game map editing addon for WorldEdit
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

package net.codedstingray.worldshaper.block.mask;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * A Mask is used to match blocks to certain conditions. In practice, it is a list of {@link MaskEntry MaskEntries}, and
 * a block matches this mask if it matches any of those mask entries.
 */
public class Mask {

    /**
     * Special mask that matches all blocks.
     */
    public static final Mask MASK_ALL = new MaskAll();

    private final List<MaskEntry> entries;

    private Mask(List<MaskEntry> entries) {
        this.entries = entries;
    }

    /**
     * Checks if the block at the current location matches this mask.
     *
     * @param location The location to check
     * @return {@code true} if the block at the given location matches this mask, {@code false} otherwise
     */
    public boolean matches(Location location) {
        for (MaskEntry entry: entries) {
            if (entry.matches(location)) {
                return true;
            }
        }
        return false;
    }

    /**
     * A mask entry. This is used to match a block with a single {@link Material}, and multiple are used within a mask.
     *
     * @param material The {@link Material} to match
     * @param blockOffset The block offset; currently this is limited to being the y-offset
     * @param not Whether this entry is inverted or not; If this is set to true, this entry will match anything *except*
     *            the given material
     */
    private record MaskEntry(Material material, int blockOffset, boolean not) {
        private boolean matches(Location location) {
            Location checkLocation = blockOffset == 0 ? location : location.add(0, blockOffset, 0);

            World world = Objects.requireNonNull(location.getWorld());
            boolean materialMatches = world.getBlockAt(checkLocation).getType() == material;
            return not != materialMatches;
        }
    }

    /**
     * Creates a new {@link Builder} instance.
     *
     * @return The new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A builder that produces a new {@link Mask}.
     */
    public static class Builder {

        private final List<MaskEntry> entries = new LinkedList<>();

        private Builder() {}

        /**
         * Creates a new {@link MaskEntry} instance with the given parameters and adds it to the mask entry list.
         *
         * @param material The {@link Material} to match
         * @param blockOffset The block offset; currently this is limited to being the y-offset
         * @param not Whether this entry is inverted or not; If this is set to true, this entry will match anything *except*
         *            the given material
         * @return This builder instance for chaining
         */
        public Builder with(Material material, int blockOffset, boolean not) {
            entries.add(new MaskEntry(material, blockOffset, not));
            return this;
        }

        /**
         * Creates the Mask instance.
         *
         * @return The created {@link Mask}
         */
        public Mask build() {
            return new Mask(entries);
        }
    }

    /**
     * Special subclass of {@link Mask} that matches all blocks.
     */
    private static class MaskAll extends Mask {
        private MaskAll() {
            super(null);
        }

        /**
         * Overrides the standard {@link Mask#matches(Location)} method to simply return true. This makes this mask match
         * all blocks and also makes this check very efficient.
         *
         * @param location The location to check
         * @return {@code true} regardless of the Block checked, making this mask match all blocks
         */
        @Override
        public boolean matches(Location location) {
            return true;
        }
    }
}
