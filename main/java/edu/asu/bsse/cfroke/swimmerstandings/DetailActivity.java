package edu.asu.bsse.cfroke.swimmerstandings;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * Copyright 2017 Casey Froke
 *
 * Rights granted to any instructor for SER423 and Arizona State University
 * to build and evaluate the software package for the purpose of
 * determining grade and program assessment.
 *
 * Purpose - This android application creates a Swimmer Standings database.
 * Users can press the + newSwimmer2 button to add another swimmers
 * information to the table. The swimmers information is then displayed
 * in a list item on the MainActivity. If no database or table are present
 * on the device one is created, with 3 default entries.
 *
 * @author  Casey Froke    mailto: cfroke@asu.edu
 *
 * @version April 26, 2017
 */


public class DetailActivity extends AppCompatActivity {
    private SQLiteDatabase db;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        View backgroundImage = findViewById(R.id.waterbg);
        Drawable background = backgroundImage.getBackground();
        background.setAlpha(80);
        final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        db= openOrCreateDatabase("StandingsDB", Context.MODE_PRIVATE, null);
        Button addButton = (Button) findViewById(R.id.buttonAdd);
        Button deleteButton = (Button) findViewById(R.id.buttonDelete);
        final EditText nameET = (EditText) findViewById(R.id.editName);
        final EditText teamET = (EditText) findViewById(R.id.editTeam);
        final EditText eventET = (EditText) findViewById(R.id.editEvent);
        final EditText timeET = (EditText) findViewById(R.id.editTime);

        Intent p = getIntent();
        final String name = p.getStringExtra("name");
        final String team = p.getStringExtra("team");
        final String event = p.getStringExtra("event");
        final String time = p.getStringExtra("time");

        if (name != null) {
            nameET.setText(name);
            teamET.setText(team);
            eventET.setText(event);
            timeET.setText(time);
            addButton.setText("Update");
            deleteButton.setVisibility(View.VISIBLE);

            addButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Cursor cur = db.rawQuery("SELECT id from standings where name = " + "\"" + name + "\""
                            + "and team =" + "\"" + team + "\"" + "and event =" + "\"" + event + "\"", null);
                    cur.moveToFirst();
                    if (cur.getCount() == 0) {
                        dlgAlert.setMessage("Record does not exist.");
                        dlgAlert.setTitle("Error");
                        dlgAlert.setPositiveButton("OK", null);
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();
                        dlgAlert.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                    } else {
                        String name = nameET.getText().toString();
                        String team = teamET.getText().toString();
                        String event = eventET.getText().toString();
                        String time = timeET.getText().toString();
                        if (name.isEmpty()) {
                            nameET.setError("Please enter a swimmer name.");
                        } else if (team.isEmpty()) {
                            teamET.setError("Please enter a team name.");
                        }else if (event.isEmpty()) {
                            eventET.setError("Please enter an event name.");
                        }else if (time.isEmpty()) {
                            timeET.setError("Please enter an event completion time.");
                        } else {
                            ContentValues values = new ContentValues();
                            values.put("name", name);
                            values.put("team", team);
                            values.put("event", event);
                            values.put("time", time);
                            int id = cur.getInt(cur.getColumnIndex("id"));
                            String finalId = Integer.toString(id);
                            cur.close();
                            if (finalId != null) {
                                db.update("standings", values, "id=?", new String[]{finalId});
                            }
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        }
                    }
                }
            });

        } else {

            addButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    String name = nameET.getText().toString();
                    String team = teamET.getText().toString();
                    String event = eventET.getText().toString();
                    String time = timeET.getText().toString();
                    if (name.isEmpty()) {
                        nameET.setError("Please enter a swimmer name.");
                    } else if (team.isEmpty()) {
                        teamET.setError("Please enter a team name.");
                    }else if (event.isEmpty()) {
                        eventET.setError("Please enter an event name.");
                    }else if (time.isEmpty()) {
                        timeET.setError("Please enter an event completion time.");
                    } else {
                        ContentValues values = new ContentValues();
                        values.put("name", name);
                        values.put("team", team);
                        values.put("event", event);
                        values.put("time", time);
                        db.insert("standings", null, values);
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }

                }
            });

        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = nameET.getText().toString();
                String team = teamET.getText().toString();
                String event = eventET.getText().toString();
                Cursor cur = db.rawQuery("SELECT id from standings where name = " + "\"" + name + "\""
                        + "and team =" + "\"" + team + "\"" + "and event =" + "\"" + event + "\"", null);
                cur.moveToFirst();
                if (cur.getCount() == 0) {
                    dlgAlert.setMessage("Record does not exist.");
                    dlgAlert.setTitle("Error");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                    dlgAlert.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });

                } else {
                    int id = cur.getInt(cur.getColumnIndex("id"));
                    String finalId = Integer.toString(id);
                    cur.close();
                    if (finalId != null) {
                        db.delete("standings", "id=?", new String[]{finalId});
                    }
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);

                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

    }
}