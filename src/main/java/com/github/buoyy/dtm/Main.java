package com.github.buoyy.dtm;

import com.github.buoyy.dtm.utils.Manager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    Manager manager;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        manager = new Manager(this);
        if (!manager.initJDA()) {
            getPluginLoader().disablePlugin(this);
        }
        manager.registerDiscordEvents();
        manager.registerMCEvents();
    }
    
    @Override
    public void onDisable() {
        manager.shutdownJDA();
    }
}