package com.github.buoyy.dtm.commands.mc;

import com.github.buoyy.dtm.utils.files.CustomYAML;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class MCCommandReload implements ISubCommand {
    private final JavaPlugin plugin;
    private final CustomYAML saves;
    public MCCommandReload(JavaPlugin plugin, CustomYAML saves) {
        this.plugin = plugin;
        this.saves = saves;
    }
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        plugin.reloadConfig();
        saves.reload();
        sender.sendMessage("Reload complete.");
        return true;
    }
}
