package afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import afinal.proyecto.proyectofinaldemojunio.Adapters.adapterFunciones;
import afinal.proyecto.proyectofinaldemojunio.Adapters.adapterSelectFuncDiagnosticos;
import afinal.proyecto.proyectofinaldemojunio.DB.HttpHandler;
import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentFunciones;
import afinal.proyecto.proyectofinaldemojunio.MainActivity;
import afinal.proyecto.proyectofinaldemojunio.Model.Funcion;
import afinal.proyecto.proyectofinaldemojunio.R;

import static android.content.ContentValues.TAG;

/**
 * Created by ianfr on 18/08/2017.
 */

public class datosDiagnosticos extends Fragment {

    public ArrayList<Funcion> listaFunciones;
    adapterSelectFuncDiagnosticos af;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.datos_diagnosticos, container, false);

        final ListView funciones = (ListView)view.findViewById(R.id.list_funciones);

        listaFunciones = new ArrayList<>();
        AsyncGetAllFunciones();
        af = new adapterSelectFuncDiagnosticos(getActivity(), this, listaFunciones);
        funciones.setAdapter(af);
        final EditText nombre = (EditText) view.findViewById(R.id.nombreDiagnostico);

        view.findViewById(R.id.botonNuevoDiagnostico).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View childView;
                ArrayList<String> funcionesUsadas = new ArrayList<String>();

                for (int i = 0; i < listaFunciones.size(); i++) {
                    childView = funciones.getChildAt(i);

                    if (childView != null) {
                        CheckBox checkBox = (CheckBox) childView.findViewById(R.id.checkboxListRow);
                        if (checkBox.isChecked()) {
                            TextView textView = (TextView) childView.findViewById(R.id.listTextView2);
                            funcionesUsadas.add(textView.getText().toString());
                        }
                    }
                }

                if (funcionesUsadas.size() > 0) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.setVariablesFunciones(funcionesUsadas);

                    if (nombre.length() > 0) {
                        mainActivity.setNombreFuncion(nombre.getText().toString());

                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new condicionesDiagnosticos(), "condicionesDiagnosticos")
                                .addToBackStack("condicionesDiagnosticos").commit();
                    } else {
                        Toast.makeText(getActivity(), "Complete el nombre", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Seleccione al menos una función", Toast.LENGTH_LONG).show();
                }
            }
        });

        MainActivity mainActivity = (MainActivity)getActivity();

        if (mainActivity.isEditandoDiag()){
            nombre.setText(mainActivity.getDiagnosticosEditando().getNombre());
        }

        return view;
    }

    public void AsyncGetAllFunciones() { new GetAllFunciones().execute(); }


    ArrayList<Funcion> parseResultGSON(String resultado) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Funcion[] arr = gson.fromJson(resultado, Funcion[].class);
        return new ArrayList<>(Arrays.asList(arr));
    }

    private ProgressDialog pDialog;
    private static String url = "http://192.99.56.223/basedd/Selects/traeFunciones.php";

    private class GetAllFunciones extends AsyncTask<Void, Void, ArrayList<Funcion>> {
        private ArrayList<Funcion>listaTemp;

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

        @Override
        protected ArrayList<Funcion> doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler(getActivity());
            String jsonStr = "";

            if (sh.hasActiveInternetConnection())
                jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null && jsonStr.length() > 0) {
                try {
                    if (jsonStr.contains(System.getProperty("line.separator"))){
                        jsonStr = jsonStr.replace(System.getProperty("line.separator"), "");
                    }
                    listaTemp = parseResultGSON(jsonStr);
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
            return listaTemp;
        }
        @Override
        protected void onPostExecute(ArrayList<Funcion> result) {
            super.onPostExecute(result);
            if (pDialog.isShowing()){
                pDialog.dismiss();
            }
            listaFunciones = listaTemp;
            if (listaFunciones != null){
                af.notifyDataChanged(listaFunciones);
            } else {
                Sneaker.with(getActivity())
                        .setTitle("¡Error! :(")
                        .setDuration(5000)
                        .setHeight(77)
                        .setMessage("Hubo un error al recuperar las funciones.")
                        .sneakError();
            }
        }
    }
}
