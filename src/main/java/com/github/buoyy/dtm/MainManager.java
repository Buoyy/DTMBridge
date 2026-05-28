package com.github.buoyy.dtm;

import com.github.buoyy.dtm.commands.ReloadCommand;
import com.github.buoyy.dtm.listeners.discord.DiscordChatListener;
import com.github.buoyy.dtm.listeners.minecraft.MinecraftAdvancementListener;
import com.github.buoyy.dtm.listeners.minecraft.MinecraftChatListener;
import com.github.buoyy.dtm.listeners.minecraft.MinecraftLoginListener;
import com.github.buoyy.dtm.listeners.minecraft.MinecraftServerListener;
import com.github.buoyy.spigot_utils.command.CommandManager;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class MainManager 
{
    private JDA bot;
    private final DTM plugin;
    private final CommandManager commandManager;

    private Guild guild;
    private TextChannel chatChannel;

    public MainManager(DTM plugin)
    {
        this.plugin = plugin;
        this.commandManager = new CommandManager(plugin);
    }

    // Build the bot with required gateway intents
    public boolean initBot()
    {
        String botToken = plugin.getConfig().getString("bot-token");
        try 
        {
            bot = JDABuilder
                .createLight(botToken)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                .build();
                bot.awaitReady(); // TODO: Make it thread independent
            plugin.MSGR.logGood("Discord Bot initialized successfully!");
        }
        catch (Exception e)
        {
            plugin.MSGR.logError("Couldn't initialize bot!: "+e.getMessage());
            return false;
        }
        return true;
    }

    public void shutdownBot()
    {
        if (bot == null)
            return;
        try 
        {
            bot.shutdown();
            bot.awaitShutdown();
            plugin.MSGR.logGood("Discord Bot shut down successfully.");
        } 
        catch (Exception e) 
        {
            plugin.MSGR.logError("Couldn't shutdown bot!: "+e.getMessage());
        }
    }

    public boolean connectGuild()
    {
        if (bot == null)
        {
            plugin.MSGR.logError("Can't initialize Guild because Bot is not ready.");
            return false;
        }

        String guildID = plugin.getConfig().getString("guild-id");
        if (guildID.isBlank())
        {
            plugin.MSGR.logError("Guild ID may be empty.");
            return false;
        }

        guild = bot.getGuildById(guildID);
        if (guild == null)
        {
            plugin.MSGR.logError("Invalid Guild ID!\nID: "+guildID);
            return false;
        }
        plugin.MSGR.logGood("Initialized connection with Discord Server: "+guild.getName());

        return true;
    }

    public boolean connectChatChannel()
    {
        if (bot == null)
        {
            plugin.MSGR.logError("Can't initialize Chat Channel because Bot is not ready.");
            return false;
        }

        String channelID = plugin.getConfig().getString("chat-ch-id");
        if (channelID.isBlank())
        {
            plugin.MSGR.logError("Guild ID may be empty.");
            return false;
        }

        chatChannel = bot.getTextChannelById(channelID);
        if (chatChannel == null)
        {
            plugin.MSGR.logError("Invalid Chat Channel ID!\nID: "+channelID);
            return false;
        }
        plugin.MSGR.logGood("Initialized connection with Text Channel for chat: "+chatChannel.getName());

        return true;
    }

    public void registerDiscordListeners()
    {
        bot.addEventListener(new DiscordChatListener(this));
        plugin.MSGR.logInfo("Registered event listeners for Discord.");
    }

    public void registerMinecraftListeners()
    {
        plugin.getServer().getPluginManager().registerEvents(new MinecraftServerListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new MinecraftLoginListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new MinecraftChatListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new MinecraftAdvancementListener(this), plugin);
        plugin.MSGR.logInfo("Registered event listeners for Minecraft.");
    }

    public void registerMinecraftCommands()
    {
        commandManager.registerCommand("dtm-reload", new ReloadCommand(this));
        plugin.MSGR.logInfo("Registered commands for Minecraft.");
    }

// ----------------------------------------------------------------------------------------------------------------

    public Guild getGuild() 
    {
        return guild;
    }

    public TextChannel getChatChannel() 
    {
        return chatChannel;
    }

    public DTM getPlugin()
    {
        return plugin;
    }
}
