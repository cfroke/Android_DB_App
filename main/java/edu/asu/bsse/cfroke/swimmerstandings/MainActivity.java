package edu.asu.bsse.cfroke.swimmerstandings;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View backgroundImage = findViewById(R.id.waterbg2);
        Drawable background = backgroundImage.getBackground();
        background.setAlpha(80);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SQLiteDatabase db = openOrCreateDatabase("StandingsDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS standings" +
                "(id integer primary key, " +
                "name TEXT NOT NULL," +
                "team TEXT NOT NULL," +
                "event BLOB NOT NULL, " +
                "time BLOB NOT NULL);"
        );

        ImageView addNew = (ImageView) findViewById(R.id.newSwimmer2);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                startActivity(i);
            }
        });

        List<Map<String,String>> standingsMapList = new ArrayList<>();
        Cursor res =  db.rawQuery( "select * from standings", null );
        res.moveToFirst();
        // Initial data for the table if db is not available
        if (res.getCount() == 0) {
            db.execSQL("INSERT INTO standings VALUES(\"0\",\"Casey Froke\",\"ASU\",\"50 Breast\",\"00:00:34\");");
            db.execSQL("INSERT INTO standings VALUES(\"1\",\"Michael Phelps\",\"USA\",\"200 Fly\",\"00:01:53\");");
            db.execSQL("INSERT INTO standings VALUES(\"2\",\"Katie Ledecky\",\"USA\",\"800 Free\",\"00:08:04\");");
            res =  db.rawQuery( "select * from standings", null ); // gets all data from the table
            res.moveToFirst();
        }

        while(!res.isAfterLast()){
            Map<String, String> standingsData = new HashMap<>(2);
            standingsData.put("name", res.getString(1));
            standingsData.put("team", res.getString(2));
            standingsData.put("event", res.getString(3));
            standingsData.put("time", res.getString(4));
            standingsMapList.add(standingsData);
            res.moveToNext();
        }
        SimpleAdapter adapter = new SimpleAdapter(this, standingsMapList,
                R.layout.list_layout, new String[] {"name","team","event","time"},
                new int[] {R.id.name, R.id.team, R.id.event, R.id.time});
        ListView list = (ListView) findViewById(R.id.standingsList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                TextView selectedName = (TextView) v.findViewById(R.id.name);
                TextView selectedTeam = (TextView) v.findViewById(R.id.team);
                TextView selectedEvent = (TextView) v.findViewById(R.id.event);
                TextView selectedStandings = (TextView) v.findViewById(R.id.time);
                String name = selectedName.getText().toString();
                String team = selectedTeam.getText().toString();
                String event = selectedEvent.getText().toString();
                String time = selectedStandings.getText().toString();
                i.putExtra("name", name);
                i.putExtra("team", team);
                i.putExtra("event", event);
                i.putExtra("time", time);
                startActivity(i);
            }
        });
        res.close();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }


}
