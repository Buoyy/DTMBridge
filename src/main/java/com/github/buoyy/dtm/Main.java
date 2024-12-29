package com.github.buoyy.dtm;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.Bukkit;


public final class Main extends JavaPlugin {

    private JDA bot; String token; FileConfiguration config = getConfig();
    private boolean example;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        // Plugin startup logic
        example = config.getBoolean("example-setting");
        token = "Your bot token here";
        getLogger().info("Starting plugin..");
        try {
            bot = JDABuilder
                    .createLight(token)
                    .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                    .addEventListeners(new DiscordListener())
                    .build();
            bot.awaitReady();
            getLogger().info("Initialised bot successfully.");
        } catch (InvalidTokenException e) {
            getLogger().severe("Invalid bot token!");
            getPluginLoader().disablePlugin(this);
        } catch (Exception e) {
            getLogger().severe("Couldn't load bot: "+e.getMessage());
            getPluginLoader().disablePlugin(this);
        }
        getServer().getPluginManager().registerEvents(new MCChatListener(bot), this);
        if (example) {
            Bukkit.broadcastMessage("Setting was true.");
        } else Bukkit.broadcastMessage("Setting was false.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Disabling DTM...");
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
