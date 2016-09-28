package com.example.winrob.spaceinvaders2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Juego extends AppCompatActivity {

    private ImageView nave;
    private Button der;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        nave=(ImageView)findViewById(R.id.nave);
        //der=(Button)findViewById(R.id.Derecha);
    }

    //Se ejecuta cuando pulso Der
    //Incremento el valor de la X de la ImageView nave
    public void desder(View view){
        nave.setX(nave.getX()+100);
        //R.drawable.nave.
    }

    //Se ejecuta cuando pulso Izq
    //Disminuyo el valor de la X de la ImageView nave
    public void desIzq(View view){
        nave.setX(nave.getX()-100);
        //R.drawable.nave.
    }

}
