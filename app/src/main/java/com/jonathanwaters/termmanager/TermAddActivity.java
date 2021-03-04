package com.jonathanwaters.termmanager;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TermAddActivity extends AppCompatActivity {

    TextView termNameText;
    EditText startDateText;
    EditText endDateText;
    Term newTerm;

    Database db = Database.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_edit);

        termNameText = (TextView) findViewById(R.id.termNameEditText);
        startDateText = (EditText) findViewById(R.id.termStartDateEditText);
        endDateText = (EditText) findViewById(R.id.termEndDateEditText);

    }
}
