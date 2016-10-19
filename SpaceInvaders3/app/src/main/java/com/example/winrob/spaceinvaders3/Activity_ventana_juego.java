package com.example.winrob.spaceinvaders3;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class Activity_ventana_juego extends AppCompatActivity {
    private ImageView nave;
    private Disparo d;
    private long backPressedTime = 0;
    private MediaPlayer reproductor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_juego);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  //Para que se quede vertical (Portrait)
        reproductor2= MediaPlayer.create (this,R.raw.musicagame);
        reproductor2.setLooping(true);
        reproductor2.start();
        d= new Disparo();
        d.start();
        nave=(ImageView)findViewById(R.id.naveico);

    }

    //Boton BACK, juego-menu
    @Override
    public void onBackPressed() {
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 3000) {    // 2 secs
            backPressedTime = t;
            Toast.makeText(this, "Doble Tap para SALIR",
                    Toast.LENGTH_SHORT).show();
        } else {
            d.parar=false;
            Intent jugar2 = new Intent(Activity_ventana_juego.this, Activity_Menu_Principal.class);
            startActivity(jugar2);
            finish();
        }
    }

    public void desder(View view){
        if(nave.getX()<790) //Límite derecha MEJORAR!
           nave.setX(nave.getX()+90); //MEJORAR!
        //else nave.setX(View.FOCUS_LEFT);
    }

    public void desizq (View view){
        if(nave.getX()>118)  //Límite izquierda MEJORAR!
            nave.setX(nave.getX()-90); //MEJORAR!
        //else nave.setX(View.FOCUS_RIGHT);
    }

    @Override
    protected void onDestroy (){    // Para que pare la musica cuando salimos de la app
       super.onDestroy();
        if (reproductor2.isPlaying())
        {
        } reproductor2.stop();
            reproductor2.release();
        }

    //UTILES PARA EL SONIDO
   // @Override
   // protected void onPause (){
   //     super.onPause();
   //     reproductor.pause();
   // }

    //   Volvemos a la app
    //@Override
    //  protected void onResume (){
    //       super.onResume();
    //      reproductor.start();
    //  }

    class Disparo extends Thread{
        boolean parar=true;
        int vel=1;
        ImageView bala;
        @Override
        public void run() {
            while (parar) {
                bala=(ImageView)findViewById(R.id.bala);
                //bala.setX(bala.getX()+10); //Con esto conseguimos cambiar el ángulo del disparo

                try {
                    if (bala.getY()>-51) { //MEJORAR
                        bala.setY(bala.getY() - 40);
                    }else {
                        bala.setX(nave.getX()); //MEJORAR!
                        bala.setY(nave.getY());
                    }
                    Thread.sleep(vel * 10);
                } catch (InterruptedException ex) {
                    System.err.println("Error en Disparo: Thread");
                }
            }
            System.out.println("¡Se paró el hilo!");
        }
    }

}


