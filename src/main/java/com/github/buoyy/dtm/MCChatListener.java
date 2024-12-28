package com.github.buoyy.dtm;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class MCChatListener implements Listener {
    JDA jda; TextChannel channel;
    public MCChatListener(JDA jda) {
        this.jda = jda;
    }
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (jda == null) return;
        channel = jda.getTextChannelById("Your channel here");
        String msg = String.format("<<%s>> %s", event.getPlayer().getDisplayName(), event.getMessage());
        channel.sendMessage(msg).queue();
    }
}
