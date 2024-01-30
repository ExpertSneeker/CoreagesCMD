package com.coreages.coreagescmd.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * ClassName: containsLoreKeyword
 * Package: com.coreages.coreagescmd.util
 * Description:
 *
 * @Author ExpertSneeker
 * @Create 2024/1/30 20:57
 * @Version 1.0
 */
public class LoreHasKey {
    public boolean containsLoreKeyword(Player player, String keyword) {
        PlayerInventory inventory = player.getInventory();

        // 检查所有装备槽位（头盔、胸甲、护腿、靴子）
        ItemStack[] armors = inventory.getArmorContents();
        for (ItemStack armor : armors) {
            if (armor != null && armor.hasItemMeta()) {
                ItemMeta meta = armor.getItemMeta();
                if (meta.hasLore()) {
                    List<String> lores = meta.getLore();
                    for (String lore : lores) {
                        if (lore.contains(keyword)) {
                            return true; // 找到包含关键字的lore
                        }
                    }
                }
            }
        }

        return false; // 没有找到含有关键字的lore
    }
}
