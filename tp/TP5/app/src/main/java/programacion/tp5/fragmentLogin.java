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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by PC on 10/6/2017.
 */

public class fragmentLogin extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);

        final EditText txtUsuario = (EditText)view.findViewById(R.id.txtUsuario);
        final EditText txtContrasena = (EditText)view.findViewById(R.id.txtContrasena);
        Button inicio = (Button)view.findViewById(R.id.btnIngresar);
        Button registro = (Button)view.findViewById(R.id.btnRegistro);

        final DataBaseManager dataBaseManager = new DataBaseManager();

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = txtUsuario.getText().toString();
                String pass = txtContrasena.getText().toString();

                if (user.length() > 0 && pass.length() > 0) {
                    if (dataBaseManager.login(txtUsuario.getText().toString(), txtContrasena.getText().toString())) {
                        getFragmentManager().beginTransaction().replace(
                                R.id.fragment_container, new fragmentIniciado(),
                                "iniciadoTag").commit();
                    } else{
                        Toast.makeText(getActivity(), "Usuario y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
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
}
