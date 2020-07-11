package br.com.grupoirmaosfranciosi.appmodelo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.com.grupoirmaosfranciosi.appmodelo.R;
import br.com.grupoirmaosfranciosi.appmodelo.api.AppUtil;

public class ConfiguracaoActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    TextView ip;
    TextView porta;
    public static final String Config = "conf";
    public static final String Ip = "ip";
    public static final String Porta = "porta";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        //Hooks
        ip = (TextView) findViewById(R.id.edtConfIp);
        porta = (TextView) findViewById(R.id.edtConfPorta);

        sharedpreferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);

        if (sharedpreferences.contains(Ip)) {
            ip.setText(sharedpreferences.getString(Ip, ""));
        }
        if (sharedpreferences.contains(Porta)) {
            porta.setText(sharedpreferences.getString(Porta, ""));

        }


    }

    public void Save(View view) {
        String i = ip.getText().toString();
        String p = porta.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Ip, i);
        editor.putString(Porta, p);
        editor.apply();


        Toast.makeText(getApplicationContext(), "Salvo com sucesso!", Toast.LENGTH_SHORT).show();

    }

    public void clear(View view) {
        ip = (TextView) findViewById(R.id.edtConfIp);
        porta = (TextView) findViewById(R.id.edtConfPorta);
        ip.setText("");
        porta.setText("");

    }

    public void Get(View view) {
        ip = (TextView) findViewById(R.id.edtConfIp);
        porta = (TextView) findViewById(R.id.edtConfPorta);
        sharedpreferences = getSharedPreferences(Config,
                Context.MODE_PRIVATE);

        if (sharedpreferences.contains(Ip)) {
            ip.setText(sharedpreferences.getString(Ip, ""));
        }
        if (sharedpreferences.contains(Porta)) {
            porta.setText(sharedpreferences.getString(Porta, ""));

        }

    }


}