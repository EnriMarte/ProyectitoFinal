package afinal.proyecto.proyectofinaldemojunio.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import afinal.proyecto.proyectofinaldemojunio.R;


/*
*   Para cambiar entre 4 y 5 tabs, comentar donde se cambia el tabclickeado de 4
*   a 3 más abajo y descomentar el selecttabatposition(tabclickeado)
*   además, en el bottombar:tabs.xml hay que agregar un nuevo tab y cambiar el de mas
* */

public class tabMenu extends Fragment {

    public int tabClickeado;
    public Boolean llegaDesdeMenuPrincipal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.tab_menu, container, false);

        final BottomBar bottomBar = (BottomBar)view.findViewById(R.id.bottomBar);

        bottomBar.selectTabAtPosition(tabClickeado, false);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                if (llegaDesdeMenuPrincipal) {
                    switch (tabClickeado) {
                        case 0:
                            tabId = R.id.tab_laboratorio;
                            break;
                        case 1:
                            tabId = R.id.tab_pacientes;
                            break;
                        case 2:
                            tabId = R.id.tab_usuarios;
                            break;
                        case 3:
                            tabId = R.id.tab_funciones;
                            break;
                        case 4:
                            tabId = R.id.tab_diagnosticos;
                            break;
                    }
                }

                if(!llegaDesdeMenuPrincipal) {
                    if (tabId == R.id.tab_laboratorio) {
                        ft.replace(R.id.fragment_container, new fragmentLaboratorio(), "laboratorioTag");
                        ft.addToBackStack("laboratorioTag");
                        getActivity().setTitle("Laboratorio");
                    }
                    if (tabId == R.id.tab_pacientes) {
                        ft.replace(R.id.fragment_container, new fragmentPacientes(), "pacientesTag");
                        ft.addToBackStack("pacientesTag");
                        getActivity().setTitle("Pacientes");
                    }
                    if (tabId == R.id.tab_usuarios) {
                        ft.replace(R.id.fragment_container, new fragmentUsuarios(), "usuariosTag");
                        ft.addToBackStack("usuariosTag");
                        getActivity().setTitle("Usuarios");
                    }
                    if (tabId == R.id.tab_funciones) {
                        ft.replace(R.id.fragment_container, new fragmentFunciones(), "funcionesTag");
                        ft.addToBackStack("funcionesTag");
                        getActivity().setTitle("Funciones");
                    }
                    if (tabId == R.id.tab_diagnosticos) {
                        ft.replace(R.id.fragment_container, new fragmentDiagnosticos(), "diagnosticosTag");
                        ft.addToBackStack("diagnosticosTag");
                        getActivity().setTitle("Diagnósticos");
                    }
                    ft.commit();
                }
                llegaDesdeMenuPrincipal = false;
            }
        });

        return view;
    }
}
