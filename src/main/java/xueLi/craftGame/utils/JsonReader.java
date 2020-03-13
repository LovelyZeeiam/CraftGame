package xueLi.craftGame.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import xueLi.craftGame.entity.Bone;
import xueLi.craftGame.entity.HitBox;
import xueLi.craftGame.template.bilibili.TUpperRelation;
import xueLi.craftGame.template.entity.*;

public class JsonReader {

	private static Gson gson = new Gson();

	/**
	 * @throws JsonSyntaxException      json格式错误
	 * @throws JsonIOException          json文件读取错误
	 * @throws FileNotFoundException    没找到文件
	 * @throws IllegalArgumentException json参数错误
	 */
	public static AttributeEntity readToEntityData(String path)
			throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		TEntity raw = gson.fromJson(new FileReader(path), TEntity.class);
		if (raw == null) {
			System.err.println("Can't read entity: " + path);
			return null;
		}

		AttributeEntity attrib = new AttributeEntity();
		attrib.name = raw.name;

		TModel rawModel = raw.model;
		attrib.box = new HitBox(rawModel.box[0], rawModel.box[1], rawModel.box[2], rawModel.box[3], rawModel.box[4],
				rawModel.box[5]);

		TBone[] bones = rawModel.bones;

		for (int s = 0; s < bones.length; s++) {
			TBone rawData = bones[s];
			Bone bone = new Bone();

			bone.id = rawData.id;

			bone.vertices[0] = rawData.rotPoint[0]-rawData.size[0] / 2;
			bone.vertices[1] = rawData.rotPoint[1]-rawData.size[1] / 2;
			bone.vertices[2] = rawData.rotPoint[2]-rawData.size[2] / 2;

			bone.vertices[3] = rawData.rotPoint[0]-rawData.size[0] / 2;
			bone.vertices[4] = rawData.rotPoint[1]+rawData.size[1] / 2;
			bone.vertices[5] = rawData.rotPoint[2]-rawData.size[2] / 2;

			bone.vertices[6] = rawData.rotPoint[0]+rawData.size[0] / 2;
			bone.vertices[7] = rawData.rotPoint[1]+rawData.size[1] / 2;
			bone.vertices[8] = rawData.rotPoint[2]-rawData.size[2] / 2;

			bone.vertices[9] = rawData.rotPoint[0]+rawData.size[0] / 2;
			bone.vertices[10] = rawData.rotPoint[1]-rawData.size[1] / 2;
			bone.vertices[11] = rawData.rotPoint[2]-rawData.size[2] / 2;

			bone.vertices[12] = rawData.rotPoint[0]-rawData.size[0] / 2;
			bone.vertices[13] = rawData.rotPoint[1]-rawData.size[1] / 2;
			bone.vertices[14] = rawData.rotPoint[2]+rawData.size[2] / 2;

			bone.vertices[15] = rawData.rotPoint[0]-rawData.size[0] / 2;
			bone.vertices[16] = rawData.rotPoint[1]+rawData.size[1] / 2;
			bone.vertices[17] = rawData.rotPoint[2]+rawData.size[2] / 2;

			bone.vertices[18] = rawData.rotPoint[0]+rawData.size[0] / 2;
			bone.vertices[19] = rawData.rotPoint[1]+rawData.size[1] / 2;
			bone.vertices[20] = rawData.rotPoint[2]+rawData.size[2] / 2;

			bone.vertices[21] = rawData.rotPoint[0]+rawData.size[0] / 2;
			bone.vertices[22] = rawData.rotPoint[1]-rawData.size[1] / 2;
			bone.vertices[23] = rawData.rotPoint[2]+rawData.size[2] / 2;

			bone.rotPoint = rawData.rotPoint;
			bone.rawOffset = rawData.offset;

			if (rawData.parent != -1) {
				attrib.bones.get(rawData.parent).children.add(bone);
			} else {
				attrib.bones.add(bone);
			}

		}

		return attrib;
	}

	public static long getBilibiliUpperFollower(long uuid) throws IOException {
		URL url = new URL("http://api.bilibili.com/x/relation/stat?vmid=" + uuid);
		InputStream in = url.openStream();
		TUpperRelation r = gson.fromJson(new InputStreamReader(in), TUpperRelation.class);
		in.close();
		return r.data.follower;
	}

}
