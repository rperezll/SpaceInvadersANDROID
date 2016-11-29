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
import android.net.Uri;
import android.os.Handler;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import org.w3c.dom.Text;
import java.io.IOException;
import java.util.Random;


public class Activity_ventana_juego extends AppCompatActivity implements View.OnTouchListener{
    private ImageView nave,enemigo,enemigo2;
    private Disparo d;
    private MovimientoEnemigo d2;
    private MovimientoEnemigo2 d4;
    private long backPressedTime = 0;
    private MediaPlayer reproductor2;
    RelativeLayout layoutJuego;
    //Switch pausa;
    //private Disparodelenemigo d3;
    private View balaE;
    private TextView puntuacionNumero;
    private int puntuacion;
    boolean activado=true;
    TextView mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_juego);
        //Siempre orientación vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        reproductor2= MediaPlayer.create (this,R.raw.musicagame);
        reproductor2.setLooping(true);
        reproductor2.start();

        //pausa =(Switch)findViewById(R.id.switchPausa);
        puntuacionNumero=(TextView)findViewById(R.id.puntuacion);
        nave=(ImageView)findViewById(R.id.naveico);
        nave.setOnTouchListener(this);
        layoutJuego=(RelativeLayout) findViewById(R.id.activity_ventana_juego);

        mensaje = (TextView) findViewById(R.id.mensajeNivel);
        mensaje.setX(800);
        mensaje.setY(800);


        d= new Disparo();
        d2 = new MovimientoEnemigo();
        d4 = new MovimientoEnemigo2();
        d.start();
        d2.start();
        d4.start();
/*
        pausa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    System.out.print("Pausame plis");
                }else{

                }
            }
        });*/
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
            //d3.parar3=false;
            Intent jugar2 = new Intent(Activity_ventana_juego.this, Activity_Menu_Principal.class);
            startActivity(jugar2);
            finish();
        }
    }

    class MovimientoEnemigo2 extends Thread{
        int vel=1;
        boolean derecha=true,izquierda=false,parar4=true, pausa4=true;
        @Override
        public void run() {
            enemigo2=(ImageView)findViewById(R.id.enemigo2);
            enemigo2.setX(25);
            while (parar4) {
                try {
                    if (pausa4) {
                        System.out.println("Entra");
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
        boolean derecha=true,izquierda=false,parar2=true, pausa2=true;
        @Override
        public void run() {
            enemigo=(ImageView)findViewById(R.id.enemigo);
            enemigo.setX(25);
            while (parar2) {
                try {
                    if (pausa2) {
                        System.out.println("Entra2");
                        if (derecha) {
                            if (enemigo.getX() >= layoutJuego.getWidth() - enemigo.getWidth() * 2) {
                                derecha = false;
                                izquierda = true;
                            } else {
                                enemigo.setX(enemigo.getX() + 10);
                            }
                        } else if (izquierda) {
                            if (enemigo.getX() <= enemigo.getWidth()) {
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
            d.pausa=false;
            d2.pausa2=false;
            d4.pausa4=false;
            activado=false;
        }else{
            d.pausa=true;
            d2.pausa2=true;
            d4.pausa4=true;
            activado=true;
        }
    }

    class Disparo extends Thread{
        boolean parar=true, pausa=true;
        int vel=1,findePantall=-40,i;
        ImageView bala;
        Random rnd;
        Chronometer ch;
        @Override
        public void run() {
            bala=(ImageView)findViewById(R.id.bala);
            Random  rnd = new Random();
            //Iniciamos el cronómetro de nivel
            ch=(Chronometer)findViewById(R.id.chronometer2);
            ch.start();
            try {
            //Escucha cambios en la variable puntuación; posterorimente se actualiza textView: puntuacion
            while (parar) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            puntuacionNumero.setText(Integer.toString(puntuacion));
                        }
                    });
                if(pausa) {
                    if (bala.getY() > findePantall) {
                        bala.setY(bala.getY() - 10);
                        //Contacto de la bala de nuestra nave con el enemigo
                        if (
                                (enemigo.getX() + enemigo.getWidth() > bala.getX())
                                        && (enemigo.getY() + enemigo.getHeight() > bala.getY())
                                        && (enemigo.getX() < bala.getX() + bala.getWidth())
                                        && (enemigo.getY() < bala.getY() + bala.getHeight())
                                ) {
                            //Aumentamos la puntuación cada vez que nuestra bala toca a la nave nodriza
                            puntuacion++;
                            //NIVEL 1:
                            if (puntuacion==5){
                                //d.pausa=false;
                                d2.pausa2=false;
                                d4.pausa4=false;
                                activado=false;
                                puntuacion=0;
                                //TextView mensaje = (TextView) findViewById(R.id.mensajeNivel);
                                mensaje.setX(50);
                                mensaje.setY(50);
                                sleep(2000);
                                d.parar=false;
                                Intent jugar2 = new Intent(Activity_ventana_juego.this, Activity_Menu_Principal.class);
                                startActivity(jugar2);
                                finish();

                            }
                            i = (int) (rnd.nextDouble() * 2 + 1);
                            if (i == 1) {
                                enemigo.setX(layoutJuego.getWidth() + 500);
                            } else {
                                enemigo.setX(0 - 500);
                            }
                        }
                        //Contacto de la bala de nuestra nave con el enemigo2
                        if (
                                (enemigo2.getX() + enemigo2.getWidth() > bala.getX())
                                        && (enemigo2.getY() + enemigo2.getHeight() > bala.getY())
                                        && (enemigo2.getX() < bala.getX() + bala.getWidth())
                                        && (enemigo2.getY() < bala.getY() + bala.getHeight())
                                ) {
                            bala.setY(nave.getY());
                            bala.setX(nave.getX());
                        }
                    } else {
                        bala.setX(nave.getX() + (nave.getWidth() / 2) - (bala.getWidth() / 2)); //MEJORAR!
                        bala.setY(nave.getY());
                    }
                }
                    Thread.sleep(vel * 3);
            }
            System.out.println("Interrupción de THREAD: Disparo");
            } catch (InterruptedException ex) {
                System.err.println("Error en THREAD Disparo: Thread. Error en el movimiento del disparo");
            }
        }

    }
/*
    class Disparodelenemigo extends Thread {
        boolean parar3 = true;
        int vel = 1;
        int desplazamiento = 10;
        int anchoPantalla = 0;
        int altoPantalla = 0;

        @Override
        public void run() {

            balaE.setX(120);
            balaE.setY(106);

            try {
                Thread.sleep(3000);
                while (parar3) {
                    anchoPantalla = layoutJuego.getWidth();
                    altoPantalla = layoutJuego.getHeight();
                    //   try {
                    if (balaE.getY() <= altoPantalla) { //MEJORAR
                        //System.out.println("llega");
                        balaE.setY(balaE.getY() + desplazamiento);
                    } else {
                        if ((enemigo.getX() > 0) && (enemigo.getX() < anchoPantalla)) {
                            //System.out.println("o por aqui" + enemigo.getX() + "  " + enemigo.getY());
                            //System.out.println("o por aqui");
                            //balaE.setX(enemigo.getX() + enemigo.getWidth() / 2);
                            balaE.setX(enemigo.getX()+10);
                            //balaE.setY(enemigo.getY() + enemigo.getHeight() / 2);
                            balaE.setY(enemigo.getY()+10);
                        } else {
                            //System.out.println("o tal vez aqui" + enemigo.getX() + "  " + enemigo.getY() + "  ->" + i);
                            balaE.setY(enemigo.getY()+10);

                                if (i % 2 == 0) {
                                    balaE.setX(layoutJuego.getWidth() - 1);
                                } else {
                                    balaE.setX(1);
                                    balaE.setY(enemigo.getY() + enemigo.getHeight() / 2);
                                }
                        }
                        //Thread.sleep(vel * 5);
                    }
                }
               // }


                //}

                //
                // } catch (InterruptedException ex) {
                // System.err.println("Error en Disparo: Thread. Error en el movimiento del disparo");
                     catch (IllegalArgumentException ev){
                        System.out.println("Fuera de Rango");
                    }catch(ArrayIndexOutOfBoundsException exception) {
                        System.out.println(exception);
                    }
                // }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/


}


