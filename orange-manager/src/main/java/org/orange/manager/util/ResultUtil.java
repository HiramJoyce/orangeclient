package org.orange.manager.util;

import org.orange.manager.domain.Result;

/**
 * @author caohailiang 返回结果工具类
 */
public class ResultUtil {
	/**
	 * 
	 * @description：Base result method @author： Daniel Pine
	 * 
	 * @email:danielpine@sina.com @datetime： 2018年12月7日-上午11:47:02
	 * @return
	 */
	public static Result<?> msg(Integer code, String msg, Object data, boolean success) {
		Result<Object> result = new Result<Object>();
		result.setCode(code);
		result.setMsg(msg);
		result.setData(data);
		result.setSuccess(success);
		return result;
	}

	public static Result<?> success(Object object, String msg) {
		return msg(1, msg, object, true);
	}

	public static Result<?> success(Object object) {
		return success(object, "成功");
	}

	public static Result<?> success() {
		return success(null);
	}

	public static Result<?> error(Integer code, String msg) {
		return msg(code, msg);
	}

	public static Result<?> error(String msg) {
		return msg(0, msg);
	}

	public static Result<?> msg(Integer code, String msg) {
		return msg(code, msg, null, false);
	}

}
