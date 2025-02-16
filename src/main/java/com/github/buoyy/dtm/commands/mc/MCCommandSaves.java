package com.github.buoyy.dtm.commands.mc;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.github.buoyy.dtm.utils.files.CustomYAML;

public class MCCommandSaves implements MCSubCommand {
    private final Map<String, MCSubCommand> subSubs = new HashMap<>();
    public MCCommandSaves(CustomYAML yaml) {
        subSubs.put("add", new MCCommandAddSave(yaml));
        subSubs.put("delete", new MCCommandDeleteSave(yaml));
        subSubs.put("list", new MCCommandListSaves(yaml));
        subSubs.put("info", new MCCommandSavesInfo(yaml));
    }
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.GREEN+"Usage: /dtm saves <add/list/info/delete>");
            return true;
        }
        MCSubCommand subSub = subSubs.get(args[1].toLowerCase());
        if (subSub == null) {
            sender.sendMessage(ChatColor.DARK_RED + "Unknown subcommand!\n"+ChatColor.RED+"Usage: /dtm saves <add/list/info/delete>");
            return true;
        }
        subSub.execute(sender, args);
        return true;
    }
    
}
