package afinal.proyecto.proyectofinaldemojunio.Fragments.Detalles;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.Toolbar;

import com.irozon.sneaker.Sneaker;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import afinal.proyecto.proyectofinaldemojunio.DB.HttpHandler;
import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentLaboratorio;
import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentOCR;
import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentPacientes;
import afinal.proyecto.proyectofinaldemojunio.MainActivity;
import afinal.proyecto.proyectofinaldemojunio.Model.Paciente;
import afinal.proyecto.proyectofinaldemojunio.R;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by PC on 7/6/2017.
 */

public class detallesPaciente extends Fragment {
    Paciente paciente;
    boolean editando = false, listoParaAgregar = false;

    public Paciente getPaciente() {
        return paciente;
    }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    public boolean isEditando() {
        return editando;
    }
    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.datos_pacientes, container, false);

        final EditText nombre = (EditText) view.findViewById(R.id.editarNombrePaciente);
        final EditText apellido = (EditText) view.findViewById(R.id.editarApellidoPaciente);
        final EditText sexo = (EditText) view.findViewById(R.id.editarSexoPaciente);
        final EditText altura = (EditText) view.findViewById(R.id.editarAlturaPaciente);
        final EditText edad = (EditText) view.findViewById(R.id.editarEdadPaciente);
        final EditText peso = (EditText) view.findViewById(R.id.editarPesoPaciente);
        final EditText tipo = (EditText) view.findViewById(R.id.editarTipoSangre);
        final TextView fecha = (TextView) view.findViewById(R.id.txtUltimoEstudioFecha);

        if(!editando){
            nombre.setFocusable(false);
            nombre.setFocusableInTouchMode(false);
            nombre.setClickable(false);
            nombre.setText(paciente.getNombre());

            apellido.setFocusable(false);
            apellido.setFocusableInTouchMode(false);
            apellido.setClickable(false);
            apellido.setText(paciente.getApellido());

            sexo.setFocusable(false);
            sexo.setFocusableInTouchMode(false);
            sexo.setClickable(false);
            sexo.setText(paciente.getSexo());

            altura.setFocusable(false);
            altura.setFocusableInTouchMode(false);
            altura.setClickable(false);
            altura.setText(String.valueOf(paciente.getAltura()));

            edad.setFocusable(false);
            edad.setFocusableInTouchMode(false);
            edad.setClickable(false);
            edad.setText(String.valueOf(paciente.getEdad()));

            peso.setFocusable(false);
            peso.setFocusableInTouchMode(false);
            peso.setClickable(false);
            peso.setText(String.valueOf(paciente.getPeso()));

            peso.setFocusable(false);
            peso.setFocusableInTouchMode(false);
            peso.setClickable(false);
            tipo.setText(String.valueOf(paciente.getTipoSangre()));

            view.findViewById(R.id.botonNuevoPaciente).setVisibility(View.INVISIBLE);

            fecha.setText(String.valueOf(paciente.getUltimoEstudio()));

            setHasOptionsMenu(true);
        } else {
            setHasOptionsMenu(false);
            nombre.setText(paciente.getNombre());
            apellido.setText(paciente.getApellido());
            sexo.setText(String.valueOf(paciente.getSexo()));
            altura.setText(String.valueOf(paciente.getAltura()));
            edad.setText(String.valueOf(paciente.getEdad()));
            peso.setText(String.valueOf(paciente.getPeso()));
            tipo.setText(String.valueOf(paciente.getTipoSangre()));
            view.findViewById(R.id.txtUltimoEstudio).setVisibility(View.INVISIBLE);
            fecha.setVisibility(View.INVISIBLE);

            Button button = (Button) view.findViewById(R.id.botonNuevoPaciente);
            button.setText("Guardar cambios");

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        new EditPaciente().execute(new String[]{
                                String.valueOf(paciente.getidPacientes()),
                                nombre.getText().toString(), apellido.getText().toString(),
                                sexo.getText().toString(),
                                String.valueOf(altura.getText().toString()), String.valueOf(paciente.getEdad()),
                                String.valueOf(paciente.getPeso()), tipo.getText().toString(),
                                paciente.getMedicoCabecera(), paciente.getUltimoEstudio()});
                    } catch (Exception e){
                        Sneaker.with(getActivity())
                                .setTitle("¡Error inesperado")
                                .setDuration(4000)
                                .setHeight(95)
                                .setMessage(e.getMessage())
                                .setIcon(R.drawable.ic_cloud_off)
                                .sneak(R.color.red);
                    }
                }
            });
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuInflater menuInflater= getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.nuevo_labo_menu, menu);
        menuInflater.inflate(R.menu.edit_delete_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.nuevo_labo:
                MainActivity ma = (MainActivity)getActivity();
                ma.setIdPacienteSelecc(ma.getIdClickeadoEnVista());
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,
                                new fragmentLaboratorio(), "fragmentLaboratorio")
                        .addToBackStack("fragmentLaboratorio").commit();
                return true;

            case R.id.action_delete:
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("¿Está seguro que desea eliminar el paciente? Esta acción no se puede deshacer.");
                alertDialog.setCancelable(true);
                alertDialog.setTitle("Atención");
                alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new deletePaciente().execute(String.valueOf(paciente.getidPacientes()));
                        getFragmentManager().beginTransaction().addToBackStack("fragmentPacientesTag")
                                .replace(R.id.fragment_container, new fragmentPacientes(), "fragmentPacientesTag").commit();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }});
                AlertDialog alertLogin = alertDialog.create();
                alertLogin.show();
                return true;

            case R.id.action_edit:
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                detallesPaciente fragment = new detallesPaciente();
                fragment.setPaciente(paciente);
                fragment.setEditando(true);

                ft.replace(R.id.fragment_container, fragment, "detallesPacientesTag");
                ft.addToBackStack("detallesPacientesTag");
                ft.commit();
                return true;

            case android.R.id.home:
                getFragmentManager().popBackStackImmediate();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class deletePaciente extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s.equals("error")){
                Sneaker.with(getActivity())
                        .setTitle("¡Error de conexión! :(")
                        .setDuration(4000)
                        .setMessage("Error al contactar con el servidor, comprueba tu conexión a internet.")
                        .setIcon(R.drawable.ic_cloud_off)
                        .sneak(R.color.red);
            } else {
                Sneaker.with(getActivity())
                        .setTitle("¡Paciente eliminado!")
                        .setDuration(3000)
                        .setMessage("Se ha eliminado el paciente correctamente.")
                        .setIcon(R.drawable.ic_cloud_done)
                        .sneak(R.color.colorAccent);
            }
        }

        @Override
        protected String doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();
            client.newBuilder().connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS).build();

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("id", params[0])
                    .build();

            Request request = new Request.Builder()
                    .url("http://192.99.56.223/basedd/Deletes/deletePaciente.php")
                    .method("POST", RequestBody.create(null, new byte[0]))
                    .post(requestBody)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String resultado = response.body().string();
                return resultado;
            } catch (Exception e) {
                Log.d("Debug", e.getMessage());
                return "error";
            }
        }
    }
    private class EditPaciente extends  AsyncTask<String, Void, String>{
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
                    (R.id.fragment_container, new fragmentPacientes(), "pacientesTag").commit();
            getActivity().getFragmentManager().beginTransaction().addToBackStack("pacientesTag");
            getActivity().setTitle("Pacientes");
            //}
        }

        @Override
        protected String doInBackground(String... params) {
            if (new HttpHandler(getActivity()).hasActiveInternetConnection()) {
                OkHttpClient client = new OkHttpClient();
                client.newBuilder().connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS).build();

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("idPacientes", params[0])
                        .addFormDataPart("nombre", params[1])
                        .addFormDataPart("apellido", params[2])
                        .addFormDataPart("sexo", params[3])
                        .addFormDataPart("altura", params[4])
                        .addFormDataPart("edad", params[5])
                        .addFormDataPart("peso", params[6])
                        .addFormDataPart("tipoSangre", params[7])
                        .addFormDataPart("medicoCabecera", params[8])
                        .addFormDataPart("ultimoEstudio", String.valueOf(new Date()))
                        .build();

                listoParaAgregar = true;

                Request request = new Request.Builder()
                        .url("http://192.99.56.223/basedd/Updates/updatePacientes.php")
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
