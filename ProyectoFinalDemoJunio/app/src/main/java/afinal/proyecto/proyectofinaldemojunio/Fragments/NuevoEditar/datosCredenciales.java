package afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar;

import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.irozon.sneaker.Sneaker;

import java.util.concurrent.TimeUnit;

import afinal.proyecto.proyectofinaldemojunio.DB.HttpHandler;
import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentCredenciales;
import afinal.proyecto.proyectofinaldemojunio.R;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ianfr on 16/06/2017.
 */

public class datosCredenciales extends Fragment {

    boolean listoParaAgregar;
    public int editarONuevo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.datos_credenciales, container, false);

        Button button = (Button)view.findViewById(R.id.botonNuevaCredencial);

        if (editarONuevo == 0){
            button.setText("Guardar cambios");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editarONuevo == 0){
                    Sneaker.with(getActivity())
                            .setTitle("Cambios guardados")
                            .setDuration(3000)
                            .setMessage("Los cambios se realizaron satisfactoriamente")
                            .setIcon(R.drawable.ic_cloud_done)
                            .sneak(R.color.colorAccent);
                }else{
                    Sneaker.with(getActivity())
                            .setTitle("Credencial creada")
                            .setDuration(3000)
                            .setMessage("Se ha agregado la credencial a la base de datos")
                            .setIcon(R.drawable.ic_cloud_done)
                            .sneak(R.color.colorAccent);
                }
            }
        });

        return view;
    }

    private void insertarPaciente(String[] params){
        new InsertPaciente().execute(params[0], params[1], params[2], params[3], params[4], params[5], params[6]);
    }

    private class InsertPaciente extends AsyncTask<String, Void, String> {

        protected void onPostExecute(String datos) {
            super.onPostExecute(datos);

            if (datos.equals("error")) {
                //Toast.makeText(getActivity(), "Comprueba tu conexión a Internet", Toast.LENGTH_SHORT).show();
                Sneaker.with(getActivity())
                        .setTitle("¡Error de conexión! :(")
                        .setDuration(4000)
                        .setHeight(95)
                        .setMessage("Error al contactar con el servidor, comprueba tu conexión a internet.")
                        .setIcon(R.drawable.ic_cloud_off)
                        .sneak(R.color.red);
            } else if (datos.equals("faltanDatos")) {
                Sneaker.with(getActivity())
                        .setTitle("¡No se puede agregar el usuario! :(")
                        .setHeight(77)
                        .setDuration(3000)
                        .setMessage("Todos los campos son obligatorios.")
                        .sneakError();
            } else {
                //ok
                Sneaker.with(getActivity())
                        .setTitle("¡Usuario agregado!")
                        .setHeight(77)
                        .setDuration(3000)
                        .setMessage("Se ha agregado el usuario correctamente.")
                        .setIcon(R.drawable.ic_cloud_done)
                        .sneak(R.color.colorAccent);
            }

            if (listoParaAgregar) {
                getActivity().getFragmentManager().beginTransaction().replace
                        (R.id.fragment_container, new fragmentCredenciales(), "credencialesTag").commit();
                getActivity().getFragmentManager().beginTransaction().addToBackStack("credencialesTag");
                getActivity().setTitle("Credenciales");
            }
        }

        @Override
        protected String doInBackground(String... parametros) {

            if (new HttpHandler(getActivity()).hasActiveInternetConnection()) {
                OkHttpClient client = new OkHttpClient();
                client.newBuilder().connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS).build();

                if (parametros[0].trim().length() > 0 && parametros[1].trim().length() > 0 &&
                        parametros[2].trim().length() > 0 && parametros[3].trim().length() > 0 &&
                        parametros[4].trim().length() > 0) {


                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("nombre", parametros[0])
                            .addFormDataPart("apellido", parametros[1])
                            .addFormDataPart("sexo", parametros[2])
                            .addFormDataPart("altura", parametros[3])
                            .addFormDataPart("edad", parametros[4])
                            .addFormDataPart("peso", parametros[5])
                            .addFormDataPart("tipoSangre", parametros[6])
                            .addFormDataPart("medicoCabecera", "0")
                            .addFormDataPart("ultimoEstudio", "2017-01-01")
                            .build();

                    listoParaAgregar = true;

                    Request request = new Request.Builder()
                            .url("http://192.99.56.223/basedd/insertCredenciales.php")
                            .method("POST", RequestBody.create(null, new byte[0]))
                            .post(requestBody)
                            .build();

                    try {
                        Response response = client.newCall(request).execute();
                        String resultado = response.body().string();
                        return resultado;
                    } catch (Exception e) {
                        Log.d("Debug", e.getMessage());
                        //mostrarError(e.getMessage()); // Error de Network
                        return "error";
                    }
                } else {
                    return "faltanDatos";
                }
            } else {
                return "error";
            }
        }
    }
}
