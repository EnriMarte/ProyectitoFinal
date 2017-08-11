package afinal.proyecto.proyectofinaldemojunio.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.irozon.sneaker.Sneaker;

import org.w3c.dom.Text;

import java.util.Iterator;
import java.util.Map;

import afinal.proyecto.proyectofinaldemojunio.MainActivity;
import afinal.proyecto.proyectofinaldemojunio.R;

/**
 * Created by ianfr on 08/08/2017.
 */

public class fragmentConfirFunc extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.confir_func, container, false);
        MainActivity myParent = (MainActivity) getActivity();
        String nombreFuncion = myParent.getNombreFuncion();
        String funcion = myParent.getFuncion();
        Map<String, String> condiciones = myParent.getVariablesCondicionesFuncion();

        TextView txtNombreFuncion = (TextView) view.findViewById(R.id.confirmarNombreFuncion);
        TextView txtFuncion = (TextView) view.findViewById(R.id.confirmarFuncion);
        TextView txtVars = (TextView) view.findViewById(R.id.confirmarVariables);
        Button button = (Button) view.findViewById(R.id.botonConfirmarVariables);

        txtNombreFuncion.setText("Se agregará la siguiente función: " + nombreFuncion);
        txtFuncion.setText(funcion);

        String condicionesConcatenadas = "";
        Iterator it = condiciones.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry dato = (Map.Entry)it.next();
            condicionesConcatenadas+=dato.getKey() + ": " + dato.getValue() + "\n";
            it.remove();
        }

        txtVars.setText(condicionesConcatenadas);

        getActivity().setTitle("Confirmación");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sneaker.with(getActivity())
                        .setTitle("Función creada")
                        .setDuration(3000)
                        .setMessage("Se ha agregado la función a la base de datos")
                        .setIcon(R.drawable.ic_cloud_done)
                        .sneak(R.color.colorAccent);

                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,
                                new fragmentFunciones(), "funcionesTag")
                        .addToBackStack("funcionesTag").commit();
            }
        });

        return view;
    }
}
