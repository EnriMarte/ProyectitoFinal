package afinal.proyecto.proyectofinaldemojunio.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Iterator;
import java.util.Map;

import afinal.proyecto.proyectofinaldemojunio.MainActivity;
import afinal.proyecto.proyectofinaldemojunio.R;

public class fragmentLaboratorio extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.laboratorio, container, false);

        Button button = (Button)view.findViewById(R.id.botonProcesar);
        MainActivity myParent = (MainActivity)getActivity();
        Map<String,String> detectadoOCR = myParent.getVarsOCR();

        TextView ph = (TextView) view.findViewById(R.id.txtphlaboratorio);
        TextView po2 = (TextView) view.findViewById(R.id.txtpo2laboratorio);
        TextView pco2 = (TextView) view.findViewById(R.id.txtpco2laboratorio);
        EditText edPh = (EditText) view.findViewById(R.id.editTextPH);
        EditText edPO2 = (EditText) view.findViewById(R.id.editTextPO2);
        EditText edPCO2 = (EditText) view.findViewById(R.id.editTextPCO2);
        EditText edNAHCO3 = (EditText) view.findViewById(R.id.editTextNAHCO3);

        if (detectadoOCR != null && detectadoOCR.size() > 0){
            Iterator it = detectadoOCR.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry dato = (Map.Entry)it.next();

                if (dato.getKey().toString().contains
                        (ph.getText().toString().toLowerCase())) {
                    edPh.setText(dato.getValue().toString());
                } else if (dato.getKey().toString().contains
                        (po2.getText().toString().toLowerCase())) {
                    edPO2.setText(dato.getValue().toString());
                } else if (dato.getKey().toString().contains
                        (pco2.getText().toString().toLowerCase())) {
                    edPCO2.setText(dato.getValue().toString());
                } else {
                    edNAHCO3.setText(dato.getValue().toString());
                }

                it.remove();
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuInflater menuInflater= getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.ocr_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_ocr:
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,
                                new fragmentOCR(), "OCRTag")
                        .addToBackStack("OCRTag").commit();
                return true;

            case android.R.id.home:
                getFragmentManager().popBackStackImmediate();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
