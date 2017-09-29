package afinal.proyecto.proyectofinaldemojunio.Adapters;

import android.app.Fragment;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import afinal.proyecto.proyectofinaldemojunio.R;

/**
 * Created by ianfr on 07/08/2017.
 */

public class adapterVariablesEditar extends ArrayAdapter<String> {

    private final Context mContext;
    private final Fragment f;
    private ArrayList<String> vars;
    private final String var;

    public adapterVariablesEditar(Context context, Fragment f, ArrayList<String> vars, String var) {
        super(context, 0, vars);
        mContext = context;
        this.f = f;
        this.vars = vars;
        this.var = var;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String variable = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.editar_condiciones_row, parent, false);
        }

        Spinner condicionador = (Spinner) convertView.findViewById(R.id.spinnerCondicionador);
        Spinner operador = (Spinner) convertView.findViewById(R.id.spinnerOperador);
        TextView nombreVar = (TextView) convertView.findViewById(R.id.nombreVar);

        nombreVar.setText(var);

        if (position > 0)
            condicionador.setVisibility(View.VISIBLE);
        if (position < 1)
            condicionador.setVisibility(View.INVISIBLE);

        ArrayList<String> items = new ArrayList<>();
        items.add("AND");
        items.add("OR");
        ArrayAdapter<String> aa = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, items);
        condicionador.setAdapter(aa);

        items = new ArrayList<>();
        items.add("<");
        items.add(">");
        items.add("=");
        aa = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, items);
        operador.setAdapter(aa);

        return convertView;
    }

    public void notifyDataChanged(ArrayList<String> list){
        vars = new ArrayList<>();
        for (String item:list) {
            vars.add(item);
        }
        notifyDataSetChanged();
    }
}

