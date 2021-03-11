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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CourseEditActivity extends AppCompatActivity {

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
    int courseID;
    Course selectedCourse;

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

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

        courseID = getIntent().getExtras().getInt("courseID");
        db = Database.getInstance(this);
        selectedCourse = db.courseDAO().getByID(courseID);

        FloatingActionButton termEditFAB = (FloatingActionButton) findViewById(R.id.courseEditFAB);
        termEditFAB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (isValidInput()) {
                    selectedCourse.setName(courseName);
                    selectedCourse.setStartDate(startDate);
                    selectedCourse.setEndDate(endDate);
                    selectedCourse.setStatus(status);
                    selectedCourse.setInstructorName(ciName);
                    selectedCourse.setInstructorPhone(ciPhone);
                    selectedCourse.setInstructorEmail(ciEmail);
                    selectedCourse.setStartAlarm(startAlarm);
                    selectedCourse.setDueAlarm(dueAlarm);
                    selectedCourse.setTermID(getSelectedTermID());
                    selectedCourse.setNotes(notesText.getText().toString());
                    db.courseDAO().update(selectedCourse);
                    setStartAlarm();
                    setDueAlarm();
                    Toast.makeText(getApplicationContext(), "The course was modified", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });

        populateFields();
        populateStatusSpinner();
        populateTermSpinner();
    }

    private void populateFields() {
        courseNameText.setText(selectedCourse.getName());
        startDateText.setText(dateFormat.format(selectedCourse.getStartDate()));
        endDateText.setText(dateFormat.format(selectedCourse.getEndDate()));
        ciNameText.setText(selectedCourse.getInstructorName());
        ciPhoneText.setText(selectedCourse.getInstructorPhone());
        ciEmailText.setText(selectedCourse.getInstructorEmail());
        startAlarmText.setText(dateFormat.format(selectedCourse.getStartAlarm()));
        dueAlarmText.setText(dateFormat.format(selectedCourse.getDueAlarm()));
        notesText.setText(selectedCourse.getNotes());
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

        int spinnerPosition = adapter.getPosition(selectedCourse.getStatus());
        statusSpinner.setSelection(spinnerPosition);
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

        Term selectedTerm = db.termDAO().getByID(selectedCourse.getTermID());
        int spinnerPosition = adapter.getPosition(selectedTerm.getName());
        statusSpinner.setSelection(spinnerPosition);
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

        Intent intent = new Intent(CourseEditActivity.this, AlarmReceiver.class);
        intent.putExtra("mesg", "The " + courseName + " course is starting today");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(CourseEditActivity.this, selectedCourse.getStartAlarmCode(), intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartCal.getTimeInMillis(), pendingIntent);
    }

    private void setDueAlarm() {

        Calendar alarmStartCal = Calendar.getInstance();
        alarmStartCal.setTime(dueAlarm);

        Intent intent = new Intent(CourseEditActivity.this, AlarmReceiver.class);
        intent.putExtra("mesg", "The " + courseName + " course is due today");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(CourseEditActivity.this, selectedCourse.getDueAlarmCode(), intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartCal.getTimeInMillis(), pendingIntent);
    }
}
