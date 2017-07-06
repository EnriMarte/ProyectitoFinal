package afinal.proyecto.proyectofinaldemojunio.Adapters;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar.datosFunciones;
import afinal.proyecto.proyectofinaldemojunio.Model.Funcion;
import afinal.proyecto.proyectofinaldemojunio.R;

/**
 * Created by PC on 8/6/2017.
 */

public class adapterFunciones extends ArrayAdapter<Funcion> {

    private final Context mContext;
    private final Fragment f;

    public adapterFunciones(Context context, Fragment f, ArrayList<Funcion> usuarios) {
        super(context, 0, usuarios);
        mContext = context;
        this.f = f;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Funcion funcion = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
        }

        View imagen = convertView.findViewById(R.id.listImagen);
        ((ViewGroup) imagen.getParent()).removeView(imagen);

        final TextView nombre = (TextView) convertView.findViewById(R.id.listTextView1);
        TextView funcionview = (TextView) convertView.findViewById(R.id.listTextView2);
        TextView parametros = (TextView) convertView.findViewById(R.id.listTextView3);

        nombre.setText(funcion.getNombre());
        funcionview.setText(funcion.getFuncion());

        String pars = "";

        if (funcion.getVariables().length > 0){
            for (int i = 0; i < funcion.getVariables().length; i++){
                if (i == 0){
                    pars+= "x=" + funcion.getVariables()[i];
                } else if (i == 1 && funcion.getVariables()[i] != null) {
                    pars+= " | y=" + funcion.getVariables()[i];
                } else if (i == 2 && funcion.getVariables()[i] != null) {
                    pars+= " | z=" + funcion.getVariables()[i] ;
                } else if (i == 3 && funcion.getVariables()[i] != null){
                    pars+= " | a=" + funcion.getVariables()[i];
                }
            }
            parametros.setText(pars);
        } else {
            parametros.setText("Sin parámetros");
        }


        ImageButton editar = (ImageButton)convertView.findViewById(R.id.editAbm);
        if (editar != null) {
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction ft = f.getFragmentManager().beginTransaction();
                    datosFunciones fr = new datosFunciones();
                    fr.editarONuevo = 0;
                    ft.replace(R.id.fragment_container, fr, "detallesFuncionesTag");
                    ft.addToBackStack("detallesFuncionesTag");
                    ft.commit();
                }
            });
        }

        ImageButton eliminar = (ImageButton)convertView.findViewById(R.id.deleteAbm);
        if (eliminar != null) {
            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nombre.setText("eliminar");
                }
            });
        }

        return convertView;
    }
}
