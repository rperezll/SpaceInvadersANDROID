package com.example.winrob.spaceinvaders3;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class VentanaJuego extends AppCompatActivity {
    private ImageView nave;
    private ImageView bala;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_juego);
        new Disparo().start();
        nave=(ImageView)findViewById(R.id.naveico);
    }

    public void desder(View view){
        nave.setX(nave.getX()+100);
    }
    public void desizq (View view){
        nave.setX(nave.getX()-100);
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
                    Thread.sleep(vel * 10);
                    if (bala.getY()>-51)
                        bala.setY(bala.getY()-40);
                    else {
                        bala.setX(nave.getX());
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


