package com.example.usuario.crudnuevo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Usuario on 16/6/2017.
 */

public class TablaAdapter extends BaseAdapter {
    ArrayList<Tabla> tablas;
    Context context;

    TablaAdapter(Context context, ArrayList<Tabla> tablas) {
        this.tablas = tablas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return tablas.size();
    }

    @Override
    public Object getItem(int position) {
        return tablas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setTablas(Tabla[] tablas) {
        this.tablas = new ArrayList<>(Arrays.asList(tablas)); // Convierte array en ArrayList
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, viewGroup, false);
        }

        TextView texto = (TextView) view.findViewById(R.id.texto);

        Tabla t = tablas.get(position);
        texto.setText(t.getTexto());
        return view;

    }

}
