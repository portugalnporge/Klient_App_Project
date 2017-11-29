package com.example.sergiofundanga.k;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class MainActivity extends Activity  {

    TextView status;// alle meldinger fra klienter vises her.
    EditText txt;//skriktekst
     EditText ipaddress,brukerNavn,portNr;
    Socket socket;
    String melding;
    DataOutputStream dataOutputStream;// sendes ut
     BufferedReader bufferedReader; // for å kunne lese melding,sms

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = (TextView) findViewById(R.id.status);
        txt = (EditText) findViewById(R.id.txt);
        brukerNavn=(EditText)findViewById(R.id.brukerNavn);
        ipaddress = (EditText)findViewById(R.id.ipaddress);
       portNr= (EditText)findViewById(R.id.portNr);


        Toast.makeText(this, "Now, you can use the chat", Toast.LENGTH_LONG).show();


    }
    public void start(View v)throws IOException
    {
        new TilServer().execute();
        new FraServer().execute();
    }

//metode håndtere
    private class TilServer extends AsyncTask<Void, Void, Void>
    {

       private String ip = ipaddress.getText().toString();
        private String user=brukerNavn.getText().toString();
        private int port=Integer.parseInt(portNr.getText().toString());

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //Creates a stream socket and connects it to the specified port number on the named host.
                socket = new Socket(ip,port);
                //inngang til data for å lese sms
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));//
                //utgang til data for å sende sms
                dataOutputStream =new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF(user + " connected to server \n");//

            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }
    }

    public class FraServer extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void...params) {
//denne metoden skal lese alt som blir sendt til meg

                while (true) {
                    try {

                        //melding for lagre alle meldinger som jeg får fra Server
                            melding= bufferedReader.readLine();
                        skriveTilFile(melding + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    publishProgress();
                }
            }


        //@Override
        protected void onProgressUpdate(Void... values) {

            status.setText(status.getText()+"\n"+ melding);
        }
    }
//metode for å sende tekster
    public void send(View v)
    {Log.i("","send");
        //
        SendSMS(brukerNavn.getText()+" skriv:  " + this.txt.getText());

        //etter at du har skrevet, forsvinner txt fra skriveområdet
        txt.setText("");

    }

//metode deler ut meldinger til klienter
    public void SendSMS(String sms)
    {
        Log.i("Send-Button"," Meldingen har blitt sendt");
        try
        {
            this.dataOutputStream =new DataOutputStream(socket.getOutputStream());
            this.dataOutputStream.writeUTF(sms + "\n");//tenke litt
        }
        catch (Exception e)
        {
            Log.i("Send-Button"," Meldingen har ikke blitt sendt");
        }
    }


        public void skriveTilFile(String melding)
        {

            try {

                OutputStreamWriter outputStreamWriter=new OutputStreamWriter(openFileOutput("Log.txt", Context.MODE_APPEND));
                outputStreamWriter.write(melding);//brukerskriv=txt
                outputStreamWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    public void log(View v)
    {
        Intent intent=new Intent(this,ActivityShowLog.class);
        startActivity(intent);
        Toast.makeText(this, "Showlog!", Toast.LENGTH_SHORT).show();
    }

    public void delete(View v)
    {
        try {
            OutputStreamWriter outputW=new OutputStreamWriter(openFileOutput("Log.txt", Context.MODE_PRIVATE));

         String   inputTilFile=" ";
            outputW.write(inputTilFile);
            outputW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "deleted!", Toast.LENGTH_SHORT).show();
    }

}
