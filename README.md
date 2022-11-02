# CraftGame
A Minecraft-like game programmed in Java with LWJGL 3.
The game is in beta now. So if you have some suggestion, just leave in the comment.

By analyzing the code of Minecraft originally, I figured out loads of means to import to my game while improving my study. Currently I'm working on a project called `MyMinecraftClient`, which will also help to develop the game.

Another Winter in China is around the corner. Having had a try various programming pattern, it is time to back on the right track. There is also a list below indicating what I will attempt to do:

1. `glBufferData` instead of `glMapBuffer`. This can increase variety of rendering cubes by allowing them to use different Shaders and configs.
2. Adding threads: Main thread, Vertices generating thread, lighting thread, world providing thread [**These could be changed!**]
3. Start by single player instead of making server and client at the beginning, making sure multi-player game can use the same code as well.
4. Remove resource packs and turn to a \"Modding Pack\", which contains Java code to control the game behavior and resources to be loaded.
5. Spend my spring holiday in the game~
6. Implementation of Unity UI `Canvas Scaler` with three mode: `Constant pixel size`, `Scale with screen size`, `Constant physical size`

The game is going through a large-scale rewrite, which, therefore, cannot be run currently.

****

# How to Test the Game

#### Windows

To build, just type
> gradlew.bat build

then
> gradlew.bat run

There you go!

#### Linux && MacOS

I have no computer running Linux or MacOS.
but I'm convinced it be able to run by typing the following 3 commands.

> chmod +x gradlew

> ./gradlew build

> ./gradlew run

### Have fun!

****

