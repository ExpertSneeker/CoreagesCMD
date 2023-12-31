package com.coreages.coreagescmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

/**
 * ClassName: SbCommand
 * Package: com.coreages.coreagescmd
 * Description:
 *
 * @Author ExpertSneeker
 * @Create 2023/12/31 19:58
 * @Version 1.0
 */
public class SbCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //System.out.println("sender" + sender);
        //System.out.println("command" + command);
        //System.out.println("label" + label);
        //System.out.println("args" + Arrays.asList(args));

        // 判断命令发送者是否是玩家
        if (sender instanceof Player) {
            // 强制转换为Player对象
            Player player = (Player) sender;
            // 获取玩家周围5格内的所有实体
            List<Entity> nearbyEntities = player.getNearbyEntities(5, 5, 5);
            int count = 0;
            // 遍历实体列表
            for (Entity entity : nearbyEntities) {
                // 判断实体是否是隐形盔甲架
                if (entity instanceof ArmorStand && ((ArmorStand) entity).isInvisible()) {
                    // 删除实体
                    entity.remove();
                    count++;
                }
            }
            // 向玩家发送消息
            player.sendMessage(ChatColor.YELLOW +"已清理周围5格内的 " + count + " 个隐形盔甲架");
        }
        // 返回true表示命令执行成功
        return true;
    }
}
