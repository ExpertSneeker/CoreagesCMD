package com.coreages.coreagescmd.command;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.coreages.coreagescmd.CoreagesCMD.plugin;

/**
 * ClassName: SbCommand
 * Package: com.coreages.coreagescmd
 * Description:
 *
 * @Author ExpertSneeker
 * @Create 2023/12/31 19:58
 * @Version 1.0
 */
public class SbLook implements CommandExecutor {

    //创建哈希表，用于设置指令CD
    private final HashMap<UUID,Long> cooldown;

    public SbLook(){
        this.cooldown = new HashMap<>();
    }

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

            // 获取半径
            int radius;
            if (args.length == 0){
                player.sendMessage(ChatColor.RED + "请输入正确的指令格式: /sblook <半径>");
                return true;
            }else {
                try{
                    radius = Integer.parseInt(args[0]);
                } catch (NumberFormatException e){
                    player.sendMessage(ChatColor.RED + "请输入正确的指令格式: /sblook <半径>");
                    return true;
                }
            }

            //防止半径过大卡服
            if(radius > 100 || radius < 1){
                player.sendMessage(ChatColor.RED + "半径的范围应该在 1-100 之间");
                return true;
            }

            if (!this.cooldown.containsKey(player.getUniqueId())){
                this.cooldown.put(player.getUniqueId(),System.currentTimeMillis());
            }else{
                //以毫秒为单位的时差
                long timeElapsed = System.currentTimeMillis() - cooldown.get(player.getUniqueId());

                //冷却时间ms
                int cd = 10000;

                if (timeElapsed <= cd){
                    player.sendMessage("指令冷却中, 请等待 " + (cd - timeElapsed) / 1000 + " 秒!" );
                    return true;
                }
            }

            // 获取半径内的实体列表
            List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);

            // 用于统计盔甲架数量
            int armor_stand_count = 0;

            //创建红色，大小为1的粒子数据对象
            Particle.DustOptions data = new Particle.DustOptions(Color.RED, 1);

            // 遍历实体列表
            for (Entity entity : nearbyEntities) {
                // 判断实体是否是隐形盔甲架
                if (entity instanceof ArmorStand && ((ArmorStand) entity).isInvisible()) {
                    // 统计实体
                    armor_stand_count++;

                    // 放置红石粒子
                    player.spawnParticle(Particle.REDSTONE,entity.getLocation().getBlockX() + 0.5,entity.getLocation().getBlockY() + 1,entity.getLocation().getBlockZ() + 0.5,10, data);

                    BukkitRunnable task = new BukkitRunnable() {

                        int count = 0;

                        @Override
                        public void run() {
                            player.spawnParticle(Particle.REDSTONE,entity.getLocation().getBlockX() + 0.5,entity.getLocation().getBlockY() + 1,entity.getLocation().getBlockZ() + 0.5,10, data);
                            count++;

                            if (count >= 20){
                                this.cancel();
                            }
                        }
                    };

                    task.runTaskTimer(plugin,0,10);

                }
            }
            // 向玩家发送消息
            player.sendMessage(ChatColor.YELLOW +"半径 "+ radius +" 内有 " + armor_stand_count + " 个隐形盔甲架, 已在盔甲架的位置显示红石粒子");

            this.cooldown.put(player.getUniqueId(),System.currentTimeMillis());
        }
        // 返回true表示命令执行成功
        return true;
    }
}
