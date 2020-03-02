package com.casasolarctpi.uv.controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.casasolarctpi.uv.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText txtUsuario,  txtContrasena;
    Button btnIngresar, btnRegistrar;
    TextView txtOlvidarConstraseña;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        inicializar();
        inicializarFirebase();
    }

    private void inicializarFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    private void inicializar() {
        txtUsuario = findViewById(R.id.txtUsuario);
        txtContrasena = findViewById(R.id.txtContrasena);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(this);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);
        txtOlvidarConstraseña = findViewById(R.id.txtRestablecer);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnIngresar:
                String correo = txtUsuario.getText().toString();
                String contrasena  = txtContrasena.getText().toString();
                signIn(correo,contrasena);
                break;

            case R.id.btnRegistrar:
                Intent intent = new Intent(Login.this, Registrar.class);
                startActivity(intent);
                break;
        }
    }

    private void signIn(String correo, String contrasena) {

        Log.d("Inicio de Sesión", "Ingresar" + correo);
        if (!validar()) {
            return;
        }

        MostrarProgressDialog();

        mAuth.signInWithEmailAndPassword(correo, contrasena).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d("Inicio de sesión", "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                    Toast.makeText(Login.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                }else{
                    Log.w("Inicio de sesión", "signInWithEmail:success");
                    Toast.makeText(Login.this, "El usuario no esta registrado, o la contraseña no coincide", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                    hideProgressDialog();
                }
            }
        });
    }

    private void hideProgressDialog() {
        btnIngresar.setEnabled(true);
        btnRegistrar.setEnabled(true);
        txtUsuario.setEnabled(true);
        txtContrasena.setEnabled(true);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            Intent intent = new Intent(Login.this, MenuPrincipal.class);
            startActivity(intent);
            finish();
        }else {

        }
    }

    private void MostrarProgressDialog() {
        btnIngresar.setEnabled(false);
        btnRegistrar.setEnabled(false);
        txtUsuario.setEnabled(false);
        txtContrasena.setEnabled(false);
    }

    private boolean validar(){
        boolean validar = true;

        String correo = txtUsuario.getText().toString();
        if (TextUtils.isEmpty(correo)){
            txtUsuario.setError("Este campo es obligatorio");
            validar = false;
        }else {
            txtUsuario.setError(null);
        }

        String contraseña = txtContrasena.getText().toString();
        if (TextUtils.isEmpty(contraseña)){
            txtContrasena.setError("Este campo es obligatorio");
            validar = false;
        }else {
            txtContrasena.setError(null);
        }

        return validar;
    }
}
