package afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import afinal.proyecto.proyectofinaldemojunio.Adapters.adapterVariablesEditar;
import afinal.proyecto.proyectofinaldemojunio.MainActivity;
import afinal.proyecto.proyectofinaldemojunio.R;

/**
 * Created by ianfr on 07/08/2017.
 */

public class condicionesFuncionesEditar extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.abm_variables, container, false);

        final MainActivity myParent = (MainActivity) getActivity();
        final String var = myParent.getVariableEditar();
        final ArrayList<String> condiciones = new ArrayList<>();
        final ListView listView = (ListView) view.findViewById(R.id.listViewAbm);

        getActivity().setTitle("Configurar variable " + var);

        condiciones.add("");

        final adapterVariablesEditar ave = new adapterVariablesEditar(getActivity(), this, condiciones, var);
        listView.setAdapter(ave);

        FloatingActionButton nuevaCondición = (FloatingActionButton) view.findViewById(R.id.FABAbm);
        nuevaCondición.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                condiciones.add("");
                ave.notifyDataChanged(condiciones);
            }
        });

        FloatingActionButton guardarCondicion = (FloatingActionButton) view.findViewById(R.id.FABSaveAbm);
        guardarCondicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View childView;
                String cond = "";
                boolean first = true;
                for (int i = 0; i < condiciones.size(); i++){
                    childView = listView.getChildAt(i);
                    if (!first) {
                        Spinner condicionador = (Spinner) childView.findViewById(R.id.spinnerCondicionador);
                        cond += condicionador.getSelectedItem().toString();
                        cond+=" ";
                    } else
                        first = false;

                    cond+="$"+var;

                    Spinner operador = (Spinner) childView.findViewById(R.id.spinnerOperador);
                    cond+=operador.getSelectedItem().toString();

                    EditText comparador = (EditText) childView.findViewById(R.id.editTextComparador);
                    if (comparador.getText().toString().equals(""))
                        cond+="NULLTEXT";
                    else
                        cond += comparador.getText().toString();

                    cond+=" ";
                }

                if (!cond.contains("NULLTEXT")) {
                    myParent.addCondición(var, cond);
                    getFragmentManager().popBackStackImmediate();
                }
                else
                    Toast.makeText(getActivity(), "Complete los campos", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
