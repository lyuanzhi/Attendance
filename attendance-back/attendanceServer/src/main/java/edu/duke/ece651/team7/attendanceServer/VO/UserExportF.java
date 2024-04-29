package edu.duke.ece651.team7.attendanceServer.VO;

import java.util.ArrayList;
import java.util.List;

public class UserExportF {
    public String title;
    public String netId;
    public String name;
    public String sectionName;
    public List<StudentExport> studentList;

    public UserExportF() {
        this.studentList = new ArrayList<>();
    }

}