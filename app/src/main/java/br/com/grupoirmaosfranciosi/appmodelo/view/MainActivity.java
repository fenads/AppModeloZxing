package br.com.grupoirmaosfranciosi.appmodelo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import br.com.grupoirmaosfranciosi.appmodelo.R;
import br.com.grupoirmaosfranciosi.appmodelo.api.AppUtil;
import br.com.grupoirmaosfranciosi.appmodelo.controller.FardosController;
import br.com.grupoirmaosfranciosi.appmodelo.datamodel.EmblocamentoDataModel;
import br.com.grupoirmaosfranciosi.appmodelo.model.Emblocamento;

public class MainActivity extends AppCompatActivity {

    FardosController controller;
    Context context;

    TextView txtHoraAtual;
    TextView txtDataAtual;
    Button btnEmblocar;
    Button btnAtualiza;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getBaseContext();
        controller = new FardosController(context);

        txtDataAtual = findViewById(R.id.textDataAtual);
        txtHoraAtual = findViewById(R.id.textHoraAtual);
        btnEmblocar = findViewById(R.id.btnEmblocar);
        btnAtualiza = findViewById(R.id.btnAtualiza);


        txtDataAtual.setText(AppUtil.getDataAtual());
        txtHoraAtual.setText(AppUtil.getHoraAtual());
        btnEmblocar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iTelaEmblocar = new Intent(MainActivity.this, ConsultaActivity.class);
                startActivity(iTelaEmblocar);
            }
        });

        btnAtualiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AtualizarSistema task = new AtualizarSistema();
                task.execute();
            }
        });


    }

    private class AtualizarSistema extends AsyncTask<String,String,String>{

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        HttpURLConnection conn;
        URL url = null;
        Uri.Builder builder;

        public AtualizarSistema(){

        }

        @Override
        protected void onPreExecute(){

            Log.i("WebService", "AtualizarSistema()");

            progressDialog.setMessage("Atualizando Sistema Aguarde....");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
              url = new URL(AppUtil.URL_WEB_SERVICE);
            }catch (MalformedURLException e){
                Log.e("WebService","MalformedURLException - "+e.getMessage());
            }catch (Exception error){
                Log.e("WebService","Exception - "+error.getMessage());
            }
            try{
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(AppUtil.CONNECTION_TIMEOUT);
                conn.setReadTimeout(AppUtil.READ_TIMEOUT);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("charset","utf-8");

//                conn.setDoInput(true);
//                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

                writer.flush();
                writer.close();
                os.close();
                conn.connect();


            }catch (Exception e){

            }
            try{

                int response_code = conn.getResponseCode();

                if(response_code == HttpURLConnection.HTTP_OK){

                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine())!= null){
                        result.append(line);
                    }
                    return (result.toString());
                }else{
                    return ("Error de conexão");
               }
            }catch (Exception e){
                Log.e("Webservice","IOException -"+e.getMessage());
                return e.toString();
            }finally {
                conn.disconnect();
            }

        }

        @Override
        protected void onPostExecute(String result){
            try{
                JSONArray jsonArray = new JSONArray(result);
                if(jsonArray.length() != 0){
                    controller.deletarTabela(EmblocamentoDataModel.getTABELA());
                    controller.criarTabela(EmblocamentoDataModel.criarTabela());

                    for (int i = 0; i < jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        Emblocamento obj = new Emblocamento();

                        obj.setCOD_BARRA_GS1(jsonObject.getString(EmblocamentoDataModel.getCodBarraGs1()));
                        obj.setNUM_FARDO(jsonObject.getInt(EmblocamentoDataModel.getNumFardo()));
                        obj.setNUM_BLOCO(jsonObject.getInt(EmblocamentoDataModel.getNumBloco()));

                        controller.salvar(obj);
                    }
                }else {
                  AppUtil.showMensagem(context,"Nenhum registro encontrado no momento...");
                }
            }catch (JSONException e){
                Log.e("Webservice","JSONException -"+e.getMessage());
            }finally {
                if (progressDialog != null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
        }
    }

}