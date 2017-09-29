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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PC on 10/6/2017.
 */

public class fragmentRegistro extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registro, container, false);

        final EditText usuario = (EditText)view.findViewById(R.id.txtNuevoUsuario);
        final EditText nombre = (EditText)view.findViewById(R.id.txtNuevoNombre);
        final EditText apellido = (EditText)view.findViewById(R.id.txtNuevoApellido);
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
                String name = nombre.getText().toString();
                String surname = apellido.getText().toString();

                if (user.length() > 0 && pass.length() > 0 && rePass.length() > 0 && name.length() > 0 && surname.length() > 0) {
                    if (pass.length() > 7 && !pass.equals(pass.toLowerCase())
                            && !pass.equals(pass.toUpperCase()) && pass.matches(".*\\d+.*")) {
                        if (pass.compareTo(rePass) == 0) {
                            //fijarse si existe
                        } else {
                            Toast.makeText(getActivity(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "La contraseña debe tener al menos 8 caracteres y contener al menos una minúscula, una mayúscula y un número", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Complete los campos", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }
}
