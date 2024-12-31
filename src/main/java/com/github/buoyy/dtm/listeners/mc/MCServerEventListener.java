package com.github.buoyy.dtm.listeners.mc;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public class MCServerEventListener implements Listener {

    private final Guild guild;
    private final TextChannel channel;

    public MCServerEventListener(Guild guild, TextChannel channel) {
        this.guild = guild;
        this.channel = channel;
    }

    @EventHandler
    public void onServerStart(ServerLoadEvent event) {
        if (nullCheck()) {
            String msg = "Server has **started**!";
            channel.sendMessage(msg).queue();
        }
    }

    // Should not be a problem if we call server stop and plugin disable the same thing
    @EventHandler
    public void onPluginDisable() {

    }
    private boolean nullCheck() {
        return !(guild == null || channel == null);
    }
}
