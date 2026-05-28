package com.github.buoyy.dtm.listeners.minecraft;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.buoyy.dtm.MainManager;
import com.github.buoyy.spigot_utils.Utils;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

// TODO: Fix channel topic thingy
public class MinecraftLoginListener implements Listener
{
    private final TextChannel chatChannel;
    private final String joinMsg, quitMsg, chatChannelTopic;
    private int playersOnline, playerLimit;

    public MinecraftLoginListener(MainManager manager)
    {
        this.chatChannel = manager.getChatChannel();
        this.joinMsg = manager.getPlugin().getConfig().getString("join-msg");
        this.quitMsg = manager.getPlugin().getConfig().getString("quit-msg");
        this.chatChannelTopic = manager.getPlugin().getConfig().getString("dc-topic");
        this.playerLimit = Bukkit.getMaxPlayers();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        String msg = Utils.stripColorCodes(joinMsg.replace("{player}", event.getPlayer().getDisplayName()));
        chatChannel.sendMessage(msg).queue();
        updateTopic();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        String msg = Utils.stripColorCodes(quitMsg.replace("{player}", event.getPlayer().getDisplayName()));
        chatChannel.sendMessage(msg).queue();
        updateTopic();
    }

    private void updateTopic()
    {
        playersOnline = Bukkit.getOnlinePlayers().size();
        String topic = chatChannelTopic
                .replace("{players_online}", Integer.toString(playersOnline))
                .replace("{player_limit}", Integer.toString(playerLimit));
        chatChannel.getManager().setTopic(topic);
    }
}
