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

import afinal.proyecto.proyectofinaldemojunio.Adapters.adapterFunciones;
import afinal.proyecto.proyectofinaldemojunio.Fragments.Detalles.detallesFuncion;
import afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar.datosFunciones;
import afinal.proyecto.proyectofinaldemojunio.Model.Funcion;
import afinal.proyecto.proyectofinaldemojunio.R;

public class fragmentFunciones extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.abm, container, false);

        final ListView funciones = (ListView)view.findViewById(R.id.listViewAbm);
        final ArrayList<Funcion> funcionesArray = new Funcion().getAllFunciones();

        funciones.setAdapter(new adapterFunciones(getActivity(), this, funcionesArray));

        funciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Funcion p = funcionesArray.get(position);

                detallesFuncion detallesFuncionesFragment = new detallesFuncion();
                detallesFuncionesFragment.setFuncion(p);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, detallesFuncionesFragment, "detallesFuncionTag");
                ft.commit();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.FABAbm);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                datosFunciones fr = new datosFunciones();
                fr.editarONuevo = 1;
                ft.replace(R.id.fragment_container, fr, "detallesFuncionesTag");
                ft.addToBackStack("detallesFuncionesTag");
                ft.commit();
            }
        });

        return view;
    }
}
