package com.casasolarctpi.uv.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.widget.ImageView;

import com.casasolarctpi.uv.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    ImageView ImgSplash;
    public static DatabaseReference reference;
    private FirebaseAuth mAuth;
    boolean bandera = true;
    int valor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        FirebaseApp.initializeApp(this);
        inizialiteFirebaseApp();
        ImgSplash = findViewById(R.id.ImgSplash);

        bandera = true;
        valor = 0;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(bandera){
                    try {
                        Thread.sleep(2000);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            valor++;
                            if (valor == 2){
                                animacionSplash();
                                bandera = false;
                            }
                        }
                    });
                }
            }
        }).start();
    }

    private void animacionSplash() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            try {

                Animator animator = ViewAnimationUtils.createCircularReveal(ImgSplash,0,ImgSplash.getWidth(),0,ImgSplash.getHeight()*1.5f);
                final Animator animator1 = ViewAnimationUtils.createCircularReveal(ImgSplash,ImgSplash.getMaxWidth()/2, ImgSplash.getHeight()/2, ImgSplash.getHeight()*1.5f,0);
                animator.setDuration(800);
                animator1.setDuration(800);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        animator1.start();

                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        ImgSplash.setVisibility(View.VISIBLE);

                    }
                });

                animator1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        updateUI(currentUser);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                    }
                });

                animator.start();

            }catch (Exception e){

                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        ImgSplash.setVisibility(View.VISIBLE);
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        updateUI(currentUser);
                    }
                };
                new Timer().schedule(timerTask,1000);

            }
        }
    }

    private void inizialiteFirebaseApp() {
        FirebaseApp.initializeApp(this);
        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(false);}catch (Exception e){}
        reference= FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(Splash.this,MenuPrincipal.class);
            startActivity(intent);
            finish();

        } else {
            Intent intent = new Intent(Splash.this,Login.class);
            startActivity(intent);
            finish();
        }
    }


}
