package com.jonathanwaters.termmanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TermDetailActivity extends AppCompatActivity {

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    TextView termName;
    TextView startDate;
    TextView endDate;

    Term selectedTerm = new Term();

    Database db = Database.getInstance(this);

    int termID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_detail);

        termName = (TextView) findViewById(R.id.termNameText);
        startDate = (TextView) findViewById(R.id.startDateText);
        endDate = (TextView) findViewById(R.id.endDateText);

        termID = getIntent().getExtras().getInt("termID");
        selectedTerm = db.termDAO().getByID(termID);

        termName.setText(selectedTerm.getName());
        startDate.setText(dateFormat.format(selectedTerm.getStartDate()));
        endDate.setText(dateFormat.format(selectedTerm.getEndDate()));
    }
}
