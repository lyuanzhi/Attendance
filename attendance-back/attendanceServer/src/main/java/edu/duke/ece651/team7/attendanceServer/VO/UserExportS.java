package edu.duke.ece651.team7.attendanceServer.VO;

import java.util.ArrayList;
import java.util.List;

public class UserExportS {
    public String title;
    public String netId;
    public String name;
    public List<SectionExport> sectionList;

    public UserExportS() {
        this.sectionList = new ArrayList<>();
    }
}
