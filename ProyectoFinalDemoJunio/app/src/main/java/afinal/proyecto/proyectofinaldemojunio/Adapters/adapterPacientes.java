package afinal.proyecto.proyectofinaldemojunio.Adapters;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar.datosPacientes;
import afinal.proyecto.proyectofinaldemojunio.MainActivity;
import afinal.proyecto.proyectofinaldemojunio.Model.Paciente;
import afinal.proyecto.proyectofinaldemojunio.R;

/**
 * Created by PC on 7/6/2017.
 */

public class adapterPacientes extends ArrayAdapter<Paciente> {

    private final Fragment f;
    private final Context context;
    private final ArrayList<Paciente> pacientes;

    public adapterPacientes(Context context, Fragment f, ArrayList<Paciente> pacientes) {
        super(context, 0, pacientes);
        this.f = f;
        this.context = context;
        this.pacientes = pacientes;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Paciente paciente = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
        }

        ImageView imgPerfil = (ImageView) convertView.findViewById(R.id.listImagen);
        final TextView nombreApelldoEdad = (TextView) convertView.findViewById(R.id.listTextView1);
        TextView medicoCabeza = (TextView) convertView.findViewById(R.id.listTextView3);
        TextView edad = (TextView) convertView.findViewById(R.id.listTextView2);

       //   imgPerfil.setImageDrawable(paciente.getImagen());
        nombreApelldoEdad.setText(paciente.getNombre() + " " + paciente.getApellido());
        medicoCabeza.setText(paciente.getUltimoEstudio());
        edad.setText("Edad: "+ paciente.getEdad() + " años");

        ImageButton editar = (ImageButton)convertView.findViewById(R.id.editAbm);
        if (editar != null) {
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Paciente p = getItem(position);

                    FragmentTransaction ft = f.getFragmentManager().beginTransaction();
                    datosPacientes fr = new datosPacientes();

                    Bundle bundle = new Bundle();
                    bundle.putString("nombre", p.getNombre());
                    bundle.putString("apellido", p.getApellido());
                    bundle.putInt("edad", p.getEdad());
                    bundle.putString("sexo", p.getSexo());
                    bundle.putDouble("altura", p.getAltura());
                    bundle.putDouble("peso", p.getPeso());
                    bundle.putString("tipoSangre", p.getTipoSangre());

                    fr.setArguments(bundle);

                    fr.editarONuevo = 0;
                    ft.replace(R.id.fragment_container, fr, "detallesPacientesTag");
                    ft.addToBackStack("detallesPacientesTag");
                    ft.commit();
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

    public void notifyDataChanged(List<Paciente> list){
        this.pacientes.clear();
        this.pacientes.addAll(list);
        this.notifyDataSetChanged();
    }
}
