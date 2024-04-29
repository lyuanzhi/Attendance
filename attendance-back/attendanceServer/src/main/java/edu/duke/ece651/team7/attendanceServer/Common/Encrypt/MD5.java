package edu.duke.ece651.team7.attendanceServer.Common.Encrypt;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

/**
 * Provides utility methods for creating MD5 hashes of strings.
 */
public class MD5 {

    /**
     * Generates an MD5 hash of the provided string and encodes it in Base64 format.
     *
     * @param strValue The input string to hash.
     * @return A Base64 encoded string representing the MD5 hash of the input.
     * @throws Exception if an encoding error occurs.
     */
    public static String getMD5Str(String strValue) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String newStr = Base64.encodeBase64String(md5.digest(strValue.getBytes()));
        return newStr;
    }

}
