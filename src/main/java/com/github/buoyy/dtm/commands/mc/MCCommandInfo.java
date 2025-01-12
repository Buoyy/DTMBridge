package com.github.buoyy.dtm.commands.mc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class MCCommandInfo implements CommandExecutor {
    private final JavaPlugin plugin;
    public MCCommandInfo(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender,@NotNull Command command,@NotNull String label,@NotNull String[] args) {
        sendInfo(sender);
        return true;
    }
    private void sendInfo(CommandSender sender) {
        sender.sendMessage("[DTMBridge] is a plugin which aims to make a bridge between \nMinecraft and Discord by claiming a Discord channel and \nlinking the chats. ");
        sender.sendMessage("Following commands are available: ");
        sendCommandInfo(Objects.requireNonNull(plugin.getCommand("dtminfo")), sender);
        sendCommandInfo(Objects.requireNonNull(plugin.getCommand("dtmlink")), sender);
        sendCommandInfo(Objects.requireNonNull(plugin.getCommand("dtmlinks")), sender);
        sender.sendMessage("Check out https://github.com/Buoyy/DTMBridge/ for more info. ");
    }
    private void sendCommandInfo(Command command, CommandSender sender) {
        sender.sendMessage(ChatColor.RED + command.getName()
                        +": "+ChatColor.GREEN + command.getUsage());
        sender.sendMessage(ChatColor.AQUA+command.getDescription());
    }
}