package br.com.grupoirmaosfranciosi.appmodelo.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import br.com.grupoirmaosfranciosi.appmodelo.R;
import br.com.grupoirmaosfranciosi.appmodelo.api.appConstants;
import br.com.grupoirmaosfranciosi.appmodelo.controller.FardosController;
import br.com.grupoirmaosfranciosi.appmodelo.model.Emblocamento;

public class ConsultaActivity extends AppCompatActivity {
    Button btnVoltar, btnCapturar;
    TextView txtCodigoBloco, txtBloco;
    EditText edtBloco;
    FardosController controller;
    Emblocamento emblocamento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        btnVoltar = findViewById(R.id.btnVoltar);
        btnCapturar = findViewById(R.id.btnCapturar);
        txtCodigoBloco = findViewById(R.id.txtCodigoBloco);
        edtBloco = findViewById(R.id.edtBloco);
        txtBloco = findViewById(R.id.txtBloco);


        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent iTelaPrincipal = new Intent(ConsultaActivity.this,MainActivity.class);
                startActivity(iTelaPrincipal);
            }
        });

        btnCapturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escanear();
            }
        });


    }


    public void escanear(){
        IntentIntegrator intent = new IntentIntegrator(this);
        //intent.forSupportFragment();
        intent.setCaptureActivity(TorchOnCaptureActivity.class);
        intent.addExtra(appConstants.CAMERA_FLASH_ON,false);
        intent.setOrientationLocked(false);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intent.setPrompt("Ler código de barra");
        intent.setCameraId(0);
        intent.setBeepEnabled(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if ( result != null){
            if (result.getContents() == null){
                Toast.makeText(ConsultaActivity.this,"Cancelada a leitura",Toast.LENGTH_SHORT).show();
            }else {

                edtBloco.setText(result.getContents());
                //edtBloco.setText(result.getContents().toString());
                Bloco();

            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


    public void Bloco(){

        try {
            if (edtBloco != null && edtBloco.length() > 0) {

                controller = new FardosController(this);

                String consulta = edtBloco.getText().toString();
                emblocamento = new Emblocamento();
                emblocamento.setCOD_BARRA_GS1(consulta);
                emblocamento = controller.Buscar(emblocamento);
                String retorno = emblocamento.getNUM_BLOCO().toString();
                txtBloco.setText(retorno);
                //txtBloco.setText(emblocamento.getNUM_BLOCO().toString());

            }
        }catch (Exception e){
            Log.e("getBloco","Error "+e.getMessage());
        }


    }


//teste

}