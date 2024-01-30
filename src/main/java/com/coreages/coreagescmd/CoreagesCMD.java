package com.coreages.coreagescmd;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.coreages.coreagescmd.command.FrameToChat;
import com.coreages.coreagescmd.command.Sb;
import com.coreages.coreagescmd.command.SbCount;
import com.coreages.coreagescmd.command.SbLook;
import com.coreages.coreagescmd.event.CoreagesListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class CoreagesCMD extends JavaPlugin {
    public static CoreagesCMD plugin;
    public static ResidenceApi resApi;
    private FrameToChat frameToChat;

    @Override
    public void onEnable() {
        frameToChat = new FrameToChat();
        getServer().getPluginManager().registerEvents(new CoreagesListener(), this);
        getCommand("fc").setExecutor(frameToChat);
        plugin = this;
        resApi = Residence.getInstance().getAPI();
        // Plugin startup logic
        Bukkit.getPluginCommand("sb").setExecutor(new Sb());
        Bukkit.getPluginCommand("sbcount").setExecutor(new SbCount());
        Bukkit.getPluginCommand("sblook").setExecutor(new SbLook());
        this.getServer().getConsoleSender().sendMessage(ChatColor.RED + "================");
        this.getServer().getConsoleSender().sendMessage(ChatColor.RED + "=远古指令已加载=");
        this.getServer().getConsoleSender().sendMessage(ChatColor.RED + "================");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public FrameToChat getFrameToChat() {
        return frameToChat;
    }
}
