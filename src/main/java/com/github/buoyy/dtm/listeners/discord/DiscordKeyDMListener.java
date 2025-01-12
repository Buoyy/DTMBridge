package com.github.buoyy.dtm.listeners.discord;

import com.github.buoyy.dtm.utils.accounts.Account;
import com.github.buoyy.dtm.utils.accounts.AccountManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class DiscordKeyDMListener extends ListenerAdapter {
    private final AccountManager manager;
    public DiscordKeyDMListener(AccountManager manager) {
        this.manager = manager;
    }
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!(event.getAuthor().isBot()) && !(event.getAuthor().isSystem())) {
            String msg = event.getMessage().getContentRaw();
            for (Account account : manager.getAccounts()) {
                if (account.getKey().equals(msg)) {
                    account.setUser(event.getAuthor());
                    account.setLinked(true);
                    account.getPlayer()
                            .sendMessage("Linked successfully with"+account.getUser().getEffectiveName());
                    event.getChannel()
                            .sendMessage("Linked successfully with "+account.getPlayer().getDisplayName())
                            .queue();
                }
            }
        }
     }
}
