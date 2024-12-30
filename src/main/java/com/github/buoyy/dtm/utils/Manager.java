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
import com.github.buoyy.dtm.commands.CommandInfo;

public class Manager {

    // Define all important stuff
    private JavaPlugin plugin;
    private JDA jda;
    private Guild guild;
    private TextChannel channel;

    public Manager(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    
    // Grab IDs from config, try to load bot and handle exceptions otherwise
    public boolean initJDA() {
        String botToken = plugin.getConfig().getString("bot-token");
        String channelID = plugin.getConfig().getString("channel-id");
        String guildID = plugin.getConfig().getString("server-id");
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
        jda.addEventListener(new DiscordListener(guild, channel));
    }
    
    public void registerMCEvents() {
        plugin.getServer().getPluginManager().registerEvents(new MCChatListener(guild, channel), plugin);
    }
    
    // TODO: Add more useful commands to connect player with plugin
    public void registerMCCommands() {
        plugin.getCommand("dtminfo").setExecutor(new CommandInfo());
    }
}