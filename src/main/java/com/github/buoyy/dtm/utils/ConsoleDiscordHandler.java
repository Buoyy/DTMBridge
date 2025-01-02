package com.github.buoyy.dtm.utils;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ConsoleDiscordHandler extends Handler {
    private final TextChannel channel;

    public ConsoleDiscordHandler(TextChannel channel) {
        this.channel = channel;
    }

    @Override
    public void publish(LogRecord record) {
        if (channel != null && record.getMessage() != null) {
            String message = String.format("[%s] %s", record.getLevel(), record.getMessage());
            try {
            channel.sendMessage(message).queue(null, throwable -> {
                System.err.println("Failed to send log message to Discord: " + throwable.getMessage());
            });
        } catch (Exception e) {
            System.err.println("Error while publishing log to Discord: " + e.getMessage());
            }   
        }
    }

    @Override
    public void flush() {
        // No need to implement
    }

    @Override
    public void close() throws SecurityException {
        // No need to implement
    }
}