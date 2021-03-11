package com.jonathanwaters.termmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TermEditActivity extends AppCompatActivity {

    EditText termNameText;
    EditText startDateText;
    EditText endDateText;

    String termName;
    Date startDate;
    Date endDate;

    Database db;
    int termID;
    Term selectedTerm;

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.term_edit);

        termNameText = (EditText) findViewById(R.id.termNameEditText);
        startDateText = (EditText) findViewById(R.id.termStartDateEditText);
        endDateText = (EditText) findViewById(R.id.termEndDateEditText);

        termID = getIntent().getExtras().getInt("termID");
        db = Database.getInstance(this);
        selectedTerm = db.termDAO().getByID(termID);

        FloatingActionButton termEditFAB = (FloatingActionButton) findViewById(R.id.termEditFAB);
        termEditFAB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (isValidInput()) {
                    selectedTerm.setName(termName);
                    selectedTerm.setStartDate(startDate);
                    selectedTerm.setEndDate(endDate);
                    db.termDAO().update(selectedTerm);
                    Toast.makeText(getApplicationContext(), "The term was modified", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        populateFields();
    }

    private void populateFields() {
        termNameText.setText(selectedTerm.getName());
        startDateText.setText(dateFormat.format(selectedTerm.getStartDate()));
        endDateText.setText(dateFormat.format(selectedTerm.getEndDate()));
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
