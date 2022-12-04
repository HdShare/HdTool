package com.hd.hdtool.utils;

public class StringUtils {

    public static String toHexString(byte[] paramArrayOfByte, boolean addColon, boolean isUpperCase) {
        StringBuilder localStringBuilder = new StringBuilder(2 * paramArrayOfByte.length);
        for (int i = 0; i < paramArrayOfByte.length; i++) {
            String hexString = Integer.toHexString(paramArrayOfByte[i]);
            int length = hexString.length();
            if (length == 1) {
                hexString = "0" + hexString;
            }
            if (length > 2) {
                hexString = hexString.substring(length - 2, length);
            }
            if (isUpperCase) {
                hexString = hexString.toUpperCase();
            }
            localStringBuilder.append(hexString);
            if (i < paramArrayOfByte.length - 1 && addColon) {
                localStringBuilder.append(":");
            }
        }
        return localStringBuilder.toString();
    }

}
