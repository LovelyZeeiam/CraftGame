# CraftGame Technical Tutorial

CraftGame is a game that enables all the developers to create their own world, which can be implemented by the colorful
functions we developed. With this tutorial written carefully and sincerely, you are enabled to find all the useful
information you want hopefully. If you open this file with Typora, which I have been using for a long time, or whatever
markdown editor support "[TOC]" tag, you can find the table of contents below.

[TOC]

PS: I wrote this tutorial when I developed the game from scratch, playing a vital part in the design of the function~

------

## Formatted Text

In the game, the text renderer can identify specified characters and change the styles and the colors of the text, led
by a special character '§'. All the resource can be formatted as a formatted text. The following examples can help you
understand how this works:

|    Code     |                         Description                          | Example  |
| :---------: | :----------------------------------------------------------: | :------: |
| §[\*,\*,\*] | Set text color. The three color values are limited between 0 and 9, indicating the color value of R, G, B | §[2,3,3] |
|    §[*]     | Set text style. Currently, we have 7 candidate styles, which can be changed through the English character between the square brackets according to the table below. |   §[a]   |

### Text Style

| Character |    Description     |
| :-------: | :----------------: |
|     a     |        Bold        |
|     b     |      Italics       |
|     c     |   Shadow effect    |
|     d     |   Lighted effect   |
|     e     |     Underline      |
|     f     |    Delete line     |
|     g     | restore the style. |

## Resource Pack

The game can be easily modified and beatified with the resource pack. Here is the instruction of building a brand new
resource pack yourselves~

There are several folders containing different kind of resources, which are \"textures\" stroing textures, \"lang\"
storing language files, \"audios\" storing audios used in game. And also there contains a \"Manifest.json\" in the root
folder, which will be also introduced below.

### Fonts

Fonts in game will be in the root of the pack named \"font.ttf\".

### Manifest

The manifest file in Json format shows the game what the basic information of the pack is and which version it can
support. It consists of the name and the description of the pack. A code example:

```json
{
  "version": 0,
  "manifest": {
	"name": "§[a]§[c]§[d]Minecraft§[g]",
	"description": "Vanilla"
  }
}
```

Also there is a icon file named \"pack_icon.png\" in the root folder. It is optional, and game will use its default pack
icon on condition that there is no icon file.

### Textures

Textures are stored in the \"textures\" folder. And a \"textures.json\" is presented to inform the game of the type of
the texture and how it will be loaded. Apparently it is in Json format, with a root compound tag named \"textures\"
acting like a list. All the elements in this tag whose key is its name space can be compound tag or just a single string
tag. If it is a string tag, the game will consider this resource as a normal texture and load it as normal as you do
when you begin with texturing in OpenGL xD~. And all the tags you will probably use are listed below.

|  Key  |     Type     |                         Description                          |               Default               |
| :---: | :----------: | :----------------------------------------------------------: | :---------------------------------: |
| path  |    String    | The path of the texture relative to the directory of the \"textures.json\" | (So you think it's optional right?) |
|  gui  |   Boolean    |         Whether the texture is used in GUI painting.         |                false                |
| flags | String Array | Indicate how the game should use the texture. Optional Values: repeat_x, repeat_y, flipy[Not support if !gui], premultiplied[Not support if !gui], nearest, mipmap. |                 []                  |

On condition that the path is an instance of Json object, the game will consider the texture as a atlas texture. It will
automatically stitch each of the texture to a bigger square texture atlas using the size indicated in the Json object
named \"size\" and store an entry composed of the key of each texture and the UV data relevant.

An example:

```Json
{
	"textures": {
		"splash": {
			"path": "splash/splash.jpg",
			"gui": true,
			"flags": [
				"nearest"
			]
		},
		"logo": {
			"path": "splash/CraftGame.png",
			"gui": true
		},
		"blocks": {
			"path": {
				"bedrock": "blocks/bedrock.png",
				"brick": "blocks/brick.png",
				"cobblestone": "blocks/cobblestone.png",
				"diamond_ore": "blocks/diamond_ore.png",
				"dirt": "blocks/dirt.png",
				"dirt_podzol_side": "blocks/dirt_podzol_size.png",
				"dirt_podzol_top": "blocks/dirt_podzol_top.png",
				"grass_block_top": "blocks/grass_block_top.png",
				"grass_block_side": "blocks/grass_block_side.png"
			},
			"size": 16,
			"flags": [
				"nearest"
			]
		}
	}
}
```

### UI

The user interface of the game is based on Javascript, to be compiled with Rhino, a Javascript engine library developed
by Mozilla. Each script contains a constant named \"game\", used to invoke drawing method or access some data of the
game.

Each script consists of three methods that are essential due to its being invoked by the game. They are \"init\" method
to be invoked when the UI is presented at the first time, \"onTick\" method to be invoked when the game thinks that it
is high time that the UI is drew, \"onRecieve\" method with a parameter represented a message the game sends. The last
method will be invoked when the game sends a UI message.

And, to make the UI making easy and not complicated, the game also contains a \"BasicUI.js\" file that will be included
in every UI script. The following file represented contains many of their usage.

An example of a UI is like this: ( loading_ui.js )

```javascript
// UI

// ---------------- CONST DEFINATION ---------------------
var backColor = game.getColor(0.92, 0.86, 1, 1);

var texSplash = game.getTexture("splash");
var texLoadingAnim = game.getTexture("loading_anime");

var global = {
	alpha: 0,
	loading_progress: 0.0
};

// ---------------- WIDGET DEFINATION ---------------------
var splashImageWidget = {
	w: () => 200.0 * game.getScale(),
	h: () => toNumber(splashImageWidget.w),
	x: () => (game.getWidth() - toNumber(splashImageWidget.w)) / 2,
	y: () => (game.getHeight() - toNumber(splashImageWidget.h)) / 2 - 120.0 * game.getScale(),
	image: {
		tex: texSplash
	},
	border: {
		color: game.getColor(0,0,0.54,1),
		width: () => 4 * game.getScale()
	}
};

var progressBarWidget = {
	w: () => game.getWidth() * 0.6,
	h: () => 20.0 * game.getScale(),
	x: () => (game.getWidth() - toNumber(progressBarWidget.w)) / 2,
	y: () => (game.getHeight() - toNumber(progressBarWidget.h)) / 2 + 50.0 * game.getScale(),
	border: {
    	color: game.getColor(0,0,0.54,1),
    	width: () => 3 * game.getScale()
    },
    progress_padding: () => 2.0 * game.getScale(),
    progress: 0.0,
    showProgressText: (progress) => progress + " %",
    progressTextColor: game.getColor(0.5,0.5,0.64,1)
};

var loadingAnimWidget = {
	w: () => 180.0 * game.getScale(),
	h: () => 180.0 * game.getScale(),
	x: () => toNumber(progressBarWidget.x) + toNumber(progressBarWidget.w) - toNumber(loadingAnimWidget.w),
	y: () => toNumber(progressBarWidget.y) + toNumber(progressBarWidget.h) + 20.0 * game.getScale(),
	image: {
		tex: texLoadingAnim,
		animated: {
			size: 100,
			delay: 100
		}
	},
};

var memoryOccupationWidget = {
	w: () => toNumber(loadingAnimWidget.w),
	h: () => 10.0 * game.getScale(),
	x: () => toNumber(loadingAnimWidget.x),
	y: () => toNumber(loadingAnimWidget.y) + toNumber(loadingAnimWidget.h) - 10.0 * game.getScale(),
	border: {
    	color: game.getColor(0.6,0.6,0.84,1),
    	width: () => 1 * game.getScale()
    },
	progress_padding: () => 1.0 * game.getScale(),
    progress: 0.0,
	showProgressText: (progress) => "Memory: " + progress + " %",
    progressTextColor: game.getColor(0.05,0.05,0.24,1)
};

var logTextWidget = {
    x: () => toNumber(progressBarWidget.x),
    y: () => toNumber(progressBarWidget.y) + toNumber(progressBarWidget.h) + 8.0 * game.getScale(),
	text: [
	],
	turn_inverted: true,
	color: game.getColor(0.4,0.6,0.5,1.0),
	formatted: true,
	linebreak: () => 1 * game.getScale(),
	size: () => 17 * game.getScale(),
	align: [ "left", "top" ]
};

// ---------------- ANIMATION DEFINATION ---------------------

var introAnim = {
	target: global,
	type: "linear",
	duration: 1000,
	params: {
		alpha: 1.0
	}
};

var splashImageWidget_BeatAnim = {
  	target: splashImageWidget,
  	type: "sin",
  	loop: "alternative",
  	duration: 2000,
  	params: {
  		w: () => 240.0 * game.getScale()
  	}
  };
  
// ---------------- API INPLEMENTATION ---------------------

function init() {
	anime(introAnim);
	anime(splashImageWidget_BeatAnim);

}

function onTick() {
	guiTick();

	clearColor(backColor);
	game.setGlobalAlpha(global.alpha);

	drawImage(splashImageWidget);
	drawBorder(splashImageWidget);

	progressBarWidget.progress = global.loading_progress;
	drawProgressBar(progressBarWidget);
	
	drawImage(loadingAnimWidget);
	
	memoryOccupationWidget.progress = parseFloat(game.getOccupiedMemory()) / game.getTotalMemory();
	drawProgressBar(memoryOccupationWidget);

	drawTextWidget(logTextWidget);

}

function onReceive(msg) {
	if(msg.title.equals("PROGRESS")) {
		global.loading_progress = parseFloat(msg.message);
	} else {
		var formattedText = game.compileFormattedText(msg.title);
		logTextWidget.text[logTextWidget.text.length] = formattedText;
	}

}

```

