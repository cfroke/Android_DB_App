package edu.asu.bsse.cfroke.swimmerstandings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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

public class LandingActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Button enterDB = (Button) findViewById(R.id.enterDB);

        enterDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }


}
