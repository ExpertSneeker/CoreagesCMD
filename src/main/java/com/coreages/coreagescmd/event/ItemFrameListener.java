package com.coreages.coreagescmd.event;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
            ItemFrame frame = (ItemFrame) event.getRightClicked();
            ItemStack item = frame.getItem();

            if (item != null && item.getType() != Material.AIR) {
                Player player = event.getPlayer();
                ItemMeta meta = item.getItemMeta();

                if (meta != null) {
                    // Building the hover text
                    ComponentBuilder hoverText = new ComponentBuilder("");

                    if (!meta.getDisplayName().isEmpty()) {
                        hoverText.append(meta.getDisplayName());
                    }else{
                        hoverText.append(item.getType().toString());
                    }

                    if (meta.hasEnchants()) {
                        meta.getEnchants().forEach((enchant, level) -> {
                            hoverText.append("\n" + ChatColor.GRAY + enchant.getName() + " " + level);
                        });
                    }

                    if (meta.getLore() != null) {
                        for (String lore : meta.getLore()) {
                            hoverText.append("\n" + lore);
                        }
                    }

                    TextComponent message;

                    if (!meta.getDisplayName().isEmpty()) {
                        message = new TextComponent(ChatColor.GRAY + "[" + meta.getDisplayName() + ChatColor.GRAY + "]");
                    }else {
                        message = new TextComponent(ChatColor.GRAY + "[" + item.getType() + ChatColor.GRAY + "]");
                    }

                    message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText.create()));

                    player.spigot().sendMessage(message);
                }
            }
        }
    }
}