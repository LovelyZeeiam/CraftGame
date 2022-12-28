package xueli.game.utils;

import org.lwjgl.utils.vector.Vector4b;

public class Light {

	public static final Light FULL_LIGHT = new Light((byte) 15, (byte) 15, (byte) 15, (byte) 15);

	private byte sun, r, g, b;

	public Light(byte sun, byte r, byte g, byte b) {
		this.sun = sun;
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public byte getSunLight() {
		return sun;
	}

	public void setSunLight(byte sun) {
		this.sun = sun;
	}

	public byte getR() {
		return r;
	}

	public void setR(byte r) {
		this.r = r;
	}

	public byte getG() {
		return g;
	}

	public void setG(byte g) {
		this.g = g;
	}

	public byte getB() {
		return b;
	}

	public void setB(byte b) {
		this.b = b;
	}

	public Vector4b getLightBuffer() {
		return new Vector4b(r, g, b, sun);
	}

}
