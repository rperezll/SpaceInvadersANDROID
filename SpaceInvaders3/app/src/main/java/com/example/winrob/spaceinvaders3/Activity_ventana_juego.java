package com.example.winrob.spaceinvaders3;

//**************************************************************************************
//IMPORTANTE!!
//El error del tipo "getSlotFromBufferLocked" es un error típico de sistema marshmallow.
//Tiene que ver con el uso de la función intent();
//**************************************************************************************


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class Activity_ventana_juego extends AppCompatActivity implements View.OnTouchListener{
    private int aux,aux2;
    private ImageView nave, objeto;
    private Disparo d;
    private long backPressedTime = 0;
    private MediaPlayer reproductor2;
    RelativeLayout layoutJuego;
    //Comentario de prueba.



    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_juego);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  //Para que se quede vertical (Portrait)
        reproductor2= MediaPlayer.create (this,R.raw.musicagame);
        reproductor2.setLooping(true);
        reproductor2.start();
        d= new Disparo();
        d.start();
        //objeto=(ImageView)findViewById(R.id.objeto);
        nave=(ImageView)findViewById(R.id.naveico);
        nave.setOnTouchListener(this);
        layoutJuego=(RelativeLayout) findViewById(R.id.activity_ventana_juego);

    }

    //Al tocar la pantalla...
    public boolean onTouch(View view, MotionEvent event) {
        //Recogemos las coordenadas del dedo
        final int X = (int) event.getRawX();
        if(!(X>(layoutJuego.getWidth()-nave.getWidth()/2) || X<nave.getWidth()/2)){
            //Dependiendo de la accion recogida..
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                //Al tocar la pantalla
                case MotionEvent.ACTION_DOWN:
                    break;
                //case MotionEvent.ACTION_UP:
                    //Al levantar el dedo simplemento mostramos un mensaje
                    //Toast.makeText(this, "Soltamos", Toast.LENGTH_LONG).show();
                    //break;
                case MotionEvent.ACTION_MOVE:
                    nave.setX(X-(nave.getWidth()/2));
                    break;
            }
            return true;
        }
        return true;
    }

    //Botón BACK, juego>menu
    @Override
    public void onBackPressed() {
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 3000) {    // 2 secs
            backPressedTime = t;
            Toast.makeText(this, "Pulsa de nuevo para ir al menú",
                    Toast.LENGTH_LONG).show();
        } else {
            d.parar=false;
            Intent jugar2 = new Intent(Activity_ventana_juego.this, Activity_Menu_Principal.class);
            startActivity(jugar2);
            finish(); //Para parar la ejecución del activity!
        }
    }

/* Movimiento obsoleto
    public void desder(View view){
        if(nave.getX()<780) //Límite derecha MEJORAR!
           nave.setX(nave.getX()+90); //MEJORAR!
        //else nave.setX(View.FOCUS_LEFT);
    }

    public void desizq (View view){
        if(nave.getX()>118)  //Límite izquierda MEJORAR!
            nave.setX(nave.getX()-90); //MEJORAR!
        //else nave.setX(View.FOCUS_RIGHT);
    }
*/
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
        int desplazamiento=40;
        int findePantall=-40;
        @Override
        public void run() {

            //objeto=(ImageView)findViewById(R.id.objeto);
            bala=(ImageView)findViewById(R.id.bala);

            while (parar) {

                try {
                    if (bala.getY()>findePantall) { //MEJORAR
                        bala.setY(bala.getY() - desplazamiento);
                    }else {
                        bala.setX(nave.getX()+(nave.getWidth()/2)-(bala.getWidth()/2)); //MEJORAR!
                        bala.setY(nave.getY());
                    }

                    Thread.sleep(vel * 10);
                    bala=(ImageView)findViewById(R.id.bala);

                } catch (InterruptedException ex) {
                    System.err.println("Error en Disparo: Thread. Error en el movimiento del disparo");
                }
            }
            System.out.println("¡Se paró el hilo!");
        }
    }

}


