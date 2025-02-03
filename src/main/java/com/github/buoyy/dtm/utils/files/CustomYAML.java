package com.github.buoyy.dtm.utils.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomYAML {
    private File file;
    private final String fileName;
    private FileConfiguration config;
    public CustomYAML(JavaPlugin plugin, String name) {
        this.fileName = name;
        createConfig(plugin);
    }
    public FileConfiguration getConfig() {
        return this.config;
    }
    private void createConfig(JavaPlugin plugin) {
        file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(fileName, false);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }
    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void reloadConfig() {
        if (!file.exists()) config = YamlConfiguration.loadConfiguration(file);
    }
}
