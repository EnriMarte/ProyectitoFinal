package afinal.proyecto.proyectofinaldemojunio;


import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentMenuPrincipal;
import afinal.proyecto.proyectofinaldemojunio.Model.Diagnosticos;
import afinal.proyecto.proyectofinaldemojunio.Model.Funcion;

public class MainActivity extends AppCompatActivity {

    int idPacienteSelecc = -1, idClickeadoEnVista;

    boolean debeRecibirRespuesta = false;
    boolean editandoFuncion = false;
    boolean editandoDiag = false;
    ArrayList<String> variablesFunciones;
    String variableEditar;
    String funcion;
    String nombreFuncion;
    Map<String,String> variablesCondicionesFuncion = new HashMap<>();
    Map<String,String> varsOCR = new HashMap<>();
    Funcion funcionEditando;
    Diagnosticos diagnosticosEditando;

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

    public ArrayList<String> getVariablesFunciones(){
        return variablesFunciones;
    }

    public void setVariablesFunciones(ArrayList<String> list){
        variablesFunciones = list;
    }

    public String getVariableEditar() {
        return variableEditar;
    }

    public void setVariableEditar(String variableEditar) {
        this.variableEditar = variableEditar;
    }

    public Map<String, String> getVariablesCondicionesFuncion() {
        return variablesCondicionesFuncion;
    }

    public void addCondición(String var, String condiciones) {
        this.variablesCondicionesFuncion.put(var, condiciones);
    }

    public Map<String, String> getVarsOCR() {
        return varsOCR;
    }

    public void setVarsOCR(Map<String, String> varsOCR) {
        this.varsOCR = varsOCR;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public String getNombreFuncion() {
        return nombreFuncion;
    }

    public void setNombreFuncion(String nombreFuncion) {
        this.nombreFuncion = nombreFuncion;
    }

    public int getIdPacienteSelecc() {
        return idPacienteSelecc;
    }

    public void setIdPacienteSelecc(int idPacienteSelecc) {
        this.idPacienteSelecc = idPacienteSelecc;
    }

    public int getIdClickeadoEnVista() {
        return idClickeadoEnVista;
    }

    public void setIdClickeadoEnVista(int idClickeadoEnVista) {
        this.idClickeadoEnVista = idClickeadoEnVista;
    }

    public boolean isDebeRecibirRespuesta() {
        return debeRecibirRespuesta;
    }

    public void setDebeRecibirRespuesta(boolean debeRecibirRespuesta) {
        this.debeRecibirRespuesta = debeRecibirRespuesta;
    }

    public boolean isEditandoFuncion() {
        return editandoFuncion;
    }

    public void setEditandoFuncion(boolean editandoFuncion) {
        this.editandoFuncion = editandoFuncion;
    }

    public boolean isEditandoDiag() {
        return editandoDiag;
    }

    public void setEditandoDiag(boolean editandoDiag) {
        this.editandoDiag = editandoDiag;
    }

    public Funcion getFuncionEditando() {
        return funcionEditando;
    }

    public void setFuncionEditando(Funcion funcionEditando) {
        this.funcionEditando = funcionEditando;
    }

    public Diagnosticos getDiagnosticosEditando() {
        return diagnosticosEditando;
    }

    public void setDiagnosticosEditando(Diagnosticos diagnosticosEditando) {
        this.diagnosticosEditando = diagnosticosEditando;
    }
}
