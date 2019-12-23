package org.orange.manager.controller;

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
	public String execute(String command, String host) {
		System.out.println("--->>> " + command);
		return NodeManager.send(host, command);
	}

}
