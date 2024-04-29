package edu.duke.ece651.team7.attendanceServer.Service;

import java.util.List;

import javax.annotation.Resource;

import edu.duke.ece651.team7.attendanceServer.Common.Encrypt.MD5;
import edu.duke.ece651.team7.attendanceServer.Mapper.UserMapper;
import edu.duke.ece651.team7.attendanceServer.POJO.User;
import tk.mybatis.mapper.entity.Example;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public User login(String netId, String password, int type) {
        String passwordMD5 = "";
        try {
            passwordMD5 = MD5.getMD5Str(password);
        } catch (Exception e) {
            return null;
        }
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("netid", netId).andEqualTo("password", passwordMD5).andEqualTo("type", type)
                .andEqualTo("status", 0);
        List<User> users = userMapper.selectByExample(example);
        if (users == null || users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public String getDisplayName(String netId) {
        return userMapper.selectByPrimaryKey(netId).getDisplayname();
    }

    @Override
    public Boolean changeDisplayName(String netId, String displayName) {
        User user = new User();
        user.setNetid(netId);
        user.setDisplayname(displayName);
        userMapper.updateByPrimaryKeySelective(user);
        return true;
    }

    @Override
    public User getUser(String netId) {
        return userMapper.selectByPrimaryKey(netId);
    }

}
