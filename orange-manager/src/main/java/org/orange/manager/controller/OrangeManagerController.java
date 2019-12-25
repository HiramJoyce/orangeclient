package org.orange.manager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.orange.manager.domain.Result;
import org.orange.manager.entity.Host;
import org.orange.manager.repository.HostRepository;
import org.orange.manager.service.NodeManager;
import org.orange.manager.util.MessageUtil;
import org.orange.manager.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hiram 2019年12月20日 14:58
 */
@RestController
@RequestMapping("/api")
public class OrangeManagerController {
	@Autowired
	private HostRepository hostRepository;

	@GetMapping("hello")
	public String hello() {
		return "Hello Orange!";
	}

	@GetMapping("register")
	public String register(HttpServletRequest req, String hostname) {
		String remoteHostIp = req.getRemoteHost();
		System.out.println(remoteHostIp);
		Optional<Host> findByIp = hostRepository.findByIp(remoteHostIp);
		System.out.println(findByIp.isPresent());
		if (!findByIp.isPresent()) {
			hostRepository.save(new Host(remoteHostIp, hostname));
		} else {
			Host host = findByIp.get();
			host.setHostname(hostname);
			hostRepository.save(host);
		}
		return "200";
	}

	@PostMapping("execute")
	public Result<?> execute(String command, String host) {
		String[] ipArray = host.split(",");
		ConcurrentHashMap<String, Result<?>> outputConcurrentHashMap = new ConcurrentHashMap<>();
		Arrays.asList(ipArray).parallelStream().forEach(ip -> {
			Result<?> res = NodeManager.send(ip, command);
			outputConcurrentHashMap.put(ip, res);
		});
		return ResultUtil.success(outputConcurrentHashMap);
	}

	@GetMapping("getHost")
	public Result<?> hostList() {
		List<Host> hosts = hostRepository.findAll();

		hosts=hosts.parallelStream().map(host -> {
			System.out.println("NodeChecker " + host);
			System.out.println("NodeChecker " + host.getIp());
			Result<?> heartbeatRes = NodeManager.heartbeat(host.getIp(),
					JSON.toJSONString(MessageUtil.heartbeat()));
			if (heartbeatRes.isSuccess()) {
				System.out.println(host.getIp()+" UP   :" + heartbeatRes);
				host.setStatus(1);
			} else {
				System.out.println(host.getIp()+" DOWN : " + heartbeatRes);
				host.setStatus(0);
			}
			return host;
		}).collect(Collectors.toList());
		return ResultUtil.success(hosts);
	}

}
