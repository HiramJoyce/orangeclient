package org.orange.manager.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.orange.manager.domain.Result;
import org.orange.manager.entity.Host;
import org.orange.manager.repository.HostRepository;
import org.orange.manager.service.NodeManager;
import org.orange.manager.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hiram 2019年12月20日 14:58
 */
@RestController("/api")
public class OrangeManagerController {
	@Autowired
	private HostRepository hostRepository;

	@GetMapping("hello")
	public String hello() {
		return "Hello Orange!";
	}

	@GetMapping("register")
	public String register(HttpServletRequest req) {
		String remoteHost = req.getRemoteHost();
		System.out.println(remoteHost);
		Optional<Host> findByIp = hostRepository.findByIp(remoteHost);
		System.out.println(findByIp.isPresent());
		if (!findByIp.isPresent()) {
			hostRepository.save(new Host(System.currentTimeMillis() + (int) Math.random() * 1000, remoteHost));
		}
		return "200";
	}

	@PostMapping("execute")
	public Result<?> execute(String command, String host) {

		String[] ipArray = host.split(",");

		ConcurrentHashMap<String, JSONArray> outputConcurrentHashMap = new ConcurrentHashMap<>();

		Arrays.asList(ipArray).parallelStream().forEach(ip -> {
			outputConcurrentHashMap.put(ip, JSONArray.parseArray(NodeManager.send(ip, command)));
		});

		return ResultUtil.success(outputConcurrentHashMap);
	}

	@GetMapping("getHost")
	public Result<?> hostList() {
		List<Host> hosts = hostRepository.findAll();
		return ResultUtil.success(hosts);
	}

}
