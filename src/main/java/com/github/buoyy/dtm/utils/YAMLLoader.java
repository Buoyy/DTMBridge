package com.github.buoyy.dtm.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class YAMLLoader {
    private final JavaPlugin plugin;
    private File file;
    private FileConfiguration config;
    public YAMLLoader(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    public FileConfiguration getConfig() {
        return config;
    }
    public void createConfig() {
        file = new File(plugin.getDataFolder(), "links.yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource("links.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }
    public void saveConfig() {
      if (file.exists()) config.save(file);
    }
}
