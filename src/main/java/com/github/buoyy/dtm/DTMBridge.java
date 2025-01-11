package com.github.buoyy.dtm;

import com.github.buoyy.dtm.utils.InitManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DTMBridge extends JavaPlugin {
    Manager manager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        initManager = new InitManager(this);
        if (!initManager.initJDA()) {
            getLogger().severe("Try editing the config. ");
            getLogger().severe("Then reload/restart the server.");
            getPluginLoader().disablePlugin(this);
            return;
        }
        if (!initManager.initGuildChannels()) {
            getLogger().severe("Try editing the config. ");
            getLogger().severe("Then reload/restart the server.");
            getPluginLoader().disablePlugin(this);
            return;
        }
        initManager.registerDiscordEvents();
        initManager.registerMCEvents();
        initManager.registerMCCommands();
    }

    @Override
    public void onDisable() {
        initManager.shutdownJDA();
    }
}