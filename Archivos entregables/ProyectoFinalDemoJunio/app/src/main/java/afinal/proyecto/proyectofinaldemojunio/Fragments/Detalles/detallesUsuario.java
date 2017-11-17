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
import android.widget.Toast;

import com.irozon.sneaker.Sneaker;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import afinal.proyecto.proyectofinaldemojunio.DB.HttpHandler;
import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentUsuarios;
import afinal.proyecto.proyectofinaldemojunio.Model.Usuario;
import afinal.proyecto.proyectofinaldemojunio.R;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 42567321 on 9/6/2017.
 */

public class detallesUsuario extends Fragment{
    Usuario usuario;
    boolean editando = false, listoParaAgregar = false;

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        View view = inflater.inflate(R.layout.datos_usuarios, container, false);

        final EditText nombre = (EditText) view.findViewById(R.id.editarNombreUsuario);
        final EditText apellido = (EditText) view.findViewById(R.id.editarApellidoUsuario);
        final EditText matricula = (EditText) view.findViewById(R.id.editarMatriculaUsuario);
        final EditText hospital = (EditText) view.findViewById(R.id.editarHospitalUsuario);
        final EditText credencial = (EditText) view.findViewById(R.id.editarCredencialUsuario);
        final TextView fecha = (TextView) view.findViewById(R.id.txtUltimoLoginUsuario);

        if (!editando) {
            try {
                nombre.setFocusable(false);
                nombre.setFocusableInTouchMode(false);
                nombre.setClickable(false);
                nombre.setText(usuario.getNombre());

                apellido.setFocusable(false);
                apellido.setFocusableInTouchMode(false);
                apellido.setClickable(false);
                apellido.setText(usuario.getApellido());

                matricula.setFocusable(false);
                matricula.setFocusableInTouchMode(false);
                matricula.setClickable(false);
                matricula.setText(String.valueOf(usuario.getMatricula()));

                hospital.setFocusable(false);
                hospital.setFocusableInTouchMode(false);
                hospital.setClickable(false);
                hospital.setText(usuario.getHospital());

                credencial.setFocusable(false);
                credencial.setFocusableInTouchMode(false);
                credencial.setClickable(false);
                credencial.setText(String.valueOf(usuario.getIdCredencial()));

                view.findViewById(R.id.botonNuevoUsuario).setVisibility(View.INVISIBLE);

                fecha.setText(String.valueOf(usuario.getUltimaSesion()));
            } catch (Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

            setHasOptionsMenu(true);
        } else {
            nombre.setText(usuario.getNombre());
            apellido.setText(usuario.getApellido());
            matricula.setText(String.valueOf(usuario.getMatricula()));
            hospital.setText(usuario.getHospital());
            credencial.setText(String.valueOf(usuario.getIdCredencial()));
            fecha.setVisibility(View.INVISIBLE);

            Button button = (Button) view.findViewById(R.id.botonNuevoUsuario);
            button.setText("Guardar cambios");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        new EditUsuario().execute(new String[]{
                                String.valueOf(usuario.getIdUsuario()),
                                nombre.getText().toString(), hospital.getText().toString(),
                                apellido.getText().toString(), usuario.getUsuario(),
                                usuario.getContrasenia(), String.valueOf(usuario.getUltimaSesion()),
                                matricula.getText().toString(), credencial.getText().toString()});
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
        menuInflater.inflate(R.menu.edit_delete_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("¿Está seguro que desea eliminar el usuario? Esta acción no se puede deshacer.");
                alertDialog.setCancelable(true);
                alertDialog.setTitle("Atención");
                alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new deleteUsuario().execute(String.valueOf(usuario.getIdUsuario()));
                        getFragmentManager().beginTransaction().addToBackStack("fragmentUsuariosTag")
                                .replace(R.id.fragment_container, new fragmentUsuarios(), "fragmentUsuariosTag").commit();
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
                detallesUsuario fragment = new detallesUsuario();
                fragment.setUsuario(usuario);
                fragment.setEditando(true);

                ft.replace(R.id.fragment_container, fragment, "detallesUsuariosTag");
                ft.addToBackStack("detallesUsuariosTag");
                ft.commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private class deleteUsuario extends AsyncTask<String, Void, String> {
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
                        .setTitle("¡Usuario eliminado!")
                        .setDuration(3000)
                        .setMessage("Se ha eliminado el usuario correctamente.")
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
                    .url("http://192.99.56.223/basedd/Deletes/deleteUsuario.php")
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

                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("idUsuario", params[0])
                            .addFormDataPart("nombre", params[1])
                            .addFormDataPart("hospital", params[2])
                            .addFormDataPart("apellido", params[3])
                            .addFormDataPart("usuario", params[4])
                            .addFormDataPart("contrasenia", params[5])
                            .addFormDataPart("ultimaSesion", new Date().toString())
                            .addFormDataPart("matricula", params[7])
                            .addFormDataPart("idCredencial", params[8])
                            .build();

                    listoParaAgregar = true;

                    Request request = new Request.Builder()
                            .url("http://192.99.56.223/basedd/Updates/updateUsuario.php")
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
