package com.github.buoyy.dtm.listeners.mc;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.advancement.AdvancementDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

// Send messages in Discord for player chats in Minecraft
// Sends message on Discord chat, player join/leave and player advancement
public class MCPlayerEventListener implements Listener {
    private final TextChannel channel;
    private final Guild guild;
    private final String mcMsg;
    private final String joinMsg;
    private final String quitMsg;
    private final String advMsg;

    public MCPlayerEventListener(Guild guild, TextChannel channel, String mcMsg, String joinMsg, String quitMsg, String advMsg) {
        this.guild = guild;
        this.channel = channel;
        this.mcMsg = mcMsg;
        this.joinMsg = joinMsg;
        this.quitMsg = quitMsg;
        this.advMsg = advMsg;
    }

    // Send message to the assigned Discord TextChannel
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (nullCheck()) {
            // Username is to be bold
            String msg = mcMsg.replace("{player}",
                    event.getPlayer().getDisplayName())
                            .replace("{msg}", event.getMessage());
            channel.sendMessage(msg).queue();
        }
    }

    // Join message sent to Discord
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (nullCheck()) {
            String msg = joinMsg.replace("{player}",
                            event.getPlayer().getDisplayName());
            channel.sendMessage(msg).queue();
        }
    }

    // Both have bold+italicised username
    // and italicised join message

    // Leave message sent to Discord
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (nullCheck()) {
            String msg = quitMsg.replace("{player}",
                            event.getPlayer().getDisplayName());
            channel.sendMessage(msg).queue();
        }
    }

    @EventHandler
    public void onPlayerAdvancement(PlayerAdvancementDoneEvent event) {
        if (nullCheck()) {
            AdvancementDisplay display = event.getAdvancement().getDisplay();
            if (display != null) {
                String msg = advMsg.replace("{player}",
                                event.getPlayer().getDisplayName())
                        .replace("{adv}", display.getTitle());
                channel.sendMessage(msg).queue();
            }
        }
    }

    // Return true if not null
    // I kinda think we don't need the guild instance
    // here but null checks go BRRRRRRRR
    private boolean nullCheck() {
        return !(guild == null || channel == null);
    }
}
