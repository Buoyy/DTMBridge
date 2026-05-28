package com.github.buoyy.dtm;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.buoyy.spigot_utils.Messenger;

public class DTM extends JavaPlugin
{
    public final Messenger MSGR = new Messenger("DTM");
    private final MainManager manager = new MainManager(this);

    @Override
    public void onEnable() 
    {
        saveDefaultConfig();
        if (!manager.initBot())
            return;
        if (!manager.connectGuild())
        {
            getPluginLoader().disablePlugin(this);
            return;
        }
        if (!manager.connectChatChannel())
        {
            getPluginLoader().disablePlugin(this);
            return;
        }
        manager.registerDiscordListeners();
        manager.registerMinecraftListeners();
        manager.registerMinecraftCommands();
    }

    @Override
    public void onDisable() 
    {
        manager.shutdownBot();
    }
}
