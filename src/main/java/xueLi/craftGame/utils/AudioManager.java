package xueLi.craftGame.utils;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.openal.OggData;
import org.newdawn.slick.openal.OggDecoder;
import org.newdawn.slick.openal.WaveData;

public class AudioManager {

	private static long device, context;

	public static void init() {
		String deviceString = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
		System.out.println("Get Device: " + deviceString);
		device = alcOpenDevice(deviceString);

		int[] attributes = { 0 };
		context = alcCreateContext(device, attributes);
		alcMakeContextCurrent(context);

		ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
		AL.createCapabilities(alcCapabilities);

	}

	private static HashMap<Integer, ALHelper.Audio> buffers = new HashMap<Integer, ALHelper.Audio>();

	public static void loadWavSound(int id, String path, Vector3f pos, Vector3f velocity) {
		int buffer = alGenBuffers();
		WaveData waveFileData = WaveData.create(path);
		alBufferData(buffer, waveFileData.format, waveFileData.data, waveFileData.samplerate);
		waveFileData.dispose();

		ALHelper.Audio audio = new ALHelper.Audio();
		audio.bufferID = buffer;
		audio.pos = pos;
		audio.velocity = velocity;

		buffers.put(id, audio);
	}

	private static OggDecoder decoder = new OggDecoder();

	public static void loadOggSound(int id, String path, Vector3f pos, Vector3f velocity, float gain, float pitch) {
		int buffer = alGenBuffers();
		OggData oggFileData = null;
		try {
			oggFileData = decoder.getData(new FileInputStream(new File(path)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		alBufferData(buffer, oggFileData.channels == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, oggFileData.data,
				oggFileData.rate);

		ALHelper.Audio audio = new ALHelper.Audio();
		audio.bufferID = buffer;
		audio.pos = pos;
		audio.velocity = velocity;
		audio.gain = gain;
		audio.pitch = pitch;

		buffers.put(id, audio);
	}

	public static ALHelper.Audio getBuffer(int id) {
		return buffers.get(id);
	}

	public static void close() {
		for (Map.Entry<Integer, ALHelper.Audio> entry : buffers.entrySet()) {
			alDeleteBuffers(entry.getValue().bufferID);
		}

		alcDestroyContext(context);
		alcCloseDevice(device);

	}

}
