package com.github.buoyy.dtm;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class DiscordListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String chatChannelId = "your id";
        if (event.getAuthor().isBot() || event.getAuthor().isSystem()) return;
        if (!event.getChannel().getId().equals(chatChannelId)) return;
        String msg = String.format("<<%s>> %s", (ChatColor.RED + event.getAuthor().getEffectiveName()), (ChatColor.GRAY + event.getMessage().getContentDisplay()));
        Bukkit.broadcastMessage(msg);
    }
}
