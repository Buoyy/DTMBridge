package com.github.buoyy.dtm;

//My own imports
import com.github.buoyy.dtm.listeners.discord.DiscordListener;
import com.github.buoyy.dtm.listeners.mc.MCChatListener;
import com.github.buoyy.dtm.utils.Manager;

//JDA imports
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.requests.GatewayIntent;

//Spigot/Bukkit imports
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    Manager manager;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        manager = new Manager(this);
        if (!manager.initJDA()) {
            getPluginLoader().disablePlugin(this);
        }
        manager.registerDiscordEvents();
        manager.registerMCEvents();
    }
    
    @Override
    public void onDisable() {
        manager.shutdownJDA();
    }
}