package com.example.winrob.spaceinvaders3;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class Activity_ayuda extends AppCompatActivity {
    private long backPressedTime = 0;
    VideoView v;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
        v = (VideoView) findViewById(R.id.video);
        String videopath = "android.resource://com.example.winrob.spaceinvaders3/" + R.raw.ayuda;
        uri = Uri.parse(videopath);
        v.setVideoURI(uri);
        v.start();
        //Espera a la finalización del video
        v.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp){   //Entra cuando finaliza el video.
                      Intent retorno = new Intent(Activity_ayuda.this, Activity_Menu_Principal.class);
                      startActivity(retorno);
                      finish();
                  }
          });
    }

    //Botón BACK, juego>menu
    @Override
    public void onBackPressed() {
            Intent jugar2 = new Intent(Activity_ayuda.this, Activity_Menu_Principal.class);
            startActivity(jugar2);
            finish(); //Para parar la ejecución del activity!
    }
}

