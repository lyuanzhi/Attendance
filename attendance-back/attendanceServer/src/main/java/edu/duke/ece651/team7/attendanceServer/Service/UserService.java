package edu.duke.ece651.team7.attendanceServer.Service;

import edu.duke.ece651.team7.attendanceServer.POJO.User;

public interface UserService {

    public User login(String netId, String password, int type);

    public String getDisplayName(String netId);

    public Boolean changeDisplayName(String netId, String displayName);

    public User getUser(String netId);

}
