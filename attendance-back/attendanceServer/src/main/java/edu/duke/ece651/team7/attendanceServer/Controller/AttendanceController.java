package edu.duke.ece651.team7.attendanceServer.Controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.duke.ece651.team7.attendanceServer.Common.Enums.AttendanceStatus;
import edu.duke.ece651.team7.attendanceServer.Common.Format.FormatVisitor;
import edu.duke.ece651.team7.attendanceServer.Common.Format.FormatVisitorImpl;
import edu.duke.ece651.team7.attendanceServer.Common.Format.JSONNode;
import edu.duke.ece651.team7.attendanceServer.Common.Format.XMLNode;
import edu.duke.ece651.team7.attendanceServer.Common.Notification.EmailNotification;
import edu.duke.ece651.team7.attendanceServer.Common.Notification.EmailNotificationSubscriber;
import edu.duke.ece651.team7.attendanceServer.Common.Notification.NotificationPublisher;
import edu.duke.ece651.team7.attendanceServer.Common.RedisBasic.RedisOperator;
import edu.duke.ece651.team7.attendanceServer.Common.RespResult.CommonResult;
import edu.duke.ece651.team7.attendanceServer.POJO.Attendance;
import edu.duke.ece651.team7.attendanceServer.POJO.Sectionstudent;
import edu.duke.ece651.team7.attendanceServer.POJO.User;
import edu.duke.ece651.team7.attendanceServer.Service.AttendanceService;
import edu.duke.ece651.team7.attendanceServer.Service.SectionService;
import edu.duke.ece651.team7.attendanceServer.Service.UserService;
import edu.duke.ece651.team7.attendanceServer.VO.AttendanceExport;
import edu.duke.ece651.team7.attendanceServer.VO.AttendanceResp;
import edu.duke.ece651.team7.attendanceServer.VO.HomeResp;
import edu.duke.ece651.team7.attendanceServer.VO.SectionExport;
import edu.duke.ece651.team7.attendanceServer.VO.StudentExport;
import edu.duke.ece651.team7.attendanceServer.VO.UserExportF;
import edu.duke.ece651.team7.attendanceServer.VO.UserExportS;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Resource
    SectionService sectionService;

    @Resource
    AttendanceService attendanceService;

    @Resource
    UserService userService;

    @Resource
    public RedisOperator redis;

    @PostMapping(value = "/getAttendData")
    public CommonResult<HomeResp> getAttendData(@RequestParam("sectionId") String sectionId,
            @RequestParam("netId") String netId) {

        HomeResp homeResp = new HomeResp();
        homeResp.studentNum = sectionService.getStudentsNum(sectionId);
        homeResp.presentNum = attendanceService.getAttendanceNum(sectionId, 1);
        homeResp.absentNum = attendanceService.getAttendanceNum(sectionId, 2);
        homeResp.tardyNum = attendanceService.getAttendanceNum(sectionId, 3);
        return CommonResult.success(homeResp);
    }

    @PostMapping(value = "/getAttendance")
    public CommonResult<List<AttendanceResp>> getAttendance(@RequestParam("sectionId") String sectionId,
            @RequestParam("netId") String netId, @RequestParam("date") String date) {

        List<Sectionstudent> sectionstudents;
        if (netId.equals("")) {
            sectionstudents = sectionService.getStudents(sectionId);
        } else {
            if (sectionService.checkStudent(sectionId, netId)) {
                sectionstudents = new ArrayList<>();
                Sectionstudent ss = new Sectionstudent();
                ss.setNetid(netId);
                sectionstudents.add(ss);
            } else {
                return CommonResult.failed("Invalid NetID!");
            }
        }
        List<AttendanceResp> attendanceResps = new ArrayList<>();
        for (Sectionstudent sectionstudent : sectionstudents) {
            AttendanceResp attendanceResp = new AttendanceResp();
            attendanceResp.netId = sectionstudent.getNetid();
            attendanceResp.displayName = userService.getDisplayName(sectionstudent.getNetid());
            attendanceResp.attendStatus = attendanceService.getAttendStatus(sectionId, sectionstudent.getNetid(), date);
            attendanceResps.add(attendanceResp);
        }
        return CommonResult.success(attendanceResps);
    }

    @PostMapping(value = "/takeAttendance")
    public CommonResult<Boolean> takeAttendance(@RequestParam("sectionId") String sectionId,
            @RequestParam("netId") String netId, @RequestParam("date") String date,
            @RequestParam("attendStatus") Integer attendStatus) {

        Integer oldStatus = attendanceService.getAttendStatus(sectionId, netId, date);
        if (oldStatus.equals(0)) {
            return CommonResult.success(attendanceService.addAttendance(sectionId, netId, date, attendStatus));
        }
        Boolean res = attendanceService.updateAttendance(sectionId, netId, date, attendStatus);
        Integer notifyStatus = sectionService.getNotifyStatus(sectionId, netId);
        if (res && notifyStatus.equals(0)) {
            User student = userService.getUser(netId);
            Date d = new Date(attendanceService.dateStr2Long(date).longValue());
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd");
            String formattedDate = dateFormat.format(d);
            String dstEmail = student.getEmail();
            String sectionName = sectionService.getSectionName(sectionId);
            String subject = "Attendance Status Change Notification";
            String content = "Hi " + student.getUsername().split("\\s+")[0] + ",\n\n" +
                    "Your attendance status of " + sectionName + " on " + formattedDate +
                    " has changed from:\n\n" + AttendanceStatus.statusMap.get(oldStatus) + " to "
                    + AttendanceStatus.statusMap.get(attendStatus) + ".\n\n" +
                    "Thanks,\n" +
                    "ECE651 Team7";
            // System.out.println(content);
            NotificationPublisher publisher = new NotificationPublisher();
            publisher.notifySubscriber(new EmailNotification(subject, content),
                    new EmailNotificationSubscriber(dstEmail));
        }
        return CommonResult.success(res);
    }

    public String export(String type, Object object) {
        FormatVisitor visitor = new FormatVisitorImpl();
        if (type.equals("JSON")) {
            JSONNode jsonNode = new JSONNode(object);
            return jsonNode.accept(visitor);
        } else {
            XMLNode xmlNode = new XMLNode(object);
            return xmlNode.accept(visitor);
        }
    }

    private String computeParticipation(String sectionId, String netId) {
        List<Attendance> attendances = attendanceService.getAttendancesOfSectionStudent(sectionId, netId);
        double participation = 0;
        for (Attendance attendance : attendances) {
            if (attendance.getAttendstatus().equals(AttendanceStatus.PRESENT))
                participation += 1;
            if (attendance.getAttendstatus().equals(AttendanceStatus.ABSENT))
                participation += 0;
            if (attendance.getAttendstatus().equals(AttendanceStatus.TARDY))
                participation += 0.8;
        }
        if (attendances.size() == 0) {
            return String.valueOf(0);
        }
        participation = participation / (double) attendances.size();
        return String.format("%.2f", participation * 100) + "%";
    }

    @PostMapping(value = "/facultyExport")
    public CommonResult<String> facultyExport(@RequestParam("sectionId") String sectionId,
            @RequestParam("netId") String netId, @RequestParam("type") String type) {
        User curUser = userService.getUser(netId);
        UserExportF userExportF = new UserExportF();
        userExportF.title = "Attendance Report For Faculty";
        userExportF.netId = curUser.getNetid();
        userExportF.name = curUser.getUsername();
        userExportF.sectionName = sectionService.getSectionName(sectionId);
        List<User> students = sectionService.getStudentsOfSection(sectionId);
        if (students == null) {
            students = new ArrayList<>();
        }
        for (User student : students) {
            StudentExport studentExport = new StudentExport();
            studentExport.name = student.getUsername();
            studentExport.netId = student.getNetid();
            studentExport.participation = computeParticipation(sectionId, student.getNetid());
            userExportF.studentList.add(studentExport);
        }
        return CommonResult.success(export(type, userExportF));
    }

    @PostMapping(value = "/studentExport")
    public CommonResult<String> studentExport(@RequestParam("netId") String netId,
            @RequestParam("type") String type) {
        User curUser = userService.getUser(netId);
        UserExportS userExportS = new UserExportS();
        userExportS.title = "Attendance Report For Student";
        userExportS.netId = curUser.getNetid();
        userExportS.name = curUser.getUsername();
        List<Sectionstudent> sectionstudents = sectionService.getSectionsOfStudent(curUser.getNetid());
        for (Sectionstudent ss : sectionstudents) {
            SectionExport sectionExport = new SectionExport();
            sectionExport.sectionName = sectionService.getSectionName(ss.getSectionid());
            sectionExport.participation = computeParticipation(ss.getSectionid(), curUser.getNetid());
            List<Attendance> attendances = attendanceService.getAttendancesOfSectionStudent(ss.getSectionid(),
                    curUser.getNetid());
            for (Attendance attendance : attendances) {
                AttendanceExport attendanceExport = new AttendanceExport();
                Date date = new Date(attendance.getClassdate());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                attendanceExport.date = formatter.format(date);
                attendanceExport.attendStatus = AttendanceStatus.statusMap.get(attendance.getAttendstatus());
                sectionExport.attendanceList.add(attendanceExport);
            }
            userExportS.sectionList.add(sectionExport);
        }
        return CommonResult.success(export(type, userExportS));
    }

    @PostMapping(value = "/codeSync")
    public CommonResult<Boolean> codeSync(@RequestParam("sectionId") String sectionId,
            @RequestParam("code") String code) {
        redis.set(sectionId, code, 10);
        return CommonResult.success(true);
    }

    @PostMapping(value = "/setRemainToAbsent")
    public CommonResult<Boolean> setRemainToAbsent(@RequestParam("sectionId") String sectionId,
            @RequestParam("date") String date) {
        List<Sectionstudent> sectionstudents = sectionService.getStudents(sectionId);
        for (Sectionstudent sectionstudent : sectionstudents) {
            Integer oldStatus = attendanceService.getAttendStatus(sectionId, sectionstudent.getNetid(), date);
            if (oldStatus.equals(0)) {
                attendanceService.addAttendance(sectionId, sectionstudent.getNetid(), date, AttendanceStatus.ABSENT);
            }
        }
        return CommonResult.success(true);
    }

    @PostMapping(value = "/takeAttendance2")
    public CommonResult<Boolean> takeAttendance2(@RequestParam("sectionId") String sectionId,
            @RequestParam("netId") String netId, @RequestParam("date") String date, @RequestParam("code") String code) {
        if (!redis.get(sectionId).equals(code)) {
            return CommonResult.success(false);
        }
        if (!sectionService.checkStudent(sectionId, netId)) {
            return CommonResult.success(false);
        }
        Integer oldStatus = attendanceService.getAttendStatus(sectionId, netId, date);
        if (oldStatus.equals(0)) {
            return CommonResult
                    .success(attendanceService.addAttendance(sectionId, netId, date, AttendanceStatus.PRESENT));
        }
        return CommonResult
                .success(attendanceService.updateAttendance(sectionId, netId, date, AttendanceStatus.PRESENT));
    }

}
