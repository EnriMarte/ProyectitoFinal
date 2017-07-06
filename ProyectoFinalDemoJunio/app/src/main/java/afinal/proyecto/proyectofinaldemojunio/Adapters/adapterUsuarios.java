package afinal.proyecto.proyectofinaldemojunio.Adapters;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar.datosUsuarios;
import afinal.proyecto.proyectofinaldemojunio.Model.Usuario;
import afinal.proyecto.proyectofinaldemojunio.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by PC on 8/6/2017.
 */

public class adapterUsuarios extends ArrayAdapter<Usuario> {

    private final Context mContext;
    private final Fragment f;

    public adapterUsuarios(Context context, Fragment f, ArrayList<Usuario> usuarios) {
        super(context, 0, usuarios);
        mContext = context;
        this.f = f;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    Usuario usuario = getItem(position);

    if (convertView == null) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
    }

    CircleImageView imgPerfil = (CircleImageView) convertView.findViewById(R.id.listImagen);
    final TextView nombreApelldoEdad = (TextView) convertView.findViewById(R.id.listTextView1);
    TextView credencial = (TextView) convertView.findViewById(R.id.listTextView2);
    TextView matricula = (TextView) convertView.findViewById(R.id.listTextView3);

    imgPerfil.setImageDrawable(new ColorDrawable(mContext.getResources().getColor(R.color.colorAccent)));

    nombreApelldoEdad.setText(usuario.getNombre() + " " + usuario.getApellido());
    if(usuario.getCredencial() == 0) {
        credencial.setText("Administrador");
    } else if (usuario.getCredencial() == 1) {
        credencial.setText("Jefe de área");
    } else {
        credencial.setText("Médico");
    }

    matricula.setText("Matrícula: " + usuario.getMatricula());


        ImageButton editar = (ImageButton)convertView.findViewById(R.id.editAbm);
        if (editar != null) {
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction ft = f.getFragmentManager().beginTransaction();
                    datosUsuarios fr = new datosUsuarios();
                    fr.editarONuevo = 0;
                    ft.replace(R.id.fragment_container, fr, "datosUsuariosTag");
                    ft.addToBackStack("datosUsuariosTag");
                    ft.commit();
                }
            });
        }

        ImageButton eliminar = (ImageButton)convertView.findViewById(R.id.deleteAbm);
        if (eliminar != null) {
            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nombreApelldoEdad.setText("eliminar");
                }
            });
        }

    return convertView;
    }
}

