package edu.duke.ece651.team7.attendanceServer.POJO;

import javax.persistence.*;

@Table(name = "`sectionStudent`")
public class Sectionstudent {
    @Id
    private String id;

    @Column(name = "sectionId")
    private String sectionid;

    @Column(name = "netId")
    private String netid;

    /**
     * 0 active,1 inactive
     */
    @Column(name = "notifyStatus")
    private Integer notifystatus;

    /**
     * 0 active,1 inactive
     */
    private Integer status;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return sectionId
     */
    public String getSectionid() {
        return sectionid;
    }

    /**
     * @param sectionid
     */
    public void setSectionid(String sectionid) {
        this.sectionid = sectionid;
    }

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
     * 获取0 active,1 inactive
     *
     * @return notifyStatus - 0 active,1 inactive
     */
    public Integer getNotifystatus() {
        return notifystatus;
    }

    /**
     * 设置0 active,1 inactive
     *
     * @param notifystatus 0 active,1 inactive
     */
    public void setNotifystatus(Integer notifystatus) {
        this.notifystatus = notifystatus;
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