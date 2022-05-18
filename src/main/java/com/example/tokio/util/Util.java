package com.example.tokio.util;

import java.math.BigInteger;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    /**
     * Get current date in the format ISO 8601, yyyy-MM-dd HH:mm:ss.SSS.
     *
     * @return the current date
     */
    public static String getCurrentDate() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    // convert timestamp to date and time using this format 0000-00-00 00:00:00.000
    public static String convertTime(long timestamp) {

        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(date);
    }

    // days to timestamp seconds conversion
    public static long daysToSeconds(int days) {

        return (long) days * 24 * 60 * 60;
    }

    /**
     * Calculate the number of days between two dates (inclusive) and return the result as an int.
     *
     * @param currentDate the current date
     * @param appointmentDate the appointment date
     * @return the number of days between the two dates
     */
    public static int getDays(String currentDate, String appointmentDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = sdf.parse(currentDate);
            d2 = sdf.parse(appointmentDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (d1 == null || d2 == null) {
            return 0;
        }

        long diff = d2.getTime() - d1.getTime();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }

    public static int randomNumber(int number) {

        StringBuilder value = new StringBuilder();

        for (int i = 0; i < number; i++) {
            value.append((int)(Math.random() * 10));
        }
        BigInteger bi = new BigInteger(value.toString());
        return bi.intValue();
    }

    /**
     * String to binary.
     *
     * @param value the value
     * @return the string
     */
    public static Blob stringToBinary(String value) {
        try {
            return new javax.sql.rowset.serial.SerialBlob(value.getBytes());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Binary to string.
     *
     * @param value the value
     * @return the string
     */
    public static String binaryToString(Blob value) {
        try {
            return new String(value.getBytes(1, (int) value.length()));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Generate uuid v4.
     *
     * @return the string
     */
    public static String generateUUID() {
        return java.util.UUID.randomUUID().toString();
    }
}
