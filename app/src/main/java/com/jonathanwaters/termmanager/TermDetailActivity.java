package com.jonathanwaters.termmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TermDetailActivity extends AppCompatActivity {

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    ListView courseList;
    TextView termName;
    TextView startDate;
    TextView endDate;

    Term selectedTerm = new Term();

    Database db = Database.getInstance(this);

    int termID;

    List<Course> allCourses = db.courseDAO().getAll();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_detail);

        courseList = (ListView) findViewById(R.id.termDetailListView);
        termName = (TextView) findViewById(R.id.termNameText);
        startDate = (TextView) findViewById(R.id.startDateText);
        endDate = (TextView) findViewById(R.id.endDateText);

        termID = getIntent().getExtras().getInt("termID");
        selectedTerm = db.termDAO().getByID(termID);

        FloatingActionButton termDetailFAB = (FloatingActionButton) findViewById(R.id.termDetailFAB);
        termDetailFAB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(TermDetailActivity.this, TermEditActivity.class);
                intent.putExtra("termID", termID);
                TermDetailActivity.this.startActivity(intent);
            }
        });

        termName.setText(selectedTerm.getName());
        startDate.setText(dateFormat.format(selectedTerm.getStartDate()));
        endDate.setText(dateFormat.format(selectedTerm.getEndDate()));

        populateList();
    }

    private void populateList() {
        List<String> courses = new ArrayList<>();
        String courseName;
        int courseTermID;

        for (Course course : allCourses) {
            courseName = course.getName();
            courseTermID = course.getTermID();

            if (courseTermID == termID) {
                courses.add(courseName);
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                courses
        );

        courseList.setAdapter(arrayAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        selectedTerm = db.termDAO().getByID(termID);

        populateList();

        termName.setText(selectedTerm.getName());
        startDate.setText(dateFormat.format(selectedTerm.getStartDate()));
        endDate.setText(dateFormat.format(selectedTerm.getEndDate()));
    }
}
