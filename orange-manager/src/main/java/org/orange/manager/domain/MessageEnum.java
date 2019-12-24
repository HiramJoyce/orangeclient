package org.orange.manager.domain;

public enum MessageEnum {

	COMMAND_CODE(1001), HEARTBEAT_CODE(1002);

	private int code;

	private MessageEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}