package afinal.proyecto.proyectofinaldemojunio.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
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

import afinal.proyecto.proyectofinaldemojunio.Adapters.adapterCredenciales;
import afinal.proyecto.proyectofinaldemojunio.Adapters.adapterUsuarios;
import afinal.proyecto.proyectofinaldemojunio.DB.HttpHandler;
import afinal.proyecto.proyectofinaldemojunio.Fragments.Detalles.detallesCredencial;
import afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar.datosCredenciales;
import afinal.proyecto.proyectofinaldemojunio.Model.Credencial;
import afinal.proyecto.proyectofinaldemojunio.Model.Usuario;
import afinal.proyecto.proyectofinaldemojunio.R;

import static android.content.ContentValues.TAG;

public class fragmentCredenciales extends Fragment{

    public ArrayList<Credencial>listaCrendeciales;
    adapterCredenciales ac;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.abm, container, false);

        final ListView credenciales = (ListView)view.findViewById(R.id.listViewAbm);

        listaCrendeciales = new ArrayList<>();
        AsyncGetAllCredenciales();
        ac = new adapterCredenciales(getActivity(), this, listaCrendeciales);
        credenciales.setAdapter(ac);

        credenciales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Credencial p = listaCrendeciales.get(position);

                detallesCredencial detallesCredencialFragment = new detallesCredencial();
                detallesCredencialFragment.setCredencial(p);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, detallesCredencialFragment, "detallesCredencialTag");
                ft.commit();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.FABAbm);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "¡No habilitado!", Toast.LENGTH_SHORT).show();/*
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                datosCredenciales fr = new datosCredenciales();
                fr.editarONuevo = 1;
                ft.replace(R.id.fragment_container, fr, "datosCredencialesTag");
                ft.addToBackStack("datosCredencialesTag");
                ft.commit();*/
            }
        });

        return view;
    }

    public void AsyncGetAllCredenciales() { new GetAllCredenciales().execute(); }


    ArrayList<Credencial> parseResultGSON(String resultado) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Credencial[] arr = gson.fromJson(resultado, Credencial[].class);
        return new ArrayList<>(Arrays.asList(arr));
    }

    private ProgressDialog pDialog;
    private static String url = "http://192.99.56.223/basedd/traeCredenciales.php";

    private class GetAllCredenciales extends AsyncTask<Void, Void, ArrayList<Credencial>> {
        private ArrayList<Credencial>listaTemp;

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
        protected ArrayList<Credencial> doInBackground(Void... arg0) {
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
        protected void onPostExecute(ArrayList<Credencial> result) {
            super.onPostExecute(result);
            if (pDialog.isShowing()){
                pDialog.dismiss();
            }
            listaCrendeciales = listaTemp;
            if (listaCrendeciales != null){
                ac.notifyDataChanged(listaCrendeciales);
            } else {
                Sneaker.with(getActivity())
                        .setTitle("¡Error! :(")
                        .setDuration(5000)
                        .setHeight(77)
                        .setMessage("Hubo un error al recuperar las credenciales.")
                        .sneakError();
            }
        }
    }
}
