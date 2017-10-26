package afinal.proyecto.proyectofinaldemojunio.Fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.irozon.sneaker.Sneaker;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import afinal.proyecto.proyectofinaldemojunio.DB.HttpHandler;
import afinal.proyecto.proyectofinaldemojunio.MainActivity;
import afinal.proyecto.proyectofinaldemojunio.R;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ianfr on 08/08/2017.
 */

public class fragmentConfirFunc extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.confir_func, container, false);
        MainActivity myParent = (MainActivity) getActivity();
        final String nombreFuncion = myParent.getNombreFuncion();
        final String funcion = myParent.getFuncion();
        final Map<String, String> condiciones = myParent.getVariablesCondicionesFuncion();

        TextView txtNombreFuncion = (TextView) view.findViewById(R.id.confirmarNombreFuncion);
        TextView txtFuncion = (TextView) view.findViewById(R.id.confirmarFuncion);
        TextView txtVars = (TextView) view.findViewById(R.id.confirmarVariables);
        Button button = (Button) view.findViewById(R.id.botonConfirmarVariables);

        txtNombreFuncion.setText("Se agregará la siguiente función: " + nombreFuncion);
        txtFuncion.setText(funcion);

        String unaCondicion;
        String condicionesFinal = "";
        String condicionesConcatenadas = "";

        Iterator it = condiciones.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry dato = (Map.Entry)it.next();
            condicionesConcatenadas+=dato.getKey() + ": " + dato.getValue() + "\n";

            /* PARA LA DB */
            unaCondicion = dato.getValue().toString();
            unaCondicion = unaCondicion.replaceAll("AND", "&&");
            unaCondicion = unaCondicion.replaceAll("OR", "||");

            if (!it.hasNext()) {
                condicionesFinal += "(" + unaCondicion + ")";
            } else {
                condicionesFinal += "(" + unaCondicion + ") && ";
            }
            /* FIN PARA LA DB */

            it.remove();
        }

        txtVars.setText(condicionesConcatenadas);
        getActivity().setTitle("Confirmación");


        final String[] aux = new String[1];
        aux[0] = condicionesFinal;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            insertarFuncion(new String[]{nombreFuncion, funcion, aux[0]});
            }
        });

        return view;
    }

    private void insertarFuncion(String[] params){
        new InsertFuncion().execute(params[0], params[1], params[2]);
    }


    private class InsertFuncion extends AsyncTask<String, Void, String> {
        protected void onPostExecute(String datos) {
            super.onPostExecute(datos);

            if (datos.equals("error")) {
                Sneaker.with(getActivity())
                        .setTitle("¡Error de conexión! :(")
                        .setDuration(4000)
                        .setHeight(95)
                        .setMessage("Error al contactar con el servidor, comprueba tu conexión a internet.")
                        .setIcon(R.drawable.ic_cloud_off)
                        .sneak(R.color.red);
            } else if (datos.equals("error2")) {
                // El token está mal, asi que a borrarloo y que vuelva al inicio
                Sneaker.with(getActivity())
                        .setTitle("¡Error de sesión! :(")
                        .setHeight(95)
                        .setDuration(4000)
                        .setMessage("La sesión ha expirado, vuelva a iniciar sesión.")
                        .setIcon(R.drawable.ic_cloud_off)
                        .sneak(R.color.red);
            } else {
                //ok
                Sneaker.with(getActivity())
                        .setTitle("¡Función agregado!")
                        .setHeight(77)
                        .setDuration(3000)
                        .setMessage("Se ha agregado el usuario correctamente.")
                        .setIcon(R.drawable.ic_cloud_done)
                        .sneak(R.color.colorAccent);

                getActivity().setTitle("Funciones");
                getActivity().getFragmentManager().beginTransaction().replace
                        (R.id.fragment_container, new fragmentFunciones(), "funcionesTag")
                        .addToBackStack("funcionesTag").commit();
            }
        }

        @Override
        protected String doInBackground(String... parametros) {

            if (new HttpHandler(getActivity()).hasActiveInternetConnection()) {
                OkHttpClient client = new OkHttpClient();
                client.newBuilder().connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS).build();


                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("nombre", parametros[0])
                            .addFormDataPart("funcion", parametros[1])
                            .addFormDataPart("condiciones", parametros[2])
                            .build();

                    Request request = new Request.Builder()
                            .url("http://192.99.56.223/basedd/insertFunciones.php")
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
                return "error";
            }
        }
    }
}
