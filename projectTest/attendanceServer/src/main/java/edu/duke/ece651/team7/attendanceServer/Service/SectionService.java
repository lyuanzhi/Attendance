package edu.duke.ece651.team7.attendanceServer.Service;

import java.util.List;

import edu.duke.ece651.team7.attendanceServer.POJO.Section;
import edu.duke.ece651.team7.attendanceServer.POJO.Sectionstudent;
import edu.duke.ece651.team7.attendanceServer.POJO.User;

public interface SectionService {

    public List<Section> getSections(String facultyId);

    public Integer getStudentsNum(String sectionId);

    public List<Sectionstudent> getStudents(String sectionId);

    public Boolean checkStudent(String sectionId, String netId);

    public String getSectionName(String sectionId);

    public List<Sectionstudent> getSectionsOfStudent(String netId);

    public Boolean changeNotifyStatus(String sectionId, String netId, Integer notifyStatus);

    public Integer getNotifyStatus(String sectionId, String netId);

    public List<User> getStudentsOfSection(String sectionId);
    
}
