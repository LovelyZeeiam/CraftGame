package xueli.craftgame.block;

import java.util.Objects;

public class BlockParameters {
	
	// byte from BlockFace
	public byte faceTo;
	// slab data when slab and stair data when stair
	public byte slabOrStairData;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BlockParameters that = (BlockParameters) o;
		return faceTo == that.faceTo && slabOrStairData == that.slabOrStairData;
	}

	@Override
	public int hashCode() {
		return Objects.hash(faceTo, slabOrStairData);
	}

}
