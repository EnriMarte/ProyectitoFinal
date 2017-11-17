package afinal.proyecto.proyectofinaldemojunio.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import android.support.v7.app.ActionBar;

import afinal.proyecto.proyectofinaldemojunio.R;

public class fragmentMenuPrincipal extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.menu_principal, container, false);

        getActivity().setTitle("Menú principal");

        CircleMenu circleMenu = (CircleMenu)view.findViewById(R.id.circle_menu);
        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.drawable.ic_add, R.drawable.ic_close)
                .addSubMenu(Color.parseColor("#EF008B"), R.drawable.ic_laboratorio)
                .addSubMenu(Color.parseColor("#FF8B00"), R.drawable.ic_pacientes)
                .addSubMenu(Color.parseColor("#00E0E0"), R.drawable.ic_usuarios)
                .addSubMenu(Color.parseColor("#FFD900"), R.drawable.ic_funciones)
                .addSubMenu(Color.parseColor("#b942f4"), R.drawable.ic_healing)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int i) {
                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
                        final int index = i;

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {

                                ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
                                if (actionBar != null)
                                    actionBar.setDisplayHomeAsUpEnabled(true);

                                switch (index)
                                {
                                    case 0:
                                        ft.replace(R.id.fragment_container, new fragmentLaboratorio(), "laboratorioTag");
                                        getActivity().setTitle("Laboratorio");
                                        ft.addToBackStack("laboratorioTag");
                                        break;
                                    case 1:
                                        ft.replace(R.id.fragment_container, new fragmentPacientes(), "pacientesTag");
                                        getActivity().setTitle("Pacientes");
                                        ft.addToBackStack("pacientesTag");
                                        break;
                                    case 2:
                                        ft.replace(R.id.fragment_container, new fragmentUsuarios(), "usuariosTag");
                                        getActivity().setTitle("Usuarios");
                                        ft.addToBackStack("usuariosTag");
                                        break;
                                    case 3:
                                        ft.replace(R.id.fragment_container, new fragmentFunciones(), "funcionesTag");
                                        getActivity().setTitle("Funciones");
                                        ft.addToBackStack("funcionesTag");
                                        break;
                                    case 4:
                                        ft.replace(R.id.fragment_container, new fragmentDiagnosticos(), "diagnosticosTag");
                                        getActivity().setTitle("Diagnósticos");
                                        ft.addToBackStack("diagnosticosTag");
                                        break;
                                }

                                tabMenu tab = new tabMenu();
                                tab.tabClickeado = index;
                                tab.llegaDesdeMenuPrincipal = true;
                                ft.replace(R.id.bottomBar_container, tab, "tabMenuTag");
                                ft.commit();
                            }
                        }, 590);
                    }
                });



        return view;
    }
}
