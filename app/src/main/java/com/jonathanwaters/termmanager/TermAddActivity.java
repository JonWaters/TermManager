package com.jonathanwaters.termmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TermAddActivity extends AppCompatActivity {

    TextView termNameText;
    EditText startDateText;
    EditText endDateText;

    String termName;
    Date startDate;
    Date endDate;
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

                if (isValidInput()) {
                    newTerm.setName(termName);
                    newTerm.setStartDate(startDate);
                    newTerm.setEndDate(endDate);
                    db.termDAO().insert(newTerm);
                    Toast.makeText(getApplicationContext(), "The new term was added", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private boolean isValidInput() {
        boolean isValid = true;

        termName = termNameText.getText().toString();

        if (termName.matches("")) {
            isValid = false;
            Toast.makeText(this, "The name cannot be blank", Toast.LENGTH_SHORT).show();
        }

        try {
            startDate = new SimpleDateFormat("MM/dd/yyyy").parse(startDateText.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
            isValid = false;
            Toast.makeText(this, "The start date is invalid", Toast.LENGTH_SHORT).show();
        }

        try {
            endDate = new SimpleDateFormat("MM/dd/yyyy").parse(endDateText.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
            isValid = false;
            Toast.makeText(this, "The end date is invalid", Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }
}
