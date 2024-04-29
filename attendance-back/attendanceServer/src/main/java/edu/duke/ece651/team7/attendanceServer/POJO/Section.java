package edu.duke.ece651.team7.attendanceServer.POJO;

import javax.persistence.*;

@Table(name = "`section`")
public class Section {
    @Id
    @Column(name = "sectionId")
    private String sectionid;

    @Column(name = "sectionName")
    private String sectionname;

    @Column(name = "courseId")
    private String courseid;

    @Column(name = "facultyId")
    private String facultyid;

    /**
     * 0 active,1 inactive
     */
    private Integer status;

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
     * @return sectionName
     */
    public String getSectionname() {
        return sectionname;
    }

    /**
     * @param sectionname
     */
    public void setSectionname(String sectionname) {
        this.sectionname = sectionname;
    }

    /**
     * @return courseId
     */
    public String getCourseid() {
        return courseid;
    }

    /**
     * @param courseid
     */
    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    /**
     * @return facultyId
     */
    public String getFacultyid() {
        return facultyid;
    }

    /**
     * @param facultyid
     */
    public void setFacultyid(String facultyid) {
        this.facultyid = facultyid;
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