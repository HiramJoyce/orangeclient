package org.orange.manager.domain;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;

/**
 * @author caohailiang Http请求返回的最外层对象
 */
public class Result<T> {

	// 错误码
	private Integer code;
	
    // 是否成功
	private boolean success=true;

	// 提示信息
	private String msg;

	// 具体内容
	private T data;

	// 时间戳
	private Long timestamp;

	public Result() {
		this.timestamp = new Date().getTime();
	}

	public Long getTimestamps() {
		return timestamp;
	}

	public void setTimestamps(Long timestamps) {
		this.timestamp = new Date().getTime();
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public String jsonStr() {
		return JSONObject.toJSONString(this);
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Result [code=" + code + ", success=" + success + ", msg=" + msg + ", data=" + data + ", timestamp="
				+ timestamp + "]";
	}

 
}