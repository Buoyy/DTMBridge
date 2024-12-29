# DTMBridge
A spigot plugin to link Discord and Minecraft via chat channels.

## Prerequisites:
- A Discord Server
- Your own Discord bot 
- A Spigot/Spigot fork server
- Minecraft
- Apache Maven
- Dev access on the Discord Server

## How to: Bot
- Go to the [Discord Dev Portal](https://discord.com/developers/docs/intro)
- See the [JDA Wiki](https://jda.wiki/using-jda/getting-started/#creating-a-discord-bot) to set up the bot
- Copy bot token
- Authorise and invite bot to desired Server


## How to: Configuration
- Paste bot token in Main.java at correct location
- Paste chosen Channel ID in DiscordListener.java on line 12
- Do the same for MCChatListener.java on line 18
- run "mvn clean package" in the root directory of project (with pom.xml)

## How to: Finalisation
- Copy/Cut dtm-bridge-1.0-SNAPSHOT.jar from target directory
- Paste in your Spigot server's plugins directory
- Run the server
- Hope it works

## Known issues:
- I know that it's currently hardcoded. I plan to add a config.yml in the future.
- There's a lot of functionality to be added.

## Features to add:
- Console integration 
- Server has started/stopped integration
- Player join messages
- and more...
