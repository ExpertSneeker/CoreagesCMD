package com.coreages.coreagescmd.event;

import com.Zrips.CMI.events.CMIPlayerSitEvent;
import com.bekvon.bukkit.residence.containers.Flags;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static com.coreages.coreagescmd.CoreagesCMD.plugin;
import static com.coreages.coreagescmd.CoreagesCMD.coreagesUtils;
import static com.coreages.coreagescmd.util.MsgUtils.chat;
import static com.coreages.coreagescmd.util.MsgUtils.chatActionBar;

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

                TextComponent mainComponent = new TextComponent(ChatColor.DARK_GRAY + "物品展示框内的物品: ");

                String nbt = itemStack.hasItemMeta() ? itemStack.getItemMeta().getAsString() : "{}";
                Item contents = new Item(itemStack.getType().getKey().toString(), itemStack.getAmount(), ItemTag.ofNbt(nbt));

                //获取物品名并实例化
                if (!itemStack.getItemMeta().getDisplayName().isEmpty()) {
                    mainComponent.addExtra(ChatColor.GRAY + "[" + itemStack.getItemMeta().getDisplayName() + ChatColor.GRAY + "]");
                }else {
                    mainComponent.addExtra(ChatColor.GRAY + "[" + itemStack.getType() + ChatColor.GRAY + "]");
                }

                mainComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, contents));

//                mainComponent.addExtra(ChatColor.DARK_GRAY + "  使用/fc来启用或禁用此功能");

                chatActionBar(player, "&8使用 /fc 来启用或禁用此功能");

                player.spigot().sendMessage(mainComponent);
            }
        }
    }



    @EventHandler
    public void onPlayerSit(CMIPlayerSitEvent event) {

        Player player = event.getPlayer();
        Location loc = event.getChair().getArmorStandLoc();

        if (!coreagesUtils.hasPermission(player, loc, Flags.use)){
            event.setCancelled(true);
            chat(player, "你没有这个领地的 use 权限, 无法坐在此方块上!");
        }
    }



    //死亡时检测装备的lore中是否包含指定关键字，是的话复活
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();

        if (coreagesUtils.loreKeywordArmor(player,"诡异菌索")){
            player.damage(10);
            player.spigot().respawn();
        }
    }

    //放置头颅时，只能放在方块的上面
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        //获取玩家手中的物品
        ItemStack itemInHand = event.getItemInHand();
        Player player = event.getPlayer();

        // 检查放置的是否是玩家头颅
        if (event.getBlock().getType() == Material.PLAYER_HEAD || event.getBlock().getType() == Material.PLAYER_WALL_HEAD) {

            if (coreagesUtils.loreKeywordHand(itemInHand, Arrays.asList("货运输出管道", "货运输入管道"))){
                return;
            }

            // 检查是否放置在方块的上方
            if (event.getBlockAgainst().getFace(event.getBlock()) != BlockFace.UP) {
                // 不是放在上方，取消放置
                event.setCancelled(true);
                // 可以向玩家发送一些信息
                chat(player, "此 物品 只能放置在方块的上方!");
            }
            if (event.getBlockAgainst().getType() == Material.STONE && coreagesUtils.loreKeywordHand(itemInHand, Collections.singletonList("燃料容量"))){
                event.setCancelled(true);
                chat(player, "火箭 不能直接放置在发射台上, 请先替换为其他方块, 放置火箭后再替换为发射台!");
            }
        }

        //无限方块
        if (itemInHand.getType().isBlock() && coreagesUtils.loreKeywordHand(itemInHand, Collections.singletonList("无限方块"))){
            ItemStack item = itemInHand.clone();
            player.getInventory().setItem(event.getHand(), item);
        }

    }
}