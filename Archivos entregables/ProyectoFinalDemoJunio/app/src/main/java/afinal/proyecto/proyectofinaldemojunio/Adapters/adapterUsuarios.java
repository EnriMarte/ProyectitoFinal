package afinal.proyecto.proyectofinaldemojunio.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import afinal.proyecto.proyectofinaldemojunio.DB.HttpHandler;
import afinal.proyecto.proyectofinaldemojunio.Fragments.Detalles.detallesUsuario;
import afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar.datosUsuarios;
import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentUsuarios;
import afinal.proyecto.proyectofinaldemojunio.MainActivity;
import afinal.proyecto.proyectofinaldemojunio.Model.Usuario;
import afinal.proyecto.proyectofinaldemojunio.R;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by PC on 8/6/2017.
 */

public class adapterUsuarios extends ArrayAdapter<Usuario> {

    private final Context mContext;
    private final Fragment f;
    private final ArrayList<Usuario> usuarios;
    Activity activity;

    public adapterUsuarios(Activity activity, Fragment f, ArrayList<Usuario> usuarios) {
        super(activity, 0, usuarios);
        mContext = activity.getApplicationContext();
        this.f = f;
        this.usuarios = usuarios;
        this.activity = activity;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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
    if(usuario.getIdCredencial() == 0) {
        credencial.setText("Administrador");
    } else if (usuario.getIdCredencial() == 1) {
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
                    Usuario u = getItem(position);

                    FragmentTransaction ft = f.getFragmentManager().beginTransaction();
                    detallesUsuario fragment = new detallesUsuario();
                    fragment.setUsuario(u);
                    fragment.setEditando(true);

                    ft.replace(R.id.fragment_container, fragment, "detallesUsuariosTag");
                    ft.addToBackStack("detallesUsuariosTag");
                    ft.commit();
                }
            });
        }

        ImageButton eliminar = (ImageButton)convertView.findViewById(R.id.deleteAbm);
            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                    alertDialog.setMessage("¿Está seguro que desea eliminar el usuario? Esta acción no se puede deshacer.");
                    alertDialog.setCancelable(true);
                    alertDialog.setTitle("Atención");
                    alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            new deleteUsuario().execute(String.valueOf(getItem(position).getIdUsuario()));
                        }
                    });
                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }});
                    AlertDialog alertLogin = alertDialog.create();
                    alertLogin.show();
                }
            });

        return convertView;
    }

    public void notifyDataChanged(List<Usuario> list){
        this.usuarios.clear();
        this.usuarios.addAll(list);
        this.notifyDataSetChanged();
    }





    private class deleteUsuario extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s.equals("error")){
                Sneaker.with(activity)
                        .setTitle("¡Error de conexión! :(")
                        .setDuration(4000)
                        .setMessage("Error al contactar con el servidor, comprueba tu conexión a internet.")
                        .setIcon(R.drawable.ic_cloud_off)
                        .sneak(R.color.red);
            } else {
                Sneaker.with(activity)
                        .setTitle("¡Usuario eliminado!")
                        .setDuration(3000)
                        .setMessage("Se ha eliminado el usuario correctamente.")
                        .setIcon(R.drawable.ic_cloud_done)
                        .sneak(R.color.colorAccent);

                activity.getFragmentManager().beginTransaction()
                        .addToBackStack("fragmentUsuarios").replace(R.id.fragment_container, new fragmentUsuarios())
                        .commit();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();
            client.newBuilder().connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS).build();

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("id", params[0])
                    .build();

            Request request = new Request.Builder()
                    .url("http://192.99.56.223/basedd/Deletes/deleteUsuario.php")
                    .method("POST", RequestBody.create(null, new byte[0]))
                    .post(requestBody)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String resultado = response.body().string();
                return resultado;
            } catch (Exception e) {
                Log.d("Debug", e.getMessage());
                return "error";
            }
        }
    }
}

