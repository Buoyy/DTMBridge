package com.github.buoyy.dtm.commands.discord;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.buoyy.dtm.DTMBridge;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordCommandListener extends ListenerAdapter {
    private final FileConfiguration locations, config;
    private final JDA jda;
    public DiscordCommandListener(JavaPlugin plugin, JDA jda) {
        this.locations = DTMBridge.getLocationsYaml().getConfig();
        this.config = plugin.getConfig();
        this.jda = jda;
    }
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!(event.getGuild() == jda.getGuildById(config.getString("guild-id")))) return;
        switch (event.getName()) {
            case "plist" -> {
                String plist = "";
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    plist += "\t*" + p.getDisplayName() + "*\n";
                }
                event.reply("**The following players are currently online:**\n"+plist).queue();
            }
            case "saves" -> {
                String saves = "";
                for (String i : locations.getKeys(false)) {
                    saves += "\t*" + i + "*\n";
                }
                event.reply("**The following locations are currently saved:**\n"+saves).queue();
            }
            case "saveinfo" -> {
                String key = event.getOption("name").getAsString();
                if (locations.contains(key)) {
                List<Integer> coords = locations.getIntegerList(key+".coords");
                String msg = "**Info for location "+key+":\n**"
                            +"\t*World:* ***"+locations.getString(key+".world")+"***\n"
                            +"\t*Coordinates:* ***"+coords.get(0)+", "+coords.get(1)+", "+coords.get(2)+"***";
                event.reply(msg).queue();
                } else {
                    event.reply("**No such location was found.**").queue();
                }
            }
            case "stop" -> {
                if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                    event.reply("**This command is admin-only!**");
                    return;
                }
                event.reply("***Stopping the server...***").queue();
                Bukkit.getServer().shutdown();
            }
            default -> { return; }
        }
    }
}
