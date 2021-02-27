package com.jonathanwaters.termmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static Context context;

    TextView inProgressText;
    TextView completedText;
    TextView droppedText;
    TextView planToTakeText;
    TextView pendingText;
    TextView passedText;
    TextView failedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        inProgressText = (TextView) findViewById(R.id.inProgressText);
        completedText = (TextView) findViewById(R.id.completedText);
        droppedText = (TextView) findViewById(R.id.droppedText);
        planToTakeText = (TextView) findViewById(R.id.planToTakeText);
        pendingText = (TextView) findViewById(R.id.pendingText);
        passedText = (TextView) findViewById(R.id.passedText);
        failedText = (TextView) findViewById(R.id.failedText);

        final Button enterButton = (Button) findViewById(R.id.enterButton);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        final Button dbButton = (Button) findViewById(R.id.populateDbButton);
        dbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopulateDB.populate(context);
                populateCounts();
            }
        });

        populateCounts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateCounts();
    }

    private void populateCounts() {
        int inProgressCount = 0;
        int completedCount = 0;
        int droppedCount = 0;
        int planToTakeCount = 0;
        int pendingCount = 0;
        int passedCount = 0;
        int failedCount = 0;

        Database db = Database.getInstance(context);

        List<Course> courses = db.courseDAO().getAll();
        String courseStatus;

        for (Course course : courses) {
            courseStatus = course.getStatus();

            switch (courseStatus) {
                case "In Progress":
                    inProgressCount++;
                    break;
                case "Completed":
                    completedCount++;
                    break;
                case "Dropped":
                    droppedCount++;
                    break;
                case "Plan to Take":
                    planToTakeCount++;
                    break;
            }
        }

        List<Assessment> assessments = db.assessmentDAO().getAll();
        String assessmentStatus;

        for (Assessment assessment : assessments) {
            assessmentStatus = assessment.getStatus();

            switch (assessmentStatus) {
                case "Pending":
                    pendingCount++;
                    break;
                case "Passed":
                    passedCount++;
                    break;
                case "Failed":
                    failedCount++;
                    break;
            }
        }

        inProgressText.setText(String.valueOf(inProgressCount));
        completedText.setText(String.valueOf(completedCount));
        droppedText.setText(String.valueOf(droppedCount));
        planToTakeText.setText(String.valueOf(planToTakeCount));

        pendingText.setText(String.valueOf(pendingCount));
        passedText.setText(String.valueOf(passedCount));
        failedText.setText(String.valueOf(failedCount));
    }
}