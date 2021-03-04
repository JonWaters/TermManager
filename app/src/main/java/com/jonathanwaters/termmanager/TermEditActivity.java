package com.jonathanwaters.termmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TermEditActivity extends AppCompatActivity {

    TextView termNameText;
    EditText startDateText;
    EditText endDateText;
    ListView courseList;
    List<Course> allCourses;
    int termID;
    Term selectedTerm;

    Database db = Database.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_edit);

        termNameText = (TextView) findViewById(R.id.termNameEditText);
        startDateText = (EditText) findViewById(R.id.termStartDateEditText);
        endDateText = (EditText) findViewById(R.id.termEndDateEditText);
        courseList = (ListView) findViewById(R.id.termEditListView);
        allCourses = db.courseDAO().getAll();
        termID = getIntent().getExtras().getInt("termID");
        selectedTerm = db.termDAO().getByID(termID);

        courseList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                CheckedTextView v = (CheckedTextView) view;
//                boolean currentCheck = v.isChecked();
//                UserAccount user = (UserAccount) listView.getItemAtPosition(position);
//                user.setActive(!currentCheck);
            }
        });

        populateList();
    }

    private void populateList() {
        List<String> courses = new ArrayList<>();
        String courseName;
        int courseTermID;

        for (Course course : allCourses) {
            courseName = course.getName();
            courseTermID = course.getTermID();

            if (courseTermID == termID || courseTermID == 0) {
                courses.add(courseName);
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                courses
        );

        courseList.setAdapter(arrayAdapter);

    }
}
