package afinal.proyecto.proyectofinaldemojunio.Fragments.Detalles;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentLaboratorio;
import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentOCR;
import afinal.proyecto.proyectofinaldemojunio.MainActivity;
import afinal.proyecto.proyectofinaldemojunio.Model.Paciente;
import afinal.proyecto.proyectofinaldemojunio.R;

/**
 * Created by PC on 7/6/2017.
 */

public class detallesPaciente extends Fragment {

    Paciente paciente;

    public Paciente getPaciente() {
        return paciente;
    }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detalles_pacientes, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuInflater menuInflater= getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.nuevo_labo_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.nuevo_labo:
                MainActivity ma = (MainActivity)getActivity();
                ma.setIdPacienteSelecc(ma.getIdClickeadoEnVista());
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,
                                new fragmentLaboratorio(), "fragmentLaboratorio")
                        .addToBackStack("fragmentLaboratorio").commit();
                return true;

            case android.R.id.home:
                getFragmentManager().popBackStackImmediate();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
