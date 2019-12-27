package org.orange.manager.service;

import com.alibaba.fastjson.JSON;
import org.orange.manager.domain.Result;
import org.orange.manager.entity.Host;
import org.orange.manager.entity.dto.HostDto;
import org.orange.manager.util.MessageUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hiram 2019年12月27日 17:30
 */
public class CommonService {
    public static List<Host> checkLiveAndConvert(List<HostDto> hostDtos) {
        return hostDtos.parallelStream().map(hostDto -> {
            Result<?> heartbeatRes = NodeManager.heartbeat(hostDto.getIp(),
                    JSON.toJSONString(MessageUtil.heartbeat()));
            Host host = Host.fromDto(hostDto);
            if (heartbeatRes.isSuccess()) {
                host.setStatus(1);
            } else {
                host.setStatus(0);
            }
            return host;
        }).collect(Collectors.toList());
    }
}
