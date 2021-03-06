package com.jonathanwaters.termmanager;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessment"
        ,foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "id",
                childColumns = "course_id",
                onDelete = ForeignKey.CASCADE
        )
)
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "due_date")
    private Date dueDate;
    @ColumnInfo(name = "info")
    private String info;
    @ColumnInfo(name = "alarm_date")
    private Date alarmDate;
    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name = "course_id")
    private int courseID;
    @ColumnInfo(name = "alarm_code")
    private int alarmCode;
    @ColumnInfo(name = "due_alarm_date")
    private Date dueAlarmDate;
    @ColumnInfo(name = "due_alarm_code")
    private int dueAlarmCode;
    @ColumnInfo(name = "start_date")
    private Date startDate;

    public Assessment(int id, String name, String type, String title, Date dueDate, String info,
                      Date alarmDate, String status, int courseID, int alarmCode, Date dueAlarmDate,
                      int dueAlarmCode, Date startDate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.title = title;
        this.dueDate = dueDate;
        this.info = info;
        this.alarmDate = alarmDate;
        this.status= status;
        this.courseID = courseID;
        this.alarmCode = alarmCode;
        this.dueAlarmDate = dueAlarmDate;
        this.dueAlarmCode = dueAlarmCode;
        this.startDate = startDate;
    }

    @Ignore
    public Assessment(String name, String type, String title, Date dueDate, String info,
                      Date alarmDate, String status, int courseID, int alarmCode, Date dueAlarmDate,
                      int dueAlarmCode, Date startDate) {
        this.name = name;
        this.type = type;
        this.title = title;
        this.dueDate = dueDate;
        this.info = info;
        this.alarmDate = alarmDate;
        this.status = status;
        this.courseID = courseID;
        this.alarmCode = alarmCode;
        this.dueAlarmDate = dueAlarmDate;
        this.dueAlarmCode = dueAlarmCode;
        this.startDate = startDate;
    }

    @Ignore
    public Assessment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(Date alarmDate) {
        this.alarmDate = alarmDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(int alarmCode) {
        this.alarmCode = alarmCode;
    }

    public Date getDueAlarmDate() {
        return dueAlarmDate;
    }

    public void setDueAlarmDate(Date dueAlarmDate) {
        this.dueAlarmDate = dueAlarmDate;
    }

    public int getDueAlarmCode() {
        return dueAlarmCode;
    }

    public void setDueAlarmCode(int dueAlarmCode) {
        this.dueAlarmCode = dueAlarmCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
