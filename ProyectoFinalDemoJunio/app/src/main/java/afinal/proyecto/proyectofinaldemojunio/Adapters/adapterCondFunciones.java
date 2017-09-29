package afinal.proyecto.proyectofinaldemojunio.Adapters;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import afinal.proyecto.proyectofinaldemojunio.R;

/**
 * Created by ianfr on 15/09/2017.
 */

public class adapterCondFunciones extends ArrayAdapter<String> {

    private final Context mContext;
    private final Fragment f;
    private final ArrayList<String> vars;

    public adapterCondFunciones(Context context, Fragment f, ArrayList<String> vars) {
        super(context, 0, vars);
        mContext = context;
        this.f = f;
        this.vars = vars;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String variable = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.condiciones_list_row, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.txtSelectVariable);
        textView.setText("Configurar funcion " + variable);

        return convertView;
    }
}
