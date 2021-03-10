package com.jonathanwaters.termmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CourseAddActivity extends AppCompatActivity {

    EditText courseNameText;
    EditText startDateText;
    EditText endDateText;
    Spinner statusSpinner;
    EditText ciNameText;
    EditText ciPhoneText;
    EditText ciEmailText;
    EditText startAlarmText;
    EditText dueAlarmText;
    Spinner termSpinner;
    EditText notesText;

    String courseName;
    Date startDate;
    Date endDate;
    String status;
    String ciName;
    String ciPhone;
    String ciEmail;
    Date startAlarm;
    Date dueAlarm;
    String termName;

    Database db;
    List<Term> termList;
    Course newCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_edit);

        courseNameText = (EditText) findViewById(R.id.courseNameEditText);
        startDateText = (EditText) findViewById(R.id.courseStartDateEditText);
        endDateText = (EditText) findViewById(R.id.courseEndDateEditText);
        statusSpinner = (Spinner) findViewById(R.id.courseStatusSpinner);
        ciNameText = (EditText) findViewById(R.id.instructorNameEditText);
        ciPhoneText = (EditText) findViewById(R.id.instructorPhoneEditText);
        ciEmailText = (EditText) findViewById(R.id.instructorEmailEditText);
        startAlarmText = (EditText) findViewById(R.id.alarmStartDateEditText);
        dueAlarmText = (EditText) findViewById(R.id.alarmDueDateEditText);
        termSpinner = (Spinner) findViewById(R.id.courseTermSpinner);
        notesText = (EditText) findViewById(R.id.courseNotesEditText);

        db = Database.getInstance(this);

        newCourse = new Course();

        FloatingActionButton termEditFAB = (FloatingActionButton) findViewById(R.id.courseEditFAB);
        termEditFAB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (isValidInput()) {
                    newCourse.setName(courseName);
                    newCourse.setStartDate(startDate);
                    newCourse.setEndDate(endDate);
                    newCourse.setStatus(status);
                    newCourse.setInstructorName(ciName);
                    newCourse.setInstructorPhone(ciPhone);
                    newCourse.setInstructorEmail(ciEmail);
                    newCourse.setStartAlarm(startAlarm);
                    newCourse.setDueAlarm(dueAlarm);
                    newCourse.setTermID(getSelectedTermID());
                    newCourse.setNotes(notesText.getText().toString());
                    newCourse.setStartAlarmCode(AlarmID.getAlarmID());
                    newCourse.setDueAlarmCode(AlarmID.getAlarmID());
                    db.courseDAO().insert(newCourse);
                    setStartAlarm();
                    setDueAlarm();
                    Toast.makeText(getApplicationContext(), "The new course was added", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });

        populateStatusSpinner();
        populateTermSpinner();
    }

    private void populateStatusSpinner() {
        List<String> statusList =  new ArrayList<String>();
        statusList.add("In Progress");
        statusList.add("Completed");
        statusList.add("Dropped");
        statusList.add("Plan to Take");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, statusList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter);
    }

    private void populateTermSpinner() {
        termList = db.termDAO().getAll();
        List<String> termNameList =  new ArrayList<String>();
        String termName;

        for (Term term : termList) {
            termName = term.getName();
            termNameList.add(termName);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, termNameList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        termSpinner.setAdapter(adapter);
    }

    private boolean isValidInput() {
        boolean isValid = true;

        courseName = courseNameText.getText().toString();

        if (courseName.matches("")) {
            isValid = false;
            Toast.makeText(this, "The name cannot be blank", Toast.LENGTH_SHORT).show();
        }

        try {
            startDate = new SimpleDateFormat("MM/dd/yyyy").parse(startDateText.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
            isValid = false;
            Toast.makeText(this, "The start date is invalid", Toast.LENGTH_SHORT).show();
        }

        try {
            endDate = new SimpleDateFormat("MM/dd/yyyy").parse(endDateText.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
            isValid = false;
            Toast.makeText(this, "The end date is invalid", Toast.LENGTH_SHORT).show();
        }

        status = statusSpinner.getSelectedItem().toString();

        if (status.matches("")) {
            isValid = false;
            Toast.makeText(this, "A status must be selected", Toast.LENGTH_SHORT).show();
        }

        ciName = ciNameText.getText().toString();

        if (ciName.matches("")) {
            isValid = false;
            Toast.makeText(this, "The instructor name cannot be blank", Toast.LENGTH_SHORT).show();
        }

        ciPhone = ciPhoneText.getText().toString();

        if (ciPhone.matches("")) {
            isValid = false;
            Toast.makeText(this, "The instructor phone cannot be blank", Toast.LENGTH_SHORT).show();
        }

        ciEmail = ciEmailText.getText().toString();

        if (ciEmail.matches("")) {
            isValid = false;
            Toast.makeText(this, "The instructor email cannot be blank", Toast.LENGTH_SHORT).show();
        }

        try {
            startAlarm = new SimpleDateFormat("MM/dd/yyyy").parse(startAlarmText.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
            isValid = false;
            Toast.makeText(this, "The start alarm date is invalid", Toast.LENGTH_SHORT).show();
        }

        try {
            dueAlarm = new SimpleDateFormat("MM/dd/yyyy").parse(dueAlarmText.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
            isValid = false;
            Toast.makeText(this, "The due alarm date is invalid", Toast.LENGTH_SHORT).show();
        }

        termName = termSpinner.getSelectedItem().toString();

        if (termName.matches("")) {
            isValid = false;
            Toast.makeText(this, "A term must be selected", Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }

    private int getSelectedTermID() {
        int termID;
        int spinnerPosition;
        Term selectedTerm = new Term();

        spinnerPosition = termSpinner.getSelectedItemPosition();
        selectedTerm = termList.get(spinnerPosition);
        termID = selectedTerm.getId();

        return termID;
    }

    private void setStartAlarm() {

        Calendar alarmStartCal = Calendar.getInstance();
        alarmStartCal.setTime(startAlarm);

        Intent intent = new Intent(CourseAddActivity.this, AlarmReceiver.class);
        intent.putExtra("mesg", "The " + courseName + " course is starting today");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(CourseAddActivity.this, newCourse.getStartAlarmCode(), intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartCal.getTimeInMillis(), pendingIntent);
    }

    private void setDueAlarm() {

        Calendar alarmStartCal = Calendar.getInstance();
        alarmStartCal.setTime(dueAlarm);

        Intent intent = new Intent(CourseAddActivity.this, AlarmReceiver.class);
        intent.putExtra("mesg", "The " + courseName + " course is due today");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(CourseAddActivity.this, newCourse.getDueAlarmCode(), intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartCal.getTimeInMillis(), pendingIntent);
    }
}
