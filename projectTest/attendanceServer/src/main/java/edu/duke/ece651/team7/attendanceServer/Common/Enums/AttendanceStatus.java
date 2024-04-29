package edu.duke.ece651.team7.attendanceServer.Common.Enums;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AttendanceStatus {

    public static final int PRESENT = 1;
    public static final int ABSENT = 2;
    public static final int TARDY = 3;

    public static final Map<Integer, String> statusMap;

    static {
        statusMap = new ConcurrentHashMap<>();
        statusMap.put(1, "Present");
        statusMap.put(2, "Absent");
        statusMap.put(3, "Tardy");
    }
    
}

