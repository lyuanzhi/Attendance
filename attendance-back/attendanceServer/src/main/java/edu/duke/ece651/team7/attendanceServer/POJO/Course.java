package edu.duke.ece651.team7.attendanceServer.POJO;

import javax.persistence.*;

@Table(name = "`course`")
public class Course {
    @Id
    @Column(name = "courseId")
    private String courseid;

    @Column(name = "courseName")
    private String coursename;

    /**
     * 0 active,1 inactive
     */
    private Integer status;

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
     * @return courseName
     */
    public String getCoursename() {
        return coursename;
    }

    /**
     * @param coursename
     */
    public void setCoursename(String coursename) {
        this.coursename = coursename;
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