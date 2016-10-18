package com.example.winrob.spaceinvaders3;


import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class Activity_ventana_juego extends AppCompatActivity {
    private ImageView nave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_juego);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  //Para que se quede vertical (Portrait)
        new Disparo().start();
        nave=(ImageView)findViewById(R.id.naveico);
    }

    public void desder(View view){
        if(nave.getX()<865) //Límite derecha MEJORAR!
            nave.setX(nave.getX()+100); //MEJORAR!
    }

    public void desizq (View view){
        if(nave.getX()>65)  //Límite izquierda MEJORAR!
            nave.setX(nave.getX()-100); //MEJORAR!

    }

    class Disparo extends Thread{
        boolean parar=true;
        int vel=1;
        ImageView bala;
        @Override
        public void run() {
            while (parar) {
                bala=(ImageView)findViewById(R.id.bala);
                try {
                    if (bala.getY()>-51) { //MEJORAR!
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


