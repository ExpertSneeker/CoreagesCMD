package com.coreages.coreagescmd.event;

import com.coreages.coreagescmd.util.LoreHasKey;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import static com.coreages.coreagescmd.CoreagesCMD.plugin;

/**
 * ClassName: EventListener
 * Package: com.coreages.coreagescmd.event
 * Description:
 *
 * @Author ExpertSneeker
 * @Create 2024/1/8 16:08
 * @Version 1.0
 */
public class CoreagesListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {


        if (event.getRightClicked() instanceof ItemFrame) {
            Player player = event.getPlayer();
            UUID playerId = player.getUniqueId();

            boolean isEnabled = plugin.getFrameToChat().getPlayerStates().getOrDefault(playerId, true);

            if (!isEnabled){
                return;
            }

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

                component.addExtra(ChatColor.DARK_GRAY + "  使用/fc来启用或禁用此功能");

                player.spigot().sendMessage(component);
            }
        }
    }


    //死亡时检测装备的lore中是否包含指定关键字，是的话复活
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();
        LoreHasKey loreHasKey = new LoreHasKey();

        if (loreHasKey.containsLoreKeyword(player,"诡异菌索")){
            player.damage(10);
            player.spigot().respawn();
        }
    }
}