package org.orange.manager.entity;

import javax.persistence.*;

/**
 * @author hiram 2019年12月23日 22:22
 */
@Entity
@Table(name = "t_host")
public class Host {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String hostname;
	private String desc;
	private String ip;
	private int status;

	public Host(String ip, String hostname) {
		this.ip = ip;
		this.hostname = hostname;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Host{" +
				"id=" + id +
				", name='" + name + '\'' +
				", hostname='" + hostname + '\'' +
				", desc='" + desc + '\'' +
				", ip='" + ip + '\'' +
				", status='" + status + '\'' +
				'}';
	}
}
