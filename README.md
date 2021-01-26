# CraftGame
a minecraft-like game programmed in Java with LWJGL 3.
The game is in beta now. So if you have some suggestion, just leave in the comment.
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
but I'm convinced it be able to run by typing the following 2 commands.

> ./gradlew build
> ./gradlew run

#### Have fun!

# Develop Logs

#### Added

1. Game UI
2. World Generate and Biomes
3. World save and load.

#### DOINGS

1. Blocks: stairs, slabs, attachable (eg. sign), fences, fence gate, doors
    Tip: Add a new class in charge of quads with different shaders or textures of framebuffers (can be used in text renderer in 3 dimension. Example: sign or text tag)

#### Plans

1. World render: multi-buffer to hold different blocks rendered with different shader
   Render the whole world in a framebuffer and then after processed, it can be rendered in screen. (eg. see light source at night)
2. Light Calculation
   Use 100 levels of light and save efforts of smooth lighting, thus improving reality feeling.
   Every light source has a parameter describing how much the light pierces. When the value increases, its start value of light level will increase.
   Also try light color and color changing when going through stained glass~ (Tips: Auto generate a texture which describe how the light changing when going through a stained class pane)
3. Audio
4. Resource pack
5. Settings
6. Arrange codes and add comments for modding
7. Texture (Because I can't draw a nice texture so,,, I use that of origin minecraft)
8. Multiplayer
   use library tcp of javaherobrine
   once accomplished, world update and render can finally be separated to diffrent thread and gameplay can be improved.
9. Physical: AABB Collision
10. Entity
   So much difficulty in bones~
11. little map
12. clouds, day night cycle
13. Data: Model Data
   Json Format. Store in the same way as Minecraft stores recipes

****
