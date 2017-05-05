package com.example.ianfr.proyectofinal_android;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ianfr on 04/05/2017.
 */

public class popup extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupwindow);

        Bundle b = getIntent().getExtras();

        TextView nombreApellido = (TextView)findViewById(R.id.nombreApellidoExtendido);
        nombreApellido.setText(b.getString("nombre") + " " + b.getString("apellido"));
        TextView edad = (TextView)findViewById(R.id.edadPacienteExtendido);
        edad.setText("Edad: " + String.valueOf(b.getInt("edad")));
        TextView peso = (TextView)findViewById(R.id.pesoPaciente);
        peso.setText("Peso: " + String.valueOf(b.getInt("peso")));
        TextView altura = (TextView)findViewById(R.id.alturaPaciente);
        altura.setText("Altura: " + String.valueOf(b.getDouble("altura")) + "m.");
        TextView sexo = (TextView)findViewById(R.id.sexoPaciente);
        sexo.setText("Sexo: " + b.getString("sexo"));
        TextView medicoCab = (TextView)findViewById(R.id.medicoCabezeraExtendido);
        medicoCab.setText("Médico de cabecera: " + b.getString("medCabeza"));
        TextView ultEst = (TextView)findViewById(R.id.ultimoEstudio);
        ultEst.setText("Último estudio: " + b.getString("ultEstudio"));
        ImageView foto = (ImageView)findViewById(R.id.imagenPacienteExtendido);

        switch (b.getInt("numPaciente"))
        {
            case 0:
                foto.setImageResource(R.drawable.paciente_1);
                break;
            case 1:
                foto.setImageResource(R.drawable.paciente_2);
                break;
            case 2:
                foto.setImageResource(R.drawable.paciente_3);
                break;
            case 3:
                foto.setImageResource(R.drawable.paciente_4);
                break;
            case 4:
                foto.setImageResource(R.drawable.paciente_5);
                break;
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.6));
    }
}
