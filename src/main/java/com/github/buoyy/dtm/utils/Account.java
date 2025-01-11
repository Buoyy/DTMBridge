package com.github.buoyy.dtm.utils;

import net.dv8tion.jda.api.entities.Member;
import org.bukkit.entity.Player;

import java.util.UUID;

// Account will be linked between Discord and Minecraft.
// Players will even be able to send quick DMs through Minecraft or Discord.
public class Account {
    private Player player;
    private Member user;
    private String key;
    private boolean linked;
    
    // All accounts when generated shouldn't be linked. Will only be done when player tries to link it. 
    public Account(Player player, String key) {
        this.player = player;
        this.pass = pass;
        setLinked(false);
    }
    
    public static String genKey() {
        return UUID.randomUUID().toString();
    }
    
    public String getKey() {
        return key;
    }
    
    public void setLinked(boolean state) {
        linked = state;
    }
    
    public boolean getLinked() {
        return linked;
    }
/*
    public static boolean hasAccount(Player player) {

    }
    public static boolean hasAccount(Member user) {

    }

 */
}
