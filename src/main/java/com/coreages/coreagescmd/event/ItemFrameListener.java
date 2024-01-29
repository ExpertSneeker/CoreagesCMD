package com.coreages.coreagescmd.event;

import com.coreages.coreagescmd.CoreagesCMD;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import static com.coreages.coreagescmd.CoreagesCMD.resApi;

import java.util.Locale;

/**
 * ClassName: EventListener
 * Package: com.coreages.coreagescmd.event
 * Description:
 *
 * @Author ExpertSneeker
 * @Create 2024/1/8 16:08
 * @Version 1.0
 */
public class ItemFrameListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof ItemFrame) {

            Player player = event.getPlayer();
            Location loc = event.getRightClicked().getLocation();

            ItemFrame frame = (ItemFrame) event.getRightClicked();
            ItemStack itemStack = frame.getItem();

            if (itemStack != null && itemStack.getType() != Material.AIR) {
                event.setCancelled(true);

                TextComponent component;

                String nbt = itemStack.hasItemMeta() ? itemStack.getItemMeta().getAsString() : "{}";
                Item contents = new Item(itemStack.getType().getKey().toString(), itemStack.getAmount(), ItemTag.ofNbt(nbt));

                //获取物品名并实例化
                if (!itemStack.getItemMeta().getDisplayName().isEmpty()) {
                    component = new TextComponent(ChatColor.GRAY + "[" + itemStack.getItemMeta().getDisplayName() + ChatColor.GRAY + "]");
                }else {
                    component = new TextComponent(ChatColor.GRAY + "[" + itemStack.getType() + ChatColor.GRAY + "]");
                }

                component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, contents));

                player.spigot().sendMessage(component);
            }
        }
    }
}