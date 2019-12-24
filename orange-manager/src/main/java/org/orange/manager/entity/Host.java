package org.orange.manager.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author hiram 2019年12月23日 22:22
 */
@Entity
@Table(name = "t_host")
@Data
public class Host {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ip;

    public Host(Long id, String ip) {
        this.id = id;
        this.ip = ip;
    }

    public Host() {
    }

    public Host(String ip) {
        this.ip = ip;
    }
    
    

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

	@Override
    public String toString() {
        return "Host{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                '}';
    }
}
