package com.example.winrob.spaceinvaders3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static java.lang.Thread.sleep;

public class pantalla_ganador extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_ganador);
    }
    void salir (View v){System.exit(0);}
    void menu (View v){
        Intent jugar3 = new Intent(pantalla_ganador.this, Activity_Menu_Principal.class);
        startActivity(jugar3);
        finish();
    }

}
