package com.github.buoyy.dtm.utils;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;

public class ConsoleDiscordHandler extends ConsoleHandler {
    private final TextChannel consoleChannel;

    public ConsoleDiscordHandler(TextChannel consoleChannel) {
        this.consoleChannel = consoleChannel;
    }

    @Override
    public void publish(LogRecord record) {
        if (consoleChannel != null) {
            System.out.println("Got a log!");
            String message = getFormatter().format(record);
            consoleChannel.sendMessage(message).queue(
                    success -> {
                        System.out.println("Sent to discord");},
                    error -> {
                        System.out.println("error sending to discord");error.printStackTrace();}
            );
        }
    }

    @Override
    public void flush() { super.flush(); }

    @Override
    public void close() throws SecurityException { super.close(); }
}
