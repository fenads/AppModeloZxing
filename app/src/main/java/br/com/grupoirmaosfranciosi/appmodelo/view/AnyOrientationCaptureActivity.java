package br.com.grupoirmaosfranciosi.appmodelo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import br.com.grupoirmaosfranciosi.appmodelo.R;

import com.journeyapps.barcodescanner.CaptureActivity;

public class AnyOrientationCaptureActivity extends CaptureActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_any_orientation_capture);
    }
}