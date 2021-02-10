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
but I'm convinced it be able to run by typing the following 3 commands.

> chmod +x gradlew

> ./gradlew build

> ./gradlew run

### Have fun!

# Develop Logs

#### Added

1. Game UI
2. World Generate and Biomes
3. World save and load.
4. multi-model blocks: stairs, slabs, fences
5. make do with the physical collision
6. daynight circle

#### DOINGS

1. Blocks: attachable (eg. sign), fence gate, doors<br/>
   Tip: Add a new class in charge of quads with different shaders or textures of framebuffers (can be used in text renderer in 3 dimension. Example: sign or text tag)
2. clouds

#### Plans

1. World render: multi-buffer to hold different blocks rendered with different shader<br/>
   Render the whole world in a framebuffer and then after processed, it can be rendered in screen. (eg. see light source at night)
2. Light Calculation<br/>
   Use 100 levels of light and save efforts of smooth lighting, thus improving reality feeling.<br/>
   Every light source has a parameter describing how much the light pierces. When the value increases, its start value of light level will increase.<br/>
   Also try light color and color changing when going through stained glass~ (Tips: Auto generate a texture which describe how the light changing when going through a stained class pane)<br/>
3. Audio
4. Resource pack
5. Settings
6. Arrange codes and add comments for modding
7. Texture (Because I can't draw a nice texture so,,, I use that of origin minecraft)
8. Multiplayer<br/>
   use library tcp of javaherobrine<br/>
   once accomplished, world update and render can finally be separated to diffrent thread and gameplay can be improved.
9. Physical: AABB Collision - TODO: Improve
10. Entity
11. little map
12. weather
14. Debug stick
   Can change block state

****
