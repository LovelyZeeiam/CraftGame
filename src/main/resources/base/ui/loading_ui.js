// UI

// ---------------- CONST DEFINATION ---------------------
var backColor = game.getColor(0.92, 0.86, 1, 1);

var texSplash = game.getTexture("splash");
var texLoadingAnim = game.getTexture("loading_anime");

var global = {
	alpha: 0,
	loading_progress: 0.0,
	loading_complete: false
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

	if(progressBarWidget.render_progress == 1.0 && !global.loading_complete) {
		game.sendMessage("COMPLETE", "");
		global.loading_complete = true;
	}

}

function onReceive(msg) {
	if(msg.title.equals("PROGRESS")) {
		global.loading_progress = parseFloat(msg.message);
	} else {
		var formattedText = game.compileFormattedText(msg.title);
		logTextWidget.text[logTextWidget.text.length] = formattedText;
	}

}
