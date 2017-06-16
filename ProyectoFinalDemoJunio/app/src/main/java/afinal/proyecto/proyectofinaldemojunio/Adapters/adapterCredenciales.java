package afinal.proyecto.proyectofinaldemojunio.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import afinal.proyecto.proyectofinaldemojunio.Model.Credencial;
import afinal.proyecto.proyectofinaldemojunio.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by PC on 8/6/2017.
 */

public class adapterCredenciales  extends ArrayAdapter<Credencial> {

    private final Context mContext;

    public adapterCredenciales(Context context, ArrayList<Credencial> usuarios) {
        super(context, 0, usuarios);
        mContext = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Credencial credencial = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
        }

        CircleImageView img = (CircleImageView) convertView.findViewById(R.id.listImagen);
        final TextView nombre = (TextView) convertView.findViewById(R.id.listTextView1);
        TextView restricciones = (TextView) convertView.findViewById(R.id.listTextView3);
        TextView nivelPermiso = (TextView) convertView.findViewById(R.id.listTextView2);

        img.setImageDrawable(new ColorDrawable(getRandomColor()));

        nombre.setText(credencial.getNombre());
        nivelPermiso.setText("Nivel de permiso: " + credencial.getNivelPermiso());
        restricciones.setText("Restricciones: " + credencial.getRestricciones());


        ImageButton editar = (ImageButton)convertView.findViewById(R.id.editAbm);
        if (editar != null) {
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nombre.setText("editar");
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

    public int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}