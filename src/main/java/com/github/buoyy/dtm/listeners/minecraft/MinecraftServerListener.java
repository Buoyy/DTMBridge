package com.github.buoyy.dtm.listeners.minecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.buoyy.dtm.MainManager;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class MinecraftServerListener implements Listener
{
    private final TextChannel chatChannel;
    private final JavaPlugin plugin;
    private final String startMsg, stopMsg;

    public MinecraftServerListener(MainManager manager)
    {
        this.chatChannel = manager.getChatChannel();
        this.startMsg = manager.getPlugin().getConfig().getString("start-msg");
        this.stopMsg = manager.getPlugin().getConfig().getString("stop-msg");
        this.plugin = manager.getPlugin();
    }

    @EventHandler
    public void onServerLoad(ServerLoadEvent event)
    {
        chatChannel.sendMessage(startMsg).queue();
    }

    @EventHandler
    public void onServerStop(PluginDisableEvent event)
    {
        if (event.getPlugin().equals(plugin))
            chatChannel.sendMessage(stopMsg).queue();
    }
}
