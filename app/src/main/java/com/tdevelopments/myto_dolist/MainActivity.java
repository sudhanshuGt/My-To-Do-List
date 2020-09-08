package com.tdevelopments.myto_dolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<>();
    static  ArrayAdapter arrayAdapter;
    SharedPreferences sharedPreferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         sharedPreferences = getApplicationContext().getSharedPreferences("com.tdevelopments.myto_dolist", Context.MODE_PRIVATE);
          HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

          if ( set == null) {

                notes.add("example note");

          }  else {
                  notes = new ArrayList<>(set);
          }
        Button buttonNotes = findViewById(R.id.buttonAddNote);

        buttonNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
                startActivity(intent);
            }
        });

        ListView listView = findViewById(R.id.todo_list);
        
         arrayAdapter  = new ArrayAdapter(this, android.R.layout.select_dialog_item, notes);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
                intent.putExtra("noteId", position);
                startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure ?")
                        .setMessage("Do you want to delete thi note")
                         .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                    notes.remove(position);
                                    arrayAdapter.notifyDataSetChanged();


                                 HashSet<String> set = new HashSet<>(MainActivity.notes);
                                 sharedPreferences.edit().putStringSet("notes", set).apply();

                             }
                         })

                        .setNegativeButton("No", null)
                        .show(); 




                return true;
            }
        });
    } 
}