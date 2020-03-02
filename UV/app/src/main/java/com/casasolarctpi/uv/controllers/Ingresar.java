package com.casasolarctpi.uv.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.casasolarctpi.uv.R;

public class Ingresar extends AppCompatActivity implements View.OnClickListener {

    Button btnIngresarLo, btnRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ingresar);
        getSupportActionBar().hide();

        inicializar();
    }

    private void inicializar() {

        btnIngresarLo = findViewById(R.id.btnIngresarLogin);
        btnRegistrarse = findViewById(R.id.btnRegistrar);
        btnIngresarLo.setOnClickListener(this);
        btnRegistrarse.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnIngresarLogin:
                Intent intent = new Intent(Ingresar.this, Login.class);
                startActivity(intent);
                break;

            case R.id.btnRegistrar:
                Intent intent1 = new Intent(Ingresar.this, Registrar.class);
                startActivity(intent1);
                break;
        }
    }
}
