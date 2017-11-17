package afinal.proyecto.proyectofinaldemojunio.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.irozon.sneaker.Sneaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
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

import static android.content.ContentValues.TAG;

public class fragmentLaboratorio extends Fragment{
    boolean listoParaAgregar;
    ProgressDialog pDialog;
    MainActivity ma;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.laboratorio, container, false);

        ma = (MainActivity) getActivity();

        if (ma.isDebeRecibirRespuesta()){
            ma.setDebeRecibirRespuesta(false);
            new RecibirResultado().execute();
        }

        Button button = (Button)view.findViewById(R.id.botonProcesar);
        MainActivity myParent = (MainActivity)getActivity();
        final Map<String,String> detectadoOCR = myParent.getVarsOCR();

        TextView ph = (TextView) view.findViewById(R.id.txtphlaboratorio);
        TextView pco2 = (TextView) view.findViewById(R.id.txtpco2laboratorio);
        TextView hco3 = (TextView) view.findViewById(R.id.txthco3laboratorio);
        TextView cl = (TextView) view.findViewById(R.id.txtcllaboratorio);
        TextView na = (TextView) view.findViewById(R.id.txtnalaboratorio);
        final EditText edPh = (EditText) view.findViewById(R.id.editTextPh);
        final EditText edPCO2 = (EditText) view.findViewById(R.id.editTextpco2);
        final EditText edHCO3 = (EditText) view.findViewById(R.id.editTextHco3);
        final EditText edCL = (EditText) view.findViewById(R.id.editTextCl);
        final EditText edNA = (EditText) view.findViewById(R.id.editTextNa);

        if (detectadoOCR != null && detectadoOCR.size() > 0){
            Iterator it = detectadoOCR.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry dato = (Map.Entry)it.next();

                if (dato.getKey().toString().contains
                        (ph.getText().toString().toLowerCase())) {
                    edPh.setText(dato.getValue().toString());
                } else if (dato.getKey().toString().contains
                        (pco2.getText().toString().toLowerCase())) {
                    edPCO2.setText(dato.getValue().toString());
                } else if (dato.getKey().toString().contains
                        (hco3.getText().toString().toLowerCase())) {
                    edHCO3.setText(dato.getValue().toString());
                } else if (dato.getKey().toString().contains
                        (cl.getText().toString().toLowerCase())) {
                    edCL.setText(dato.getValue().toString());
                } else if(dato.getKey().toString().contains
                        (na.getText().toString().toLowerCase())) {
                    edNA.setText(dato.getValue().toString());
                }

                it.remove();
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edPh.getText().length() < 1 || edCL.getText().length() < 1 ||
                        edHCO3.getText().length() < 1 || edNA.getText().length() < 1 ||
                        edPCO2.getText().length() < 1){
                    Toast.makeText(getActivity(), "Complete todos los campos.", Toast.LENGTH_LONG).show();
                } else {
                    if (ma.getIdPacienteSelecc() > -1){
                        cargarADB(new Double[]{Double.valueOf(edPh.getText().toString()),
                                Double.valueOf(edPCO2.getText().toString()),
                                Double.valueOf(edHCO3.getText().toString()),
                                Double.valueOf(edCL.getText().toString()),
                                Double.valueOf(edNA.getText().toString()),
                                Double.valueOf(ma.getIdPacienteSelecc())});
                    } else {
                        Toast.makeText(getActivity(), "No hay un usuario seleccionado. Vaya a pestaña usuarios y seleccione uno.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuInflater menuInflater= getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.ocr_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_ocr:
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,
                                new fragmentOCR(), "OCRTag")
                        .addToBackStack("OCRTag").commit();
                return true;

            case android.R.id.home:
                getFragmentManager().popBackStackImmediate();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void cargarADB(Double[] params) {
        new CargarADB().execute(params[0], params[1], params[2], params[3], params[4], params[5]);
    }


    private class CargarADB extends AsyncTask<Double, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Espere por favor...");
            pDialog.setIndeterminate(false);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCancelable(false);
            pDialog.show();
        }

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
            }

            if (listoParaAgregar) {
                ma.setIdPacienteSelecc(-1);
                ma.setDebeRecibirRespuesta(true);

                new EvaluarResultado().execute();

                pDialog.dismiss();

                tabMenu tab = new tabMenu();
                tab.tabClickeado = 0;
                tab.llegaDesdeMenuPrincipal = true;
                getActivity().getFragmentManager().beginTransaction().replace
                        (R.id.bottomBar_container, tab, "menuContainer").commit();

                getActivity().setTitle("Laboratorio");
                getActivity().getFragmentManager().beginTransaction().replace
                        (R.id.fragment_container, new fragmentLaboratorio(), "laboTag").addToBackStack("laboTag").commit();
            }
        }

        @Override
        protected String doInBackground(Double... parametros) {

            if (new HttpHandler(getActivity()).hasActiveInternetConnection()) {
                OkHttpClient client = new OkHttpClient();
                client.newBuilder().connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS).build();

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("ph", String.valueOf(parametros[0]))
                        .addFormDataPart("pco2", String.valueOf(parametros[1]))
                        .addFormDataPart("hco3", String.valueOf(parametros[2]))
                        .addFormDataPart("cl", String.valueOf(parametros[3]))
                        .addFormDataPart("na", String.valueOf(parametros[4]))
                        .addFormDataPart("idPaciente", String.valueOf(parametros[5]))
                        .build();

                listoParaAgregar = true;

                Request request = new Request.Builder()
                        .url("http://192.99.56.223/basedd/Inserts/insertEstudio.php")
                        .method("POST", RequestBody.create(null, new byte[0]))
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    String resultado = response.body().string();
                    Log.d("resultadooo", resultado);
                    return "ok";
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

    private class EvaluarResultado extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();
            client.newBuilder().connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS).build();

            Request request = new Request.Builder()
                    .url("http://192.99.56.223/basedd/chekeador.php")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String resultado = response.body().string();
                Log.d("resultadooo", resultado);
            } catch (Exception e) {
                Log.d("Debug", e.getMessage());
                //mostrarError(e.getMessage()); // Error de Network
            }
            return null;
        }
    }

    private class RecibirResultado extends AsyncTask<Void, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Recuperando datos...");
            pDialog.setIndeterminate(false);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            if (s.contains("El paciente no")){
                alertDialogBuilder =
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Resultado del diagnostico")
                                .setMessage("El paciente no sufre de nada. El resultado fue guardado.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
            } else {
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    JSONObject jsonObj = jsonArray.getJSONObject(0);
                    String temp = jsonObj.getString("diagFinal");
                    temp = temp.substring(0,1).toUpperCase() + temp.substring(1);
                    temp.trim();
                    temp = temp.substring(0, temp.lastIndexOf(','));

                    alertDialogBuilder =
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Resultado del diagnostico")
                                    .setMessage(temp + ". El resultado fue guardado.")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                } catch (JSONException e){
                    Sneaker.with(getActivity())
                            .setTitle("¡Error!")
                            .setHeight(95)
                            .setDuration(4000)
                            .setMessage(e.getMessage())
                            .setIcon(R.drawable.ic_cloud_off)
                            .sneak(R.color.red);
                }
            }

            AlertDialog alertDialog = alertDialogBuilder.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler(getActivity());
            String jsonStr = "";

            if (sh.hasActiveInternetConnection())
                jsonStr = sh.makeServiceCall("http://192.99.56.223/basedd/recuperarUltimoResultado.php");

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null && jsonStr.length() > 0) {
                try {
                    if (jsonStr.contains(System.getProperty("line.separator"))){
                        jsonStr = jsonStr.replace(System.getProperty("line.separator"), "");
                    }
                } catch (final Exception e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            }
            return jsonStr;
        }
    }
}
