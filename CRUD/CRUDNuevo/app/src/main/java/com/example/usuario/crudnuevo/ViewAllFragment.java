package com.example.usuario.crudnuevo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Usuario on 16/6/2017.
 */

public class ViewAllFragment extends DialogFragment implements View.OnClickListener {
    TablaAdapter tablaAdapter;
    public ViewAllFragment() {
        // Required empty public constructor
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_view_all, container, false);

        ListView lv = (ListView) v.findViewById(R.id.personList);
        tablaAdapter = new TablaAdapter(getContext(),new ArrayList<Tabla>());
        lv.setAdapter(tablaAdapter);

        String urlDeApi = "192.99.56.223/basedd/holamundo.php";
        new ConectarAPITask().execute(urlDeApi);
        return v;
    }
    private class ConectarAPITask extends AsyncTask<String, Void,  Tabla[]> {
        public final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        @Override
        protected Tabla[] doInBackground(String... params) {

            String urlApi = params[0];
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(urlApi)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                Tabla[] tablas = parsearResultado(response.body().string());
                return tablas;
            }
            catch (IOException e){
                Log.d("Error :", e.getMessage());
                return null;

            }

        }
        @Override
        protected void onPostExecute(Tabla[] tablas) {
            super.onPostExecute(tablas);
            tablaAdapter.setTablas(tablas);
            tablaAdapter.notifyDataSetChanged();

        }



        private Tabla[] parsearResultado(String respuesta)   {
            if (respuesta == null || respuesta.length()==0)
                return null;

            Log.d("Respuesta:", respuesta);

            try {
                Gson gson = new Gson();
                Tabla[] t = gson.fromJson(respuesta, Tabla[].class);
                //Log.d("Persona nombre:", p.getNombre());
                return t;

            }
            catch (Exception e) {
                Log.d("Error :", e.getMessage());
                return null;
            }

        }

    }

    @Override
    public void onClick(View v) {

    }
}
