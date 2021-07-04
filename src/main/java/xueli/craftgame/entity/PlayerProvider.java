package xueli.craftgame.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.CompoundTag;
import com.flowpowered.nbt.FloatTag;
import com.flowpowered.nbt.IntTag;
import com.flowpowered.nbt.ListTag;
import com.flowpowered.nbt.StringTag;
import com.flowpowered.nbt.stream.NBTInputStream;
import com.flowpowered.nbt.stream.NBTOutputStream;

import xueli.craftgame.inventory.BlockInventoryItem;
import xueli.craftgame.inventory.Inventory;
import xueli.craftgame.inventory.InventoryItem;
import xueli.craftgame.state.StateWorld;

public class PlayerProvider {

	private static final String FOLDER = "/players/";

	private Player player;

	public PlayerProvider(Player player) {
		this.player = player;
	}

	public void load() {
		File file = new File(StateWorld.savePath + FOLDER + "local.dat");
		if (file.exists()) {
			NBTInputStream out;
			CompoundTag tag = null;
			try {
				out = new NBTInputStream(new FileInputStream(file), false);
				tag = (CompoundTag) out.readTag();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			CompoundMap map = tag.getValue();
			int version = (int) map.get("version").getValue();

			switch (version) {
			case 0:
				loadVersion0(map);
				break;

			default:
				return;
			}

		}

	}

	@SuppressWarnings("unchecked")
	private void loadVersion0(CompoundMap playerMap) {
		float x = (float) playerMap.get("x").getValue();
		float y = (float) playerMap.get("y").getValue();
		float z = (float) playerMap.get("z").getValue();
		float rotX = (float) playerMap.get("rotX").getValue();
		float rotY = (float) playerMap.get("rotY").getValue();
		float rotZ = (float) playerMap.get("rotZ").getValue();

		player.pos.x = x;
		player.pos.y = y;
		player.pos.z = z;
		player.pos.rotX = rotX;
		player.pos.rotY = rotY;
		player.pos.rotZ = rotZ;

		List<CompoundTag> inventory = ((ListTag<CompoundTag>) playerMap.get("inventory")).getValue();
		for (int i = 0; i < inventory.size(); i++) {
			CompoundMap item = inventory.get(i).getValue();

			String type = (String) item.get("type").getValue();
			if (type.equals("null"))
				continue;

			String namespace = (String) item.get("namespace").getValue();
			if (type.equals("block")) {
				player.inventory.getSlots()[i] = player.inventory.findBlockItem(namespace);
			}

		}

	}

	public void save() {
		CompoundMap playerMap = new CompoundMap();
		playerMap.put(new IntTag("version", 0));

		playerMap.put(new FloatTag("x", player.pos.x));
		playerMap.put(new FloatTag("y", player.pos.y));
		playerMap.put(new FloatTag("z", player.pos.z));
		playerMap.put(new FloatTag("rotX", player.pos.rotX));
		playerMap.put(new FloatTag("rotY", player.pos.rotY));
		playerMap.put(new FloatTag("rotZ", player.pos.rotZ));

		ArrayList<CompoundTag> inventory = new ArrayList<>();
		for (int i = 0; i < Inventory.SLOT_NUM; i++) {
			CompoundMap itemMap = new CompoundMap();
			InventoryItem item = player.inventory.getSlots()[i];

			if (item != null) {
				itemMap.put(new StringTag("namespace", item.getNamespace()));
			}

			if (item == null) {
				itemMap.put(new StringTag("type", "null"));
			} else if (item instanceof BlockInventoryItem) {
				itemMap.put(new StringTag("type", "block"));
			}

			inventory.add(new CompoundTag("", itemMap));

		}
		playerMap.put(new ListTag<>("inventory", CompoundTag.class, inventory));

		try {
			NBTOutputStream out = new NBTOutputStream(new FileOutputStream(StateWorld.savePath + FOLDER + "local.dat"),
					false);
			out.writeTag(new CompoundTag("", playerMap));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
