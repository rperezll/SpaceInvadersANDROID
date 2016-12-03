package com.example.winrob.spaceinvaders3;

//**************************************************************************************
//IMPORTANTE!!
//El error del tipo "getSlotFromBufferLocked" es un error típico de sistema marshmallow.
//Tiene que ver con el uso de la función intent();
//**************************************************************************************

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;


public class Activity_ventana_juego extends AppCompatActivity implements View.OnTouchListener{
    private ImageView nave;
    private Disparo d;
    private MovimientoEnemigo d2;
    private MovimientoEnemigo2 d4;
    private long backPressedTime = 0;
    private MediaPlayer reproductor2;
    RelativeLayout layoutJuego;
    private Disparodelenemigo d3;
    private TextView puntuacionNumero;
    private int puntuacion;
    boolean activado=true;
    Chronometer ch;
    long timeWhenStopped;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_juego);
        //Siempre orientación vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        reproductor2= MediaPlayer.create (this,R.raw.musicagame);
        reproductor2.setLooping(true);
        reproductor2.start();

        puntuacionNumero=(TextView)findViewById(R.id.puntuacion);
        nave=(ImageView)findViewById(R.id.naveico);
        nave.setOnTouchListener(this);
        layoutJuego=(RelativeLayout) findViewById(R.id.activity_ventana_juego);

        d= new Disparo();
        d2 = new MovimientoEnemigo();
        d3 = new Disparodelenemigo();
        d4 = new MovimientoEnemigo2();
        d.start();
        d2.start();
        d3.start();
        d4.start();
    }

    //Al tocar la pantalla...
    public boolean onTouch(View view, MotionEvent event) {
        //Recogemos las coordenadas de la pulsación
        final int X = (int) event.getRawX();
        if(!(X>(layoutJuego.getWidth()-nave.getWidth()/2) || X<nave.getWidth()/2)){
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    nave.setX(X-(nave.getWidth()/2));
                    break;
            }
            return true;
        }
        return true;
    }

    //Botón BACK, juego->menu
    @Override
    public void onBackPressed() {
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 3000) {    // 3 segundos
            backPressedTime = t;
            Toast.makeText(this, "Pulsa de nuevo para ir al menú",
                    Toast.LENGTH_LONG).show();
        } else {
            d.parar=false;
            d2.parar2=false;
            d4.parar4=false;
            d3.parar3=false;
            reproductor2.stop();
            Intent jugar2 = new Intent(Activity_ventana_juego.this, Activity_Menu_Principal.class);
            overridePendingTransition(R.anim.left_in,R.anim.left_out);
            startActivity(jugar2);
            finish();
        }
    }

    class MovimientoEnemigo2 extends Thread{
        int vel=1;
        ImageView enemigo2;
        boolean derecha=true,izquierda=false,parar4=true, pausa4=true;
        @Override
        public void run() {
            enemigo2=(ImageView)findViewById(R.id.enemigo2);
            enemigo2.setX(25);
            while (parar4) {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (pausa4) {
                                //System.out.println("Entra");
                                if (derecha) {
                                    if (enemigo2.getX() >= layoutJuego.getWidth() - enemigo2.getWidth() * 2) {
                                        derecha = false;
                                        izquierda = true;
                                    } else {
                                        enemigo2.setX(enemigo2.getX() + 10);
                                    }
                                } else if (izquierda) {
                                    if (enemigo2.getX() <= enemigo2.getWidth()) {
                                        derecha = true;
                                        izquierda = false;
                                    } else {
                                        enemigo2.setX(enemigo2.getX() - 10);
                                    }
                                }
                            }
                        }
                    });
                    //cuanto menor sea el número, mas velocidad en el mov. del enemigo2
                    Thread.sleep(vel * 15);
                } catch (InterruptedException ex) {
                    System.err.println("Error en THREAD: MovimientoEnemigo.Error en el movimiento del Enemigo2");
                }
            }
            System.out.println("Interrupción de THREAD: MovimientoEnemigo2");
        }
    }

    class MovimientoEnemigo extends Thread{
        int vel=1;
        ImageView enemigo;
        boolean derecha=true,izquierda=false,parar2=true, pausa2=true;
        @Override
        public void run() {
            enemigo=(ImageView)findViewById(R.id.enemigo);
            enemigo.setX(25);
            while (parar2) {
                try {
                    if (pausa2) {
                        if (derecha) {
                            if (enemigo.getX() >= layoutJuego.getWidth() - enemigo.getWidth()*1.5) {
                                derecha = false;
                                izquierda = true;
                            } else {
                                enemigo.setX(enemigo.getX() + 10);
                            }
                        } else if (izquierda) {
                            if (enemigo.getX() <= enemigo.getWidth()*0.5) {
                                derecha = true;
                                izquierda = false;
                            } else {
                                enemigo.setX(enemigo.getX() - 10);
                            }
                        }
                    }
                    //cuanto menor sea el número, mayor velocidad en el movimiento
                    Thread.sleep(vel * 16);
                } catch (InterruptedException ex) {
                    System.err.println("Error en MovimientoEnemigo: Error en movimiento del Enemigo");
                }
            }
            System.out.println("Interrupción de THREAD: MovimientoEnemigo");
        }
    }

    public void pulsarPausa(View v){
        if (activado){
            timeWhenStopped = ch.getBase() - SystemClock.elapsedRealtime();
            ch.stop();
            d.pausa=false;
            d2.pausa2=false;
            d3.pausa3=false;
            d4.pausa4=false;
            activado=false;
            reproductor2.pause();
        }else{
            ch.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
            ch.start();
            d.pausa=true;
            d2.pausa2=true;
            d3.pausa3=true;
            d4.pausa4=true;
            activado=true;
            reproductor2.start();
        }
    }

    class Disparo extends Thread{
        boolean parar=true, pausa=true;
        int vel=1,findePantall=-40,i;
        ImageView bala;
        Random rnd;
        //Chronometer ch;
        @Override
        public void run() {
            bala=(ImageView)findViewById(R.id.bala);
            //Iniciamos el cronómetro de nivel
            ch=(Chronometer)findViewById(R.id.chronometer2);
            ch.start();
            //Escucha cambios en la variable puntuación; posterorimente se actualiza textView: puntuacion
            while (parar) {
                try {
                    sleep(3);
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            rnd = new Random();
                            puntuacionNumero.setText(Integer.toString(puntuacion));
                            if (pausa)
                            {
                                if (bala.getY() > findePantall) {
                                    bala.setY(bala.getY() - 10);
                                    //Contacto de la bala de nuestra nave con el enemigo
                                    try {
                                         // System.out.println("Esto es una coordenada y: " + d2.enemigo.getX());
                                        if (
                                                (d2.enemigo.getX() + d2.enemigo.getWidth() > d.bala.getX())
                                                        && (d2.enemigo.getY() + d2.enemigo.getHeight() > d.bala.getY())
                                                        && (d2.enemigo.getX() < d.bala.getX() + d.bala.getWidth())
                                                        && (d2.enemigo.getY() < d.bala.getY() + d.bala.getHeight())
                                                ) {
                                            //Aumentamos la puntuación cada vez que nuestra bala toca a la nave nodriza
                                            puntuacion++;
                                            //NIVEL 1:
                                            if (puntuacion == 10) {
                                                ch.stop();
                                                d.pausa = false;
                                                d2.pausa2=false;
                                                d3.parar3=false;
                                                d4.pausa4=false;
                                                activado = false;
                                                d.parar = false;
                                                sleep(0);
                                                reproductor2.stop();
                                                Intent jugar3 = new Intent(Activity_ventana_juego.this, pantalla_ganador.class);
                                                startActivity(jugar3);
                                                finish();
                                            }
                                            i = (int) (rnd.nextDouble() * 2 + 1);
                                            if (i == 1) {
                                                d2.enemigo.setX(layoutJuego.getWidth() + 500);
                                            } else {
                                                d2.enemigo.setX(0 - 500);
                                            }
                                        }
                                        //Contacto de la bala de nuestra nave con el enemigo2
                                        if (
                                                (d4.enemigo2.getX() + d4.enemigo2.getWidth() > d.bala.getX())
                                                        && (d4.enemigo2.getY() + d4.enemigo2.getHeight() > d.bala.getY())
                                                        && (d4.enemigo2.getX() < d.bala.getX() + d.bala.getWidth())
                                                        && (d4.enemigo2.getY() < d.bala.getY() + d.bala.getHeight())
                                                )
                                        {
                                            bala.setY(nave.getY());
                                            bala.setX(nave.getX());
                                        }
                                    }
                                    catch (NullPointerException err)
                                    {
                                        System.err.println("Da error y es:" + err);
                                    }catch (java.lang.InterruptedException err){

                                    }
                                }
                                else {
                                    bala.setX(nave.getX() + (nave.getWidth() / 2) - (bala.getWidth() / 2)); //MEJORAR!
                                    bala.setY(nave.getY());
                                }
                            }
                        }
                    });
                }
                catch (InterruptedException ex){
                    System.err.println("Error en THREAD Disparo: Thread. Error en el movimiento del disparo");
                }
            }
            System.out.println("Interrupción de THREAD: Disparo");
        }
    }

    class Disparodelenemigo extends Thread {
        boolean parar3 = true, pausa3=true, prueba=true;
        int vel = 1;
        int desplazamiento = 10;
        int anchoPantalla = 0;
        int i, altoPantalla;
        ImageView balaE;

        @Override
        public void run() {
            balaE=(ImageView)findViewById(R.id.balaEnemigo);
            altoPantalla = getWindowManager().getDefaultDisplay().getHeight();
            while (prueba) {
                try {
                    balaE.setX(d2.enemigo.getX());
                    balaE.setY(d2.enemigo.getY());
                    sleep(1500);
                    while (parar3) {
                        try {
                            sleep(10);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (pausa3) {
                                        if (balaE.getY() <= altoPantalla) {
                                            balaE.setY(balaE.getY() + 10);
                                        } else {
                                            balaE.setX(d2.enemigo.getX());
                                            balaE.setY(d2.enemigo.getY());
                                        }
                                        if( (nave.getX() + nave.getWidth() > balaE.getX())
                                                && (nave.getY() + nave.getHeight() > balaE.getY())
                                                && (nave.getX() < balaE.getX() + balaE.getWidth())
                                                && (nave.getY() < balaE.getY() + balaE.getHeight()) )
                                        {
                                            d.parar=false;
                                            d2.parar2=false;
                                            d4.parar4=false;
                                            d3.parar3=false;
                                            Intent jugar4 = new Intent(Activity_ventana_juego.this, pantalla_ganador.class);
                                            startActivity(jugar4);
                                            finish();
                                        }
                                    }
                                }
                            });

                        } catch (InterruptedException ex) {
                        } catch (java.lang.NullPointerException ex3) {}
                    }
                prueba=false;
                }catch(java.lang.NullPointerException ex2){
                }catch (InterruptedException ex) {}
            }
        }
    }
}