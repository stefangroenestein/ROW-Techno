package com.github.niqdev.ipcam;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.github.niqdev.mjpeg.DisplayMode;
import com.github.niqdev.mjpeg.Mjpeg;
import com.github.niqdev.mjpeg.MjpegView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IpCamDefaultActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private static final int TIMEOUT = 5;

    @BindView(R.id.mjpegViewDefault)
    MjpegView mjpegView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipcam_default);    //sets layout from activity_ipcam_default.xml
        ButterKnife.bind(this);

        //create spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.ruimte_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
    }

    private String getPreference(String key) {
        return PreferenceManager
                .getDefaultSharedPreferences(this)
                .getString(key, "");
    }

    //sets display from camera
    private DisplayMode calculateDisplayMode() {
        int orientation = getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_LANDSCAPE ?
                DisplayMode.FULLSCREEN : DisplayMode.BEST_FIT;
    }

    //load Mjpeg stream
    private void loadIpCam() {
        Mjpeg.newInstance()
                //gets ip from stream and timeout if not found or if stream is not on
                .open("http://192.168.22.65:8080/?action=stream", TIMEOUT)
                .subscribe(
                        //sets settings for stream
                        inputStream -> {
                            mjpegView.setSource(inputStream);
                            mjpegView.setDisplayMode(calculateDisplayMode());
                            mjpegView.showFps(true);
                        },
                        throwable -> {
                            Log.e(getClass().getSimpleName(), "mjpeg error", throwable);
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadIpCam();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mjpegView.stopPlayback();
    }

    int hek1 = 0;
    int hek2 = 0;
    int hek3 = 0;
    int hek4 = 0;
    int hek5 = 0;
    int hek6 = 0;
    int hek7 = 0;
    int hek8 = 0;

    //if spinner on selected item this method is executed
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        //create switch for hek 1
        Switch controller1 = (Switch) findViewById(R.id.control1);

        //create switch for hek 2
        Switch controller2 = (Switch) findViewById(R.id.control2);

        //print selected spinner item
        Toast.makeText(parent.getContext(), "Geselecteerd: " +
                parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();

        //if spinner iteam is the first one in the array, this part is executed
        if (position == 0) {
        /* For a switch you'll need a "OnCheckedChangeListener" */
            //checks on witch state the switch is
            controller1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
            controller2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                controller1.setChecked(true);
            }
            if (hek1 == 0) {
                controller1.setChecked(false);
            }
            if (hek2 == 1) {
                controller2.setChecked(true);
            }
            if (hek2 == 0) {
                controller2.setChecked(false);
            }
        }
        if (position == 1) {
        /* For a switch you'll need a "OnCheckedChangeListener" */
            controller1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
            controller2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                controller1.setChecked(true);
            }
            if (hek3 == 0) {
                controller1.setChecked(false);
            }
            if (hek4 == 1) {
                controller2.setChecked(true);
            }
            if (hek4 == 0) {
                controller2.setChecked(false);
            }
        }
        if (position == 2) {
        /* For a switch you'll need a "OnCheckedChangeListener" */
            controller1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
            controller2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                controller1.setChecked(true);
            }
            if (hek5 == 0) {
                controller1.setChecked(false);
            }
            if (hek6 == 1) {
                controller2.setChecked(true);
            }
            if (hek6 == 0) {
                controller2.setChecked(false);
            }
        }
        if (position == 3) {
        /* For a switch you'll need a "OnCheckedChangeListener" */
            controller1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
            controller2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                controller1.setChecked(true);
            }
            if (hek7 == 0) {
                controller1.setChecked(false);
            }
            if (hek8 == 1) {
                controller2.setChecked(true);
            }
            if (hek8 == 0) {
                controller2.setChecked(false);
            }
        }
    }

    //if no spinner state is selected this is executed
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //this is to connect to the php server on the raspberry pi en execute the php script
    private class Background_get extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://192.168.22.65/?" + params[0]);     //gets the ip from the raspberry pi and connect
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                //create a buffer for if there are to many packets
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    result.append(inputLine).append("\n");
                
                //closes the connection
                in.close();
                connection.disconnect();
                return result.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

