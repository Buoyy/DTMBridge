package com.github.buoyy.dtm.utils;
import com.github.buoyy.dtm.commands.mc.MCCommandReload;
import com.github.buoyy.dtm.commands.mc.MCCommandSave;
import com.github.buoyy.dtm.commands.mc.MCCommandSaves;
import com.github.buoyy.dtm.utils.files.CustomYAML;
import org.bukkit.plugin.java.JavaPlugin;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import com.github.buoyy.dtm.listeners.discord.DiscordChatListener;
import com.github.buoyy.dtm.listeners.mc.MCPlayerEventListener;
import com.github.buoyy.dtm.commands.mc.MCCommandInfo;
import com.github.buoyy.dtm.listeners.mc.MCServerEventListener;

import java.util.Objects;

public class MainManager {

    // Define all important stuff
    private final JavaPlugin plugin;
    private JDA jda;
    private Guild guild;
    private TextChannel chatChannel;
    private final String botToken;
    private final String chatChannelID, guildID;
    public MainManager(JavaPlugin plugin) {
        this.plugin = plugin;
        botToken = plugin.getConfig().getString("bot-token");
        chatChannelID = plugin.getConfig().getString("chat-channel-id");
        guildID = plugin.getConfig().getString("guild-id");
    }

    // Grab IDs from config, try to load bot and handle exceptions otherwise
    public boolean initJDA() {
        try {
            jda = JDABuilder
                    .createLight(botToken)
                    .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT) // Message gateway intents are most important
                    .build();
            jda.awaitReady();
        } catch (Exception e) {
            plugin.getLogger().severe("Couldn't initialize bot!: " + e.getMessage());
            plugin.getLogger().severe("In case of invalid ID(s), the config should be in the following format: ");
            plugin.getLogger().severe("id: \"the ID here\"");
            plugin.getPluginLoader().disablePlugin(plugin);
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
        if (jda != null) {
            if (guildID.isBlank() || chatChannelID.isBlank()) {
                plugin.getLogger().severe("Guild or Chat channel ID may be empty.");
                return false;
            }
            guild = jda.getGuildById(guildID);
            chatChannel = jda.getTextChannelById(chatChannelID);
            if (guild == null || chatChannel == null || !chatChannel.getGuild().equals(guild)) {
                plugin.getLogger().severe("Invalid Guild or Chat channel ID.");
                plugin.getLogger().severe("Guild: " + guildID);
                plugin.getLogger().severe("Chat channel: " + chatChannelID);
                return false;
            }
            plugin.getLogger().finest("Server and chat channel were connected to successfully.");
            return true;
        } else {
            plugin.getLogger().severe("Bot is not ready.");
            return false;
        }
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
    public void registerMCCommands(CustomYAML config) {
        Objects.requireNonNull(plugin.getCommand("dtminfo")).setExecutor(new MCCommandInfo(plugin));
        Objects.requireNonNull(plugin.getCommand("dtmsave")).setExecutor(new MCCommandSave(config));
        Objects.requireNonNull(plugin.getCommand("dtmsaves")).setExecutor(new MCCommandSaves(config.getConfig()));
        Objects.requireNonNull(plugin.getCommand("dtmreload")).setExecutor(new MCCommandReload(plugin, config));
    }
}