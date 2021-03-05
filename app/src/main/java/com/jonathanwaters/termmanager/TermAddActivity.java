package com.jonathanwaters.termmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TermAddActivity extends AppCompatActivity {

    TextView termNameText;
    EditText startDateText;
    EditText endDateText;
    Term newTerm;

    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_edit);

        db = Database.getInstance(this);
        newTerm = new Term();

        termNameText = (TextView) findViewById(R.id.termNameEditText);
        startDateText = (EditText) findViewById(R.id.termStartDateEditText);
        endDateText = (EditText) findViewById(R.id.termEndDateEditText);

        FloatingActionButton termEditFAB = (FloatingActionButton) findViewById(R.id.termEditFAB);
        termEditFAB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                System.out.println("The new term save FAB was clicked");

                newTerm.setName(termNameText.getText().toString());

                Date startDate;
                try {
                    startDate = new SimpleDateFormat("MM/dd/yyyy").parse(startDateText.getText().toString());
                    newTerm.setStartDate(startDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date endDate;
                try {
                    endDate = new SimpleDateFormat("MM/dd/yyyy").parse(endDateText.getText().toString());
                    newTerm.setEndDate(endDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                db.termDAO().insert(newTerm);
            }
        });
    }
}
