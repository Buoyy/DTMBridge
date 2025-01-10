package com.github.buoyy.dtm.utils;

import net.dv8tion.jda.api.entities.Member;
import org.bukkit.entity.Player;

// Account will be linked between Discord and Minecraft.
// Players will even be able to send quick DMs through Minecraft or Discord.
public class Account {
    private Player player;
    private Member user;
    private String pass;
    public Account(Player player, String pass) {
        this.player = player;
        this.pass = pass;
    }
/*    public static boolean hasAccount(Player player) {

    }
    public static boolean hasAccount(Member user) {

    }

 */
}
