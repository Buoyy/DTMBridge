package com.github.buoyy.dtm.mc;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class MCChatListener implements Listener {
    private final TextChannel channel;
    public MCChatListener(TextChannel channel) {
        this.channel = channel;
    }
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (channel == null) return;
        String msg = String.format("<<%s>> %s", event.getPlayer().getDisplayName(), event.getMessage());
        channel.sendMessage(msg).queue();
    }
}
