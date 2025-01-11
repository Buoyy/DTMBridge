package com.github.buoyy.dtm.listeners;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import com.github.buoyy.dtm.utils.TempAccount;
import com.github.buoyy.dtm.utils.LinksManager;

public class DiscordLinkListener extends ListenerAdapter {
    @Override
    private final LinksManager manager;
    public DiscordLinkListener(LinksManager manager) {
        this.manager = manager;
    }
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.getAuthor().isSystem()) return;
        // Now we will get the account and see if the message given is exactly same as the passkey. If it is, we can setLinked(true), and move forward. 
        // We will have a list of accounts in the Manager which keeps track of all the account links.
        if (!event.getChannelType() == ChannelType.PRIVATE) return;
        String msg = event.getMessage().getContentRaw();
        
    }
}