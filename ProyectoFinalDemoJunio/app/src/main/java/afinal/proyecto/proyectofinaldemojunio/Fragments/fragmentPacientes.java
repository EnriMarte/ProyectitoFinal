package afinal.proyecto.proyectofinaldemojunio.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import afinal.proyecto.proyectofinaldemojunio.Adapters.adapterPacientes;
import afinal.proyecto.proyectofinaldemojunio.Model.Paciente;
import afinal.proyecto.proyectofinaldemojunio.R;

public class fragmentPacientes extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.abm, container, false);


        final ListView pacientes = (ListView)view.findViewById(R.id.listViewAbm);
        final ArrayList<Paciente> pacientesArray = new Paciente().getAllPacientes(getActivity());

        pacientes.setAdapter(new adapterPacientes(getActivity(), pacientesArray));

        //todo: arreglar listener, no lo toma
        pacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Paciente p = pacientesArray.get(position);

                detallesPaciente detallesPacienteFragmento = new detallesPaciente();;
                detallesPacienteFragmento.setPaciente(p);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, detallesPacienteFragmento, "detallesPacienteTag");
                ft.addToBackStack("detallesPacienteTag");
                ft.commit();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.FABAbm);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: hacer que cargue la vista de nuevo paciente
            }
        });
/*
        ImageButton editar = (ImageButton)view.findViewById(R.id.editAbm);
        if (editar != null) {
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        ImageButton eliminar = (ImageButton)view.findViewById(R.id.deleteAbm);
        if (eliminar != null) {
            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        */

        return view;
    }
}
