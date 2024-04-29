package edu.duke.ece651.team7.attendanceServer.Service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.duke.ece651.team7.attendanceServer.Mapper.SectionMapper;
import edu.duke.ece651.team7.attendanceServer.Mapper.SectionstudentMapper;
import edu.duke.ece651.team7.attendanceServer.Mapper.UserMapper;
import edu.duke.ece651.team7.attendanceServer.POJO.Section;
import edu.duke.ece651.team7.attendanceServer.POJO.Sectionstudent;
import edu.duke.ece651.team7.attendanceServer.POJO.User;
import tk.mybatis.mapper.entity.Example;

@Service
public class SectionServiceImpl implements SectionService {

    @Resource
    UserMapper userMapper;

    @Resource
    SectionMapper sectionMapper;

    @Resource
    SectionstudentMapper sectionstudentMapper;

    @Override
    public List<Section> getSections(String facultyId) {
        Example example = new Example(Section.class);
        example.createCriteria().andEqualTo("facultyid", facultyId).andEqualTo("status", 0);
        example.orderBy("sectionname");
        List<Section> sections = sectionMapper.selectByExample(example);
        if (sections == null || sections.isEmpty()) {
            return new ArrayList<Section>();
        }
        return sections;
    }

    @Override
    public Integer getStudentsNum(String sectionId) {
        Example example = new Example(Sectionstudent.class);
        example.createCriteria().andEqualTo("sectionid", sectionId).andEqualTo("status", 0);
        return sectionstudentMapper.selectCountByExample(example);
    }

    @Override
    public List<Sectionstudent> getStudents(String sectionId) {
        Example example = new Example(Sectionstudent.class);
        example.createCriteria().andEqualTo("sectionid", sectionId).andEqualTo("status", 0);
        example.orderBy("netid");
        return sectionstudentMapper.selectByExample(example);
    }

    @Override
    public Boolean checkStudent(String sectionId, String netId) {
        Example example = new Example(Sectionstudent.class);
        example.createCriteria().andEqualTo("sectionid", sectionId).andEqualTo("netid", netId).andEqualTo("status", 0);
        return sectionstudentMapper.selectCountByExample(example) > 0;
    }

    @Override
    public String getSectionName(String sectionId) {
        return sectionMapper.selectByPrimaryKey(sectionId).getSectionname();
    }

    @Override
    public List<Sectionstudent> getSectionsOfStudent(String netId) {
        Example example = new Example(Sectionstudent.class);
        example.createCriteria().andEqualTo("netid", netId).andEqualTo("status", 0);
        example.orderBy("sectionid");
        return sectionstudentMapper.selectByExample(example);
    }

    @Override
    public Boolean changeNotifyStatus(String sectionId, String netId, Integer notifyStatus) {
        Example example = new Example(Sectionstudent.class);
        example.createCriteria().andEqualTo("sectionid", sectionId).andEqualTo("netid", netId);
        Sectionstudent sectionstudent = new Sectionstudent();
        sectionstudent.setNotifystatus(notifyStatus);
        sectionstudentMapper.updateByExampleSelective(sectionstudent, example);
        return true;
    }

    @Override
    public Integer getNotifyStatus(String sectionId, String netId) {
        Example example = new Example(Sectionstudent.class);
        example.createCriteria().andEqualTo("sectionid", sectionId).andEqualTo("netid", netId);
        return sectionstudentMapper.selectByExample(example).get(0).getNotifystatus();
    }

    @Override
    public List<User> getStudentsOfSection(String sectionId) {
        Example example = new Example(Sectionstudent.class);
        example.createCriteria().andEqualTo("sectionid", sectionId).andEqualTo("status", 0);
        List<Sectionstudent> students = sectionstudentMapper.selectByExample(example);
        if (students == null || students.isEmpty()) {
            return new ArrayList<User>();
        }
        List<User> allStudents = new ArrayList<>();
        for (Sectionstudent student : students) {
            allStudents.add(userMapper.selectByPrimaryKey(student.getNetid()));
        }
        return allStudents;
    }

}
