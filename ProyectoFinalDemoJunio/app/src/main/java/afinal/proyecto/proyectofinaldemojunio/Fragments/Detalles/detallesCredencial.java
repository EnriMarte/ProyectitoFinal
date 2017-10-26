package afinal.proyecto.proyectofinaldemojunio.Fragments.Detalles;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import afinal.proyecto.proyectofinaldemojunio.Model.Credencial;
import afinal.proyecto.proyectofinaldemojunio.R;

/**
 * Created by 42567321 on 9/6/2017.
 */

public class detallesCredencial extends Fragment {
    public Credencial getCredencial() { return credencial; }
    public void setCredencial(Credencial credencial) {
        this.credencial = credencial;
    }

    Credencial credencial;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detalles_credenciales, container, false);


        return view;
    }
}
