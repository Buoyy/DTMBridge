package com.github.buoyy.dtm.commands.mc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;

public class MCCommandSave implements CommandExecutor {

    public final JDA jda;
    public MCCommandSave(JDA jda) {
        this.jda = jda;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) sender.sendMessage("Player command only.");
        if (args.length == 0) sender.sendMessage("Name of the location was not provided");
        Player p = (Player) sender;
        int[] coords = {p.getLocation().getBlockX(),
                        p.getLocation().getBlockY(),
                        p.getLocation().getBlockZ()};

        // Note: save the discord message by ID
        return true;
    }
}
