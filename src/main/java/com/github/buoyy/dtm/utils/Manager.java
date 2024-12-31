package com.github.buoyy.dtm.utils;

import org.bukkit.plugin.java.JavaPlugin;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.requests.GatewayIntent;

import com.github.buoyy.dtm.listeners.discord.DiscordListener;
import com.github.buoyy.dtm.listeners.mc.MCPlayerEventListener;
import com.github.buoyy.dtm.commands.CommandInfo;
import com.github.buoyy.dtm.listeners.mc.MCServerEventListener;

public class Manager {

    // Define all important stuff
    private final JavaPlugin plugin;
    private JDA jda;
    private Guild guild;
    private TextChannel channel;
    private final String botToken, channelID, guildID;
    private final String joinMsg, quitMsg, advMsg;
    private final String startMsg, stopMsg;
    private final String mcMsg, discMsg;

    public Manager(JavaPlugin plugin) {
        this.plugin = plugin;
        joinMsg = plugin.getConfig().getString("join-msg");
        quitMsg = plugin.getConfig().getString("quit-msg");
        advMsg = plugin.getConfig().getString("adv-msg");
        startMsg = plugin.getConfig().getString("start-msg");
        stopMsg = plugin.getConfig().getString("stop-msg");
        botToken = plugin.getConfig().getString("bot-token");
        channelID = plugin.getConfig().getString("channel-id");
        guildID = plugin.getConfig().getString("server-id");
        mcMsg = plugin.getConfig().getString("mc-msg");
        discMsg = plugin.getConfig().getString("discord-msg");
    }
    
    // Grab IDs from config, try to load bot and handle exceptions otherwise
    public boolean initJDA() {

        try {
            jda = JDABuilder
                    .createLight(botToken)
                    .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT) // Message gateway intents are most important
                    .build();
            jda.awaitReady(); // Avoid unexpected behavior
            guild = jda.getGuildById(guildID);
            channel = jda.getTextChannelById(channelID);
            
            // Null checks for channel and server
            if (guild == null || channel == null || !channel.getGuild().equals(guild)) {
            plugin.getLogger().severe("The guild or channel ID is invalid.");
            return false;
        }
        plugin.getLogger().info("Discord server and channel were linked successfully with plugin.");
        plugin.getLogger().info("Bot was initialised successfully.");
        return true;
        
        }
        // The usual exception handling
        catch (InvalidTokenException e) {
            plugin.getLogger().severe("Invalid bot token!");
            return false;
        } catch (Exception e) {
            plugin.getLogger().severe("Couldn't load plugin: " + e.getMessage());
            return false;
        }
    }
    
    // Properly shutdown bot and wait (caused much unexpected behavior during testing)
    public void shutdownJDA() {
        if (jda != null) {
            try {
                jda.shutdown();
            jda.awaitShutdown();
            plugin.getLogger().info("Successfully shut down bot.");
            } catch (Exception e) {
            plugin.getLogger().severe("Couldn't shutdown bot correctly: " + e.getMessage());
            }
        }
    }
    
    // Might add more events
    public void registerDiscordEvents() {
        jda.addEventListener(new DiscordListener(guild, channel, discMsg));
    }
    
    public void registerMCEvents() {
        plugin.getServer().getPluginManager().registerEvents(new MCPlayerEventListener(guild, channel,
                mcMsg, joinMsg, quitMsg, advMsg), plugin);
        plugin.getServer().getPluginManager().registerEvents(new MCServerEventListener(plugin, guild, channel,
                startMsg, stopMsg), plugin);
    }
    
    // TODO: Add more useful commands to connect player with plugin
    public void registerMCCommands() {
        plugin.getCommand("dtminfo").setExecutor(new CommandInfo());
    }
}