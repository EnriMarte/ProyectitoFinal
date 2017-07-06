package afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.irozon.sneaker.Sneaker;

import afinal.proyecto.proyectofinaldemojunio.Model.Paciente;
import afinal.proyecto.proyectofinaldemojunio.R;

/**
 * Created by ianfr on 22/06/2017.
 */

public class datosPacientes extends Fragment{

        public int editarONuevo;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater,
                                 @Nullable ViewGroup container, Bundle savedInstanceState) {

            Bundle bundle = getArguments();

            View view = inflater.inflate(R.layout.datos_pacientes, container, false);

            Button button = (Button)view.findViewById(R.id.botonNuevoPaciente);

            if (editarONuevo == 0){
                button.setText("Guardar cambios");
                view.findViewById(R.id.txtUltimoEstudio).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.txtUltimoEstudioFecha).setVisibility(View.INVISIBLE);

                EditText nombre = (EditText) view.findViewById(R.id.editarNombrePaciente);

                if (bundle.getString("nombre") != null && bundle.getString("nombre").length() > 0)
                    nombre.setText(bundle.getString("nombre"));
                else
                    nombre.setText("Indefinido");

                EditText apellido = (EditText) view.findViewById(R.id.editarApellidoPaciente);

                if (bundle.getString("apellido") != null && bundle.getString("nombre").length() > 0)
                    apellido.setText(bundle.getString("apellido"));
                else
                    apellido.setText("Indefinido");

                EditText edad = (EditText) view.findViewById(R.id.editarEdadPaciente);

                if (Integer.toString(bundle.getInt("edad")) != null && Integer.toString(bundle.getInt("edad")).length() > 0)
                    edad.setText(Integer.toString(bundle.getInt("edad")));
                else
                    edad.setText("Indefinido");

                EditText sexo = (EditText) view.findViewById(R.id.editarAlturaPaciente);

                if (bundle.getString("sexo") != null && bundle.getString("nombre").length() > 0)
                    sexo.setText(bundle.getString("sexo"));
                else
                    sexo.setText("Indefinido");

                EditText peso = (EditText) view.findViewById(R.id.editarPesoPaciente);

                if (Double.toString(bundle.getDouble("peso")) != null && Double.toString(bundle.getDouble("peso")).length() > 0)
                    peso.setText(Double.toString(bundle.getDouble("peso")));
                else
                    peso.setText("Indefinido");

                EditText altura = (EditText) view.findViewById(R.id.editarAlturaPaciente);

                if (Double.toString(bundle.getDouble("altura")) != null && Double.toString(bundle.getDouble("peso")).length() > 0)
                    altura.setText(Double.toString(bundle.getDouble("altura")));
                else
                    altura.setText("Indefinido");

                EditText tipoDeSangre = (EditText) view.findViewById(R.id.editarTipoSangre);

                if (bundle.getString("tipoSangre") != null && bundle.getString("nombre").length() > 0)
                    tipoDeSangre.setText(bundle.getString("tipoSangre"));
                else
                    tipoDeSangre.setText("Indefinido");
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editarONuevo == 0){
                        Sneaker.with(getActivity())
                                .setTitle("Cambios guardados")
                                .setDuration(3000)
                                .setMessage("Los cambios se realizaron satisfactoriamente")
                                .setIcon(R.drawable.ic_cloud_done)
                                .sneak(R.color.colorAccent);
                    }else{
                        Sneaker.with(getActivity())
                                .setTitle("Paciente agregado")
                                .setDuration(3000)
                                .setMessage("Se ha agregado el paciente a la base de datos")
                                .setIcon(R.drawable.ic_cloud_done)
                                .sneak(R.color.colorAccent);
                    }
                }
            });



            return view;
    }
}
