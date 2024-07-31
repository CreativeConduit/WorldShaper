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

package net.codedstingray.worldshaper.util.world;

import net.codedstingray.worldshaper.chat.ChatMessageFormatter.MessageLevel;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

import static net.codedstingray.worldshaper.chat.ChatMessageFormatter.asWorldShaperMessage;

public class DirectionUtils {

    public static Direction determineDirectionFromPlayer(Player player, @Nullable String directionName) {
        Direction direction = determinePlayerDirection(player);
        Direction horizontalDirection = Direction.valueOf(player.getFacing().name());

        if (directionName != null) {
            try {
                direction = calculateDirectionFromParameter(horizontalDirection, directionName.toUpperCase());
            } catch (IllegalArgumentException e) {
                player.sendMessage(asWorldShaperMessage(MessageLevel.ERROR, "\"" + directionName + "\" is not a valid direction."));
                player.sendMessage(asWorldShaperMessage(MessageLevel.ERROR, "Valid directions are: \"up\", \"down\", \"north\", \"east\"" +
                        ", \"south\", \"west\", \"forward\", \"right\", \"back\", \"left\", "));
                return null;
            }
        }

        return direction;
    }

    private static Direction determinePlayerDirection(Player player) {
        float playerPitch = player.getLocation().getPitch();
        BlockFace facing = playerPitch > 45 ? BlockFace.DOWN : playerPitch < -45 ? BlockFace.UP : player.getFacing();
        return Direction.valueOf(facing.name());
    }

    private static Direction calculateDirectionFromParameter(Direction playerDirection, String directionName) {
        if (Direction.isValidDirectionName(directionName)) {
            return Direction.valueOf(directionName);
        }

        return Direction.calculateFromRelativeDirection(playerDirection, directionName);
    }
}
