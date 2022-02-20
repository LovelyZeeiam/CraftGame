package xueli.craftgame.block;

import com.flowpowered.nbt.CompoundMap;
import org.lwjgl.utils.vector.Vector2f;
import xueli.craftgame.init.Models;
import xueli.craftgame.model.TexturedModel;
import xueli.craftgame.model.TexturedModelBuilder;
import xueli.craftgame.state.StateWorld;
import xueli.craftgame.world.Dimension;
import xueli.game.utils.FloatList;
import xueli.game.utils.Light;
import xueli.game.utils.texture.AtlasTextureHolder;

import java.util.HashMap;

public class AbstractStair extends AbstractBlock {

	private HashMap<String, TexturedModel> models = new HashMap<>();

	public AbstractStair(String namespace, String nameInternational, String... textureNames) {
		super(namespace, nameInternational, textureNames);

		this.isComplete = false;

		getTags().add(BlockTags.HAS_DIFFERENT_DIRECTION);
		getTags().add(BlockTags.HAS_PART_UP_AND_PART_DOWN);

		modelInit();

	}

	public void modelInit() {
		AtlasTextureHolder[] holders = new AtlasTextureHolder[6];
		if (textureNames.length == 6)
			for (int i = 0; i < textureNames.length; i++) {
				holders[i] = StateWorld.getInstance().getBlocksTextureAtlas().getTextureHolder(textureNames[i]);
			}
		else if (textureNames.length == 1)
			for (int i = 0; i < 6; i++) {
				holders[i] = StateWorld.getInstance().getBlocksTextureAtlas().getTextureHolder(textureNames[0]);
			}
		else {
			throw new RuntimeException("Please input 6 vertices or 1 vertex for all faces!");
		}

		Models models = StateWorld.getInstance().getModels();

		this.models.put("cg:stair_down_front", new TexturedModelBuilder(models.getModule("cg:stair_down_front"))
				.add("down", holders[0].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[1].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[2].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[3].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[4].reTailor(new Vector2f(0, 0), new Vector2f(1, 1)),
						holders[5].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)))
				.add("up", holders[0].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)),
						holders[1].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)),
						holders[2].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 0.5f)),
						holders[3].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 0.5f)),
						holders[4].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 1)),
						holders[5].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 1)))
				.build("cg:stair_down_front"));
		this.models.put("cg:stair_down_back", new TexturedModelBuilder(models.getModule("cg:stair_down_back"))
				.add("down", holders[0].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[1].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[2].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[3].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[4].reTailor(new Vector2f(0, 0), new Vector2f(1, 1)),
						holders[5].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)))
				.add("up", holders[0].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)),
						holders[1].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)),
						holders[2].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 0.5f)),
						holders[3].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 0.5f)),
						holders[4].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 1)),
						holders[5].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 1)))
				.build("cg:stair_down_back"));
		this.models.put("cg:stair_down_left", new TexturedModelBuilder(models.getModule("cg:stair_down_left"))
				.add("down", holders[0].reTailor(new Vector2f(0.5f, 0.5f), new Vector2f(1, 1)),
						holders[1].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[2].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[3].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[4].reTailor(new Vector2f(0, 0), new Vector2f(1, 1)),
						holders[5].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)))
				.add("up", holders[0].reTailor(new Vector2f(0.5f, 0), new Vector2f(1, 0.5f)),
						holders[1].reTailor(new Vector2f(0.5f, 0), new Vector2f(1, 0.5f)),
						holders[2].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 0.5f)),
						holders[3].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 0.5f)),
						holders[4].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)),
						holders[5].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 1)))
				.build("cg:stair_down_left"));
		this.models.put("cg:stair_down_right", new TexturedModelBuilder(models.getModule("cg:stair_down_right"))
				.add("down", holders[0].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[1].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[2].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[3].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[4].reTailor(new Vector2f(0, 0), new Vector2f(1, 1)),
						holders[5].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)))
				.add("up", holders[0].reTailor(new Vector2f(0.5f, 0), new Vector2f(1, 0.5f)),
						holders[1].reTailor(new Vector2f(0.5f, 0), new Vector2f(1, 0.5f)),
						holders[2].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 0.5f)),
						holders[3].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 0.5f)),
						holders[4].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)),
						holders[5].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 1)))
				.build("cg:stair_down_right"));

		this.models.put("cg:stair_up_front", new TexturedModelBuilder(models.getModule("cg:stair_up_front"))
				.add("down", holders[0].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[1].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[2].reTailor(new Vector2f(0.5f, 0.5f), new Vector2f(1, 1)),
						holders[3].reTailor(new Vector2f(0.5f, 0.5f), new Vector2f(1, 1)),
						holders[4].reTailor(new Vector2f(0, 0), new Vector2f(1, 1)),
						holders[5].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 1)))
				.add("up", holders[0].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)),
						holders[1].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)),
						holders[2].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 0.5f)),
						holders[3].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 0.5f)),
						holders[4].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 1)),
						holders[5].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 1)))
				.build("cg:stair_up_front"));
		this.models.put("cg:stair_up_back", new TexturedModelBuilder(models.getModule("cg:stair_up_back"))
				.add("down", holders[0].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[1].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[2].reTailor(new Vector2f(0.5f, 0.5f), new Vector2f(1, 1)),
						holders[3].reTailor(new Vector2f(0.5f, 0.5f), new Vector2f(1, 1)),
						holders[4].reTailor(new Vector2f(0, 0), new Vector2f(1, 1)),
						holders[5].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 1)))
				.add("up", holders[0].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)),
						holders[1].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)),
						holders[2].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 0.5f)),
						holders[3].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 0.5f)),
						holders[4].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 1)),
						holders[5].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 1)))
				.build("cg:stair_up_back"));
		this.models.put("cg:stair_up_left", new TexturedModelBuilder(models.getModule("cg:stair_up_left"))
				.add("down", holders[0].reTailor(new Vector2f(0.5f, 0.5f), new Vector2f(1, 1)),
						holders[1].reTailor(new Vector2f(0.5f, 0.5f), new Vector2f(1, 1)),
						holders[2].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[3].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[4].reTailor(new Vector2f(0, 0), new Vector2f(1, 1)),
						holders[5].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)))
				.add("up", holders[0].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)),
						holders[1].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)),
						holders[2].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 0.5f)),
						holders[3].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 0.5f)),
						holders[4].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 1)),
						holders[5].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 1)))
				.build("cg:stair_up_left"));
		this.models.put("cg:stair_up_right", new TexturedModelBuilder(models.getModule("cg:stair_up_right"))
				.add("down", holders[0].reTailor(new Vector2f(0.5f, 0.5f), new Vector2f(1, 1)),
						holders[1].reTailor(new Vector2f(0.5f, 0.5f), new Vector2f(1, 1)),
						holders[2].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[3].reTailor(new Vector2f(0, 0.5f), new Vector2f(1, 1)),
						holders[4].reTailor(new Vector2f(0, 0), new Vector2f(1, 1)),
						holders[5].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)))
				.add("up", holders[0].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)),
						holders[1].reTailor(new Vector2f(0, 0), new Vector2f(1, 0.5f)),
						holders[2].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 0.5f)),
						holders[3].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 0.5f)),
						holders[4].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 1)),
						holders[5].reTailor(new Vector2f(0, 0), new Vector2f(0.5f, 1)))
				.build("cg:stair_up_right"));

	}

	@Override
	public int getRenderCubeData(FloatList buffer, int x, int y, int z, byte face, CompoundMap tag, Dimension dimension) {
		byte faceTo = (byte) tag.get(BlockTags.TAG_NAME_FACE_TO).getValue();
		byte part = (byte) tag.get(BlockTags.TAG_NAME_PART).getValue();

		Light light = dimension.getLight(x, y, z);

		if (part == BlockFace.PART_DOWN) {
			return switch (faceTo) {
			case BlockFace.FRONT -> models.get("cg:stair_down_front").getRenderData(x, y, z, face, buffer);
			case BlockFace.BACK -> models.get("cg:stair_down_back").getRenderData(x, y, z, face, buffer);
			case BlockFace.LEFT -> models.get("cg:stair_down_left").getRenderData(x, y, z, face, buffer);
			case BlockFace.RIGHT -> models.get("cg:stair_down_right").getRenderData(x, y, z, face, buffer);
			default -> 0;
			};
		} else if (part == BlockFace.PART_UP) {
			return switch (faceTo) {
			case BlockFace.FRONT -> models.get("cg:stair_up_front").getRenderData(x, y, z, face, buffer);
			case BlockFace.BACK -> models.get("cg:stair_up_back").getRenderData(x, y, z, face, buffer);
			case BlockFace.LEFT -> models.get("cg:stair_up_left").getRenderData(x, y, z, face, buffer);
			case BlockFace.RIGHT -> models.get("cg:stair_up_right").getRenderData(x, y, z, face, buffer);
			default -> 0;
			};
		} else {
			return 0;
		}
	}

	@Override
	public int getRenderModelViewData(FloatList buffer) {
		int v = 0;
		TexturedModel model = this.models.get("cg:stair_down_right");
		for (byte f = 0; f < 6; f++) {
			v += model.getRenderData(0, 0, 0, f, buffer);
		}
		return v;

	}

}
