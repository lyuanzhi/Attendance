package edu.duke.ece651.team7.attendanceServer.Controller;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.duke.ece651.team7.attendanceServer.Common.RespResult.CommonResult;
import edu.duke.ece651.team7.attendanceServer.POJO.Section;
import edu.duke.ece651.team7.attendanceServer.POJO.User;
import edu.duke.ece651.team7.attendanceServer.Service.SectionService;
import edu.duke.ece651.team7.attendanceServer.Service.UserService;
import edu.duke.ece651.team7.attendanceServer.VO.LoginResp;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @Resource
    SectionService sectionService;

    @PostMapping(value = "/login")
    public CommonResult<LoginResp> login(@RequestParam("netID") String netID,
            @RequestParam("password") String password,
            @RequestParam("isFaculty") Boolean isFaculty) {

        User user = userService.login(netID, password, isFaculty ? 0 : 1);
        if (user == null) {
            return CommonResult.success(null);
        }
        LoginResp loginResp = new LoginResp();
        loginResp.netid = user.getNetid();
        loginResp.username = user.getUsername();
        loginResp.displayname = user.getDisplayname();
        loginResp.email = user.getEmail();
        loginResp.sections = new ArrayList<Section>();
        if (isFaculty) {
            loginResp.sections = sectionService.getSections(netID);
        }
        return CommonResult.success(loginResp);
    }

    @PostMapping(value = "/changeDisplayName")
    public CommonResult<Boolean> changeDisplayName(@RequestParam("netId") String netId,
            @RequestParam("displayName") String displayName) {

        return CommonResult.success(userService.changeDisplayName(netId, displayName));
    }

}
