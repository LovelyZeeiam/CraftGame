package xueli.gamengine.utils;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.AL_GAIN;
import static org.lwjgl.openal.AL10.AL_PITCH;
import static org.lwjgl.openal.AL10.AL_PLAYING;
import static org.lwjgl.openal.AL10.AL_POSITION;
import static org.lwjgl.openal.AL10.AL_SOURCE_STATE;
import static org.lwjgl.openal.AL10.AL_STOPPED;
import static org.lwjgl.openal.AL10.AL_VELOCITY;
import static org.lwjgl.openal.AL10.alBufferData;
import static org.lwjgl.openal.AL10.alDeleteSources;
import static org.lwjgl.openal.AL10.alGenBuffers;
import static org.lwjgl.openal.AL10.alGenSources;
import static org.lwjgl.openal.AL10.alGetError;
import static org.lwjgl.openal.AL10.alGetSourcei;
import static org.lwjgl.openal.AL10.alSource3f;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourceStop;
import static org.lwjgl.openal.AL10.alSourcef;
import static org.lwjgl.openal.AL10.alSourcei;
import static org.lwjgl.openal.ALC10.ALC_DEFAULT_DEVICE_SPECIFIER;
import static org.lwjgl.openal.ALC10.alcCloseDevice;
import static org.lwjgl.openal.ALC10.alcCreateContext;
import static org.lwjgl.openal.ALC10.alcDestroyContext;
import static org.lwjgl.openal.ALC10.alcGetString;
import static org.lwjgl.openal.ALC10.alcMakeContextCurrent;
import static org.lwjgl.openal.ALC10.alcOpenDevice;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.newdawn.slick.openal.WaveData;

public class SoundManager {

	private static long dev, ctx;
	private static HashMap<String, Integer> sounds = new HashMap<>();

	private static ArrayList<Integer> speakers = new ArrayList<>();

	static {
		String deviceString = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
		Logger.info("[Sound] Device: " + deviceString);
		dev = alcOpenDevice(deviceString);

		int[] attributes = { 0 };
		ctx = alcCreateContext(dev, attributes);
		alcMakeContextCurrent(ctx);

		ALCCapabilities alcCapabilities = ALC.createCapabilities(dev);
		AL.createCapabilities(alcCapabilities);

	}

	public static void loadWav(String name, String path) throws IOException {
		int buffer = alGenBuffers();
		WaveData waveFileData = WaveData.create(new BufferedInputStream(new FileInputStream(path)));
		alBufferData(buffer, waveFileData.format, waveFileData.data, waveFileData.samplerate);
		waveFileData.dispose();

		sounds.put(name, buffer);

	}

	public static void play(String name, float volume, float pitch) {
		int buffer = sounds.get(name);

		int src = alGenSources();
		alSource3f(src, AL_POSITION, 0, 0, 0);
		alSourcef(src, AL_GAIN, volume);
		alSourcef(src, AL_PITCH, pitch);
		alSourcei(src, AL_BUFFER, buffer);
		alSource3f(src, AL_VELOCITY, 0, 0, 0);
		alSourcePlay(src);

		speakers.add(src);

	}

	public static void tick() {
		ArrayList<Integer> speakerToDispose = new ArrayList<>();
		speakers.forEach(s -> {
			if(alGetSourcei(s, AL_SOURCE_STATE) == AL_STOPPED) {
				speakerToDispose.add(s);
				alDeleteSources(s);
			}
		});
		speakers.removeAll(speakerToDispose);

		checkALError();

	}

	public static void release() {
		speakers.forEach(s -> {
			if(alGetSourcei(s, AL_SOURCE_STATE) == AL_PLAYING) {
				alSourceStop(s);
			}
			alDeleteSources(s);
		});

		alcDestroyContext(ctx);
		alcCloseDevice(dev);

	}

	private static void checkALError() {
		int error = alGetError();
		switch (error) {
		case 40961:
			System.out.println("Invalid name parameter");
			break;
		case 40962:
			System.out.println("Invalid enumerated parameter value");
			break;
		case 40963:
			System.out.println("Invalid parameter parameter value");
			break;
		case 40964:
			System.out.println("Invalid operation");
			break;
		case 40965:
			System.out.println("Unable to allocate memory");
			break;
		}
	}

}
