package com.github.buoyy.dtm.listeners.mc;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

// Send messages in Discord for player chats in Minecraft
public class MCChatListener implements Listener
{
    private TextChannel channel;
    private Guild guild;

    public MCChatListener(Guild guild, TextChannel channel) 
    {
        this.guild = guild;
        this.channel = channel;
    }

    // Send message to the assigned Discord TextChannel
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        if (nullCheck())
        {
            // Username is to be bold
            String msg = String.format("**<%s>:** %s", event.getPlayer().getDisplayName(), event.getMessage());
            channel.sendMessage(msg).queue();
        }
    }

    // Join message sent to Discord
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        if (nullCheck()) 
        {
            String msg = String.format("***%s*** *has joined the server!*", event.getPlayer().getDisplayName());
        channel.sendMessage(msg).queue();
        }
    }

    // Both have bold+italicised user name
    // and italicised join message
    
    // Leave message sent to Discord
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
        if (nullCheck())
        {
            String msg = String.format("***%s*** *has left the server.*", event.getPlayer().getDisplayName());
        channel.sendMessage(msg).queue();
        }
    }

    // Return true if not null
    // I kinda think we don't need the guild instance
    // here but null checks go BRRRRRRRR
    private boolean nullCheck() {
        if (guild == null || channel == null) return false;
        else return true;
        }
    }
