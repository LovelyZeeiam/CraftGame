package net.dengzixu.java.payload;

public class AuthPayload {
	/**
	 * uid 游客为 0
	 */
	private Integer uid = 0;

	/**
	 * 房间 ID
	 */
	private long roomid;

	/**
	 * 是协议版本 为 1 时不会使用zlib压缩 为 2 时会发送带有zlib压缩的包，也就是数据包协议为 2
	 */
	private Integer protover = 2;

	/**
	 * 平台
	 */
	private String platform = "web";

	/**
	 * 未知 取 2
	 */
	private int type = 2;

	/**
	 * key 可以通过
	 * https://api.live.bilibili.com/room/v1/Danmu/getConf?room_id={ROOM_ID} 获取
	 */
	private String key;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public long getRoomid() {
		return roomid;
	}

	public void setRoomid(long roomid) {
		this.roomid = roomid;
	}

	public Integer getProtover() {
		return protover;
	}

	public void setProtover(Integer protover) {
		this.protover = protover;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
