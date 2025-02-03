package com.github.buoyy.dtm.commands.mc;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class MCCommandSave implements CommandExecutor {

    private final TextChannel channel;
    private final JavaPlugin plugin;
    public MCCommandSave(TextChannel channel, JavaPlugin plugin) {
        this.channel = channel;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) sender.sendMessage("Player command only.");
        if (args.length == 0) sender.sendMessage("Name of the location was not provided");
        assert sender instanceof Player;
        Player p = (Player) sender;
        plugin.getConfig().addDefault(args[0]+".world", p.getWorld().getName());
        plugin.getConfig().addDefault(args[0]+".x", Integer.toString(p.getLocation().getBlockX()));
        plugin.getConfig().addDefault(args[0]+".y", Integer.toString(p.getLocation().getBlockY()));
        plugin.getConfig().addDefault(args[0]+".z", Integer.toString(p.getLocation().getBlockZ()));
        plugin.saveConfig();
        p.sendMessage(ChatColor.AQUA + "Location "+ ChatColor.GREEN + args[0] + ChatColor.AQUA + " was successfully set as a save.");
        channel.sendMessage("Location **"+ args[0] + "** was successfully set as a save.").queue();
        // Note: save the discord message by ID
        return true;
    }
}
