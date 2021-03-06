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

public class CourseListActivity extends AppCompatActivity {

    ListView courseList;
    Database db;
    List<Course> allCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);

        db = Database.getInstance(this);
        allCourses = db.courseDAO().getAll();

        courseList = (ListView) findViewById(R.id.itemListView);

        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(CourseListActivity.this, CourseDetailActivity.class);
                intent.putExtra("courseID", allCourses.get(position).getId());
                CourseListActivity.this.startActivity(intent);
            }
        });

        FloatingActionButton itemListFAB = (FloatingActionButton) findViewById(R.id.itemListFAB);
        itemListFAB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CourseListActivity.this, CourseAddActivity.class);
                CourseListActivity.this.startActivity(intent);
            }
        });

        populateList();
    }

    private void populateList() {
        List<String> courseNames = new ArrayList<>();
        String courseName;

        for (Course course : allCourses) {
            courseName = course.getName();
            courseNames.add(courseName);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                courseNames
        );

        courseList.setAdapter(arrayAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        allCourses = db.courseDAO().getAll();

        populateList();
    }
}
