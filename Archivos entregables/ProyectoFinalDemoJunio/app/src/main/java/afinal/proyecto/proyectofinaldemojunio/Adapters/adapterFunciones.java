package afinal.proyecto.proyectofinaldemojunio.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import afinal.proyecto.proyectofinaldemojunio.Fragments.Detalles.detallesDiagnosticos;
import afinal.proyecto.proyectofinaldemojunio.Fragments.Detalles.detallesFuncion;
import afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar.datosFunciones;
import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentFunciones;
import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentPacientes;
import afinal.proyecto.proyectofinaldemojunio.Model.Funcion;
import afinal.proyecto.proyectofinaldemojunio.R;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by PC on 8/6/2017.
 */

public class adapterFunciones extends ArrayAdapter<Funcion> {

    private final Activity activity;
    private final Fragment f;
    private final ArrayList<Funcion>funciones;

    public adapterFunciones(Activity activity, Fragment f, ArrayList<Funcion> funciones) {
        super(activity, 0, funciones);
        this.activity = activity;
        this.f = f;
        this.funciones = funciones;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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


        ImageButton editar = (ImageButton)convertView.findViewById(R.id.editAbm);
        if (editar != null) {
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction ft = f.getFragmentManager().beginTransaction();
                    detallesFuncion fragment = new detallesFuncion();
                    fragment.setFuncion(getItem(position));
                    fragment.setEditando(true);
                    ft.replace(R.id.fragment_container, fragment, "detallesFuncionesTag");
                    ft.addToBackStack("detallesFuncionesTag");
                    ft.commit();
                }
            });
        }

        ImageButton eliminar = (ImageButton)convertView.findViewById(R.id.deleteAbm);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                alertDialog.setMessage("¿Está seguro que desea eliminar la función? Esta acción no se puede deshacer.");
                alertDialog.setCancelable(true);
                alertDialog.setTitle("Atención");
                alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new deleteFunc().execute(String.valueOf(getItem(position).getIdFunciones()));
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

    public void notifyDataChanged(List<Funcion> list){
        this.funciones.clear();
        this.funciones.addAll(list);
        this.notifyDataSetChanged();
    }

    private class deleteFunc extends AsyncTask<String, Void, String> {
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
                        .setTitle("¡Función eliminada!")
                        .setDuration(3000)
                        .setMessage("Se ha eliminado la función correctamente.")
                        .setIcon(R.drawable.ic_cloud_done)
                        .sneak(R.color.colorAccent);

                activity.getFragmentManager().beginTransaction()
                        .addToBackStack("fragmentUsuarios").replace(R.id.fragment_container, new fragmentFunciones())
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
                    .url("http://192.99.56.223/basedd/Deletes/deleteFunc.php")
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
