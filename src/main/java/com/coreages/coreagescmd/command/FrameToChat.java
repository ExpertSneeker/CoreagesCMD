package com.coreages.coreagescmd.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ClassName: FrameToChat
 * Package: com.coreages.coreagescmd.command
 * Description:
 *
 * @Author ExpertSneeker
 * @Create 2024/1/30 21:37
 * @Version 1.0
 */
public class FrameToChat implements CommandExecutor {
    private Map<UUID, Boolean> playerStates = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("fc")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                UUID playerId = player.getUniqueId();

                // 切换玩家的状态
                boolean isEnabled = playerStates.getOrDefault(playerId, true);
                isEnabled = !isEnabled;
                playerStates.put(playerId, isEnabled);

                // 向玩家发送状态信息
                player.sendMessage(ChatColor.RED+"远古展示框 "+ChatColor.GRAY+"> 此功能已 " + (isEnabled ? ChatColor.GREEN+"启用" : ChatColor.RED+"禁用"));
            } else {
                sender.sendMessage("只有玩家可以使用这个指令。");
            }
            return true;
        }
        return false;
    }

    public Map<UUID, Boolean> getPlayerStates() {
        return playerStates;
    }
}
