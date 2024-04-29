package edu.duke.ece651.team7.attendanceServer.Service;

import java.util.List;

import edu.duke.ece651.team7.attendanceServer.POJO.Attendance;

public interface AttendanceService {

    public Integer getAttendanceNum(String sectionId, Integer attendStatus);

    public Integer getAttendStatus(String sectionId, String netId, String date);

    public Boolean addAttendance(String sectionId, String netId, String date, Integer attendStatus);

    public Boolean updateAttendance(String sectionId, String netId, String date, Integer attendStatus);

    public Long dateStr2Long(String date);

    public List<Attendance> getAttendancesOfSectionStudent(String sectionId, String netId);
    
}
