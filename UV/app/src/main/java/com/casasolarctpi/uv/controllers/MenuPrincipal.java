package com.casasolarctpi.uv.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.casasolarctpi.uv.Maps.MapsActivity;
import com.casasolarctpi.uv.R;

public class MenuPrincipal extends AppCompatActivity implements View.OnClickListener {
    Button btnUv, btnMaterial, btnCalidadAire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu_principal);
        getSupportActionBar().hide();
        inicializar();
    }

    private void inicializar() {
        btnUv = findViewById(R.id.btnUV);
        btnMaterial = findViewById(R.id.btnMaterial);
        btnCalidadAire = findViewById(R.id.btnCalidadAire);
        btnUv.setOnClickListener(this);
        btnMaterial.setOnClickListener(this);
        btnCalidadAire.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnUV:
                Intent intent = new Intent(MenuPrincipal.this, MainActivityUv.class);
                startActivity(intent);

                break;

            case R.id.btnMaterial:
                break;

            case R.id.btnCalidadAire:
                break;
        }

    }
}
