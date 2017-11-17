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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import afinal.proyecto.proyectofinaldemojunio.DB.HttpHandler;
import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentConfirFunc;
import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentDiagnosticos;
import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentFunciones;
import afinal.proyecto.proyectofinaldemojunio.MainActivity;
import afinal.proyecto.proyectofinaldemojunio.R;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ianfr on 15/09/2017.
 */

public class fragmentConfirDiagnos  extends Fragment {
    boolean listoParaAgregar = false;

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

        if (myParent.isEditandoDiag()) {
            txtNombreFuncion.setText("Se editará el siguiente diagnóstico: " + nombreFuncion);
        }else {
            txtNombreFuncion.setText("Se agregará el siguiente diagnóstico: " + nombreFuncion);
        }
        txtFuncion.setText(funcion);

        String unaCondicion;
        String condicionesFinal = "return ";
        String condicionesConcatenadas = "";

        Iterator it = condiciones.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry dato = (Map.Entry)it.next();
            condicionesConcatenadas+=dato.getValue() + "\n";

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

        condicionesFinal+=";";

        txtVars.setText(condicionesConcatenadas);
        getActivity().setTitle("Confirmación");


        final String[] aux = new String[1];
        aux[0] = condicionesFinal;

        final ArrayList<String>temp = new ArrayList<>();
        temp.add(condicionesFinal);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity)getActivity();
                if (!mainActivity.isEditandoDiag()) {
                    insertarDiagnostico(new String[]{nombreFuncion, temp.get(0)});
                } else{
                    editarDiagnostico(new String[]{String.valueOf(mainActivity.getDiagnosticosEditando().getIdDiagnostico()),
                    nombreFuncion, temp.get(0)});
                }
            }
        });

        return view;
    }

    private void insertarDiagnostico(String[] params){
        new InsertarDiagnostico().execute(params[0], params[1]);
    }

    private void editarDiagnostico(String[] params){
        new EditDiagnostico().execute(params[0], params[1], params[2]);
    }

    private class InsertarDiagnostico extends AsyncTask<String, Void, String> {
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
                        .setTitle("¡Diagnóstico agregado!")
                        .setHeight(77)
                        .setDuration(3000)
                        .setMessage("Se ha agregado el diagnóstico correctamente.")
                        .setIcon(R.drawable.ic_cloud_done)
                        .sneak(R.color.colorAccent);

                getActivity().setTitle("Diagnósticos");
                getActivity().getFragmentManager().beginTransaction().replace
                        (R.id.fragment_container, new fragmentDiagnosticos(), "diagnosticosTag")
                        .addToBackStack("diagnosticosTag").commit();
            }
        }

        @Override
        protected String doInBackground(String... parametros) {
            OkHttpClient client = new OkHttpClient();
            client.newBuilder().connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS).build();

            int tPh, tPco2, tHco3, tCl, tNa;
            tPh = parametros[1].contains("ph") ? 1 : 0;
            tPco2 = parametros[1].contains("pco2") ? 1 : 0;
            tHco3 = parametros[1].contains("hco3") ? 1 : 0;
            tCl = parametros[1].contains("cl") ? 1 : 0;
            tNa = parametros[1].contains("na") ? 1 : 0;

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("nombre", parametros[0])
                    .addFormDataPart("condiciones", parametros[1])
                    .addFormDataPart("ph", String.valueOf(tPh))
                    .addFormDataPart("pco2", String.valueOf(tPco2))
                    .addFormDataPart("hco3", String.valueOf(tHco3))
                    .addFormDataPart("cl", String.valueOf(tCl))
                    .addFormDataPart("na", String.valueOf(tNa))
                    .build();

            Request request = new Request.Builder()
                    .url("http://192.99.56.223/basedd/Inserts/insertDiag.php")
                    .method("POST", RequestBody.create(null, new byte[0]))
                    .post(requestBody)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                //String resultado = response.body().string();
                return "ok";
            } catch (Exception e) {
                Log.d("Debug", e.getMessage());
                //mostrarError(e.getMessage()); // Error de Network
                return "error";
            }
        }
    }

    private class EditDiagnostico extends  AsyncTask<String, Void, String>{
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
                        .setTitle("¡Diagnóstico modificado!")
                        .setHeight(77)
                        .setDuration(3000)
                        .setMessage("Se ha modificado el diagnóstico correctamente.")
                        .setIcon(R.drawable.ic_cloud_done)
                        .sneak(R.color.colorAccent);
            }

            //if (listoParaAgregar) {
            getActivity().getFragmentManager().beginTransaction().replace
                    (R.id.fragment_container, new fragmentDiagnosticos(), "usuariosTag").commit();
            getActivity().getFragmentManager().beginTransaction().addToBackStack("usuariosTag");
            getActivity().setTitle("Diagnósticos");
            //}
        }

        @Override
        protected String doInBackground(String... params) {
            if (new HttpHandler(getActivity()).hasActiveInternetConnection()) {
                OkHttpClient client = new OkHttpClient();
                client.newBuilder().connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS).build();

                int tPh, tPco2, tHco3, tCl, tNa;
                tPh = params[2].contains("ph") ? 1 : 0;
                tPco2 = params[2].contains("pco2") ? 1 : 0;
                tHco3 = params[2].contains("hco3") ? 1 : 0;
                tCl = params[2].contains("cl") ? 1 : 0;
                tNa = params[2].contains("na") ? 1 : 0;

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("idDiagnostico", params[0])
                        .addFormDataPart("nombre", params[1])
                        .addFormDataPart("condiciones", params[2])
                        .addFormDataPart("ph", String.valueOf(tPh))
                        .addFormDataPart("pco2", String.valueOf(tPco2))
                        .addFormDataPart("hco3", String.valueOf(tHco3))
                        .addFormDataPart("cl", String.valueOf(tCl))
                        .addFormDataPart("na", String.valueOf(tNa))
                        .build();

                listoParaAgregar = true;
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.setEditandoDiag(false);

                Request request = new Request.Builder()
                        .url("http://192.99.56.223/basedd/Updates/updateDiagnosticos.php")
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
