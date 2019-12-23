package org.orange.manager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.orange.manager.service.NodeManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hiram 2019年12月20日 14:58
 */
@RestController("/")
public class OrangeClientController {

	@GetMapping("hello")
	public String hello() {
		return "Hello Orange!";
	}

	@PostMapping("execute")
	public JSONObject execute(String command, String host) {

		String[] ipArray = host.split(",");

		JSONObject output = new JSONObject();

		for (int i = 0; i < ipArray.length; i++) {
			output.put(ipArray[i], JSONArray.parseArray(NodeManager.send(ipArray[i], command)));
		}
		return output;
	}

}
