package programacion.trabajo.tp4;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by PC on 10/6/2017.
 */

public class fragmentRegistro extends Fragment {

    public DataBaseManager _dataBaseManager;
    public SQLiteDatabase _db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registro, container, false);

        final EditText usuario = (EditText)view.findViewById(R.id.txtNuevoUsuario);
        final EditText contrasena = (EditText)view.findViewById(R.id.txtNuevaContrasena);
        final EditText repitaContrasena = (EditText)view.findViewById(R.id.txtRepitaContrasena);
        final Button button = (Button)view.findViewById(R.id.btnFinalizarRegistro);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean registrado = false;
                String user = usuario.getText().toString();
                String pass = contrasena.getText().toString();
                String rePass = repitaContrasena.getText().toString();
                if (user.length() > 0 && pass.length() > 0 && rePass.length() > 0) {
                    if (pass.compareTo(rePass) == 0) {
                            if (connectToDatabase()) {
                                Cursor rows;
                                rows = _db.rawQuery("select usuario from usuarios where usuario = '" + user + "'", null);
                                if (!rows.moveToFirst()) {
                                    ContentValues reg;
                                    reg = new ContentValues();

                                    reg.put("usuario", user);
                                    reg.put("contrasena", pass);

                                    _db.insert("jugadores", null, reg);

                                    registrado = true;
                                } else {
                                    Toast.makeText(getActivity(), "Usuario ya registrado", Toast.LENGTH_LONG).show();
                                }
                                _db.close();

                                if (registrado) {
                                    Toast.makeText(getActivity(), "Ingrese con sus datos", Toast.LENGTH_LONG).show();

                                    getFragmentManager().beginTransaction().replace(
                                            R.id.fragment_container, new fragmentLogin(),
                                            "loginTag").commit();
                                }
                            }
                    } else {
                        Toast.makeText(getActivity(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Complete los campos", Toast.LENGTH_LONG).show();
                }
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
