package com.coreages.coreagescmd.command;

import com.bekvon.bukkit.residence.containers.Flags;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

import static com.coreages.coreagescmd.CoreagesCMD.coreagesUtils;

/**
 * ClassName: SbCommand
 * Package: com.coreages.coreagescmd
 * Description:
 *
 * @Author ExpertSneeker
 * @Create 2023/12/31 19:58
 * @Version 1.0
 */
public class Sb implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        // 判断命令发送者是否是玩家
        if (sender instanceof Player) {
            // 强制转换为Player对象
            Player player = (Player) sender;

            // 检查的权限
            Flags check = Flags.destroy;

            // 获取玩家周围10格内的所有实体
            List<Entity> nearbyEntities = player.getNearbyEntities(10, 10, 10);
            int failcount = 0;
            int count = 0;
            // 遍历实体列表
            for (Entity entity : nearbyEntities) {
                // 判断实体是否是隐形盔甲架
                if (entity instanceof ArmorStand && ((ArmorStand) entity).isInvisible()) {
                    if (coreagesUtils.hasPermission(player, entity.getLocation() , check) || player.hasPermission("coreages.command.sb.admin")){
                        // 删除实体
                        entity.remove();
                        count++;
                    }else {
                        failcount++;
                    }
                }
            }
            // 向玩家发送消息
            if (player.hasPermission("coreages.command.sb.admin")){
                player.sendMessage(ChatColor.RED + "远古科技" + ChatColor.GRAY +" > 已无视权限清理附近5格的 " + count + " 个隐形盔甲架");
            }else if(failcount == 0) {
                player.sendMessage(ChatColor.RED + "远古科技" + ChatColor.GRAY +" > 成功清理附近5格的 " + count + " 个隐形盔甲架");
            }else {
                player.sendMessage(ChatColor.RED + "远古科技" + ChatColor.GRAY +" > 成功清理附近5格的 " + count + " 个隐形盔甲架, 清理失败" + failcount + "个, 原因: 缺少盔甲架所在领地的权限: " + check);
            }
        }
        // 返回true表示命令执行成功
        return true;
    }
}
