package com.github.buoyy.dtm.listeners.discord;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

// Send messages in the Minecraft server chat
public class DiscordChatListener extends ListenerAdapter {

    private final TextChannel channel;
    private final Guild guild;
    private final String dcChatMsg;

    public DiscordChatListener(Guild guild, TextChannel channel, FileConfiguration config) {
        this.guild = guild;
        this.channel = channel;
        this.dcChatMsg = config.getString("dc-chat-msg");
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.getAuthor().isSystem())
            return; //Return if message is from bot or system; could cause infinite loop if not used
        if (nullCheck()) {
            if (!event.getChannel().equals(channel)) return; // Channel check
            String msg = dcChatMsg.replace("{player}", event.getAuthor().getEffectiveName())
                    .replace("{msg}", event.getMessage().getContentDisplay());
            String formattedMsg = ChatColor.translateAlternateColorCodes('&', msg);
            Bukkit.broadcastMessage(formattedMsg);
        }
    }

    private boolean nullCheck() {
        return !(guild == null || channel == null);
    }
}
