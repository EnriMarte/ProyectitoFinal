package com.example.ianfr.proyectofinal_android;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ianfr on 04/05/2017.
 */

public class listPacientesAdapter extends ArrayAdapter<Pacientes> {
    public listPacientesAdapter(Context context, ArrayList<Pacientes> pacientes) {
        super(context, 0, pacientes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Pacientes paciente = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_pacientes_row, parent, false);
        }
        // Lookup view for data population
        ImageView imgPerfil = (ImageView) convertView.findViewById(R.id.imagenPaciente);
        TextView nombreApelldoEdad = (TextView) convertView.findViewById(R.id.nombreApellido);
        TextView medicoCabeza = (TextView) convertView.findViewById(R.id.medicoCabezera);
        // Populate the data into the template view using the data object
        imgPerfil.setImageDrawable(paciente.imagen);
        nombreApelldoEdad.setText(paciente.nombre + " " + paciente.apellido + " (" + paciente.edad + " años)");
        medicoCabeza.setText("Médico de cabecera: " + paciente.medicoCabecera);
        // Return the completed view to render on screen
        return convertView;
    }
}
