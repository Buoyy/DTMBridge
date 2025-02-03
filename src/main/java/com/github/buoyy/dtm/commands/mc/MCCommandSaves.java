package com.github.buoyy.dtm.commands.mc;

import com.github.buoyy.dtm.utils.files.CustomYAML;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MCCommandSaves implements CommandExecutor {
    private final CustomYAML saves;
    public MCCommandSaves(CustomYAML saves) {
        this.saves = saves;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.GREEN + "Following saves are currently available: ");
            for (String i : saves.getConfig().getKeys(false)) {
                sender.sendMessage(ChatColor.RED + i);
            }
        } else if (args[0] != null) {
            String msg = String.format("World: %s\nX: %s\nY: %s\nZ: %s",
                    saves.getConfig().getString(args[0]+".world"),
                    saves.getConfig().getInt(args[0]+".x"),
                    saves.getConfig().getInt(args[0]+".y"),
                    saves.getConfig().getInt(args[0]+".z"));
            sender.sendMessage(msg);
        }
        return true;
    }
}
