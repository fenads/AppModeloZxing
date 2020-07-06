package br.com.grupoirmaosfranciosi.appmodelo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import br.com.grupoirmaosfranciosi.appmodelo.R;
import br.com.grupoirmaosfranciosi.appmodelo.api.AppUtil;
import br.com.grupoirmaosfranciosi.appmodelo.datasource.DataSource;

public class SplashActivity extends AppCompatActivity {

    DataSource dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        dataBase = new DataSource(getApplicationContext());

        iniciarAplicativo();
    }

    private void iniciarAplicativo(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        }, AppUtil.TIME_SPLASH);
    }
}