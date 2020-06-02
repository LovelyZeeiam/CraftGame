package xueLi.craftGame.utils;

import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.openal.AL10.*;

public class ALHelper {

	public static class Audio {
		public int bufferID;
		public Vector3f pos;
		public Vector3f velocity;
		public float gain, pitch;
	}

	public static class Speaker {
		public int id;

		public Speaker(float gain, float pitch) {
			id = alGenSources();
			alSourcef(id, AL_GAIN, gain);
			alSourcef(id, AL_PITCH, pitch);
			alSource3f(id, AL_POSITION, 0, 0, 0);

		}

		public void setGain(float gain) {
			alSourcef(id, AL_GAIN, gain);
		}

		public void setPitch(float pitch) {
			alSourcef(id, AL_PITCH, pitch);
		}

		public void setVelocity(Vector3f velocity) {
			alSource3f(id, AL_VELOCITY, velocity.x, velocity.y, velocity.z);
		}

		public boolean isPlaying() {
			return alGetSourcei(id, AL_SOURCE_STATE) == AL_PLAYING;
		}

		public void play(int bufferID) {
			alSourcei(id, AL_BUFFER, bufferID);
			alSourcePlay(id);
			printALError();

		}

		public void pause() {
			alSourcePause(id);
		}

		public void stop() {
			alSourceStop(id);
		}

		public void delete() {
			alDeleteSources(id);
		}

	}

	public static void printALError() {
		int error = alGetError();
		if (error != 0) {
			System.out.println("OpenAL error: " + error);
		}
	}

}
