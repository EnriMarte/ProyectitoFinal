package afinal.proyecto.proyectofinaldemojunio;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentMenuPrincipal;

public class MainActivity extends AppCompatActivity {

    public boolean onMenuPricipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onMenuPricipal = true;

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentMenuPrincipal fragmentPrincipal = new fragmentMenuPrincipal();
        fragmentTransaction.replace(R.id.fragment_container, fragmentPrincipal, "menuPrincipalTag");
        fragmentTransaction.commit();
        //todo: armar vistas detalles (paciente, usuario, credencial y funcion)
        //todo: armar vistas agregar nuevo (usuario, paciente, credencial y funcion)
        //todo: armar vista editar (usuario, paciente, credencial y funcion)
        //todo: terminar diseño menuPrincipal (posiblemente despues del login)
        //todo: armar clase, fragment y vista "login"
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //todo: arreglar el volver al fragment anterior (esconder actionbar button)
        switch (item.getItemId()) {
            case android.R.id.home:

                getFragmentManager().popBackStackImmediate();

                if ((getFragmentManager().findFragmentByTag("laboratorioTag") != null &&
                        getFragmentManager().findFragmentByTag("laboratorioTag").isVisible()) ||
                        (getFragmentManager().findFragmentByTag("pacientesTag") != null &&
                        getFragmentManager().findFragmentByTag("pacientesTag").isVisible()) ||
                        (getFragmentManager().findFragmentByTag("usuariosTag") != null &&
                        getFragmentManager().findFragmentByTag("usuariosTag").isVisible()) ||
                        (getFragmentManager().findFragmentByTag("credencialesTag") != null &&
                        getFragmentManager().findFragmentByTag("credencialesoTag").isVisible()) ||
                        (getFragmentManager().findFragmentByTag("laboratorioTag") != null &&
                        getFragmentManager().findFragmentByTag("funcionesTag").isVisible())) {
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    }
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
