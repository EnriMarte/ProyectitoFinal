package dev.edmt.fingerprintauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActivityResultados extends AppCompatActivity {
    String texto;
    TextView lblresult;
    TextView lblencontrado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        Bundle bundle = getIntent().getExtras();
        texto = bundle.getString("texto");
        lblencontrado =  (TextView)findViewById(R.id.lblencontrado);
        lblresult = (TextView)findViewById(R.id.lblresultados);
        lblresult.setText("el texto detectado fue: " + texto);
    }
    public void Buscar(View view)
    {
        String detectadomin = texto.toLowerCase();
        String ph;
        String nahco3;
        String po2;
        String pco2;
        ph = detectadomin.substring(detectadomin.indexOf("ph")+2,7);
        if (ph.contains("ph")) {lblencontrado.setText(ph);}
        else {lblencontrado.setText("No se encontro ph en el texto");}

        nahco3 = detectadomin.substring(detectadomin.indexOf("nahco3")+7,11);
        if (nahco3.contains("nahco3")) {lblencontrado.setText(nahco3);}
        else {lblencontrado.setText("No se encontro nahco3 en el texto");}

        po2 = detectadomin.substring(detectadomin.indexOf("po2")+3,8);
        if (ph.contains("po2")) {lblencontrado.setText(po2);}
        else {lblencontrado.setText("No se encontro po2 en el texto");}

        pco2 = detectadomin.substring(detectadomin.indexOf("pco2")+5,10);
        if (ph.contains("pco2")) {lblencontrado.setText(pco2);}
        else {lblencontrado.setText("No se encontro pco2 en el texto");}

    }
}
