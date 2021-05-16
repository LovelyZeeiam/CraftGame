package xueli.game.utils;

import java.awt.Color;

public class Light {

	private float sunLight;
	private Color color;
	
	public Light(float sunLight, Color color) {
		this.sunLight = sunLight;
		this.color = color;
	}
	
	public float getSunLight() {
		return sunLight;
	}
	
	public Color getColor() {
		return color;
	}

}
