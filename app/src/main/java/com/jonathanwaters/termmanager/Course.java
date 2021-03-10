package com.jonathanwaters.termmanager;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "course"
        ,foreignKeys = @ForeignKey(
                entity = Term.class,
                parentColumns = "id",
                childColumns = "term_id",
                onDelete = ForeignKey.CASCADE
        )
)
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "start_date")
    private Date startDate;
    @ColumnInfo(name = "end_date")
    private Date endDate;
    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name = "ci_name")
    private String instructorName;
    @ColumnInfo(name = "ci_phone")
    private String instructorPhone;
    @ColumnInfo(name = "ci_email")
    private String instructorEmail;
    @ColumnInfo(name = "start_alarm")
    private Date startAlarm;
    @ColumnInfo(name = "due_alarm")
    private Date dueAlarm;
    @ColumnInfo(name = "notes")
    private String notes;
    @ColumnInfo(name = "term_id")
    private int termID;
    @ColumnInfo(name = "start_alarm_code")
    private int startAlarmCode;
    @ColumnInfo(name = "due_alarm_code")
    private int dueAlarmCode;

    public Course(int id, String name, Date startDate, Date endDate, String status,
                  String instructorName, String instructorPhone, String instructorEmail,
                  Date startAlarm, Date dueAlarm, String notes, int termID,
                  int startAlarmCode, int dueAlarmCode) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.startAlarm = startAlarm;
        this.dueAlarm = dueAlarm;
        this.notes = notes;
        this.termID = termID;
        this.startAlarmCode = startAlarmCode;
        this.dueAlarmCode = dueAlarmCode;
    }

    @Ignore
    public Course(String name, Date startDate, Date endDate, String status, String instructorName,
                  String instructorPhone, String instructorEmail, Date startAlarm, Date dueAlarm,
                  String notes, int termID, int startAlarmCode, int dueAlarmCode) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.startAlarm = startAlarm;
        this.dueAlarm = dueAlarm;
        this.notes = notes;
        this.termID = termID;
        this.startAlarmCode = startAlarmCode;
        this.dueAlarmCode = dueAlarmCode;
    }

    @Ignore
    public Course() {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public Date getStartAlarm() {
        return startAlarm;
    }

    public void setStartAlarm(Date startAlarm) {
        this.startAlarm = startAlarm;
    }

    public Date getDueAlarm() {
        return dueAlarm;
    }

    public void setDueAlarm(Date dueAlarm) {
        this.dueAlarm = dueAlarm;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public int getStartAlarmCode() {
        return startAlarmCode;
    }

    public void setStartAlarmCode(int startAlarmCode) {
        this.startAlarmCode = startAlarmCode;
    }

    public int getDueAlarmCode() {
        return dueAlarmCode;
    }

    public void setDueAlarmCode(int dueAlarmCode) {
        this.dueAlarmCode = dueAlarmCode;
    }
}
