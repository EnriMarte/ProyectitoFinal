package afinal.proyecto.proyectofinaldemojunio;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;

import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentMenuPrincipal;

/*
 BAJAR TECLADO (USAR AL CREAR NUEVO X)

 public void cerrarTeclado()
    {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }*/

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentMenuPrincipal fragmentPrincipal = new fragmentMenuPrincipal();
        fragmentTransaction.replace(R.id.fragment_container, fragmentPrincipal, "menuPrincipalTag");
        fragmentTransaction.commit();


        //todo: terminar diseño menuPrincipal (posiblemente despues del login)
        //todo: armar clase, fragment y vista "login"
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                getFragmentManager().popBackStackImmediate();

                if (getFragmentManager().findFragmentByTag("menuPrincipalTag") != null &&
                        getFragmentManager().findFragmentByTag("menuPrincipalTag").isVisible() &&
                        getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
