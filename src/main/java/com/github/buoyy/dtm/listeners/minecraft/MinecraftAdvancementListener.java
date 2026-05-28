package com.github.buoyy.dtm.listeners.minecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import com.github.buoyy.dtm.MainManager;
import com.github.buoyy.spigot_utils.Utils;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class MinecraftAdvancementListener implements Listener
{
    private final TextChannel chatChannel;
    private final String advMsg;
    public MinecraftAdvancementListener(MainManager manager)
    {
        this.chatChannel = manager.getChatChannel();
        this.advMsg = manager.getPlugin().getConfig().getString("adv-msg");
    }

    @EventHandler
    public void onPlayerAdvancement(PlayerAdvancementDoneEvent event)
    {
        String advName = event.getAdvancement().getDisplay().getTitle();
        String msg = advMsg.replace("{player}", event.getPlayer().getDisplayName()).replace("{adv}", advName);
        msg = Utils.stripColorCodes(msg);
        chatChannel.sendMessage(msg).queue();
    }
}
