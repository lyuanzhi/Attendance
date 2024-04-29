package edu.duke.ece651.team7.attendanceServer.Service;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.duke.ece651.team7.attendanceServer.Common.IdGenerator.Sid;
import edu.duke.ece651.team7.attendanceServer.Mapper.AttendanceMapper;
import edu.duke.ece651.team7.attendanceServer.POJO.Attendance;
import tk.mybatis.mapper.entity.Example;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Resource
    AttendanceMapper attendanceMapper;

    @Override
    public Integer getAttendanceNum(String sectionId, Integer attendStatus) {
        Example example = new Example(Attendance.class);
        example.createCriteria().andEqualTo("sectionid", sectionId).andEqualTo("attendstatus", attendStatus);
        return attendanceMapper.selectCountByExample(example);
    }

    @Override
    public Integer getAttendStatus(String sectionId, String netId, String date) {
        Example example = new Example(Attendance.class);
        example.createCriteria().andEqualTo("sectionid", sectionId).andEqualTo("netid", netId).andEqualTo("classdate",
                dateStr2Long(date));
        List<Attendance> attendances = attendanceMapper.selectByExample(example);
        if (attendances.isEmpty()) {
            return 0;
        }
        return attendances.get(0).getAttendstatus();
    }

    @Override
    public Boolean addAttendance(String sectionId, String netId, String date, Integer attendStatus) {
        Attendance attendance = new Attendance();
        attendance.setId(Sid.next());
        attendance.setSectionid(sectionId);
        attendance.setNetid(netId);
        attendance.setAttendstatus(attendStatus);
        attendance.setClassdate(dateStr2Long(date));
        attendanceMapper.insert(attendance);
        return true;
    }

    @Override
    public Boolean updateAttendance(String sectionId, String netId, String date, Integer attendStatus) {
        Example example = new Example(Attendance.class);
        example.createCriteria().andEqualTo("sectionid", sectionId).andEqualTo("netid", netId).andEqualTo("classdate",
                dateStr2Long(date));
        Attendance attendance = new Attendance();
        attendance.setAttendstatus(attendStatus);
        attendanceMapper.updateByExampleSelective(attendance, example);
        return true;
    }

    @Override
    public Long dateStr2Long(String date) {
        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime().getTime();
    }

    @Override
    public List<Attendance> getAttendancesOfSectionStudent(String sectionId, String netId) {
        Example example = new Example(Attendance.class);
        example.createCriteria().andEqualTo("sectionid", sectionId).andEqualTo("netid", netId);
        return attendanceMapper.selectByExample(example);
    }

}
