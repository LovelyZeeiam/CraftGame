package xueLi.craftGame.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import xueLi.craftGame.entity.Bone;
import xueLi.craftGame.entity.BoneType;
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
			TBone rawBone = bones[s];
			Bone bone = attrib.model[BoneType.valueOf(rawBone.type).id];
			if (rawBone.parent != null)
				bone.parent = BoneType.valueOf(rawBone.parent).id;

			TBoneData[] rawDatas = rawBone.data;
			// One data 8 vertices,one vertex 3 floats
			bone.vertices = new float[rawDatas.length * 8 * 3];
			bone.rawData = rawDatas;
			
			//在这里写关于rawOffset的
			for (int z = 0; z < rawDatas.length; z++) {
				TBoneData rawData = rawDatas[z];

				bone.vertices[z * 8 * 3 + 0] = rawData.offset[0] - rawData.size[0] / 2;
				bone.vertices[z * 8 * 3 + 1] = rawData.offset[1] - rawData.size[1] / 2;
				bone.vertices[z * 8 * 3 + 2] = rawData.offset[2] - rawData.size[2] / 2;

				bone.vertices[z * 8 * 3 + 3] = rawData.offset[0] - rawData.size[0] / 2;
				bone.vertices[z * 8 * 3 + 4] = rawData.offset[1] + rawData.size[1] / 2;
				bone.vertices[z * 8 * 3 + 5] = rawData.offset[2] - rawData.size[2] / 2;

				bone.vertices[z * 8 * 3 + 6] = rawData.offset[0] + rawData.size[0] / 2;
				bone.vertices[z * 8 * 3 + 7] = rawData.offset[1] + rawData.size[1] / 2;
				bone.vertices[z * 8 * 3 + 8] = rawData.offset[2] - rawData.size[2] / 2;

				bone.vertices[z * 8 * 3 + 9] = rawData.offset[0] + rawData.size[0] / 2;
				bone.vertices[z * 8 * 3 + 10] = rawData.offset[1] - rawData.size[1] / 2;
				bone.vertices[z * 8 * 3 + 11] = rawData.offset[2] - rawData.size[2] / 2;

				bone.vertices[z * 8 * 3 + 12] = rawData.offset[0] - rawData.size[0] / 2;
				bone.vertices[z * 8 * 3 + 13] = rawData.offset[1] - rawData.size[1] / 2;
				bone.vertices[z * 8 * 3 + 14] = rawData.offset[2] + rawData.size[2] / 2;

				bone.vertices[z * 8 * 3 + 15] = rawData.offset[0] - rawData.size[0] / 2;
				bone.vertices[z * 8 * 3 + 16] = rawData.offset[1] + rawData.size[1] / 2;
				bone.vertices[z * 8 * 3 + 17] = rawData.offset[2] + rawData.size[2] / 2;

				bone.vertices[z * 8 * 3 + 18] = rawData.offset[0] + rawData.size[0] / 2;
				bone.vertices[z * 8 * 3 + 19] = rawData.offset[1] + rawData.size[1] / 2;
				bone.vertices[z * 8 * 3 + 20] = rawData.offset[2] + rawData.size[2] / 2;

				bone.vertices[z * 8 * 3 + 21] = rawData.offset[0] + rawData.size[0] / 2;
				bone.vertices[z * 8 * 3 + 22] = rawData.offset[1] - rawData.size[1] / 2;
				bone.vertices[z * 8 * 3 + 23] = rawData.offset[2] + rawData.size[2] / 2;

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
