package edu.duke.ece651.team7.attendanceServer.VO;

import java.util.ArrayList;
import java.util.List;

public class SectionExport {
    public String sectionName;
    public String participation;
    public List<AttendanceExport> attendanceList;

    public SectionExport() {
        this.attendanceList = new ArrayList<>();
    }

}
