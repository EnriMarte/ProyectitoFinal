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

import afinal.proyecto.proyectofinaldemojunio.Adapters.adapterCredenciales;
import afinal.proyecto.proyectofinaldemojunio.Fragments.Detalles.detallesCredencial;
import afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar.datosCredenciales;
import afinal.proyecto.proyectofinaldemojunio.Model.Credencial;
import afinal.proyecto.proyectofinaldemojunio.R;

public class fragmentCredenciales extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.abm, container, false);

        final ListView credenciales = (ListView)view.findViewById(R.id.listViewAbm);
        final ArrayList<Credencial> credencialesArray = new Credencial().getAllCredenciales();

        credenciales.setAdapter(new adapterCredenciales(getActivity(), this, credencialesArray));

        credenciales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Credencial p = credencialesArray.get(position);

                detallesCredencial detallesCredencialFragment = new detallesCredencial();
                detallesCredencialFragment.setCredencial(p);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, detallesCredencialFragment, "detallesCredencialTag");
                ft.commit();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.FABAbm);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                datosCredenciales fr = new datosCredenciales();
                fr.editarONuevo = 1;
                ft.replace(R.id.fragment_container, fr, "datosCredencialesTag");
                ft.addToBackStack("datosCredencialesTag");
                ft.commit();
            }
        });

        return view;
    }
}
