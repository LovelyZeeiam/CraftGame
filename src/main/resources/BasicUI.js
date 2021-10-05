// ----------------- UI RENDER ------------------

function clearColor(r,g,b,a) {
	var width = game.getWidth();
    var height = game.getHeight();
	drawQuad(0,0,width,height,game.getColor(r,g,b,a));
}

function clearColor(c) {
	var width = game.getWidth();
    var height = game.getHeight();
   	drawQuad(0,0,width,height,c);
}

function drawQuad(x,y,w,h,color) {
	game.beginPath();
	game.rect(x,y,w,h);
	game.fillColor(color);
	game.fill();
}

function drawTextWidget(widget) {
	var x = toNumber(widget.x);
    var y = toNumber(widget.y);
	var align = parseAlign(widget.align);
	var size = toNumber(widget.size);
	var linebreak = widget.linebreak == undefined ? 0.0 : toNumber(widget.linebreak);
	var texts = widget.text;
	var color = widget.color;
	var formatted = widget.formatted != undefined && widget.formatted;

	if(typeof texts === "string") {
		texts = [ texts ];
	}

	if(widget.turn_inverted != undefined && widget.turn_inverted) {
		for(var i = texts.length - 1;i >= 0;i--) {
        	var t = texts[i];

        	if(formatted) {
        		game.drawFormattedText(t,x,y,size,color,align);
        	} else {
        		drawText(t, x,y,size,color,align);
        	}

        	y += linebreak + size;

        }
	} else
		for(var i in texts) {
			var t = texts[i];

			if(formatted) {
				game.drawFormattedText(t,x,y,size,color,align);
			} else {
				drawText(t, x,y,size,color,align);
			}

			y += linebreak + size;

		}

}

function drawText(text, x,y, size, color, align) {
	game.setFont(game.getFont());
	game.setFontSize(size);
	game.setTextAlign(parseAlign(align));
	game.fillColor(color);
	return game.text(x,y,text);
}

function parseAlign(align) {
	var gameAlign = [];
    if(typeof align === "object") {
    	for(var i in align) {
    		gameAlign[i] = game.getTextAlign(align[i]);
    	}
    } else if(typeof align === "string") {
    	gameAlign[0] = game.getTextAlign(align);
    }
    return gameAlign;
}

function drawImage(widget) {
	var x = toNumber(widget.x);	
	var y = toNumber(widget.y);
	var w = toNumber(widget.w);
	var h = toNumber(widget.h);
	var angle = (widget.image.angle !== undefined) ? widget.image.angle : 0.0;
	var alpha = (widget.image.alpha !== undefined) ? widget.image.alpha : 1.0;
	var tex = widget.image.tex;
	
	var paint = undefined;
	
	var animated = widget.image.animated;
	if(animated == undefined) {
		paint = game.imagePattern(x,y,w,h,0,alpha,tex);
	} else {
		var size = animated.size;
		var delay = animated.delay;
		
		var allCount = parseInt(tex.getHeight() / size);
		var thisImageIndex = parseInt(game.time() / delay) % allCount;
		
		var ratio = w / tex.getWidth();
		
		paint = game.imagePattern(x,y - thisImageIndex * size * ratio,w,tex.getHeight() * ratio,0,alpha,tex);
	}

	game.beginPath();
    game.rect(x,y,w,h);
   	game.fillPaint(paint);
   	game.fill();

}

function drawBorder(widget) {
	var x = toNumber(widget.x);
    var y = toNumber(widget.y);
    var w = toNumber(widget.w);
    var h = toNumber(widget.h);
    var thick = widget.border.width();
    var color = widget.border.color;

    drawQuad(x - thick, y - thick, w + thick, thick, color);
    drawQuad(x - thick, y - thick, thick, h + thick, color);
    drawQuad(x - thick, y + h, w + thick, thick, color);
    drawQuad(x + w, y - thick, thick, h + thick * 2, color);

}

function drawProgressBar(widget) {
	progressBarAnimTick(widget);
	drawBorder(widget);

	var x = toNumber(widget.x);
    var y = toNumber(widget.y);
    var w = toNumber(widget.w);
    var h = toNumber(widget.h);
    var padding = toNumber(widget.progress_padding);

	var qualifiedProgress = parseInt(widget.progress * 100.0 * 100.0) / 100.0;
	drawQuad(x + padding,y + padding,(w - 2 * padding) * widget.render_progress,h - 2 * padding,widget.border.color);
	if(widget.showProgressText !== undefined)
		drawText(widget.showProgressText(qualifiedProgress), x + w / 2.0, y + h / 2.0, h - padding, widget.progressTextColor, [ "center", "middle" ]);

}

function progressBarAnimTick(widget) {
	if(widget.render_progress === undefined) {
		widget.render_progress = 0.0;
	}

	var delta = widget.progress - widget.render_progress;
	widget.render_progress += 0.004 * delta * time_delta;

	if(widget.render_progress > 0.9999)
		widget.render_progress = 1.0;

}

function guiTick() {
	anim_tick();
	timer_tick();
}

// ----------------- ANIM BINDER ------------------
var anims = {};
var anim_count = 0;

function anime(anim) {
	var params_from = {};
	for(var p in anim.params) {
		params_from[p] = anim.target[p];
	}

	anims[anim_count] = {
		target: anim.target,
		params_from: params_from,
		params: anim.params,
		duration: anim.duration,
		type: anim.type,
		loop: anim.loop,
		then: anim.then
	};
	anim_count++;
	return anim_count;
}

function anim_tick() {
	for(var i in anims) {
		var animObj = anims[i];

		if(animObj.nowTime === undefined) {
			animObj.nowTime = 0.0;
		}
		var progress = Math.min(animObj.nowTime / animObj.duration, 1.0);
		var deleteFlag = animObj.nowTime >= animObj.duration;

		var type = (animObj.type !== undefined) ? animObj.type : "linear";

		for(var param in animObj.params) {
			var to = animObj.params[param];
			var from = animObj.params_from[param];

			if(typeof to === "function") {
				to = to();
				from = from();
			}

			animObj.target[param] = interpolate(from, to, progress, type);

		}

		animObj.nowTime += time_delta;

		if(deleteFlag) {
			anim_tick_onAnimEnd(i, animObj);
		}

	}
}

function interpolate(from, to, progress, type) {
	switch(type) {
		default: return interpolate_linear(from,to,progress);
		case "smooth": return interpolate_smooth(from,to,progress);
		case "sin": return interpolate_sin_curve(from,to,progress);
	}
}

function anim_tick_onAnimEnd(i,anim) {
	var how = anim.loop;
	switch(how) {
		case "alternative":
			anim.nowTime = 0.0;
			[anim.params, anim.params_from] = [anim.params_from, anim.params];
			break;
		default:
			if(typeof anim.then === "function") anim.then();
			delete anims[i];
			break;
	}
}

function interpolate_linear(a,b,blend) {
	return a + (b - a) * blend;
}

function interpolate_smooth(a,b,blend) {
	return a + (b - a) * (Math.log(1 + blend * (Math.E - 1)));
}

function interpolate_sin_curve(a,b,blend) {
	return a + (b - a) * (Math.sin((blend * 2 - 1) * Math.PI / 2) + 1) / 2;
}


// ----------------- UTILS ------------------
function toNumber(a) {
	if(typeof a === "function") {
		return a();
	}
	return a;
}

var lastTimeCall = game.time();
var time_delta = 0;

function timer_tick() {
	time_delta = game.time() - lastTimeCall;
	lastTimeCall = game.time();
}

function printAllInObj(obj) {
	for(var i in obj) {
		game.println(i + ": " + (typeof obj[i]));
	}
}
