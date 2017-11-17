package afinal.proyecto.proyectofinaldemojunio.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.Arrays;

import afinal.proyecto.proyectofinaldemojunio.Adapters.adapterDiagnosticos;
import afinal.proyecto.proyectofinaldemojunio.DB.HttpHandler;
import afinal.proyecto.proyectofinaldemojunio.Fragments.Detalles.detallesDiagnosticos;
import afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar.datosDiagnosticos;
import afinal.proyecto.proyectofinaldemojunio.Model.Diagnosticos;
import afinal.proyecto.proyectofinaldemojunio.R;

import static android.content.ContentValues.TAG;

/**
 * Created by ianfr on 18/08/2017.
 */

public class fragmentDiagnosticos extends Fragment {

    public ArrayList<Diagnosticos>listaDiagnosticos;
    adapterDiagnosticos ad;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.abm, container, false);

        final ListView diagnosticos = (ListView)view.findViewById(R.id.listViewAbm);
        listaDiagnosticos = new ArrayList<>();
        AsyncGetAllDiagnosticos();
        ad = new adapterDiagnosticos(getActivity(), this, listaDiagnosticos);
        diagnosticos.setAdapter(ad);

        diagnosticos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Diagnosticos d = listaDiagnosticos.get(position);

                detallesDiagnosticos detallesDiagnosticos = new detallesDiagnosticos();
                detallesDiagnosticos.setDiagnosticos(d);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, detallesDiagnosticos, "detallesUsuariosTag");
                ft.addToBackStack("detallesUsuariosTag");
                ft.commit();
            }
        });

        view.findViewById(R.id.FABAbm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new datosDiagnosticos(), "datosDiagnosticos").addToBackStack("datosDiagnosticos").commit();
            }
        });

        return view;
    }

    private void AsyncGetAllDiagnosticos(){
        new GetAllDiagnosticos().execute();
    }

    ArrayList<Diagnosticos> parseResultGSON(String resultado) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Diagnosticos[] arr = gson.fromJson(resultado, Diagnosticos[].class);
        return new ArrayList<>(Arrays.asList(arr));
    }



    ProgressDialog pDialog;
    private static String url = "http://192.99.56.223/basedd/Selects/traeDiagnosticos.php";
    private class GetAllDiagnosticos extends AsyncTask<Void, Void, ArrayList<Diagnosticos>> {
        private ArrayList<Diagnosticos>listaTemp;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Enanos laburando...");
            pDialog.setIndeterminate(false);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected ArrayList<Diagnosticos> doInBackground(Void... arg0) {
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
        protected void onPostExecute(ArrayList<Diagnosticos> result) {
            super.onPostExecute(result);
            if (pDialog.isShowing()){
                pDialog.dismiss();
            }
            listaDiagnosticos = listaTemp;
            if (listaDiagnosticos != null){
                ad.notifyDataChanged(listaDiagnosticos);
            } else {
                Sneaker.with(getActivity())
                        .setTitle("¡Error! :(")
                        .setDuration(5000)
                        .setHeight(77)
                        .setMessage("Hubo un error al recuperar los diagnosticos.")
                        .sneakError();
            }
        }
    }
}
