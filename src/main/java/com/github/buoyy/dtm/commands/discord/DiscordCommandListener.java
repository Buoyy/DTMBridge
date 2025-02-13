package com.github.buoyy.dtm.commands.discord;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.github.buoyy.dtm.utils.files.CustomYAML;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordCommandListener extends ListenerAdapter {
    private final FileConfiguration config;
    public DiscordCommandListener(CustomYAML yaml) {
        this.config = yaml.getConfig();
    }
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "plist" -> {
                String plist = "";
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    plist += p.getDisplayName() +'\n';
                }
                event.reply("The following players are currently online:\n"+plist).queue();
            }
            case "saves" -> {
                String saves = "";
                for (String i : config.getKeys(false)) {
                    saves += i + '\n';
                }
                event.reply("The following locations are currently saved:\n"+saves).queue();
            }
            case "saveinfo" -> {
                String key = event.getOption("name").getAsString();
                List<Integer> coords = config.getIntegerList(key+".coords");
                String msg = "Info for location "+key+":\n"
                            +"World: "+config.getString(key+".world")+'\n'
                            +"Coordinates: "+coords.get(0)+','+coords.get(1)+','+coords.get(2);
                event.reply(msg).queue();
            }
            default -> { return; }
        }
    }

}
