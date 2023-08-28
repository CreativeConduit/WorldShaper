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

package net.codedstingray.worldshaper.block.mask;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Mask {

    public static final Mask MASK_ALL = new MaskAll();

    private final List<MaskEntry> entries;

    private Mask(List<MaskEntry> entries) {
        this.entries = entries;
    }

    public boolean matches(Location location) {
        for (MaskEntry entry: entries) {
            if (entry.matches(location)) {
                return true;
            }
        }
        return false;
    }

    private record MaskEntry(Material material, int blockOffset, boolean not) {
        private boolean matches(Location location) {
            Location checkLocation = blockOffset == 0 ? location : location.add(0, blockOffset, 0);

            World world = Objects.requireNonNull(location.getWorld());
            boolean materialMatches = world.getBlockAt(checkLocation).getType() == material;
            return not != materialMatches;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final List<MaskEntry> entries = new LinkedList<>();

        private Builder() {}

        public Builder with(Material material, int blockOffset, boolean not) {
            entries.add(new MaskEntry(material, blockOffset, not));
            return this;
        }

        public Mask build() {
            return new Mask(entries);
        }
    }

    private static class MaskAll extends Mask {
        private MaskAll() {
            super(null);
        }

        @Override
        public boolean matches(Location location) {
            return true;
        }
    }
}
