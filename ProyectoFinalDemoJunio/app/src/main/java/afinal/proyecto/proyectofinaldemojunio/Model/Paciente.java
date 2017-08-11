package afinal.proyecto.proyectofinaldemojunio.Model;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.zip.Inflater;

import afinal.proyecto.proyectofinaldemojunio.Adapters.adapterPacientes;
import afinal.proyecto.proyectofinaldemojunio.DB.HttpHandler;
import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentPacientes;
import afinal.proyecto.proyectofinaldemojunio.MainActivity;
import afinal.proyecto.proyectofinaldemojunio.R;

import static android.content.ContentValues.TAG;

/**
 * Created by PC on 7/6/2017.
 */

public class Paciente {

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public Integer getEdad() {
        return edad;
    }
    public void setEdad(Integer edad) {
        this.edad = edad;
    }
    public String getMedicoCabecera() {
        return medicoCabecera;
    }
    public void setMedicoCabecera(String medicoCabecera) {
        this.medicoCabecera = medicoCabecera;
    }
    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    public double getAltura() {
        return altura;
    }
    public void setAltura(double altura) {
        this.altura = altura;
    }
    public Double getPeso() {
        return peso;
    }
    public void setPeso(Double peso) {
        this.peso = peso;
    }
    public String getUltimoEstudio() { return ultimoEstudio; }
    public void setUltimoEstudio(String ultimoEstudio) {
        this.ultimoEstudio = ultimoEstudio;
    }
    public String getTipoSangre() {
        return tipoSangre;
    }
    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }
    public int getidPacientes() {
        return idPacientes;
    }
    public void setIdPacientes(int idPacientes) { this.idPacientes = idPacientes; }
    //public Drawable getImagen() {return imagen;}
   // public void setImagen(Drawable imagen) {
   //     this.imagen = imagen;
    //}

    int idPacientes;
    String nombre;
    String apellido;
    Integer edad;
    String medicoCabecera;
    String sexo;
    double altura;
    double peso;
    String ultimoEstudio;
    String tipoSangre;
   // Drawable imagen;
    String graf;
}
