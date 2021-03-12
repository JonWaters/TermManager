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

public class AssessmentAddActivity extends AppCompatActivity {

    EditText assessmentNameText;
    EditText typeText;
    EditText titleText;
    EditText dueDateText;
    EditText infoText;
    EditText alarmDateText;
    Spinner statusSpinner;
    Spinner courseSpinner;

    String assessmentName;
    String type;
    String title;
    Date dueDate;
    String info;
    Date alarmDate;
    String status;

    Database db;
    List<Course> courseList;
    Assessment newAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_edit);

        assessmentNameText = (EditText) findViewById(R.id.assessmentNameEditText);
        typeText = (EditText) findViewById(R.id.typeEditText);
        titleText = (EditText) findViewById(R.id.assessmentTitleEditText);
        dueDateText = (EditText) findViewById(R.id.assessmentDueDateEditText);
        infoText = (EditText) findViewById(R.id.assessmentInfoEditText);
        alarmDateText = (EditText) findViewById(R.id.assessmentAlarmDateEditText);
        statusSpinner = (Spinner) findViewById(R.id.assessmentStatusSpinner);
        courseSpinner = (Spinner) findViewById(R.id.assessmentCourseSpinner);

        db = Database.getInstance(this);

        newAssessment = new Assessment();

        FloatingActionButton assessmentEditFAB = (FloatingActionButton) findViewById(R.id.assessmentEditFAB);
        assessmentEditFAB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (isValidInput()) {
                    newAssessment.setName(assessmentName);
                    newAssessment.setType(type);
                    newAssessment.setTitle(title);
                    newAssessment.setDueDate(dueDate);
                    newAssessment.setInfo(info);
                    newAssessment.setAlarmDate(alarmDate);
                    newAssessment.setStatus(status);
                    newAssessment.setCourseID(getSelectedCourseID());
                    newAssessment.setAlarmCode(AlarmID.getAlarmID(getApplicationContext()));
                    db.assessmentDAO().insert(newAssessment);
                    setAlarm();
                    Toast.makeText(getApplicationContext(), "The new assessment was added", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });

        populateStatusSpinner();
        populateCourseSpinner();
    }

    private void populateStatusSpinner() {
        List<String> statusList =  new ArrayList<String>();
        statusList.add("Pending");
        statusList.add("Passed");
        statusList.add("Failed");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, statusList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter);
    }

    private void populateCourseSpinner() {
        courseList = db.courseDAO().getAll();
        List<String> courseNameList =  new ArrayList<String>();
        String courseName;

        for (Course course : courseList) {
            courseName = course.getName();
            courseNameList.add(courseName);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, courseNameList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(adapter);
    }

    private boolean isValidInput() {
        boolean isValid = true;

        assessmentName = assessmentNameText.getText().toString();

        if (assessmentName.matches("")) {
            isValid = false;
            Toast.makeText(this, "The name cannot be blank", Toast.LENGTH_SHORT).show();
        }

        type = typeText.getText().toString();

        if (type.matches("")) {
            isValid = false;
            Toast.makeText(this, "The type cannot be blank", Toast.LENGTH_SHORT).show();
        }

        title = titleText.getText().toString();

        if (title.matches("")) {
            isValid = false;
            Toast.makeText(this, "The title cannot be blank", Toast.LENGTH_SHORT).show();
        }

        info = infoText.getText().toString();

        if (info.matches("")) {
            isValid = false;
            Toast.makeText(this, "The info cannot be blank", Toast.LENGTH_SHORT).show();
        }

        try {
            dueDate = new SimpleDateFormat("MM/dd/yyyy").parse(dueDateText.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
            isValid = false;
            Toast.makeText(this, "The due date is invalid", Toast.LENGTH_SHORT).show();
        }

        try {
            alarmDate = new SimpleDateFormat("MM/dd/yyyy").parse(alarmDateText.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
            isValid = false;
            Toast.makeText(this, "The alarm date is invalid", Toast.LENGTH_SHORT).show();
        }

        status = statusSpinner.getSelectedItem().toString();

        if (status.matches("")) {
            isValid = false;
            Toast.makeText(this, "A status must be selected", Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }

    private int getSelectedCourseID() {
        int courseID;
        int spinnerPosition;
        Course selectedCourse = new Course();

        spinnerPosition = courseSpinner.getSelectedItemPosition();
        selectedCourse = courseList.get(spinnerPosition);
        courseID = selectedCourse.getId();

        return courseID;
    }

    private void setAlarm() {

        Calendar alarmCal = Calendar.getInstance();
        alarmCal.setTime(alarmDate);

        Intent intent = new Intent(AssessmentAddActivity.this, AlarmReceiver.class);
        intent.putExtra("mesg", "The " + assessmentName + " assessment is starting today");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AssessmentAddActivity.this, newAssessment.getAlarmCode(), intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmCal.getTimeInMillis(), pendingIntent);
    }
}
