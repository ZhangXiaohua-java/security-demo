package cn.edu.huel.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 张晓华
 * @date 2023-2-16
 */
@Data
public class Result {

	private Integer code;

	private String msg;

	private Map<String, Object> data;

	public Result(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static Result ok() {
		return new Result(200, "ok");
	}

	public static Result error() {
		return new Result(500, "fail");
	}

	public Result put(String key, Object value) {
		if (this.data == null) {
			this.data = new HashMap<>();
		}
		this.data.put(key, value);
		return this;
	}

	public static Result ok(String key, Object value) {
		return Result.ok().put(key, value);
	}

	public static Result error(String key, Object value) {
		return Result.error().put(key, value);
	}


}
