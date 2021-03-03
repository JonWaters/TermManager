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

public class TermListActivity extends AppCompatActivity {

    ListView termList;
    Database db = Database.getInstance(this);
    List<Term> allTerms = db.termDAO().getAll();
    //List<String> termNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);

        termList = (ListView) findViewById(R.id.itemListView);

        termList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(TermListActivity.this, TermDetailActivity.class);
                intent.putExtra("termID", allTerms.get(position).getId());
                TermListActivity.this.startActivity(intent);
            }
        });

        FloatingActionButton itemListFAB = (FloatingActionButton) findViewById(R.id.itemListFAB);
        itemListFAB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(TermListActivity.this, TermAddActivity.class);
                TermListActivity.this.startActivity(intent);
            }
        });

        populateList();
    }

    private void populateList() {
        List<String> termNames = new ArrayList<>();
        String termName;

        for (Term term : allTerms) {
            termName = term.getName();
            termNames.add(termName);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                termNames
        );

        termList.setAdapter(arrayAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateList();
    }
}
