package com.github.buoyy.dtm.utils;

import org.bukkit.plugin.java.JavaPlugin;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.requests.GatewayIntent;

import com.github.buoyy.dtm.listeners.discord.DiscordListener;
import com.github.buoyy.dtm.listeners.mc.MCChatListener;

public class Manager {

    private JavaPlugin plugin;
    private JDA jda;
    private Guild guild;
    private TextChannel channel;

    public Manager(JavaPlugin plugin) {
        this.plugin = plugin;
}
    public boolean initJDA() {
        String botToken = plugin.getConfig().getString("bot-token");
        String channelID = plugin.getConfig().getString("channel-id");
        String guildID = plugin.getConfig().getString("server-id");
        try {
            jda = JDABuilder
                    .createLight(botToken)
                    .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                    .build();
            jda.awaitReady();
            guild = jda.getGuildById(guildID);
            channel = jda.getTextChannelById(channelID);
            
            if (guild == null || channel == null || !channel.getGuild().equals(guild)) {
            plugin.getLogger().severe("The guild or channel ID is invalid.");
            return false;
        }
        plugin.getLogger().info("Bot was initialised successfully.");
        return true;
        
        } catch (InvalidTokenException e) {
            plugin.getLogger().severe("Invalid bot token!");
            return false;
        } catch (Exception e) {
            plugin.getLogger().severe("Couldn't load plugin: " + e.getMessage());
            return false;
        }
    }
    public void shutdownJDA() {
        if (jda != null) {
            jda.shutdownNow();
            plugin.getLogger().info("Successfully shut down bot.");
        }
    }
    public void registerDiscordEvents() {
        jda.addEventListener(new DiscordListener(guild, channel));
    }
    public void registerMCEvents() {
        plugin.getServer().getPluginManager().registerEvents(new MCChatListener(guild, channel), plugin);
    }
}