package com.example.mobilelab_42;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    final String LOG_TAG = "myLogs";

    private List<String> list;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHelper = new DataBaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        ListView listView = findViewById(R.id.listViewMain);

        ArrayList<String> theList = new ArrayList<>();
        ArrayList<Integer> ar_ids = new ArrayList<>();
        Cursor data = mDb.rawQuery("SELECT * FROM citizenship", null);

        while(data.moveToNext()){
            ar_ids.add(data.getInt(0));
            theList.add(data.getString(1) + " " + data.getString(2) + " " + data.getString(3));
            ListAdapter listAdapter = new ArrayAdapter<>(this,R.layout.custom_list_view,theList);
            listView.setAdapter(listAdapter);
        }

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                Intent intent = new Intent(MainActivity.this, CitizenshipByIdActivity.class); //это код из фрагмента
                intent.putExtra("pos", ar_ids.get(position));
                startActivity(intent);
            }
        });
    }
}