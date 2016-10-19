package com.example.winrob.spaceinvaders3;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class Activity_Menu_Principal extends AppCompatActivity {
    Button jugar, salir;
    private MediaPlayer reproductor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__menu__principal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        jugar = (Button) findViewById(R.id.button_Jugar);
        salir =  (Button) findViewById(R.id.button_Salir);
        reproductor= MediaPlayer.create (this,R.raw.musicafondo);
        reproductor.setLooping(true);
        reproductor.start();
    }

    public void presjugar(View v) { //Arrancar el activity del juego
        Intent jugar2 = new Intent(Activity_Menu_Principal.this, Activity_ventana_juego.class);
        startActivity(jugar2);
        finish();
    }

    public void pressalir(View v){  //Salir de la aplicación
        System.exit(0);
    }

    @Override
    protected void onDestroy (){    // Para que pare la musica cuando salimos del activity
        super.onDestroy();
        if (reproductor.isPlaying())
        {
            reproductor.stop();
            reproductor.release();
        }
    }



}


