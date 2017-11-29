package com.example.sergiofundanga.k;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by sergiofundanga on 01.05.2016.
 */
public class ActivityShowLog extends AppCompatActivity {


    public TextView textViewLog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showlog);


        textViewLog=(TextView)findViewById(R.id.textViewLog);
        textViewLog.setText(readFromFile());

    }


    private String readFromFile() { //leser fra logg-filen
        String ret = "";
        try {
            InputStream inputStream = openFileInput("Log.txt");
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    while ((receiveString = bufferedReader.readLine()) != null) {
                        stringBuilder.append(receiveString+"\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "Filen er der ikke: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Kan ikke lese filen: " + e.toString());
        }
        return ret; //returnerer det som var i filen
    }
}
