package net.dengzixu.java.message;

import java.util.HashMap;

import net.dengzixu.java.constant.BodyCommandEnum;

public class Message {
	public HashMap<String, Object> content;

	public BodyCommandEnum bodyCommandEnum;

	public UserInfo userInfo;
	public FansMedal fansMedal;

	public HashMap<String, Object> getContent() {
		return content;
	}

	public void setContent(HashMap<String, Object> content) {
		this.content = content;
	}

	public BodyCommandEnum getBodyCommand() {
		return bodyCommandEnum;
	}

	public void setBodyCommand(BodyCommandEnum bodyCommandEnum) {
		this.bodyCommandEnum = bodyCommandEnum;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public FansMedal getFansMedal() {
		return fansMedal;
	}

	public void setFansMedal(FansMedal fansMedal) {
		this.fansMedal = fansMedal;
	}

	@Override
	public String toString() {
		return "Message{" + "content=" + content + ", bodyCommand=" + bodyCommandEnum + ", userInfo=" + userInfo
				+ ", fansMedal=" + fansMedal + '}';
	}
}
