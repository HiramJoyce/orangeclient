package org.orange.manager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.orange.manager.domain.Result;
import org.orange.manager.entity.Group;
import org.orange.manager.entity.Host;
import org.orange.manager.entity.dto.GroupDto;
import org.orange.manager.entity.dto.HostDto;
import org.orange.manager.repository.GroupRepository;
import org.orange.manager.repository.HostRepository;
import org.orange.manager.service.NodeManager;
import org.orange.manager.util.MessageUtil;
import org.orange.manager.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
	private final HostRepository hostRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public OrangeManagerController(HostRepository hostRepository, GroupRepository groupRepository) {
        this.hostRepository = hostRepository;
        this.groupRepository = groupRepository;
    }

    @GetMapping("hello")
	public String hello() {
//        GroupDto groupDto = new GroupDto();
//        groupDto.setName("hahaha3");
//        HostDto hostDto = new HostDto();
//        hostDto.setId(7L);
//        HostDto hostDto2 = new HostDto();
//        hostDto2.setId(5L);
//        groupDto.setHostDtos(Arrays.asList(hostDto, hostDto2));
//        groupRepository.save(groupDto);
        return "Hello Orange!";
	}

	@GetMapping("register")
	public String register(HttpServletRequest req, String hostname) {
		String remoteHostIp = req.getRemoteHost();
		System.out.println(remoteHostIp);
		Optional<HostDto> findByIp = hostRepository.findByIp(remoteHostIp);
		System.out.println(findByIp.isPresent());
		if (!findByIp.isPresent()) {
            HostDto hostDto = new HostDto();
            hostDto.setIp(remoteHostIp);
            hostDto.setHostname(hostname);
            hostRepository.save(hostDto);
		} else {
            HostDto hostDto = findByIp.get();
            hostDto.setHostname(hostname);
			hostRepository.save(hostDto);
		}
		return "200";
	}

//	@PostMapping("execute")
//	public Result<?> execute(String command, String host) {
//		String[] ipArray = host.split(",");
//		ConcurrentHashMap<String, Result<?>> outputConcurrentHashMap = new ConcurrentHashMap<>();
//		Arrays.asList(ipArray).parallelStream().forEach(ip -> {
//			long start = System.currentTimeMillis();
//			Result<?> res = NodeManager.send(ip, command);
//			res.setMs(System.currentTimeMillis() - start);
//			outputConcurrentHashMap.put(ip, res);
//		});
//		return ResultUtil.success(outputConcurrentHashMap);
//	}

//	@GetMapping("getHosts")
//	public Result<?> getHosts() {
//		List<HostDto> hostDtos = hostRepository.findAll();
//        List<Host> hosts = checkLiveAndConvert(hostDtos);
//		return ResultUtil.success(hosts);
//	}

//    @GetMapping("getGroups")
//    public Result<?> getGroups() {
//        List<GroupDto> groupDtos = groupRepository.findAll();
//        List<Group> groups = groupDtos.parallelStream().map((GroupDto groupDto) -> {
//            Group group = Group.fromDto(groupDto);
//            List<HostDto> hostDtos = groupDto.getHostDtos();
//            int status = 0;
//            if (!CollectionUtils.isEmpty(hostDtos)) {
//                status = 1;
//                for (HostDto hostDto : hostDtos) {
//                    Result<?> heartbeatRes = NodeManager.heartbeat(hostDto.getIp(), JSON.toJSONString(MessageUtil.heartbeat()));
//                    if (!heartbeatRes.isSuccess()) {
//                        status = 0;
//                        break;
//                    }
//                }
//            }
//            group.setStatus(status);
//            group.setHosts(checkLiveAndConvert(hostDtos));
//            return group;
//        }).collect(Collectors.toList());
//        return ResultUtil.success(groups);
//    }

//    private List<Host> checkLiveAndConvert(List<HostDto> hostDtos) {
//        return hostDtos.parallelStream().map(hostDto -> {
//            Result<?> heartbeatRes = NodeManager.heartbeat(hostDto.getIp(),
//                    JSON.toJSONString(MessageUtil.heartbeat()));
//            Host host = Host.fromDto(hostDto);
//            if (heartbeatRes.isSuccess()) {
//                host.setStatus(1);
//            } else {
//                host.setStatus(0);
//            }
//            return host;
//        }).collect(Collectors.toList());
//    }

}
