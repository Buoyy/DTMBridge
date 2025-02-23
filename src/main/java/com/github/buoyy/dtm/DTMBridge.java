package com.github.buoyy.dtm;

import com.github.buoyy.dtm.utils.MainManager;
import com.github.buoyy.dtm.utils.files.CustomYAML;
import org.bukkit.plugin.java.JavaPlugin;

public class DTMBridge extends JavaPlugin {
    private MainManager manager;
    private final static CustomYAML locations = new CustomYAML("locations");
    @Override
    public void onEnable() {
        locations.getConfig().options().copyDefaults(true);
        locations.save();
        saveDefaultConfig();
        manager = new MainManager(this);
        if (!manager.initJDA()) {
            getLogger().severe("Try editing the config. ");
            getLogger().severe("Then reload/restart the server.");
            return;
        }
        if (!manager.initGuildChannels()) {
            getLogger().severe("Try editing the config. ");
            getLogger().severe("Then reload/restart the server.");
            return;
        }
        manager.registerDiscordEvents(locations);
        manager.registerMCEvents();
        manager.registerDiscordCommands();
        manager.registerMCCommands(locations);
    }

    @Override
    public void onDisable() {
        manager.shutdownJDA();
    }
    public static CustomYAML getLocationsYaml() {
        return locations;
    }
}