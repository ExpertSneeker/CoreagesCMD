package com.coreages.coreagescmd;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.coreages.coreagescmd.command.SbCommand;
import com.coreages.coreagescmd.command.SbCountCommand;
import com.coreages.coreagescmd.command.SbLookCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class CoreagesCMD extends JavaPlugin {

    public static CoreagesCMD plugin;
    public static ResidenceApi resApi;

    @Override
    public void onEnable() {
        plugin = this;
        resApi = Residence.getInstance().getAPI();
        // Plugin startup logic
        Bukkit.getPluginCommand("sb").setExecutor(new SbCommand());
        Bukkit.getPluginCommand("sbcount").setExecutor(new SbCountCommand());
        Bukkit.getPluginCommand("sblook").setExecutor(new SbLookCommand());
        this.getServer().getConsoleSender().sendMessage(ChatColor.RED + "================");
        this.getServer().getConsoleSender().sendMessage(ChatColor.RED + "=远古指令已加载=");
        this.getServer().getConsoleSender().sendMessage(ChatColor.RED + "================");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
