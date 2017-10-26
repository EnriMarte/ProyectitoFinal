package afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.irozon.sneaker.Sneaker;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import afinal.proyecto.proyectofinaldemojunio.DB.HttpHandler;
import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentUsuarios;
import afinal.proyecto.proyectofinaldemojunio.R;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ianfr on 22/06/2017.
 */

public class datosUsuarios extends Fragment {

    public int editarONuevo;
    private boolean listoParaAgregar;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        bundle = getArguments();

        View view = inflater.inflate(R.layout.datos_usuarios, container, false);

        Button button = (Button) view.findViewById(R.id.botonNuevoUsuario);
        final EditText nombre = (EditText) view.findViewById(R.id.editarNombreUsuario);
        final EditText apellido = (EditText) view.findViewById(R.id.editarApellidoUsuario);
        final EditText matricula = (EditText) view.findViewById(R.id.editarMatriculaUsuario);
        final EditText hospital = (EditText) view.findViewById(R.id.editarHospitalUsuario);
        final EditText credencial = (EditText) view.findViewById(R.id.editarCredencialUsuario);

        if (editarONuevo == 0) {
            button.setText("Guardar cambios");

            if (editarONuevo == 0) {
                button.setText("Guardar cambios");
                view.findViewById(R.id.ultimoLoginUsuario).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.txtUltimoLoginUsuario).setVisibility(View.INVISIBLE);

                if (bundle.getString("nombre") != null && bundle.getString("nombre").length() > 0)
                    nombre.setText(bundle.getString("nombre"));
                else
                    nombre.setText("Indefinido");

                if (bundle.getString("apellido") != null && bundle.getString("apellido").length() > 0)
                    apellido.setText(bundle.getString("apellido"));
                else
                    apellido.setText("Indefinido");

                if (Integer.toString(bundle.getInt("matricula")) != null &&
                        Integer.toString(bundle.getInt("matricula")).length() > 0)
                    matricula.setText(Integer.toString(bundle.getInt("matricula")));
                else
                    matricula.setText("Indefinido");

                if (bundle.getString("hospital") != null && bundle.getString("hospital").length() > 0)
                    hospital.setText(bundle.getString("hospital"));
                else
                    hospital.setText("Indefinido");

                if (bundle.getInt("credencial") != 0)
                    credencial.setText(String.valueOf(bundle.getInt("credencial")));
                else
                    credencial.setText("Indefinido");
            }

        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editarONuevo == 0) {
                    editarUsuario(new String[]{String.valueOf(bundle.getInt("idUsuario")),
                            bundle.getString("nombre"),
                            bundle.getString("apellido"),
                            String.valueOf(bundle.getInt("matricula")),
                            bundle.getString("hospital"),
                            String.valueOf(bundle.getInt("credencial"))});
                } else {
                    listoParaAgregar = false;

                    insertarUsuario(new String[]{nombre.getText().toString(),
                            apellido.getText().toString(),
                            matricula.getText().toString(),
                            hospital.getText().toString(),
                            credencial.getText().toString()});
                }
            }
        });

        return view;
    }

    /*********************************************************************************************/
    /******************************************CONSULTAS******************************************/
    /*********************************************************************************************/

    private void insertarUsuario(String[] params){
        new InsertUsuario().execute(params[0], params[1], params[2], params[3], params[4]);
    }

    private void editarUsuario(String[] params){
        new EditUsuario().execute(params[0], params[1], params[2], params[3], params[4], params[5]);
    }

    private class InsertUsuario extends AsyncTask<String, Void, String> {

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
            } else if (datos.equals("error2")) {
                // El token está mal, asi que a borrarloo y que vuelva al inicio
                //Toast.makeText(getActivity(), "Sesión expirada, vuelve a iniciar sesion.", Toast.LENGTH_SHORT).show();
                Sneaker.with(getActivity())
                        .setTitle("¡Error de sesión! :(")
                        .setHeight(95)
                        .setDuration(4000)
                        .setMessage("La sesión ha expirado, vuelva a iniciar sesión.")
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
                        (R.id.fragment_container, new fragmentUsuarios(), "usuariosTag").commit();
                getActivity().getFragmentManager().beginTransaction().addToBackStack("usuariosTag");
                getActivity().setTitle("Usuarios");
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
                            .addFormDataPart("usuario", parametros[0].toLowerCase() + "." + parametros[1].toLowerCase())
                            .addFormDataPart("contrasenia", parametros[1].toLowerCase() + "." + parametros[0].toLowerCase())
                            .addFormDataPart("matricula", parametros[2])
                            .addFormDataPart("ultimaSesion", (new Date()).toString())
                            .addFormDataPart("hospital", parametros[3])
                            .addFormDataPart("idCredencial", parametros[4])
                            .build();

                    listoParaAgregar = true;

                    Request request = new Request.Builder()
                            .url("http://192.99.56.223/basedd/insertUsuario.php")
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

    private class EditUsuario extends  AsyncTask<String, Void, String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("error")) {
                //Toast.makeText(getActivity(), "Comprueba tu conexión a Internet", Toast.LENGTH_SHORT).show();
                Sneaker.with(getActivity())
                        .setTitle("¡Error de conexión! :(")
                        .setDuration(4000)
                        .setHeight(95)
                        .setMessage("Error al contactar con el servidor, comprueba tu conexión a internet.")
                        .setIcon(R.drawable.ic_cloud_off)
                        .sneak(R.color.red);
            } else if (s.equals("error2")) {
                // El token está mal, asi que a borrarloo y que vuelva al inicio
                //Toast.makeText(getActivity(), "Sesión expirada, vuelve a iniciar sesion.", Toast.LENGTH_SHORT).show();
                Sneaker.with(getActivity())
                        .setTitle("¡Error de sesión! :(")
                        .setHeight(95)
                        .setDuration(4000)
                        .setMessage("La sesión ha expirado, vuelva a iniciar sesión.")
                        .setIcon(R.drawable.ic_cloud_off)
                        .sneak(R.color.red);
            } else if (s.equals("faltanDatos")) {
                Sneaker.with(getActivity())
                        .setTitle("¡No se puede agregar el usuario! :(")
                        .setHeight(77)
                        .setDuration(3000)
                        .setMessage("Todos los campos son obligatorios.")
                        .sneakError();
            } else {
                //ok
                Sneaker.with(getActivity())
                        .setTitle("¡Usuario modificado!")
                        .setHeight(77)
                        .setDuration(3000)
                        .setMessage("Se ha modificado el usuario correctamente.")
                        .setIcon(R.drawable.ic_cloud_done)
                        .sneak(R.color.colorAccent);
            }

            //if (listoParaAgregar) {
                getActivity().getFragmentManager().beginTransaction().replace
                        (R.id.fragment_container, new fragmentUsuarios(), "usuariosTag").commit();
                getActivity().getFragmentManager().beginTransaction().addToBackStack("usuariosTag");
                getActivity().setTitle("Usuarios");
            //}
        }

        @Override
        protected String doInBackground(String... params) {
            if (new HttpHandler(getActivity()).hasActiveInternetConnection()) {
                OkHttpClient client = new OkHttpClient();
                client.newBuilder().connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS).build();

                if (params[0].trim().length() > 0 && params[1].trim().length() > 0 &&
                        params[2].trim().length() > 0 && params[3].trim().length() > 0 &&
                        params[4].trim().length() > 0) {


                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("idUsuario", params[0])
                            .addFormDataPart("nombre", params[1])
                            .addFormDataPart("apellido", params[2])
                            .addFormDataPart("usuario", params[1].toLowerCase() + "." + params[2].toLowerCase())
                            .addFormDataPart("contrasenia", params[2].toLowerCase() + "." + params[1].toLowerCase())
                            .addFormDataPart("ultimaSesion", (new Date()).toString())
                            .addFormDataPart("hospital", params[4])
                            .addFormDataPart("idCredencial", params[5])
                            .build();

                    listoParaAgregar = true;

                    Request request = new Request.Builder()
                            .url("http://192.99.56.223/basedd/updateUsuario.php")
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
