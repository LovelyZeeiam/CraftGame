package xueli.gamengine.utils;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.newdawn.slick.openal.WaveData;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;

public class SoundManager {

    private static long dev, ctx;
    private static HashMap<String, Integer> sounds = new HashMap<>();
    private static ArrayList<CompletableFuture> tasks = new ArrayList<>();

    static {
        String deviceString = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        Logger.info("[Sound] Device: " + deviceString);
        dev = alcOpenDevice(deviceString);

        int[] attributes = {0};
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
        CompletableFuture future = CompletableFuture.supplyAsync(() -> {
            int src = alGenSources();
            alSource3f(src, AL_POSITION, 0, 0, 0);
            alSourcef(src, AL_GAIN, volume);
            alSourcef(src, AL_PITCH, pitch);
            alSourcei(src, AL_BUFFER, buffer);
            alSource3f(src, AL_VELOCITY, 0, 0, 0);
            alSourcePlay(src);

            while (alGetSourcei(src, AL_SOURCE_STATE) == AL_PLAYING) {
                synchronized (Thread.currentThread()) {
                    try {
                        Thread.currentThread().wait(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            checkALError();

            return src;
        }).thenAccept((src) -> alDeleteSources(src));
        tasks.add(future);

    }

    public static void join() {
        tasks.forEach(CompletableFuture::join);

    }

    public static void release() {
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