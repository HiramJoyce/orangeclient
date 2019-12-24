package org.orange.manager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.orange.manager.entity.Host;
import org.orange.manager.repository.HostRepository;
import org.orange.manager.service.NodeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hiram 2019年12月20日 14:58
 */
@RestController("/")
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
		if(!findByIp.isPresent()) {
			hostRepository.save(new Host(System.currentTimeMillis()+(int)Math.random()*1000, remoteHost));
		}
		return "200";
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

	@GetMapping("getHost")
	public String hostList() {
		JSONObject result = new JSONObject();
		List<String> ips = new ArrayList<>();
		hostRepository.findAll().forEach(host -> ips.add(host.getIp()));
		result.put("ips", ips);
		return result.toJSONString();
	}

}
