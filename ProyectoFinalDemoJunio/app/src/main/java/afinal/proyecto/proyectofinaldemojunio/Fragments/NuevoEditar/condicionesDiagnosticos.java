package afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import afinal.proyecto.proyectofinaldemojunio.Adapters.adapterCondFunciones;
import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentConfirDiagnos;
import afinal.proyecto.proyectofinaldemojunio.MainActivity;
import afinal.proyecto.proyectofinaldemojunio.R;

/**
 * Created by ianfr on 15/09/2017.
 */

public class condicionesDiagnosticos extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.abm, container, false);

        final MainActivity myParent = (MainActivity) getActivity();
        final ArrayList<String> vars = myParent.getVariablesFunciones();

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.FABAbm);
        fab.setImageResource(R.drawable.ic_next);
        getActivity().setTitle("Configuración de funciones");

        final ListView variables = (ListView)view.findViewById(R.id.listViewAbm);
        adapterCondFunciones acf = new adapterCondFunciones(getActivity(), this, vars);
        variables.setAdapter(acf);

        variables.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myParent.setVariableEditar(vars.get(position));

                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,
                                new condicionesFuncionesEditar(), "condicionesEditarTag")
                        .addToBackStack("condicionesEditarTag").commit();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,
                                new fragmentConfirDiagnos(), "confDiagsTag")
                        .addToBackStack("confDiagsTag").commit();
            }
        });

        return view;
    }
}
