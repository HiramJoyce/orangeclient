package org.orange.manager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.orange.manager.service.NodeManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
		System.out.println("--->>> " + command);
		JSONObject out = new JSONObject();
		out.put("data", JSONArray.parseArray(NodeManager.send(host, command)));
		return out;
	}

}
