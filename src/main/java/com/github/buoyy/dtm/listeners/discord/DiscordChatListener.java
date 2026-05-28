package com.github.buoyy.dtm.listeners.discord;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.buoyy.dtm.MainManager;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

// Broadcasts the messages sent in Discord to Minecraft
public class DiscordChatListener extends ListenerAdapter
{
    private final TextChannel chatChannel;
    private final String dcChatMsg;
    private final Sound sound;
    private final float soundVolume, soundPitch;
    
    public DiscordChatListener(MainManager manager)
    {
        this.chatChannel = manager.getChatChannel();
        this.dcChatMsg = manager.getPlugin().getConfig().getString("dc-chat-msg");
        this.sound = Sound.valueOf(manager.getPlugin().getConfig().getString("notif.sound"));
        this.soundVolume = (float)manager.getPlugin().getConfig().getDouble("notif.volume");
        this.soundPitch = (float)manager.getPlugin().getConfig().getDouble("notif.pitch");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) 
    {
        if (event.getAuthor().isBot() || event.getAuthor().isSystem())
            return;
        if (!chatChannel.equals((TextChannel)event.getChannel()))
            return;
        String msg = dcChatMsg
                        .replace("{player}", event.getAuthor().getEffectiveName())
                        .replace("{msg}", event.getMessage().getContentStripped());
        String formattedMsg = ChatColor.translateAlternateColorCodes('&', msg);
        Bukkit.broadcastMessage(formattedMsg);

        // Notification sound
        for (Player player : Bukkit.getOnlinePlayers().stream().toList())
            player.playSound(player.getLocation(), sound, soundVolume, soundPitch);
    }
}
