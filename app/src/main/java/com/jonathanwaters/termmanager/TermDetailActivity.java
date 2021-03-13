package com.jonathanwaters.termmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_deleteterm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.deleteterm) {
            List<Course> courseList = db.courseDAO().getAll();
            int courseTermID;
            int courseCount = 0;

            for (Course course : courseList) {
                courseTermID = course.getTermID();

                if (courseTermID == termID) {
                    courseCount++;
                }
            }

            if (courseCount > 0) {
                Toast.makeText(getApplicationContext(), "A term cannot be deleted with courses associated", Toast.LENGTH_SHORT).show();
            } else {
                db.termDAO().delete(selectedTerm);
                Toast.makeText(getApplicationContext(), "The term was deleted", Toast.LENGTH_SHORT).show();
                populateList();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
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
