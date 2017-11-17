package afinal.proyecto.proyectofinaldemojunio.Adapters;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import afinal.proyecto.proyectofinaldemojunio.Model.Diagnosticos;
import afinal.proyecto.proyectofinaldemojunio.Model.Funcion;
import afinal.proyecto.proyectofinaldemojunio.R;

/**
 * Created by ianfr on 15/09/2017.
 */

public class adapterSelectFuncDiagnosticos  extends ArrayAdapter<Funcion> {

    private final Context mContext;
    private final Fragment f;
    private final ArrayList<Funcion> funciones;
    Activity activity;

    public adapterSelectFuncDiagnosticos(Activity activity, Fragment f, ArrayList<Funcion> funciones) {
        super(activity, 0, funciones);
        mContext = activity.getApplicationContext();
        this.f = f;
        this.funciones = funciones;
        this.activity = activity;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Funcion funcion = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row_checkbox, parent, false);
        }

//        View imagen = convertView.findViewById(R.id.listImagen);
  //      ((ViewGroup) imagen.getParent()).removeView(imagen);

        final TextView nombre = (TextView) convertView.findViewById(R.id.listTextView1);
        TextView funcionview = (TextView) convertView.findViewById(R.id.listTextView2);
        TextView parametros = (TextView) convertView.findViewById(R.id.listTextView3);

        nombre.setText(funcion.getNombre());
        funcionview.setText(funcion.getFuncion());

        String pars = "";

        return convertView;
    }

    public void notifyDataChanged(List<Funcion> list){
        this.funciones.clear();
        this.funciones.addAll(list);
        this.notifyDataSetChanged();
    }
}