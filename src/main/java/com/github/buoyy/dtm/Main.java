package com.github.buoyy.dtm;

import com.github.buoyy.dtm.discord.DiscordListener;
import com.github.buoyy.dtm.mc.MCChatListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;


public final class Main extends JavaPlugin {

    private JDA bot;
    private final FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        // Saving default config and loading important data
        saveDefaultConfig();
        String guildID = config.getString("server-id");
        String channelID = config.getString("channel-id");
        String botToken = config.getString("bot-token");
        Guild guild; TextChannel channel;
        // Trying to set up bot
        try {
            if (guildID == null) {
                getLogger().severe("Provided server ID is invalid.");
                getPluginLoader().disablePlugin(this);
                return;
            } if (channelID == null) {
                getLogger().severe("Provided channel ID is invalid.");
                getPluginLoader().disablePlugin(this);
                return;
            }
            bot = JDABuilder
                    .createLight(botToken)
                    .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                    .build();
            bot.awaitReady();
            getLogger().info("Initialised bot successfully.");
            guild = bot.getGuildById(guildID);
            channel = bot.getTextChannelById(channelID);
            
            bot.addEventListener(new DiscordListener(guild, channel));
        getServer().getPluginManager().registerEvents(new MCChatListener(guild, channel), this);


        } catch (InvalidTokenException e) {
            getLogger().severe("Invalid bot token!");
            getPluginLoader().disablePlugin(this);
        } catch (Exception e) {
            getLogger().severe("Couldn't load bot: " + e.getMessage());
            getPluginLoader().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (bot != null) {
            try {
                bot.shutdownNow();
                bot.awaitShutdown();
                getLogger().info("Bot was shut down successfully.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
