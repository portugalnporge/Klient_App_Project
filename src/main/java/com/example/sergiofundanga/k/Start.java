package com.example.sergiofundanga.k;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sergiofundanga on 21.05.2016.
 */
public class Start extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_layout);

        Toast.makeText(this, "Welcome", Toast.LENGTH_LONG).show();

    }

    public void start(View v)
    {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
