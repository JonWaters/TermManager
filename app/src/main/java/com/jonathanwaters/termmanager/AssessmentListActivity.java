package com.jonathanwaters.termmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AssessmentListActivity extends AppCompatActivity {

    ListView assessmentList;
    Database db;
    List<Assessment> allAssessments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);

        db = Database.getInstance(this);
        allAssessments = db.assessmentDAO().getAll();

        assessmentList = (ListView) findViewById(R.id.itemListView);

        assessmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(AssessmentListActivity.this, AssessmentDetailActivity.class);
                intent.putExtra("assessmentID", allAssessments.get(position).getId());
                AssessmentListActivity.this.startActivity(intent);
            }
        });

        FloatingActionButton itemListFAB = (FloatingActionButton) findViewById(R.id.itemListFAB);
        itemListFAB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AssessmentListActivity.this, AssessmentAddActivity.class);
                AssessmentListActivity.this.startActivity(intent);
            }
        });

        populateList();
    }

    private void populateList() {
        List<String> assessmentNames = new ArrayList<>();
        String assessmentName;

        for (Assessment assessment : allAssessments) {
            assessmentName = assessment.getName();
            assessmentNames.add(assessmentName);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                assessmentNames
        );

        assessmentList.setAdapter(arrayAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        allAssessments = db.assessmentDAO().getAll();

        populateList();
    }
}
