package com.jonathanwaters.termmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AssessmentDetailActivity extends AppCompatActivity {

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    TextView assessmentName;
    TextView typeText;
    TextView titleText;
    TextView dueDateText;
    TextView infoText;
    TextView alarmDateText;
    TextView statusText;

    int assessmentID;
    Database db;
    Assessment selectedAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_detail);

        assessmentName = (TextView) findViewById(R.id.assessmentNameText);
        typeText = (TextView) findViewById(R.id.typeText);
        titleText = (TextView) findViewById(R.id.titleText);
        dueDateText = (TextView) findViewById(R.id.dueDateText);
        infoText = (TextView) findViewById(R.id.infoText);
        alarmDateText = (TextView) findViewById(R.id.alarmDateText);
        statusText = (TextView) findViewById(R.id.assessmentStatusText);

        db = Database.getInstance(this);
        assessmentID = getIntent().getExtras().getInt("assessmentID");
        selectedAssessment = db.assessmentDAO().getByID(assessmentID);

        FloatingActionButton assessmentDetailFAB = (FloatingActionButton) findViewById(R.id.assessmentDetailFAB);
        assessmentDetailFAB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AssessmentDetailActivity.this, AssessmentEditActivity.class);
                intent.putExtra("assessmentID", assessmentID);
                AssessmentDetailActivity.this.startActivity(intent);
            }
        });

        populateFields();
    }

    private void populateFields() {
        assessmentName.setText(selectedAssessment.getName());
        typeText.setText(selectedAssessment.getType());
        titleText.setText(selectedAssessment.getTitle());
        dueDateText.setText(dateFormat.format(selectedAssessment.getDueDate()));
        infoText.setText(selectedAssessment.getInfo());
        alarmDateText.setText(dateFormat.format(selectedAssessment.getAlarmDate()));
        statusText.setText(selectedAssessment.getStatus());
    }

    @Override
    protected void onResume() {
        super.onResume();

        selectedAssessment = db.assessmentDAO().getByID(assessmentID);

        populateFields();
    }
}
