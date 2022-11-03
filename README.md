![Splash](./splash/splash.png)

# Minecraft-Classic-Remake

A Minecraft Classic remake programmed in Java 17 with LWJGL 3.

During the last several months, I attempted to write a client for Minecraft Java 1.19.2, which helped me better understand how the network is programmed. So I just copy the source code base in that project and paste it here in `xueli.game2.network` (There seems to be no other completely unfamiliar protocol :} ).

By the way, to separate the packet into `game` and `game2` because the code about my game utilities is optimized to adapt to things like resource packs or multi-renderer for sub-chunks and so on.

This goal of this fork is to program a basic winnable game based on the Classic Minecraft color and some terrain generation in its later version, around `a1.0.4`. The resources is extracted from Minecraft a1.2.6.

****

# How to Test the Game

#### Windows

To build, just execute

> gradlew.bat build

then

> gradlew.bat run

There you go!

#### Linux && MacOS

I have no computer running MacOS, but I got a dual system, Windows and Ubuntu.
I'm convinced it be able to run by typing the following 3 commands.

> chmod +x gradlew
> 
> ./gradlew build
> 
> ./gradlew run

### Have fun!

****
