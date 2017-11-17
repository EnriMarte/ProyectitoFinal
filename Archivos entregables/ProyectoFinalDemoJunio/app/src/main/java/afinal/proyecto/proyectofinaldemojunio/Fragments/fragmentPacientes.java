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

import afinal.proyecto.proyectofinaldemojunio.Adapters.adapterPacientes;
import afinal.proyecto.proyectofinaldemojunio.DB.HttpHandler;
import afinal.proyecto.proyectofinaldemojunio.Fragments.Detalles.detallesPaciente;
import afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar.datosPacientes;
import afinal.proyecto.proyectofinaldemojunio.MainActivity;
import afinal.proyecto.proyectofinaldemojunio.Model.Paciente;
import afinal.proyecto.proyectofinaldemojunio.R;

import static android.content.ContentValues.TAG;

public class fragmentPacientes extends Fragment{

    public ArrayList<Paciente>listaPacientes;
    adapterPacientes ap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.abm, container, false);

        final ListView pacientes = (ListView)view.findViewById(R.id.listViewAbm);

        listaPacientes = new ArrayList<>();
        AsyncGetAllPacientes();
        ap = new adapterPacientes(getActivity(), this, listaPacientes);
        pacientes.setAdapter(ap);

        pacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Paciente p = listaPacientes.get(position);

                detallesPaciente detallesPacienteFragmento = new detallesPaciente();
                detallesPacienteFragmento.setPaciente(p);

                MainActivity ma = (MainActivity) getActivity();
                ma.setIdClickeadoEnVista(listaPacientes.get(position).getidPacientes());

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, detallesPacienteFragmento, "detallesPacienteTag");
                ft.addToBackStack("detallesPacienteTag");
                ft.commit();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.FABAbm);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                datosPacientes fr = new datosPacientes();
                fr.editarONuevo = 1;
                ft.replace(R.id.fragment_container, fr, "detallesPacientesTag");
                ft.addToBackStack("detallesPacientesTag");
                ft.commit();
            }
        });

        return view;
    }


    /*********************************************************************************************/
    /******************************************CONSULTAS******************************************/
    /*********************************************************************************************/


    public void AsyncGetAllPacientes() {
        new GetAllPacientes().execute();
    }


    ArrayList<Paciente> parseResultGSON(String resultado) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Paciente[] arr = gson.fromJson(resultado, Paciente[].class);
        return new ArrayList<>(Arrays.asList(arr));
    }

    private ProgressDialog pDialog;
    private static String url = "http://192.99.56.223/basedd/Selects/traePacientes.php";

    private class GetAllPacientes extends AsyncTask<Void, Void, ArrayList<Paciente>> {
        private ArrayList<Paciente>listaTemp;

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
        protected ArrayList<Paciente> doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler(getActivity());
            String jsonStr;

            if (sh.makeServiceCall(url) != null)
                jsonStr = sh.makeServiceCall(url);
            else
                return null;

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    listaTemp = parseResultGSON(jsonStr);
                } catch (final Exception e) {
                    Log.e(TAG, "Json parsing error: "
                            + e.getMessage());
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
        protected void onPostExecute(ArrayList<Paciente> result) {
            super.onPostExecute(result);
            if (pDialog.isShowing()){
                pDialog.dismiss();
            }
            listaPacientes = listaTemp;

            if (listaPacientes != null) {
                ap.notifyDataChanged(listaPacientes);
            } else {
                Sneaker.with(getActivity())
                        .setTitle("¡Error! :(")
                        .setDuration(5000)
                        .setHeight(77)
                        .setMessage("Hubo un error al recuperar los pacientes.")
                        .sneakError();
            }
        }
    }
}
