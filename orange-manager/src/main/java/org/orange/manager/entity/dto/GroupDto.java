package org.orange.manager.entity.dto;

import javax.persistence.*;
import java.util.List;

/**
 * @author hiram 2019年12月25日 17:19
 */
@Entity
@Table(name = "t_group")
public class GroupDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "group_host",joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "host_id"))
    private List<HostDto> hostDtos;

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

    public List<HostDto> getHostDtos() {
        return hostDtos;
    }

    public void setHostDtos(List<HostDto> hostDtos) {
        this.hostDtos = hostDtos;
    }

    @Override
    public String toString() {
        return "GroupDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hostDtos=" + hostDtos +
                '}';
    }
}
