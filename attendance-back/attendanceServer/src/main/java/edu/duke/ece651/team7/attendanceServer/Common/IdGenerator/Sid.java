package edu.duke.ece651.team7.attendanceServer.Common.IdGenerator;

import java.util.Random;

/**
 * Utility class for generating unique string identifiers.
 */
public class Sid {

    /**
     * Generates a new unique identifier based on the current system time and a
     * random number.
     * 
     * @return A unique string identifier.
     */
    public static String next() {
        return String.valueOf(System.currentTimeMillis()) + String.valueOf(new Random().nextInt(1000));
    }

}
