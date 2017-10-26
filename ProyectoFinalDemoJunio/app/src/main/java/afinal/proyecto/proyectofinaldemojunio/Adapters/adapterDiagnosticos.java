package afinal.proyecto.proyectofinaldemojunio.Adapters;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import afinal.proyecto.proyectofinaldemojunio.Model.Diagnosticos;
import afinal.proyecto.proyectofinaldemojunio.R;

/**
 * Created by ianfr on 24/08/2017.
 */

public class adapterDiagnosticos extends ArrayAdapter<Diagnosticos> {

    private final Context mContext;
    private final Fragment f;
    private final ArrayList<Diagnosticos> diagnosticos;
    Activity activity;

    public adapterDiagnosticos(Activity activity, Fragment f, ArrayList<Diagnosticos> usuarios) {
        super(activity, 0, usuarios);
        mContext = activity.getApplicationContext();
        this.f = f;
        this.diagnosticos = usuarios;
        this.activity = activity;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Diagnosticos diagnostico = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
        }

        diagnostico.setCondiciones(diagnostico.getCondiciones().replace("return ", ""));
        diagnostico.setCondiciones(diagnostico.getCondiciones().replace(";", ""));

        TextView textView1 = (TextView)convertView.findViewById(R.id.listTextView1);
        TextView textView2 = (TextView)convertView.findViewById(R.id.listTextView2);

        textView1.setText(diagnostico.getNombre());
        textView2.setText(diagnostico.getCondiciones());

        return convertView;
    }

    public void notifyDataChanged(List<Diagnosticos> list){
        this.diagnosticos.clear();
        this.diagnosticos.addAll(list);
        this.notifyDataSetChanged();
    }
}
