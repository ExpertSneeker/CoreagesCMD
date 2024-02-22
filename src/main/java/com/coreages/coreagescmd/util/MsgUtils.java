package com.coreages.coreagescmd.util;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * ClassName: MsgUtil
 * Package: com.coreages.coreagescmd.util
 * Description:
 *
 * @Author ExpertSneeker
 * @Create 2024/2/22 16:36
 * @Version 1.0
 */
public class MsgUtils {
    public static String format(String msg) {
        if (msg == null)
            return "";
        return msg.replace("&", "§");
    }

    public static void chat(CommandSender sender, String msg) {
        sender.sendMessage(format("&c远古科技 &7> " + msg));
    }

    public static void chatActionBar(Player player, String msg){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(format(msg)));
    }
}
