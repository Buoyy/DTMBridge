package com.github.buoyy.dtm;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

//ListenerAdapter for listening to message event
public class DiscordListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String chatChannelId = "Your channel id here";
        if (event.getAuthor().isBot() || event.getAuthor().isSystem()) return; //Return if message is from bot or system; could cause infinite loop if not used
        if (!event.getChannel().getId().equals(chatChannelId)) return; // Channel check
        String msg = String.format("<<%s>> %s", (ChatColor.RED + event.getAuthor().getEffectiveName()), (ChatColor.GRAY + event.getMessage().getContentDisplay())); //Will change colouring later
        Bukkit.broadcastMessage(msg); 
    }
}
