package com.github.buoyy.dtm.commands.mc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.buoyy.dtm.utils.MainManager;

public class MCCommandReload implements CommandExecutor {
    private final MainManager manager;
    public MCCommandReload(MainManager manager) {
        this.manager = manager;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("Reloading all files...");
        manager.reloadPlugin();
        sender.sendMessage("Reloaded!");
        return true;
    }    
}
