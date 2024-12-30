package com.github.buoyy.dtm.listeners.mc;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

// Send messages in Discord for player chats in Minecraft
public class MCChatListener implements Listener 
{
    private TextChannel channel;
    private Guild guild;
    
    public MCChatListener(Guild guild, TextChannel channel) {
        this.guild = guild;
        this.channel = channel;
    }
    // I kinda think we don't need the guild instance
    // here but null checks go BRRRRRRRR
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (guild == null) return;
        if (channel == null) return;
        // Username is to be bold
        String msg = String.format("**<%s>:** %s", event.getPlayer().getDisplayName(), event.getMessage());
        channel.sendMessage(msg).queue();
    }
}
