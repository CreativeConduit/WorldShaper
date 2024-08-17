/*
 * WorldShaper, a powerful in-game map editing and terraforming tool for Minecraft.
 * Copyright (C) 2024 CreativeConduit
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

package net.codedstingray.worldshaper.block.property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IntegerProperty extends AbstractProperty<Integer> {

    public IntegerProperty(String name, int min, int max) {
        this(name, createFromMinMax(min, max));
    }

    public IntegerProperty(String name, List<Integer> possibleValues) {
        super(name, possibleValues);
    }

    @Override
    public Integer getValueFor(String string) {
        return Integer.valueOf(string);
    }

    //TODO: extract into utility class
    private static List<Integer> createFromMinMax(int min, int max) {
        List<Integer> possibleValues = new ArrayList<>();

        for (int i = min; i <= max; i++) {
            possibleValues.add(i);
        }

        return Collections.unmodifiableList(possibleValues);
    }
}
