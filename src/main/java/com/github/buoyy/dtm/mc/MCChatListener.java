package com.github.buoyy.dtm.mc;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class MCChatListener implements Listener 
{
    private TextChannel channel;
    private Guild guild;
    
    public MCChatListener(Guild guild, TextChannel channel) {
        this.guild = guild;
        this.channel = channel;
    }
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (guild == null) return;
        if (channel == null) return;
        String msg = String.format("<<%s>> %s", event.getPlayer().getDisplayName(), event.getMessage());
        channel.sendMessage(msg).queue();
    }
}
