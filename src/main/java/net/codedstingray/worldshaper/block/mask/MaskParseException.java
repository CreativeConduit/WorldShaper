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

public class MaskParseException extends Exception {

    static final long serialVersionUID = -5699890126207895788L;

    public MaskParseException() {
        super();
    }

    public MaskParseException(String message) {
        super(message);
    }

    public MaskParseException(Throwable cause) {
        super(cause);
    }

    public MaskParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
