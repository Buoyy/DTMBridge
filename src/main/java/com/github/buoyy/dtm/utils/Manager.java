package com.github.buoyy.dtm.utils;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.logging.Handler;
import java.util.logging.Logger;

import com.github.buoyy.dtm.listeners.discord.DiscordChatListener;
import com.github.buoyy.dtm.listeners.mc.MCPlayerEventListener;
import com.github.buoyy.dtm.commands.CommandInfo;
import com.github.buoyy.dtm.listeners.mc.MCServerEventListener;
import com.github.buoyy.dtm.utils.ConsoleDiscordHandler;

public class Manager {

    // Define all important stuff
    private final JavaPlugin plugin;
    private JDA jda;
    private Guild guild;
    private TextChannel chatChannel, consoleChannel;
    private final String botToken;
    private String chatChannelID, consoleChannelID, guildID;
    private final Logger consoleLogger;
    
    public Manager(JavaPlugin plugin) {
        this.plugin = plugin;
        botToken = plugin.getConfig().getString("bot-token");
        chatChannelID = plugin.getConfig().getString("chat-channel-id");
        guildID = plugin.getConfig().getString("server-id");
        consoleChannelID = plugin.getConfig().getString("console-channel-id");
        consoleLogger = plugin.getServer().getLogger();
    }
    
    // Grab IDs from config, try to load bot and handle exceptions otherwise
    public boolean initJDA() {
        try {
            jda = JDABuilder
                    .createLight(botToken)
                    .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT) // Message gateway intents are most important
                    .build();
            Bukkit.getScheduler().runTaskAsynchronously(plugin, ()-> {
                try {
                    jda.awaitReady();
                    plugin.getLogger().info("Bot was initialised successfully.");
                }
                catch (Exception e) {
                    plugin.getLogger().severe("Couldn't initialize bot!: " + e.getMessage());
                    plugin.getLogger().severe("In case of invalid ID(s), the config should be in the following format: ");
                    plugin.getLogger().severe("id: \"the ID here\"");
                }
            });
        } catch (Exception e) {
            plugin.getLogger().severe("Error during bot initialization: " + e.getMessage());
            return false;
        }
        return true;
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
    
    public boolean initGuildChannels() {
        if (jda == null || !jda.getStatus().isInit()) {
            plugin.getLogger().severe("Bot is not ready. Cannot initialize channels.");
            return false;
        }
        // Trim any whitespace around IDs to avoid format issues
        guildID = guildID != null ? guildID.trim() : "";
        chatChannelID = chatChannelID != null ? chatChannelID.trim() : "";
        consoleChannelID = consoleChannelID != null ? consoleChannelID.trim() : "";
        
        if (guildID.isEmpty()) {
            plugin.getLogger().severe("Server ID is empty!");
            return false;
        }
        guild = jda.getGuildById(guildID);
        if (chatChannelID.isEmpty()) {
            plugin.getLogger().severe("Chat channel ID is empty!");
            return false;
        }
        chatChannel = jda.getTextChannelById(chatChannelID);
            // Null checks for chat channel and server
        if (guild == null || chatChannel == null || !chatChannel.getGuild().equals(guild)) {
        plugin.getLogger().severe("The server or chat channel ID is invalid.");
        plugin.getLogger().severe("Server ID:"+guildID);
        plugin.getLogger().severe("chatChannelID:"+chatChannelID);
            return false;
        }
        if (plugin.getConfig().getBoolean("enable-console")) {
            if (consoleChannelID.isEmpty()) {
                plugin.getLogger().severe("Console Channel ID was not found or is invalid.");
                return false;
        } else {
            consoleChannel = jda.getTextChannelById(consoleChannelID);
            if (consoleChannel == null || !consoleChannel.getGuild().equals(guild)) {
                plugin.getLogger().severe("The server or console channel ID is invalid.");
                    return false;
                }
            consoleLogger.addHandler(new ConsoleDiscordHandler(consoleChannel));
            plugin.getLogger().info("Discord console Channel was linked successfully with the plugin.");
            }
        } else {
            plugin.getLogger().info("Console integration is disabled as of now.");
        }
        plugin.getLogger().info("Discord server and channel were linked successfully with plugin.");
        return true;
    }
    
    // Might add more events
    public void registerDiscordEvents() {
        jda.addEventListener(new DiscordChatListener(guild, chatChannel, plugin.getConfig()));
    }
    
    // Check the corresponding classes for more info
    public void registerMCEvents() {
        plugin.getServer().getPluginManager().registerEvents(new MCPlayerEventListener(guild, chatChannel, plugin.getConfig()), plugin);
        plugin.getServer().getPluginManager().registerEvents(new MCServerEventListener(plugin, guild, chatChannel, plugin.getConfig()), plugin);
    }
    
    // TODO: Add more useful commands to connect player with plugin
    public void registerMCCommands() {
        plugin.getCommand("dtminfo").setExecutor(new CommandInfo());
    }
}