package programacion.tp5;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by 42567321 on 13/6/2017.
 */

public class fragmentIniciado extends Fragment {
    DataBaseManager dataBaseManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.iniciado, container, false);

        dataBaseManager = new DataBaseManager();

        final Spinner antJug;
        antJug = (Spinner)view.findViewById(R.id.anterioresJugadores);

        final ArrayList<String> datos;
        datos=new ArrayList<>();
        ArrayAdapter<String> adaptador;

        int i = 0;

        for (String a:dataBaseManager.getAllUsers()) {
            datos.add(0, a);

            i++;
        }

        final ArrayList<String> itemsByLogin;
        itemsByLogin = getUltimoLogin();

        adaptador = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, datos);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_item);
        antJug.setAdapter(adaptador);

        RadioButton ordenAlfabetico = (RadioButton) view.findViewById(R.id.rbAlfabetico);
        RadioButton ordenLogin = (RadioButton) view.findViewById(R.id.rbLogin);

        ordenAlfabetico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayAdapter<String> otroAdaptador;
                datos.clear();

                int i = 0;

                for (String a:dataBaseManager.getUsersByAlphabet()) {
                    datos.add(0, a);

                    i++;
                }

                otroAdaptador = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, datos);
                otroAdaptador.setDropDownViewResource(android.R.layout.simple_spinner_item);
                antJug.setAdapter(otroAdaptador);
            }
        });

        ordenLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayAdapter<String> otroAdaptadorMas;

                int i = 0;

                for (String a:dataBaseManager.getUsersByLogin()) {
                    datos.add(0, a);

                    i++;
                }

                otroAdaptadorMas = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, itemsByLogin);
                otroAdaptadorMas.setDropDownViewResource(android.R.layout.simple_spinner_item);
                antJug.setAdapter(otroAdaptadorMas);
            }
        });


            //actualizar fecha


        return view;
    }

    ArrayList<String> getUltimoLogin(){
        ArrayList<String> datos=new ArrayList<>();

        //todo: como llevar el usuario a buscar
        datos.add(0, dataBaseManager.getLastLogin("a"));

        return datos;
    }
}
