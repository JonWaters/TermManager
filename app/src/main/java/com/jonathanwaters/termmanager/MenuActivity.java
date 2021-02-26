package com.jonathanwaters.termmanager;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        final Button termsButton = (Button) findViewById(R.id.termsButton);
        termsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, TermListActivity.class);
                MenuActivity.this.startActivity(intent);
            }
        });

        final Button coursesButton = (Button) findViewById(R.id.coursesButton);
        coursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CourseListActivity.class);
                MenuActivity.this.startActivity(intent);
            }
        });

        final Button assessmentsButton = (Button) findViewById(R.id.assessmentsButton);
        assessmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, AssessmentListActivity.class);
                MenuActivity.this.startActivity(intent);
            }
        });
    }
}
