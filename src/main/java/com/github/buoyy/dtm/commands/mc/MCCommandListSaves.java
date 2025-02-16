package com.github.buoyy.dtm.commands.mc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.buoyy.dtm.utils.files.CustomYAML;

public class MCCommandListSaves implements MCSubCommand {
    private final FileConfiguration config;
    public MCCommandListSaves(CustomYAML yaml) {
        this.config = yaml.getConfig();
    }
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.GREEN+"The following saves are currently available: ");
        for (String i : config.getKeys(false)) {
                sender.sendMessage(ChatColor.AQUA+i);
            }
        return true;
    }

}
