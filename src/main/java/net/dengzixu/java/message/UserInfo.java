package net.dengzixu.java.message;

public class UserInfo {
	private long uid;
	private String username;
	private String face;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	@Override
	public String toString() {
		return "userInfo{" + "uid=" + uid + ", username='" + username + '\'' + ", face='" + face + '\'' + '}';
	}
}
