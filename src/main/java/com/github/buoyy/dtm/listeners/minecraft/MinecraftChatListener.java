package com.github.buoyy.dtm.listeners.minecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.github.buoyy.dtm.MainManager;
import com.github.buoyy.spigot_utils.Utils;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class MinecraftChatListener implements org.bukkit.event.Listener
{
    private final TextChannel chatChannel;
    private final String mcChatMsg;
    public MinecraftChatListener(MainManager manager)
    {
        this.chatChannel = manager.getChatChannel();
        this.mcChatMsg  = manager.getPlugin().getConfig().getString("mc-chat-msg");
    }

    // Send message to the assigned Discord TextChannel
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        String msg = mcChatMsg.replace("{player}", event.getPlayer().getDisplayName()).replace("{msg}", event.getMessage());
        msg = Utils.stripColorCodes(msg);
        chatChannel.sendMessage(msg).queue();
    }
}
