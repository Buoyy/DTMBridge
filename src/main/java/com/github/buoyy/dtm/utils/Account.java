package com.github.buoyy.dtm.utils;

import net.dv8tion.jda.api.entities.Member;
import org.bukkit.entity.Player;

import java.util.UUID;

// Account will be linked between Discord and Minecraft.
// Players will even be able to send quick DMs through Minecraft or Discord.
public class Account {
    private final Player player;
    private final Member user;
    private final String key;
    private boolean linked;
    
    // All accounts when generated shouldn't be linked. Will only be done when player tries to link it. 
    public Account(Player player, Member user) {
        this.player = player;
        this.user = user;
        this.key = genKey();
        this.linked = false;
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
    
    public boolean isLinked() {
        return linked;
    }
    
    public void setPlayer(Player player) {
        this.player = player:
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public void setUser(Member user) {
        this.user = user;
    }
    public Member getUser() {
        return user;
    }
}
