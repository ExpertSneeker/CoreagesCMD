package com.coreages.coreagescmd.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

import static com.coreages.coreagescmd.util.MsgUtils.chat;

public class SbCount implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // 判断命令发送者是否是玩家
        if (sender instanceof Player) {
            // 强制转换为Player对象
            Player player = (Player) sender;
            // 获取半径
            int radius;
            if (args.length == 0){
                chat(player, "&c请输入正确的指令格式: /sbcount <半径>");
                return true;
            }else {
                try{
                    radius = Integer.parseInt(args[0]);
                } catch (NumberFormatException e){
                    chat(player, "&c请输入正确的指令格式: /sbcount <半径>");
                    return true;
                }
            }
            //防止半径过大卡服
            if(radius > 100 || radius < 1){
                chat(player, "&c半径的范围应该在 1-100 之间");
                return true;
            }
            // 获取半径内的实体列表
            List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);
            // 用于统计盔甲架数量
            int armor_stand_count = 0;
            // 遍历实体列表
            for (Entity entity : nearbyEntities) {
                // 判断实体是否是隐形盔甲架
                if (entity instanceof ArmorStand && ((ArmorStand) entity).isInvisible()) {
                    // 统计实体
                    armor_stand_count++;
                }
            }
            // 向玩家发送消息
            chat(player, "半径 "+ radius +" 内有 " + armor_stand_count + " 个隐形盔甲架");
        }
        // 返回true表示命令执行成功
        return true;
    }
}
