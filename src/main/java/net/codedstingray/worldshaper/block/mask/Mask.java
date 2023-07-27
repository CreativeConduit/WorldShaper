package net.codedstingray.worldshaper.block.mask;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Mask {

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
}
