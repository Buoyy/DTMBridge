package com.github.buoyy.dtm.commands.mc;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MCCommandHandler implements CommandExecutor {
    private final Map<String, ISubCommand> subCommandMap = new HashMap<>();
    public void registerSubCommand(String name, ISubCommand command) {
        subCommandMap.put(name.toLowerCase(), command);
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /dtm <save/saves/reload/delete>");
            return true;
        }
        ISubCommand subCommand = subCommandMap.get(args[0].toLowerCase());
        if (subCommand == null) {
            sender.sendMessage(ChatColor.DARK_RED + "Unknown subcommand!\n"+ChatColor.RED+"Usage: /dtm <save/saves/reload>");
            return true;
        }
        return subCommand.execute(sender, args);
    }
}
