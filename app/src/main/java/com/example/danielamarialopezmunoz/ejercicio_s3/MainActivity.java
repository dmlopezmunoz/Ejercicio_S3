package com.example.danielamarialopezmunoz.ejercicio_s3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    private TextView texto;
    private final String IPGRUPO =  "226.24.30.8";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = (TextView) findViewById(R.id.textResult);

        texto.setText("Resultados IP\n");
        Comunicacion.getInstance().addObserver(this);

    }

public void onResume(){
    super.onResume();
    Comunicacion.getInstance().enviar("corre",IPGRUPO,2227);

}

    public void onPause(){
        super.onPause();
        Comunicacion.getInstance().enviar("pare",IPGRUPO,2227);

    }


 public void update(Observable o, Object arg){
     if(arg instanceof String){
         String message = (String) arg;
         llegada(message);
     }
 }


    public void llegada(final String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                texto.setText(texto.getText()+"  "+ message+"\n");
            }
        });
    }

}
