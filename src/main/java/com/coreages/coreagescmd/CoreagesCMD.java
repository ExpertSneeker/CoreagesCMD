package com.coreages.coreagescmd;

import com.coreages.coreagescmd.command.SbCommand;
import com.coreages.coreagescmd.command.SbCountCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class CoreagesCMD extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginCommand("sb").setExecutor(new SbCommand());
        Bukkit.getPluginCommand("sbcount").setExecutor(new SbCountCommand());
        this.getServer().getConsoleSender().sendMessage(ChatColor.RED + "================");
        this.getServer().getConsoleSender().sendMessage(ChatColor.RED + "=远古指令已加载=");
        this.getServer().getConsoleSender().sendMessage(ChatColor.RED + "================");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
