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

import java.util.List;

public interface BlockProperty<T> {

    /**
     * Returns the name of this property.
     *
     * @return The name of this Property
     */
    String getName();

    /**
     * Returns the possible values this property can take.
     *
     * @return The {@link List} of possible values for this property
     */
    List<? extends T> getPossibleValues();

    /**
     * Returns the property value for the given String representation of that value.
     *
     * @param string The name / string representation of the property value
     * @return The property value
     */
    T getValueFor(String string);
}
