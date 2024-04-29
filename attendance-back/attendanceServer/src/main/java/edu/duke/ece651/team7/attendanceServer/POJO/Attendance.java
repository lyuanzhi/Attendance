package edu.duke.ece651.team7.attendanceServer.POJO;

import javax.persistence.*;

@Table(name = "`attendance`")
public class Attendance {
    @Id
    private String id;

    @Column(name = "sectionId")
    private String sectionid;

    @Column(name = "netId")
    private String netid;

    /**
     * 1 present, 2 absent, 3 tardy
     */
    @Column(name = "attendStatus")
    private Integer attendstatus;

    @Column(name = "classDate")
    private Long classdate;

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
     * 获取1 present, 2 absent, 3 tardy
     *
     * @return attendStatus - 1 present, 2 absent, 3 tardy
     */
    public Integer getAttendstatus() {
        return attendstatus;
    }

    /**
     * 设置1 present, 2 absent, 3 tardy
     *
     * @param attendstatus 1 present, 2 absent, 3 tardy
     */
    public void setAttendstatus(Integer attendstatus) {
        this.attendstatus = attendstatus;
    }

    /**
     * @return classDate
     */
    public Long getClassdate() {
        return classdate;
    }

    /**
     * @param classdate
     */
    public void setClassdate(Long classdate) {
        this.classdate = classdate;
    }
}