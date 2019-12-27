package org.orange.manager.entity;

import org.orange.manager.entity.dto.GroupDto;
import org.orange.manager.entity.dto.HostDto;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hiram 2019年12月25日 17:19
 */
public class Group {
    private Long id;
    private String name;
    private int status;
    private List<Host> hosts;

    public Group() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Host> getHosts() {
        return hosts;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }

    public static Group fromDto(GroupDto groupDto) {
        Group group = new Group();
        group.setId(groupDto.getId());
        group.setName(groupDto.getName());
        List<Host> hosts = null;
        Host host;
        if (!CollectionUtils.isEmpty(groupDto.getHostDtos())) {
            hosts = new ArrayList<>();
            for (HostDto hostDto : groupDto.getHostDtos()) {
                host = Host.fromDto(hostDto);
                hosts.add(host);
            }
        }
        group.setHosts(hosts);
        return group;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", hosts=" + hosts +
                '}';
    }
}
