package com.example.ianfr.proyectofinal_android;

import android.graphics.drawable.Drawable;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ianfr on 04/05/2017.
 */

public class Pacientes {
    public String nombre;
    public String apellido;
    public Integer edad;
    public String medicoCabecera;
    public String sexo;
    public double altura;
    public int peso;
    public String ultimoEstudio;
    public Drawable imagen;

    public String getNombre()
    {
        return nombre;
    }

    public String getApellido()
    {
        return apellido;
    }

    public String getDoctor()
    {
        return medicoCabecera;
    }
}
