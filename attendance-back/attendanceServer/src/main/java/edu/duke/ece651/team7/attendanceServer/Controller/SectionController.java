package edu.duke.ece651.team7.attendanceServer.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.duke.ece651.team7.attendanceServer.Common.RespResult.CommonResult;
import edu.duke.ece651.team7.attendanceServer.POJO.Sectionstudent;
import edu.duke.ece651.team7.attendanceServer.Service.SectionService;
import edu.duke.ece651.team7.attendanceServer.VO.SectionResp;

@RestController
@RequestMapping("/section")
public class SectionController {

    @Resource
    SectionService sectionService;

    @PostMapping(value = "/getSectionData")
    public CommonResult<List<SectionResp>> getSectionData(@RequestParam("netId") String netId) {

        List<SectionResp> sectionResps = new ArrayList<>();
        List<Sectionstudent> sectionstudents = sectionService.getSectionsOfStudent(netId);
        for (Sectionstudent ss : sectionstudents) {
            SectionResp sectionResp = new SectionResp();
            sectionResp.sectionId = ss.getSectionid();
            sectionResp.notifyStatus = ss.getNotifystatus();
            sectionResp.sectionName = sectionService.getSectionName(ss.getSectionid());
            sectionResps.add(sectionResp);
        }
        return CommonResult.success(sectionResps);
    }

    @PostMapping(value = "/changeNotifyStatus")
    public CommonResult<Boolean> changeNotifyStatus(@RequestParam("sectionId") String sectionId,
            @RequestParam("netId") String netId, @RequestParam("notifyStatus") Integer notifyStatus) {

        return CommonResult.success(sectionService.changeNotifyStatus(sectionId, netId, notifyStatus));
    }

}
