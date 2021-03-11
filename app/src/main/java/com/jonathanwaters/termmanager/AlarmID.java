package com.jonathanwaters.termmanager;

import android.content.Context;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "alarm")
public class AlarmID {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "request_code")
    private int requestCode;

    public AlarmID(int id, int requestCode) {
        this.id = id;
        this.requestCode = requestCode;
    }

    @Ignore
    public AlarmID() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public static int getAlarmID(Context context) {
        AlarmID alarmID = new AlarmID();
        int newID = 10;
        Database db = Database.getInstance(context);
        List<AlarmID> alarmIDList = db.alarmDAO().getAll();

        for (AlarmID alarm : alarmIDList) {
            if (alarm.getRequestCode() > newID)
                newID = alarm.getRequestCode();
        }

        newID++;

        alarmID.setRequestCode(newID);

        db.alarmDAO().insert(alarmID);

        return newID;
    }
}
