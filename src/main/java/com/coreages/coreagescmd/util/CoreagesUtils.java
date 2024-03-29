package com.coreages.coreagescmd.util;

import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static com.coreages.coreagescmd.CoreagesCMD.resApi;

public class CoreagesUtils {
    //检测装备是否包含lore
    public boolean loreKeywordArmor(Player player, String keyword) {
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

    public boolean loreKeywordHand(ItemStack item, List<String> words){

        //获取物品meta
        ItemMeta meta = item.getItemMeta();

        if (meta != null && meta.hasLore()) {
            //获取物品的lore
            List<String> lore = meta.getLore();
            // 检查lore中是否包含特定的字符串
            if (lore != null) {
                for (String line : lore) {
                    for (String keyword : words) {
                        if (line.contains(keyword)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;//不包含
    }

    // 这个方法需要根据的领地权限系统进行实现
    public boolean hasPermission(Player player, Location location, Flags check) {
        if (resApi.getResidenceManager().getByLoc(location) != null){
            //获取领地
            ClaimedResidence res = resApi.getResidenceManager().getByLoc(location);
            //获取领地权限
            FlagPermissions perms = res.getPermissions();
            return perms.playerHas(player, check, true);
        }
        return true; // 不在领地内
    }
}
