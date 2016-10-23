package com.example.winrob.spaceinvaders3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Activity_ayuda extends AppCompatActivity {
    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
    }

    //Botón BACK, juego>menu
    @Override
    public void onBackPressed() {
            Intent jugar2 = new Intent(Activity_ayuda.this, Activity_Menu_Principal.class);
            startActivity(jugar2);
            finish(); //Para parar la ejecución del activity!
    }
}

