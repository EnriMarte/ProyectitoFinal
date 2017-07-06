package afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.irozon.sneaker.Sneaker;

import afinal.proyecto.proyectofinaldemojunio.R;

/**
 * Created by ianfr on 22/06/2017.
 */

public class datosUsuarios extends Fragment {

    public int editarONuevo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.datos_usuarios, container, false);

        Button button = (Button)view.findViewById(R.id.botonNuevoUsuario);

        if (editarONuevo == 0){
            button.setText("Guardar cambios");
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
                            .setMessage("Se ha agregado el usuario a la base de datos")
                            .setIcon(R.drawable.ic_cloud_done)
                            .sneak(R.color.colorAccent);
                }
            }
        });

        return view;
    }
}
