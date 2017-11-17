package afinal.proyecto.proyectofinaldemojunio.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
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
import java.util.concurrent.TimeUnit;

import afinal.proyecto.proyectofinaldemojunio.Fragments.Detalles.detallesDiagnosticos;
import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentDiagnosticos;
import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentUsuarios;
import afinal.proyecto.proyectofinaldemojunio.Model.Diagnosticos;
import afinal.proyecto.proyectofinaldemojunio.R;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

        ImageButton editar = (ImageButton)convertView.findViewById(R.id.editAbm);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = f.getFragmentManager().beginTransaction();
                detallesDiagnosticos fragment = new detallesDiagnosticos();
                fragment.setDiagnosticos(getItem(position));
                fragment.setEditando(true);

                ft.replace(R.id.fragment_container, fragment, "detallesDiagnosticos");
                ft.addToBackStack("detallesDiagnosticos");
                ft.commit();
            }
        });

        ImageButton eliminar = (ImageButton)convertView.findViewById(R.id.deleteAbm);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                alertDialog.setMessage("¿Está seguro que desea eliminar el diagnóstico? Esta acción no se puede deshacer.");
                alertDialog.setCancelable(true);
                alertDialog.setTitle("Atención");
                alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new deleteDiagnostico().execute(String.valueOf(getItem(position).getIdDiagnostico()));
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

    public void notifyDataChanged(List<Diagnosticos> list){
        this.diagnosticos.clear();
        this.diagnosticos.addAll(list);
        this.notifyDataSetChanged();
    }

    private class deleteDiagnostico extends AsyncTask<String, Void, String> {
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
                        .setTitle("¡Diagnóstico eliminado!")
                        .setDuration(3000)
                        .setMessage("Se ha eliminado el diagnóstico correctamente.")
                        .setIcon(R.drawable.ic_cloud_done)
                        .sneak(R.color.colorAccent);

                activity.getFragmentManager().beginTransaction()
                        .addToBackStack("fragmentUsuarios").replace(R.id.fragment_container, new fragmentDiagnosticos() )
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
                    .url("http://192.99.56.223/basedd/Deletes/deleteDiag.php")
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
