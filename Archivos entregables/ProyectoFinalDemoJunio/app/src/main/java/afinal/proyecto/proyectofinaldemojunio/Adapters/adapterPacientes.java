package afinal.proyecto.proyectofinaldemojunio.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import afinal.proyecto.proyectofinaldemojunio.Fragments.Detalles.detallesPaciente;
import afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar.datosPacientes;
import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentPacientes;
import afinal.proyecto.proyectofinaldemojunio.MainActivity;
import afinal.proyecto.proyectofinaldemojunio.Model.Paciente;
import afinal.proyecto.proyectofinaldemojunio.R;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by PC on 7/6/2017.
 */

public class adapterPacientes extends ArrayAdapter<Paciente> {

    private final Fragment f;
    private final Activity activity;
    private final ArrayList<Paciente> pacientes;

    public adapterPacientes(Activity activity, Fragment f, ArrayList<Paciente> pacientes) {
        super(activity, 0, pacientes);
        this.f = f;
        this.activity = activity;
        this.pacientes = pacientes;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Paciente paciente = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
        }

        ImageView imgPerfil = (ImageView) convertView.findViewById(R.id.listImagen);
        final TextView nombreApelldoEdad = (TextView) convertView.findViewById(R.id.listTextView1);
        TextView medicoCabeza = (TextView) convertView.findViewById(R.id.listTextView3);
        TextView edad = (TextView) convertView.findViewById(R.id.listTextView2);

       //   imgPerfil.setImageDrawable(paciente.getImagen());
        nombreApelldoEdad.setText(paciente.getNombre() + " " + paciente.getApellido());
        medicoCabeza.setText(paciente.getUltimoEstudio());
        edad.setText("Edad: "+ paciente.getEdad() + " años");

        ImageButton editar = (ImageButton)convertView.findViewById(R.id.editAbm);
        if (editar != null) {
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Paciente p = getItem(position);

                    FragmentTransaction ft = f.getFragmentManager().beginTransaction();
                    detallesPaciente fragment = new detallesPaciente();
                    fragment.setPaciente(p);
                    fragment.setEditando(true);

                    ft.replace(R.id.fragment_container, fragment, "detallesPaciente");
                    ft.addToBackStack("detallesPaciente");
                    ft.commit();
                }
            });
        }

        ImageButton eliminar = (ImageButton)convertView.findViewById(R.id.deleteAbm);
        if (eliminar != null) {
            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                    alertDialog.setMessage("¿Está seguro que desea eliminar el paciente? Esta acción no se puede deshacer.");
                    alertDialog.setCancelable(true);
                    alertDialog.setTitle("Atención");
                    alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            new deletePaciente().execute(String.valueOf(getItem(position).getidPacientes()));
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
        }


        return convertView;
    }

    public void notifyDataChanged(List<Paciente> list){
        this.pacientes.clear();
        this.pacientes.addAll(list);
        this.notifyDataSetChanged();
    }

    private class deletePaciente extends AsyncTask<String, Void, String> {
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
                        .setTitle("¡Paciente eliminado!")
                        .setDuration(3000)
                        .setMessage("Se ha eliminado el paciente correctamente.")
                        .setIcon(R.drawable.ic_cloud_done)
                        .sneak(R.color.colorAccent);

                activity.getFragmentManager().beginTransaction()
                        .addToBackStack("fragmentUsuarios").replace(R.id.fragment_container, new fragmentPacientes())
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
                    .url("http://192.99.56.223/basedd/Deletes/deletePaciente.php")
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
