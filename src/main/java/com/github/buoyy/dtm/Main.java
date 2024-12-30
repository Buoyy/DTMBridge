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
            getLogger().severe("Try editing the config. ");
            getLogger().severe("Then use /dtmreload");
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