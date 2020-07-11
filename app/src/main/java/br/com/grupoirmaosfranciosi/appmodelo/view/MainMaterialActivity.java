package br.com.grupoirmaosfranciosi.appmodelo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.grupoirmaosfranciosi.appmodelo.R;
import br.com.grupoirmaosfranciosi.appmodelo.api.AppUtil;
import br.com.grupoirmaosfranciosi.appmodelo.controller.FardosController;
import br.com.grupoirmaosfranciosi.appmodelo.datamodel.EmblocamentoDataModel;
import br.com.grupoirmaosfranciosi.appmodelo.model.Emblocamento;

public class MainMaterialActivity extends AppCompatActivity {

    //Variavel
    SharedPreferences sharedpreferences;
    FardosController controller;
    Context context;
    String local;
    String portaCon;
    String ipCon;

    public CardView listarCard,configurarCard,atualizarCard,emblocarCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_material);

        //Definindo Cards
        listarCard = (CardView) findViewById(R.id.listarId);
        configurarCard = (CardView) findViewById(R.id.configurarId);
        atualizarCard = (CardView) findViewById(R.id.atualizarId);
        emblocarCard = (CardView) findViewById(R.id.emblocarId);

        //Hooks
        context = getBaseContext();
        controller = new FardosController(context);

        //Add Click Listerner
        listarCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent iTelaListar = new Intent(MainMaterialActivity.this, ConsultarBlocosActivity.class);
                startActivity(iTelaListar);
            }
        });

        configurarCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent iTelaConfigurar = new Intent(MainMaterialActivity.this, ConfiguracaoActivity.class);
                startActivity(iTelaConfigurar);
            }
        });

        atualizarCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getLocal();
                String a = local.substring(0,1);

                if(a.equals("0") || a.equals("n")){
                    Toast.makeText(getApplicationContext(), "Falta configuração de ip e porta", Toast.LENGTH_SHORT).show();
                }else {
                    MainMaterialActivity.AtualizarSistema task = new MainMaterialActivity.AtualizarSistema();
                    task.execute();
                }

            }
        });

        emblocarCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent iTelaEmblocar = new Intent(MainMaterialActivity.this, ConsultaActivity.class);
                startActivity(iTelaEmblocar);
            }
        });
    }

    public String getLocal(){

        sharedpreferences = getSharedPreferences(AppUtil.PREF_APP, MODE_PRIVATE);
        ipCon = sharedpreferences.getString("ip","null");
        portaCon = sharedpreferences.getString("porta","null");


        local = ipCon+':'+portaCon;
        return local;
    }


    private class AtualizarSistema extends AsyncTask<String,String,String> {

        ProgressDialog progressDialog = new ProgressDialog(MainMaterialActivity.this);


        HttpURLConnection conn;
        URL url = null;
        String Servidor = "http://"+local+"/api/Bloco/Recuperar";
        Uri.Builder builder;


        @Override
        protected void onPreExecute(){

            try {
                    progressDialog.setMessage("Atualizando Sistema Aguarde....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            catch (Exception e){

                Log.e("WebService", "AtualizarSistema"+e.getMessage());
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                url = new URL(Servidor);
                //url = new URL(AppUtil.URL_WEB_SERVICE);
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
                }
                else{

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
                        obj.setNUM_BLOCO(jsonObject.getInt(EmblocamentoDataModel.getNumBloco()));
                        obj.setNUM_FARDO(jsonObject.getInt(EmblocamentoDataModel.getNumFardo()));

                        controller.salvar(obj);
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Nenhum registro encontrado no momento...", Toast.LENGTH_SHORT).show();
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