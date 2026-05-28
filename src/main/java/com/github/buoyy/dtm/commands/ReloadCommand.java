package com.github.buoyy.dtm.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.github.buoyy.dtm.MainManager;
import com.github.buoyy.spigot_utils.command.SimpleCommand;

public class ReloadCommand implements SimpleCommand
{
    private final MainManager manager;

    public ReloadCommand(MainManager manager)
    {
        this.manager = manager;
    }

    @Override
    public void execute(CommandSender sender, String[] args) 
    {
        manager.getPlugin().onDisable();
        manager.getPlugin().reloadConfig();
        manager.getPlugin().onEnable();  
    }

    @Override
    public List<String> getTabs(String[] args) 
    {
        return List.of();
    }

}
