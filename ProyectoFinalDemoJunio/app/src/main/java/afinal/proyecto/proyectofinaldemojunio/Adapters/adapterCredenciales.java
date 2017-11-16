package afinal.proyecto.proyectofinaldemojunio.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import java.util.Random;
import java.util.concurrent.TimeUnit;

import afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar.datosCredenciales;
import afinal.proyecto.proyectofinaldemojunio.Model.Credencial;
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

public class adapterCredenciales  extends ArrayAdapter<Credencial> {

    private final Activity activity;
    private final Fragment f;
    private final ArrayList<Credencial> credenciales;

    public adapterCredenciales(Activity activity, Fragment f, ArrayList<Credencial> credenciales) {
        super(activity, 0, credenciales);
        this.activity = activity;
        this.f=f;
        this.credenciales = credenciales;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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
                    FragmentTransaction ft = f.getFragmentManager().beginTransaction();
                    datosCredenciales fr = new datosCredenciales();
                    fr.editarONuevo = 0;
                    ft.replace(R.id.fragment_container, fr, "detallesCredencialesTag");
                    ft.addToBackStack("detallesCredencialesTag");
                    ft.commit();
                }
            });
        }

        ImageButton eliminar = (ImageButton)convertView.findViewById(R.id.deleteAbm);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                alertDialog.setMessage("¿Está seguro que desea eliminar la credencial? Esta acción no se puede deshacer.");
                alertDialog.setCancelable(true);
                alertDialog.setTitle("Atención");
                alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new deleteCred().execute(String.valueOf(getItem(position).getIdCredenciales()));
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

    public int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public void notifyDataChanged(List<Credencial> list){
        this.credenciales.clear();
        this.credenciales.addAll(list);
        this.notifyDataSetChanged();
    }



    private class deleteCred extends AsyncTask<String, Void, String> {
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
                        .setTitle("¡Credencial eliminado!")
                        .setDuration(3000)
                        .setMessage("Se ha eliminado la credencial correctamente.")
                        .setIcon(R.drawable.ic_cloud_done)
                        .sneak(R.color.colorAccent);
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
                    .url("http://192.99.56.223/basedd/deleteCred.php")
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