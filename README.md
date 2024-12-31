# DTMBridge
A spigot plugin to link Discord and Minecraft via chat channels.

## Features:
- Bidirectional chat between Minecraft and Discord
  - link a Discord text channel and a SpigotMC (or a fork) server chat channel
- Server info messages:
  - started/stopped 
  - player join/leave
  - player advancements
- Commands: /dtminfo;

## Prerequisites:
- A Discord Server
- Your own Discord bot 
- A Spigot/Spigot fork server
- Minecraft
- Apache Maven (if building from source)
- Dev access on the Discord Server

## How to: Bot
- Go to the [Discord Dev Portal](https://discord.com/developers/docs/intro)
- Enable Message Content Intent in Bot
- See the [JDA Wiki](https://jda.wiki/using-jda/getting-started/#creating-a-discord-bot) to set up the bot
- Copy bot token
- Authorise and invite bot to desired Server


## How to: Build
- Clone the project 
- run "mvn clean package" in the root directory of project (with pom.xml)

## How to: Finalisation
- Copy/Cut dtm-bridge-1.0-SNAPSHOT.jar from target directory
- Paste in your Spigot server's plugins directory
- Run the server
- Copy Server ID, Channel ID, Bot token and paste into the config.
- Restart server
- Hope it works.

## Known issues:
- Config needs more refining.
- There's a bit more functionality to be added.

## Features to add:
- Console integration 
- Customisability of info messages (like server start, advancement etc) through config
- and more...
