package xueLi.craftGame.utils;

import java.util.Random;

import xueLi.craftGame.world.Chunk;

public class NoiseGenerator {

	private Random r = new Random();
	
    public NoiseGenerator(long seed) {
        r.setSeed(seed);
    }
    
    private float[][] genWhiteNoise(){
    	float[][] noise = new float[Chunk.size][Chunk.size];
    	for(int i = 0;i < Chunk.size;i++)
    		for(int j = 0;j < Chunk.size;j++)
    			noise[i][j] = r.nextFloat() % 1;
    	return noise;
    }

    
    

    
}
