package com.github.buoyy.dtm.utils;
import com.github.buoyy.dtm.commands.discord.DiscordCommandListener;
import com.github.buoyy.dtm.commands.mc.*;
import com.github.buoyy.dtm.utils.files.CustomYAML;
import org.bukkit.plugin.java.JavaPlugin;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.IntegrationType;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import com.github.buoyy.dtm.listeners.discord.DiscordChatListener;
import com.github.buoyy.dtm.listeners.mc.MCPlayerEventListener;
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
    public void registerDiscordEvents(CustomYAML yaml) {
        jda.addEventListener(new DiscordChatListener(guild, chatChannel, plugin.getConfig()));
        jda.addEventListener(new DiscordCommandListener(plugin, jda));
    }

    // Check the corresponding classes for more info
    public void registerMCEvents() {
        plugin.getServer().getPluginManager().registerEvents(new MCPlayerEventListener(guild, chatChannel, plugin.getConfig()), plugin);
        plugin.getServer().getPluginManager().registerEvents(new MCServerEventListener(plugin, guild, chatChannel, plugin.getConfig()), plugin);
    }

    // TODO: Add more useful commands to connect player with plugin
    public void registerMCCommands(CustomYAML yaml) {
        MCCommandHandler handler = new MCCommandHandler();
        handler.registerSubCommand("save", new MCCommandAddSave(yaml));
        handler.registerSubCommand("saves", new MCCommandSaves(yaml));
        handler.registerSubCommand("reload", new MCCommandReload(plugin, yaml));
        handler.registerSubCommand("delete", new MCCommandDeleteSave(yaml));
        Objects.requireNonNull(plugin.getCommand("dtm")).setExecutor(handler);
    }

    public void registerDiscordCommands() {
        CommandListUpdateAction commands = jda.updateCommands();
        commands.addCommands(Commands.slash("plist", "List all currently online players.")
                        .setContexts(InteractionContextType.GUILD)
                        .setIntegrationTypes(IntegrationType.GUILD_INSTALL));
        commands.addCommands(Commands.slash("saves", "List all current location saves.")
                        .setContexts(InteractionContextType.GUILD)
                        .setIntegrationTypes(IntegrationType.GUILD_INSTALL));
        commands.addCommands(Commands.slash("saveinfo", "Sends info of given location save.")
                        .addOption(OptionType.STRING, "name", "which save to get info from.", true)
                        .setContexts(InteractionContextType.GUILD));
        commands.addCommands(Commands.slash("stop", "Stop the server. Admin only command.")
                        .setContexts(InteractionContextType.GUILD)
                        .setIntegrationTypes(IntegrationType.GUILD_INSTALL)
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR)));
        commands.queue();
    }
}