package com.github.buoyy.dtm.commands.mc;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;


public class MCCommandInfo implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equals("dtminfo")) {
            sender.sendMessage("[DTMBridge] is a plugin which aims to make a bridge between \nMinecraft and Discord by claiming a Discord channel and \nlinking the chats. ");
            sender.sendMessage("Check out https://github.com/Buoyy/DTMBridge/ for more info. ");
        }
        return true;
    }
}