package org.orange.manager.entity.dto;

import javax.persistence.*;
import java.util.List;

/**
 * @author hiram 2019年12月27日 10:53
 */
@Entity
@Table(name = "t_host")
public class HostDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ip;
    private String hostname;
    private String name;
    private String desc;
    @ManyToMany(mappedBy = "hostDtos")
    private List<GroupDto> groupDtos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<GroupDto> getGroupDtos() {
        return groupDtos;
    }

    public void setGroupDtos(List<GroupDto> groupDtos) {
        this.groupDtos = groupDtos;
    }

    @Override
    public String toString() {
        return "HostDto{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", hostname='" + hostname + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
