package com.jonathanwaters.termmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    TextView notesText;

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
        notesText = (TextView) findViewById(R.id.courseDetailNotesText);

        notesText.setMovementMethod(new ScrollingMovementMethod());
        db = Database.getInstance(this);
        courseID = getIntent().getExtras().getInt("courseID");

        final Button shareButton = (Button) findViewById(R.id.courseDetailShareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                String shareTitle = "My " + selectedCourse.getName() + " course notes.";
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareTitle);
                intent.putExtra(android.content.Intent.EXTRA_TEXT, selectedCourse.getNotes());
                startActivity(Intent.createChooser(intent, null));
            }
        });

        FloatingActionButton courseDetailFAB = (FloatingActionButton) findViewById(R.id.courseDetailFAB);
        courseDetailFAB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetailActivity.this, CourseEditActivity.class);
                intent.putExtra("courseID", courseID);
                CourseDetailActivity.this.startActivity(intent);
            }
        });

        populateFields();
        populateList();
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
        notesText.setText(selectedCourse.getNotes());
    }

    private void populateList() {
        allAssessments = db.assessmentDAO().getAll();
        List<String> assessments = new ArrayList<>();
        String assessmentName;
        int assessmentCourseID;

        for (Assessment assessment : allAssessments) {
            assessmentName = assessment.getName();
            assessmentCourseID = assessment.getCourseID();

            if (assessmentCourseID == courseID) {
                assessments.add(assessmentName);
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                assessments
        );

        assessmentList.setAdapter(arrayAdapter);

    }

    protected void onResume() {
        super.onResume();
        populateFields();
        populateList();
    }
}
