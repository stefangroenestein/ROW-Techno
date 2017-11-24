package com.github.niqdev.ipcam;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.ButterKnife;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    //@BindView(R.id.buttonDefault)
    //Button buttonDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //create spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.ruimte_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Switch switchUp = (Switch) findViewById(R.id.switchUp);

        switchUp.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    new Background_get().execute("hek1=1");
                    new Background_get().execute("hek2=1");
                    new Background_get().execute("hek3=1");
                    new Background_get().execute("hek4=1");
                    new Background_get().execute("hek5=1");
                    new Background_get().execute("hek6=1");
                    new Background_get().execute("hek7=1");
                    new Background_get().execute("hek8=1");
                } else {
                    new Background_get().execute("hek1=0");
                    new Background_get().execute("hek2=0");
                    new Background_get().execute("hek3=0");
                    new Background_get().execute("hek4=0");
                    new Background_get().execute("hek5=0");
                    new Background_get().execute("hek6=0");
                    new Background_get().execute("hek7=0");
                    new Background_get().execute("hek8=0");
                }
            }
        });
    }

    //if the default button is pressed the IpCamDefaultActivity class is now on the screen
//    @OnClick(R.id.buttonDefault)
//    public void onClickDefault() {
//        startActivity(new Intent(this, IpCamDefaultActivity.class));
//
//        Switch switchUp = (Switch) findViewById(R.id.switchUp);
//        if (switchUp.isChecked()) {
//            switchUp.setChecked(false);
//        }
//        Switch switch1 = (Switch) findViewById(R.id.switch1);
//        if (switch1.isChecked()) {
//            switch1.setChecked(false);
//        }
//        Switch switch2 = (Switch) findViewById(R.id.switch2);
//        if (switch2.isChecked()) {
//            switch2.setChecked(false);
//        }
//        new Background_get().execute("hek1=0");
//        new Background_get().execute("hek2=0");
//        new Background_get().execute("hek3=0");
//        new Background_get().execute("hek4=0");
//        new Background_get().execute("hek5=0");
//        new Background_get().execute("hek6=0");
//        new Background_get().execute("hek7=0");
//        new Background_get().execute("hek8=0");
//    }

//    @OnClick(R.id.buttonReset)
//    public void onClickReset() {
//        new Background_get().execute("hek1=0");
//        new Background_get().execute("hek2=0");
//        new Background_get().execute("hek3=0");
//        new Background_get().execute("hek4=0");
//        new Background_get().execute("hek5=0");
//        new Background_get().execute("hek6=0");
//        new Background_get().execute("hek7=0");
//        new Background_get().execute("hek8=0");
//        new Background_get().execute("reset=1");
//    }

    int hek1 = 0;
    int hek2 = 0;
    int hek3 = 0;
    int hek4 = 0;
    int hek5 = 0;
    int hek6 = 0;
    int hek7 = 0;
    int hek8 = 0;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //create switch for hek 1
        Switch switch1 = (Switch) findViewById(R.id.switch1);

        //create switch for hek 2
        Switch switch2 = (Switch) findViewById(R.id.switch2);

        //print selected spinner item
        Toast.makeText(parent.getContext(), "Geselecteerd: " +
                parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();

        if (position == 0) {
        /* For a switch you'll need a "OnCheckedChangeListener" */
            //checks on witch state the switch is
            switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
            /* Switch is hek 1 */
                        new Background_get().execute("hek1=1"); //if checked http://192.168.42.1/hek1=1 is executed on the pi
                        hek1 = 1;
                    } else {
                        new Background_get().execute("hek1=0"); //if checked http://192.168.42.1/hek1=0 is executed on the pi
                        hek1 = 0;
                    }
                }
            });
            switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
            /* Switch is hek 2 */
                        new Background_get().execute("hek2=1");
                        hek2 = 1;
                    } else {
                        new Background_get().execute("hek2=0");
                        hek2 = 0;
                    }
                }
            });
            if (hek1 == 1) {
                switch1.setChecked(true);
            }
            if (hek1 == 0) {
                switch1.setChecked(false);
            }
            if (hek2 == 1) {
                switch2.setChecked(true);
            }
            if (hek2 == 0) {
                switch2.setChecked(false);
            }
        }
        if (position == 1) {
        /* For a switch you'll need a "OnCheckedChangeListener" */
            //checks on witch state the switch is
            switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        new Background_get().execute("hek3=1");
                        hek3 = 1;
                    } else {
                        new Background_get().execute("hek3=0");
                        hek3 = 0;
                    }
                }
            });
            switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        new Background_get().execute("hek4=1");
                        hek4 = 1;
                    } else {
                        new Background_get().execute("hek4=0");
                        hek4 = 0;
                    }
                }
            });
            if (hek3 == 1) {
                switch1.setChecked(true);
            }
            if (hek3 == 0) {
                switch1.setChecked(false);
            }
            if (hek4 == 1) {
                switch2.setChecked(true);
            }
            if (hek4 == 0) {
                switch2.setChecked(false);
            }
        }
        if (position == 2) {
        /* For a switch you'll need a "OnCheckedChangeListener" */
            //checks on witch state the switch is
            switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        new Background_get().execute("hek5=1");
                        hek5 = 1;
                    } else {
                        new Background_get().execute("hek5=0");
                        hek5 = 0;
                    }
                }
            });
            switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        new Background_get().execute("hek6=1");
                        hek6 = 1;
                    } else {
                        new Background_get().execute("hek6=0");
                        hek6 = 0;
                    }
                }
            });
            if (hek5 == 1) {
                switch1.setChecked(true);
            }
            if (hek5 == 0) {
                switch1.setChecked(false);
            }
            if (hek6 == 1) {
                switch2.setChecked(true);
            }
            if (hek6 == 0) {
                switch2.setChecked(false);
            }
        }
        if (position == 3) {
        /* For a switch you'll need a "OnCheckedChangeListener" */
            //checks on witch state the switch is
            switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        new Background_get().execute("hek7=1");
                        hek7 = 1;
                    } else {
                        new Background_get().execute("hek7=0");
                        hek7 = 0;
                    }
                }
            });
            switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        new Background_get().execute("hek8=1");
                        hek8 = 1;
                    } else {
                        new Background_get().execute("hek8=0");
                        hek8 = 0;  
                    }
                }
            });
            if (hek7 == 1) {
                switch1.setChecked(true);
            }
            if (hek7 == 0) {
                switch1.setChecked(false);
            }
            if (hek8 == 1) {
                switch2.setChecked(true);
            }
            if (hek8 == 0) {
                switch2.setChecked(false);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class Background_get extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://192.168.22.65/?" + params[0]);     //gets the ip from the raspberry pi and connect
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                //create a buffer for if there are to many packets
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder(/*in.readLine()*/);
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    result.append(inputLine).append("\n");
                //closes the connection
                in.close();
                connection.disconnect();
                Log.d("CREATION", result.toString());
                return result.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
