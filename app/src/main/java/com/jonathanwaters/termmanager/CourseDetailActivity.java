package com.jonathanwaters.termmanager;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class CourseDetailActivity extends AppCompatActivity {

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    ListView assessmentList;
    TextView courseName;
    TextView startDate;
    TextView endDate;
    TextView status;
    TextView ciName;
    TextView ciPhone;
    TextView ciEmail;
    TextView startAlarm;
    TextView dueAlarm;
    Course selectedCourse;
    int courseID;
    List<Assessment> allAssessments;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail);

        assessmentList = (ListView) findViewById(R.id.courseDetailListView);
        courseName = (TextView) findViewById(R.id.courseNameText);
        startDate = (TextView) findViewById(R.id.startDateText);
        endDate = (TextView) findViewById(R.id.endDateText);
        status = (TextView) findViewById(R.id.statusText);
        ciName = (TextView) findViewById(R.id.instructorNameText);
        ciPhone = (TextView) findViewById(R.id.instructorPhoneText);
        ciEmail = (TextView) findViewById(R.id.instructorEmailText);
        startAlarm = (TextView) findViewById(R.id.startDateAlarmText);
        dueAlarm = (TextView) findViewById(R.id.dueDateAlarmText);

        db = Database.getInstance(this);
        courseID = getIntent().getExtras().getInt("courseID");

        populateFields();
    }

    private void populateFields() {
        selectedCourse = db.courseDAO().getByID(courseID);

        courseName.setText(selectedCourse.getName());
        startDate.setText(dateFormat.format(selectedCourse.getStartDate()));
        endDate.setText(dateFormat.format(selectedCourse.getEndDate()));
        status.setText(selectedCourse.getStatus());
        ciName.setText(selectedCourse.getInstructorName());
        ciPhone.setText(selectedCourse.getInstructorPhone());
        ciEmail.setText(selectedCourse.getInstructorEmail());
        startAlarm.setText(dateFormat.format(selectedCourse.getStartAlarm()));
        dueAlarm.setText(dateFormat.format(selectedCourse.getDueAlarm()));
    }
}
