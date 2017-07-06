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

import afinal.proyecto.proyectofinaldemojunio.Adapters.adapterUsuarios;
import afinal.proyecto.proyectofinaldemojunio.Fragments.Detalles.detallesUsuario;
import afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar.datosUsuarios;
import afinal.proyecto.proyectofinaldemojunio.Model.Usuario;
import afinal.proyecto.proyectofinaldemojunio.R;

public class fragmentUsuarios extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.abm, container, false);

        final ListView usuarios = (ListView)view.findViewById(R.id.listViewAbm);
        final ArrayList<Usuario> usuariosArray = new Usuario().getAllUsuarios();

        usuarios.setAdapter(new adapterUsuarios(getActivity(), this, usuariosArray));

        usuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Usuario p = usuariosArray.get(position);

                detallesUsuario detallesUsuarioFragmento = new detallesUsuario();;
                detallesUsuarioFragmento.setUsuario(p);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, detallesUsuarioFragmento, "detallesUsuariosTag");
                ft.commit();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.FABAbm);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                datosUsuarios fr = new datosUsuarios();
                fr.editarONuevo = 1;
                ft.replace(R.id.fragment_container, fr, "datosUsuariosTag");
                ft.addToBackStack("datosUsuariosTag");
                ft.commit();
            }
        });

        return view;
    }
}
