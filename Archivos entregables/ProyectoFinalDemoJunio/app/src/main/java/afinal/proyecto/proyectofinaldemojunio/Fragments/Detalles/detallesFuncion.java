package afinal.proyecto.proyectofinaldemojunio.Fragments.Detalles;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.irozon.sneaker.Sneaker;

import java.util.concurrent.TimeUnit;

import afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar.datosFunciones;
import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentDiagnosticos;
import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentFunciones;
import afinal.proyecto.proyectofinaldemojunio.MainActivity;
import afinal.proyecto.proyectofinaldemojunio.Model.Funcion;
import afinal.proyecto.proyectofinaldemojunio.R;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 42567321 on 9/6/2017.
 */

public class detallesFuncion extends Fragment {
    Funcion funcion;
    boolean editando = false, listoParaAgregar = false;

    public Funcion getFuncion() {
        return funcion;
    }
    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }
    public boolean isEditando() {
        return editando;
    }
    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.confir_func, container, false);

        final TextView titulo = (TextView) view.findViewById(R.id.confirmarNombreFuncion);
        final TextView funciontxt = (TextView) view.findViewById(R.id.confirmarFuncion);
        final TextView vars = (TextView) view.findViewById(R.id.confirmarVariables);
        final TextView boton = (TextView) view.findViewById(R.id.botonConfirmarVariables);

        if (!editando) {
            try {
                titulo.setText(funcion.getNombre());
                funciontxt.setText(funcion.getFuncion());
                String var = funcion.getCondiciones();
                var = var.replaceAll("&&", "AND");
                var = var.replaceAll("\\$", "");
                var = var.replaceAll("\\|\\|", "OR");
                vars.setText(var);

                boton.setVisibility(View.INVISIBLE);
            } catch (Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

            setHasOptionsMenu(true);
        } else {
            MainActivity mainActivity = (MainActivity)getActivity();
            mainActivity.setFuncionEditando(funcion);
            mainActivity.setEditandoFuncion(true);
            setHasOptionsMenu(false);

            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new datosFunciones(),
                    "datosFunciones").addToBackStack("datosFunciones").commit();
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuInflater menuInflater= getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.edit_delete_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("¿Está seguro que desea eliminar el usuario? Esta acción no se puede deshacer.");
                alertDialog.setCancelable(true);
                alertDialog.setTitle("Atención");
                alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new deleteFunc().execute(String.valueOf(funcion.getIdFunciones()));
                        getFragmentManager().beginTransaction().addToBackStack("funcionesTag")
                                .replace(R.id.fragment_container, new fragmentFunciones(), "funcionesTag").commit();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }});
                AlertDialog alertLogin = alertDialog.create();
                alertLogin.show();
                return true;

            case R.id.action_edit:
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                detallesFuncion fragment = new detallesFuncion();
                fragment.setFuncion(funcion);
                fragment.setEditando(true);

                ft.replace(R.id.fragment_container, fragment, "detallesFuncion");
                ft.addToBackStack("detallesFuncion");
                ft.commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class deleteFunc extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s.equals("error")){
                Sneaker.with(getActivity())
                        .setTitle("¡Error de conexión! :(")
                        .setDuration(4000)
                        .setMessage("Error al contactar con el servidor, comprueba tu conexión a internet.")
                        .setIcon(R.drawable.ic_cloud_off)
                        .sneak(R.color.red);
            } else {
                Sneaker.with(getActivity())
                        .setTitle("¡Función eliminada!")
                        .setDuration(3000)
                        .setMessage("Se ha eliminado la función correctamente.")
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
