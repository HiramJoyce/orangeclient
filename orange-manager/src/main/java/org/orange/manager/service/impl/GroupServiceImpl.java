package org.orange.manager.service.impl;

import com.alibaba.fastjson.JSON;
import org.orange.manager.domain.Result;
import org.orange.manager.entity.Group;
import org.orange.manager.entity.dto.GroupDto;
import org.orange.manager.entity.dto.HostDto;
import org.orange.manager.repository.GroupRepository;
import org.orange.manager.service.CommonService;
import org.orange.manager.service.GroupService;
import org.orange.manager.service.NodeManager;
import org.orange.manager.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hiram 2019年12月27日 17:28
 */
@Service
public class GroupServiceImpl implements GroupService {
    private final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> queryGroups() {
        logger.info("--->>> call queryGroups()");
        List<GroupDto> groupDtos = groupRepository.findAll();
        List<Group> groups = groupDtos.parallelStream().map((GroupDto groupDto) -> {
            Group group = Group.fromDto(groupDto);
            List<HostDto> hostDtos = groupDto.getHostDtos();
            int status = 0;
            if (!CollectionUtils.isEmpty(hostDtos)) {
                status = 1;
                for (HostDto hostDto : hostDtos) {
                    Result<?> heartbeatRes = NodeManager.heartbeat(hostDto.getIp(), JSON.toJSONString(MessageUtil.heartbeat()));
                    if (!heartbeatRes.isSuccess()) {
                        status = 0;
                        break;
                    }
                }
            }
            group.setStatus(status);
            group.setHosts(CommonService.checkLiveAndConvert(hostDtos));
            return group;
        }).collect(Collectors.toList());
        logger.info(groups.toString());
        return groups;
    }
}
