package afinal.proyecto.proyectofinaldemojunio.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import afinal.proyecto.proyectofinaldemojunio.Model.Paciente;
import afinal.proyecto.proyectofinaldemojunio.R;

/**
 * Created by PC on 7/6/2017.
 */

public class adapterPacientes extends ArrayAdapter<Paciente> {
    public adapterPacientes(Context context, ArrayList<Paciente> pacientes) {
        super(context, 0, pacientes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Paciente paciente = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
        }

        ImageView imgPerfil = (ImageView) convertView.findViewById(R.id.listImagen);
        final TextView nombreApelldoEdad = (TextView) convertView.findViewById(R.id.listTextView1);
        TextView medicoCabeza = (TextView) convertView.findViewById(R.id.listTextView3);
        TextView edad = (TextView) convertView.findViewById(R.id.listTextView2);

        imgPerfil.setImageDrawable(paciente.getImagen());
        nombreApelldoEdad.setText(paciente.nombre + " " + paciente.getApellido());
        medicoCabeza.setText("Méd. cabecera: " + paciente.getMedicoCabecera());
        edad.setText("Edad: "+ paciente.getEdad() + " años");

        ImageButton editar = (ImageButton)convertView.findViewById(R.id.editAbm);
        if (editar != null) {
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nombreApelldoEdad.setText("editar");
                }
            });
        }

        ImageButton eliminar = (ImageButton)convertView.findViewById(R.id.deleteAbm);
        if (eliminar != null) {
            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nombreApelldoEdad.setText("eliminar");
                }
            });
        }


        return convertView;
    }
}
