package ar.edu.ort.crud101;

import java.util.Date;

/**
 * Created by leandro on 5/18/17.
 */

public class Persona {
    private int Id;
    private String Nombre;
    private String FechaNac;
    private int Edad;
    private float Peso;
    private double Altura;
    private String Foto;
    private String GrupoSanguineo;
    private String Medico;
    private String ultimoestudio;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombre() {return Nombre;}
    public int getEdad() {return Edad; }
    public float getPeso(){return Peso;}
    public String getFoto(){return Foto;}
    public double getAltura(){return Altura;}
    public String getGrupoSanguineo(){return GrupoSanguineo;}
    public String getMedico(){return Medico;}
    public String getUltimoestudio(){return ultimoestudio;}

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getFechaNac() {
        return FechaNac;
    }

    public void setFechaNac(String fechaNac) {
        FechaNac = fechaNac;
    }
}
