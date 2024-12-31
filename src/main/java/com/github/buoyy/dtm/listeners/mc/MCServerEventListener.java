package com.github.buoyy.dtm.listeners.mc;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MCServerEventListener implements Listener {

    private final Guild guild;
    private final TextChannel channel;
    private final JavaPlugin plugin;
    private final String startMsg;
    private final String stopMsg;

    public MCServerEventListener(JavaPlugin plugin, Guild guild, TextChannel channel,
                                String startMsg, String stopMsg) {
        this.plugin = plugin;
        this.guild = guild;
        this.channel = channel;
        this.startMsg = startMsg;
        this.stopMsg = stopMsg;
    }

    @EventHandler
    public void onServerStart(ServerLoadEvent event) {
        if (nullCheck()) {
            channel.sendMessage(startMsg).queue();
        }
    }

    // Should not be a problem if we call server stop and plugin disable the same thing
    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (!nullCheck()) return;
        if (event.getPlugin().equals(plugin)) {
            channel.sendMessage(stopMsg).queue();
        }
    }
    private boolean nullCheck() {
        return !(guild == null || channel == null);
    }
}
