package edu.duke.ece651.team7.attendanceServer.POJO;

import javax.persistence.*;

@Table(name = "`user`")
public class User {
    @Id
    @Column(name = "netId")
    private String netid;

    /**
     * 0 faculty, 1 student
     */
    private Integer type;

    @Column(name = "userName")
    private String username;

    @Column(name = "displayName")
    private String displayname;

    private String password;

    private String email;

    /**
     * 0 active,1 inactive
     */
    private Integer status;

    /**
     * @return netId
     */
    public String getNetid() {
        return netid;
    }

    /**
     * @param netid
     */
    public void setNetid(String netid) {
        this.netid = netid;
    }

    /**
     * 获取0 faculty, 1 student
     *
     * @return type - 0 faculty, 1 student
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置0 faculty, 1 student
     *
     * @param type 0 faculty, 1 student
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return userName
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return displayName
     */
    public String getDisplayname() {
        return displayname;
    }

    /**
     * @param displayname
     */
    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取0 active,1 inactive
     *
     * @return status - 0 active,1 inactive
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0 active,1 inactive
     *
     * @param status 0 active,1 inactive
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}