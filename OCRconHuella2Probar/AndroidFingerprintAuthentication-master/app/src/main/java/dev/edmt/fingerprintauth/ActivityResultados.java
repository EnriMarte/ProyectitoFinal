package dev.edmt.fingerprintauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityResultados extends AppCompatActivity {
    String texto;
    TextView lblresult;
    TextView lblencontrado;
    ArrayList<String>valores = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        Bundle bundle = getIntent().getExtras();
        texto = bundle.getString("texto");
        lblresult = (TextView) findViewById(R.id.lblresultados);
        lblresult.setText("el texto detectado fue: " + texto);
        spinner = (Spinner)findViewById(R.id.Spinner);
    }

    public void Buscar(View view) {
        String detectadomin = texto.toLowerCase();
        String detectadomin2 = texto.toLowerCase();
        String detectadomin3 = texto.toLowerCase();
        String detectadomin4 = texto.toLowerCase();
        String ph;
        String nahco3;
        String po2;
        String pco2;

        ph = detectadomin.substring(detectadomin.indexOf("ph")+3, 8);
        if (ph.contains("ph")) {
            Toast.makeText(this, ph,Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se encontro ph en el texto", Toast.LENGTH_SHORT).show();
        }
/*
        po2 = detectadomin2.substring(detectadomin.indexOf("po2")+4, 9);
        if (po2.contains("po2")) {
            Toast.makeText(this, po2,Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se encontro po2 en el texto", Toast.LENGTH_SHORT).show();
        }*/
/*
        po2 = detectadomin2.substring(detectadomin2.indexOf("po2"),18);
        if (po2.contains("po2")) {
            valores.add(po2);
            }
        else {Toast.makeText(this, "No se encontro po2 en el texto",Toast.LENGTH_SHORT).show();}

        nahco3 = detectadomin3.substring(detectadomin3.indexOf("nahco3"),22);
        if (nahco3.contains("nahco3")) {
            valores.add(nahco3);
        }
        else {Toast.makeText(this,"No se encontro nahco3 en el texto",Toast.LENGTH_SHORT).show();}

        pco2 = detectadomin4.substring(detectadomin4.indexOf("pco2"),21);
        if (pco2.contains("pco2")) {
            valores.add(pco2);
        }
        else {Toast.makeText(this,"No se encontro pco2 en el texto",Toast.LENGTH_SHORT).show();}

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

    }*/
}

}
