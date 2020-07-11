package br.com.grupoirmaosfranciosi.appmodelo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.grupoirmaosfranciosi.appmodelo.R;
import br.com.grupoirmaosfranciosi.appmodelo.api.AppUtil;
import br.com.grupoirmaosfranciosi.appmodelo.datasource.DataSource;

public class SplashActivity extends AppCompatActivity {

    //Variavel
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo, slogan;

    DataSource dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        dataBase = new DataSource(getApplicationContext());

        //Animação
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.txtAlgodoeira);
        slogan = findViewById(R.id.textLogo);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        iniciarAplicativo();
    }

    private void iniciarAplicativo(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainMaterialActivity.class);
                startActivity(intent);
                finish();
                //return;
            }
        }, AppUtil.TIME_SPLASH);
    }
}