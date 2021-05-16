package xueli.game.sound;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.newdawn.slick.openal.WaveData;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import xueli.utils.io.Files;

public class SoundManager {
	
	private static final Logger logger = Logger.getLogger(SoundManager.class.getName());

	public static long dev, ctx;
	
	private static HashMap<String, Integer> sounds = new HashMap<>();
	private static ArrayList<Integer> speakers = new ArrayList<>();
	
	static {
		String deviceString = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
		logger.info("[Sound] Device: " + deviceString);
		dev = alcOpenDevice(deviceString);

		int[] attributes = { 0 };
		ctx = alcCreateContext(dev, attributes);
		alcMakeContextCurrent(ctx);

		ALCCapabilities alcCapabilities = ALC.createCapabilities(dev);
		AL.createCapabilities(alcCapabilities);

	}
	
	public SoundManager() {
		init();
		
	}
	
	public void init() {
		JsonObject obj = null;
		try {
			obj = new Gson().fromJson(new JsonReader(new FileReader(Files.getResourcePackedInJar("sounds/sounds.json"))), JsonObject.class);
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		JsonObject soundsObj = obj.get("sounds").getAsJsonObject();
		soundsObj.entrySet().forEach((e) -> {
			String namespace = e.getKey();
			String path = e.getValue().getAsString();
			
			if(com.google.common.io.Files.getFileExtension(path).equals("wav")) {
				try {
					loadWav(namespace, Files.getResourcePackedInJar(path).getPath());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		
	}
	
	public static void loadWav(String name, String path) throws IOException {
		int buffer = alGenBuffers();
		WaveData waveFileData = WaveData.create(new BufferedInputStream(new FileInputStream(path)));
		alBufferData(buffer, waveFileData.format, waveFileData.data, waveFileData.samplerate);
		waveFileData.dispose();

		sounds.put(name, buffer);

	}

	public void play(String name, float volume, float pitch) {
		if(!sounds.containsKey(name)) {
			logger.severe("Can't find sound: " + name);
			return;
		}

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

	public void tick() {
		ArrayList<Integer> speakerToDispose = new ArrayList<>();
		speakers.forEach(s -> {
			if (alGetSourcei(s, AL_SOURCE_STATE) == AL_STOPPED) {
				speakerToDispose.add(s);
				alDeleteSources(s);
			}
		});
		speakers.removeAll(speakerToDispose);

		checkALError();

	}

	public void release() {
		speakers.forEach(s -> {
			if (alGetSourcei(s, AL_SOURCE_STATE) == AL_PLAYING) {
				alSourceStop(s);
			}
			alDeleteSources(s);
		});

		alcDestroyContext(ctx);
		alcCloseDevice(dev);

	}

	private void checkALError() {
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
