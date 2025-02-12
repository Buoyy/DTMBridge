package com.github.buoyy.dtm.commands.mc;

import org.bukkit.command.CommandSender;

public interface ISubCommand {
    boolean execute(CommandSender sender, String[] args);
}
