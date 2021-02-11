package xueli.craftgame.block;

import java.util.Objects;

public class BlockParameters {

	// byte from BlockFace
	public byte faceTo;
	// slab data when slab and stair data when stair
	public byte slabOrStairData;
	// message from block
	public String message;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		BlockParameters that = (BlockParameters) o;
		return faceTo == that.faceTo && slabOrStairData == that.slabOrStairData
				&& Objects.equals(message, that.message);
	}

	@Override
	public int hashCode() {
		return Objects.hash(faceTo, slabOrStairData, message);
	}

}
