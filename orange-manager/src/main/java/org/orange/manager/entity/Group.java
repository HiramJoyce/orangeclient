package org.orange.manager.entity;

/**
 * @author hiram 2019年12月25日 17:19
 */
public class Group {
    private Long id;
    private String name;
    private String hostIds;

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

    public String getHostIds() {
        return hostIds;
    }

    public void setHostIds(String hostIds) {
        this.hostIds = hostIds;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hostIds='" + hostIds + '\'' +
                '}';
    }
}
