package com.github.buoyy.dtm;

import com.github.buoyy.dtm.utils.Manager;
import org.bukkit.plugin.java.JavaPlugin;

public class DTMBridge extends JavaPlugin {
    Manager manager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        manager = new Manager(this);
        if (!manager.initJDA()) {
            getLogger().severe("Try editing the config. ");
            getLogger().severe("Then reload/restart the server.");
            getPluginLoader().disablePlugin(this);
            return;
        }
        if (!manager.initGuildChannels()) {
            getLogger().severe("Try editing the config. ");
            getLogger().severe("Then reload/restart the server.");
            getPluginLoader().disablePlugin(this);
            return;
        }
        manager.registerDiscordEvents();
        manager.registerMCEvents();
        manager.registerMCCommands();
    }

    @Override
    public void onDisable() {
        manager.shutdownJDA();
    }
}