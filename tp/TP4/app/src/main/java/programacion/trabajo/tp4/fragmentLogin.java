package programacion.trabajo.tp4;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by PC on 10/6/2017.
 */

public class fragmentLogin extends Fragment {

    public DataBaseManager _dataBaseManager;
    public SQLiteDatabase _db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);

        final EditText txtUsuario = (EditText)view.findViewById(R.id.txtUsuario);
        final EditText txtContrasena = (EditText)view.findViewById(R.id.txtContrasena);
        Button inicio = (Button)view.findViewById(R.id.btnIngresar);
        Button registro = (Button)view.findViewById(R.id.btnRegistro);

        Spinner antJug;
        antJug = (Spinner)view.findViewById(R.id.anterioresJugadores);

        ArrayList<String>datos;
        datos=new ArrayList<>();
        ArrayAdapter<String> adaptador;

        if(connectToDatabase()) {
            Cursor rows;
            rows = _db.rawQuery("select usuario, contrasena from usuarios", null);

            if (rows.moveToFirst()) {
                do {
                    datos.add(rows.getString(0));
                } while (rows.moveToNext());
            }

            _db.close();
        }

        adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, datos);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_item);
        antJug.setAdapter(adaptador);

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = txtUsuario.getText().toString();
                String pass = txtContrasena.getText().toString();

                if (user.length() > 0 && pass.length() > 0) {
                    if (connectToDatabase()){
                        Cursor rows;
                        rows = _db.rawQuery("select usuario, contrasena from usuarios where usuario = ? and contrasena = ?", new String[]{user, pass});

                        if(rows.moveToFirst()) {
                            getActivity().setTitle("Iniciado");
                        } else {
                            Toast.makeText(getActivity(), "Datos erróneos", Toast.LENGTH_LONG).show();
                        }
                        _db.close();
                    }
                } else {
                    Toast.makeText(getActivity(), "Complete ambos campos", Toast.LENGTH_LONG).show();
                }
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(
                        R.id.fragment_container, new fragmentRegistro(),
                        "registroTag").commit();
            }
        });

        return view;
    }

    boolean connectToDatabase() {
        _dataBaseManager = new DataBaseManager(getActivity(), "db", null, 1);
        _db = _dataBaseManager.getWritableDatabase();
        if (_db != null) {
            return true;
        }
        return false;
    }
}
