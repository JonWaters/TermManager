package com.jonathanwaters.termmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TermListActivity extends AppCompatActivity {

    ListView termList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);

        termList = (ListView) findViewById(R.id.itemListView);

        termList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(TermListActivity.this, TermDetailActivity.class);
                TermListActivity.this.startActivity(intent);
            }
        });

        populateList();
    }

    private void populateList() {
        Database db = Database.getInstance(this);
        List<Term> allTerms = db.termDAO().getAll();
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
