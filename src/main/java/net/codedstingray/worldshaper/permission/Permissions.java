/*
 * WorldShaper: a powerful in-game map editor for Minecraft
 * Copyright (C) 2024 CodedStingray
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

package net.codedstingray.worldshaper.permission;

public class Permissions {
    public static final String PERMISSION_SELECTION   = "worldshaper.builder.interaction.selection";
    public static final String PERMISSION_WORLDSHAPER = "worldshaper.builder.command.util.worldshaper";
    public static final String PERMISSION_AREATYPE    = "worldshaper.builder.command.area.areatype";
    public static final String PERMISSION_EXPAND      = "worldshaper.builder.command.area.expand";
    public static final String PERMISSION_RETRACT     = "worldshaper.builder.command.area.retract";
    public static final String PERMISSION_MOVEAREA    = "worldshaper.builder.command.area.movearea";
    public static final String PERMISSION_SET         = "worldshaper.builder.command.edit.set";
    public static final String PERMISSION_REPLACE     = "worldshaper.builder.command.edit.replace";
    public static final String PERMISSION_CEILING     = "worldshaper.builder.command.edit.ceiling";
    public static final String PERMISSION_FLOOR       = "worldshaper.builder.command.edit.floor";
    public static final String PERMISSION_HULL        = "worldshaper.builder.command.edit.hull";
    public static final String PERMISSION_WALLS       = "worldshaper.builder.command.edit.walls";

    public static final String[] ALL_PERMISSIONS = {
            PERMISSION_SELECTION,
            PERMISSION_WORLDSHAPER,
            PERMISSION_AREATYPE,
            PERMISSION_EXPAND,
            PERMISSION_RETRACT,
            PERMISSION_MOVEAREA,
            PERMISSION_SET,
            PERMISSION_REPLACE,
            PERMISSION_CEILING,
            PERMISSION_FLOOR,
            PERMISSION_HULL,
            PERMISSION_WALLS,
    };

    public static final String[] EDIT_PERMISSIONS = {
            PERMISSION_SET,
            PERMISSION_REPLACE,
            PERMISSION_CEILING,
            PERMISSION_FLOOR,
            PERMISSION_HULL,
            PERMISSION_WALLS,
    };
}
