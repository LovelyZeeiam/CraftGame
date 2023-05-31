package riven;

import java.util.Random;

import xueli.mcremake.registry.PocketNativeType;

/**
 * <p>
 * Adapted from Riven's Implementation of Perlin noise. Modified it to be more
 * OOP rather than C like.
 * </p>
 * 
 * @author Matthew A. Johnston (WarmWaffles)
 * 
 */
@PocketNativeType(version = "0.1.3", value = "ImprovedNoise")
public class PerlinNoise {

	private static final double[] pow = new double[32];
	static {
		for (int i = 0; i < pow.length; i++)
			pow[i] = Math.pow(2, i);

	}

	@PocketNativeType("*((_DWORD*)this + 2)")
	private double xo;
	@PocketNativeType("*((_DWORD*)this + 3)")
	private double yo;
	@PocketNativeType("*((_DWORD*)this + 4)")
	private double zo;

	@PocketNativeType("*((_DWORD*)v4_this + 5)")
	private int[] perm;

	/**
	 * Builds the Perlin Noise generator.
	 * 
	 * @param seed The seed for the random number generator
	 */
	public PerlinNoise(int seed) {
		this(new Random(seed));
	}

	public PerlinNoise(Random r) {
		this.xo = r.nextDouble(256.0); // Origin: (double)random.genrand_int32() * 2.32830644e-10
		this.yo = r.nextDouble(256.0);
		this.zo = r.nextDouble(256.0);

		perm = new int[512];
		for (int i = 0; i < 256; i++) {
			perm[i] = i;
		}

		for (int i = 0; i < 256; i++) {
			int v3 = r.nextInt(256 - i);

			int v4 = perm[i];
			perm[i] = perm[i + v3];
			perm[i + v3] = v4;

			perm[i + 256] = perm[i];

		}

//		if (permutation.length != 256)
//			throw new IllegalStateException();

//		for (int i = 0; i < 256; i++)
//			perm[256 + i] = perm[i] = permutation[i];

	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void offset(double x, double y, double z) {
		this.xo = x;
		this.yo = y;
		this.zo = z;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param octaves
	 * @return
	 */
	public double smoothNoise(double x, double y, double z, int octaves) {
		double height = 0.0;
		for (int octave = 1; octave <= octaves; octave++)
			height += noise(x, y, z, octave);
		return height;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param octaves
	 * @return
	 */
	public double turbulentNoise(double x, double y, double z, int octaves) {
		double height = 0.0;
		for (int octave = 1; octave <= octaves; octave++) {
			double h = noise(x, y, z, octave);
			if (h < 0.0f)
				h *= -1.0;
			height += h;
		}
		return height;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public double noise(double x, double y, double z) {
		double fx = floor(x);
		double fy = floor(y);
		double fz = floor(z);

		int gx = (int) fx & 0xFF;
		int gy = (int) fy & 0xFF;
		int gz = (int) fz & 0xFF;

		double u = fade(x -= fx);
		double v = fade(y -= fy);
		double w = fade(z -= fz);

		int a0 = perm[gx + 0] + gy;
		int b0 = perm[gx + 1] + gy;
		int aa = perm[a0 + 0] + gz;
		int ab = perm[a0 + 1] + gz;
		int ba = perm[b0 + 0] + gz;
		int bb = perm[b0 + 1] + gz;

		double a1 = grad(perm[bb + 1], x - 1, y - 1, z - 1);
		double a2 = grad(perm[ab + 1], x - 0, y - 1, z - 1);
		double a3 = grad(perm[ba + 1], x - 1, y - 0, z - 1);
		double a4 = grad(perm[aa + 1], x - 0, y - 0, z - 1);
		double a5 = grad(perm[bb + 0], x - 1, y - 1, z - 0);
		double a6 = grad(perm[ab + 0], x - 0, y - 1, z - 0);
		double a7 = grad(perm[ba + 0], x - 1, y - 0, z - 0);
		double a8 = grad(perm[aa + 0], x - 0, y - 0, z - 0);

		double a2_1 = lerp(u, a2, a1);
		double a4_3 = lerp(u, a4, a3);
		double a6_5 = lerp(u, a6, a5);
		double a8_7 = lerp(u, a8, a7);
		double a8_5 = lerp(v, a8_7, a6_5);
		double a4_1 = lerp(v, a4_3, a2_1);
		double a8_1 = lerp(w, a8_5, a4_1);

		return a8_1;
	}

	public double[] add(double[] v39, double v38, double v37, double a5, int a6, int a7, double a8, double a9,
			double a10, double a11, double a12) {
		if (a7 == 1) {
			if (a6 > 0) {
				int v74 = 0;
				for (int i = 0; i < a6; i++) {
					double v49 = (i + v38) * a9 + this.xo;
					int v49_floor = (int) Math.floor(v49); // Maybe casting it directly is faster
					double v49_remain = v49 - v49_floor;
					v49_floor = Math.floorMod(v49_floor, 256); // Cast to unsigned __int8

					if (a8 > 0) {
						double v49_fade = this.fade(v49_remain);
						for (int j = 0; j < a8; j++) {
							double v58 = (j + a5) * a11 + this.zo;
							int v58_floor = (int) Math.floor(v58);
							double v58_remain = v58 - v58_floor;
							v58_floor = Math.floorMod(v58_floor, 256); // Cast to unsigned __int8

							int v62 = v58_floor + perm[perm[v49_floor]];
							int v64 = v58_floor + perm[perm[v49_floor + 1]];

							double v65 = grad2(perm[v62], v49_remain, v58_remain); // Index 574 out of bounds for length
																					// 512
							double v66 = grad(perm[v64], v49_remain - 1.0, 0.0, v58_remain);
							double v67 = lerp(v49_fade, v65, v66);

							double v68 = grad(perm[v62 + 1], v49_remain, 0.0, v58_remain - 1.0);
							double v69 = grad(perm[v64 + 1], v49_remain - 1.0, 0.0, v58_remain - 1.0);
							double v70 = lerp(v49_fade, v68, v69);

							double result = lerp(fade(v58_remain), v67, v70);
							v39[v74] += result / a12;
							v74++;

						}
					}
				}
			}
		} else if (a6 > 0) {
			int v60 = 0;
			int v59 = -1;
			double v58 = 0.0, v57 = 0.0, v56 = 0.0, v55 = 0.0;
			for (int k = 0; k < a6; k++) {
				double v52 = (k + v38) * a9 + this.xo;
				int v52_floor = (int) Math.floor(v52);
				double v52_remain = v52 - v52_floor;
				double v52_fade = fade(v52_remain);
				v52_floor = Math.floorMod(v52_floor, 256); // Cast to unsigned __int8

				for (int l = 0; l < a8; l++) {
					double v47 = (l + a5) * a11 + this.zo;
					int v47_floor = (int) Math.floor(v47);
					double v47_remain = v47 - v47_floor;
					double v47_fade = fade(v47_remain);
					v47_floor = Math.floorMod(v47_floor, 256); // Cast to unsigned __int8

					for (int m = 0; m < a7; m++) {
						double v43 = (m + v37) * a10 + this.yo;
						int v43_floor = (int) Math.floor(v43);
						double v43_remain = v43 - v43_floor;
						double v43_fade = fade(v43_remain);
						v43_floor = Math.floorMod(v43_floor, 256); // Cast to unsigned __int8

						if (m == 0 || v43_floor != v59) {
							v59 = v43_floor;

							int v21 = v43_floor + perm[v52_floor];
							int v22 = v47_floor + perm[v21];
							int v23 = v47_floor + perm[v21 + 1];

							int v24 = v43_floor + perm[v52_floor + 1];
							int v25 = v47_floor + perm[v24];
							int v26 = v47_floor + perm[v24 + 1];

							double v27 = grad(perm[v22], v52_remain, v43_remain, v47_remain);
							double v28 = grad(perm[v25], v52_remain - 1.0, v43_remain, v47_remain);
							v58 = lerp(v52_fade, v27, v28);
							double v29 = grad(perm[v23], v52_remain, v43_remain - 1.0, v47_remain);
							double v30 = grad(perm[v26], v52_remain - 1.0, v43_remain - 1.0, v47_remain);
							v57 = lerp(v52_fade, v29, v30);
							double v31 = grad(perm[v22 + 1], v52_remain, v43_remain, v47_remain - 1.0);
							double v32 = grad(perm[v25 + 1], v52_remain - 1.0, v43_remain, v47_remain - 1.0);
							v56 = lerp(v52_fade, v31, v32);
							double v33 = grad(perm[v23 + 1], v52_remain, v43_remain - 1.0, v47_remain - 1.0);
							double v34 = grad(perm[v26 + 1], v52_remain - 1.0, v43_remain - 1.0, v47_remain - 1.0);
							v55 = lerp(v52_fade, v33, v34);

						}

						double v35 = lerp(v43_fade, v58, v57);
						double v36 = lerp(v43_fade, v56, v55);
						double result = lerp(v47_fade, v35, v36);
						v39[v60] += result / a12;
						v60++;
					}
				}
			}

		}
		return v39;
	}

	// ========================================================================
	// PRIVATE
	// ========================================================================

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param octave
	 * @return
	 */
	private double noise(double x, double y, double z, int octave) {
		double p = pow[octave];
		return this.noise(x * p + this.xo, y * p + this.yo, z * p + this.zo) / p;
	}

	/**
	 * 
	 * @param v
	 * @return
	 */
	private final double floor(double v) {
		return (int) v;
	}

	/**
	 * 
	 * @param t
	 * @return
	 */
	private final double fade(double t) {
		return t * t * t * (t * (t * 6.0 - 15.0) + 10.0);
	}

	/**
	 * 
	 * @param t
	 * @param a
	 * @param b
	 * @return
	 */
	private final double lerp(double t, double a, double b) {
		return a + t * (b - a);
	}

	/**
	 * 
	 * @param hash
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	private static double grad(int hash, double x, double y, double z) {
		int h = hash & 15;
		double u = (h < 8) ? x : y;
		double v = (h < 4) ? y : ((h == 12 || h == 14) ? x : z);
		return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
	}

	private static double grad2(int hash, double a3, double a4) {
		int h = hash & 0xF;
		double v4, v5;
		if (h <= 3) {
			v4 = 0.0;
		} else if (h != 12 && h != 14) {
			v4 = a4;
		} else {
			v4 = a3;
		}

		v5 = ((1 - ((hash & 8) >> 3)) * a3);

		if ((h & 1) != 0)
			v5 = -v5;
		if ((h & 2) != 0)
			v4 = -v4;

		return v4 + v5;
	}

}