package net.codedstingray.worldshaper.block.pattern;

import org.bukkit.block.data.BlockData;

import java.util.*;

public class Pattern {

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
                return Optional.of(entries.get(r).blockData);
            }
        }
        return Optional.empty();
    }

    public static Builder builder() {
        return new Builder();
    }

    private record PatternEntry(int percentage, BlockData blockData) {
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
            if (percentage <= 0) {
                throw new IllegalArgumentException("Percentage must be above 0");
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
