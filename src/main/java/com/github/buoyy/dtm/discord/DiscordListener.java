package com.github.buoyy.dtm.discord;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

//ListenerAdapter for listening to message event
public class DiscordListener extends ListenerAdapter {

    TextChannel channel;

    public DiscordListener(TextChannel channel) {
        this.channel = channel;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.getAuthor().isSystem()) return; //Return if message is from bot or system; could cause infinite loop if not used
        if (!event.getChannel().equals(channel)) return; // Channel check
        String msg = String.format("<<%s>> %s", (ChatColor.GREEN + event.getAuthor().getEffectiveName()), (ChatColor.AQUA + event.getMessage().getContentDisplay())); //Will change colouring later
        Bukkit.broadcastMessage(msg); 
    }
}
