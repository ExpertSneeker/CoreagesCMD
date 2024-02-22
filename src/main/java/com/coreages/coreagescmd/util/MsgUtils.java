package com.coreages.coreagescmd.util;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
