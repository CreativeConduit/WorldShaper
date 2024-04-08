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

package net.codedstingray.worldshaper.block.pattern;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import java.util.*;

public class Pattern {

    public static final Pattern ALL_AIR = builder().with(Bukkit.createBlockData(Material.AIR)).build();

    private final List<PatternEntry> entries;
    private final int totalWeight;

    private Pattern(List<PatternEntry> entries, int totalWeight) {
        if (entries.size() == 0) {
            throw new IllegalArgumentException("Pattern must have at least one entry");
        }
        this.entries = new ArrayList<>(entries);
        this.totalWeight = totalWeight;
    }

    public Optional<BlockData> getRandomBlockData() {
        int r = (int) (Math.random() * totalWeight);
        int cumulativeChance = 0;
        for (PatternEntry entry: entries) {
            cumulativeChance += entry.percentage;
            if (r < cumulativeChance) {
                return Optional.of(entry.blockData);
            }
        }
        return Optional.empty();
    }

    private record PatternEntry(int percentage, BlockData blockData) {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final List<PatternEntry> entries = new LinkedList<>();
        private final List<Integer> entryIndicesWithoutPercentage = new LinkedList<>();

        private Builder() {}

        public Builder with(BlockData blockData) {
            Objects.requireNonNull(blockData);

            entryIndicesWithoutPercentage.add(entries.size());
            entries.add(new PatternEntry(0, blockData));
            return this;
        }

        public Builder with(int percentage, BlockData blockData) {
            Objects.requireNonNull(blockData);
            if (percentage < 0) {
                throw new IllegalArgumentException("Percentage must be 0 or above");
            }

            if (percentage == 0) {
                entryIndicesWithoutPercentage.add(entries.size());
            }

            entries.add(new PatternEntry(percentage, blockData));
            return this;
        }

        public Pattern build() {
            if (entries.size() == 0) {
                throw new IllegalStateException("Pattern Builder must have at least one entry");
            }
            int totalWeight = entries.stream().mapToInt(entry -> entry.percentage).sum();
            int numEntriesWithoutPercentage = entryIndicesWithoutPercentage.size();
            if (totalWeight < 100) {
                if (numEntriesWithoutPercentage > 0) {
                    int remainingPercentage = 100 - totalWeight;
                    int percentagePerEntry = Math.max(1, remainingPercentage / numEntriesWithoutPercentage);
                    for (int index: entryIndicesWithoutPercentage) {
                        entries.set(index, new PatternEntry(percentagePerEntry, entries.get(index).blockData));
                    }
                    totalWeight += (percentagePerEntry * numEntriesWithoutPercentage);
                } else {
                    totalWeight = 100;
                }
            }

            return new Pattern(entries, totalWeight);
        }
    }
}
