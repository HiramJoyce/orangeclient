package org.orange.manager.domain;

public class Message {
	private int code;
	private String data;

	public Message(int code, String data) {
		super();
		this.code = code;
		this.data = data;
	}

	public Message() {
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
