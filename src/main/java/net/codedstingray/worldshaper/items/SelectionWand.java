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

package net.codedstingray.worldshaper.items;

import net.codedstingray.worldshaper.chat.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static net.codedstingray.worldshaper.chat.ChatMessageFormatter.ACCENT_COLOR;

/**
 * Class holding the {@link ItemStack} for the selection wand as well as associated name and lore metadata.
 */
public class SelectionWand {

    public static final String SELECTION_WAND_NAME = ACCENT_COLOR + "Area Wand" + TextColor.WHITE;
    public static final String SELECTION_WAND_LORE = TextColor.WHITE + "The " + ACCENT_COLOR + "Selection Wand"
            + TextColor.WHITE + " is used to select WorldShaper Areas";

    /**
     * The selection wand {@link ItemStack}.
     */
    public static final ItemStack SELECTION_WAND;

    static {
        SELECTION_WAND = new ItemStack(Material.IRON_AXE);
        ItemMeta selectionWandMeta = SELECTION_WAND.getItemMeta();

        if (selectionWandMeta != null) {
            selectionWandMeta.setDisplayName(SELECTION_WAND_NAME);
            selectionWandMeta.setLore(List.of(SELECTION_WAND_LORE));

            SELECTION_WAND.setItemMeta(selectionWandMeta);
        }
    }
}
