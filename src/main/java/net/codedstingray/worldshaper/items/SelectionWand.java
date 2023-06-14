package net.codedstingray.worldshaper.items;

import net.codedstingray.worldshaper.util.chat.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Class holding the {@link ItemStack} for the selection wand as well as associated name and lore metadata.
 */
public class SelectionWand {

    public static final String SELECTION_WAND_NAME = TextColor.AQUA + "Area Wand" + TextColor.WHITE;
    public static final String SELECTION_WAND_LORE = TextColor.WHITE + "The " + TextColor.AQUA + "Selection Wand"
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
