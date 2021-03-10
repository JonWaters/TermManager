package com.jonathanwaters.termmanager;

public class AlarmID {
    private static int alarmID = 10;

    public static int getAlarmID() {
        return ++alarmID;
    }
}
