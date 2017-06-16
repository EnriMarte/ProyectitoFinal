package afinal.proyecto.proyectofinaldemojunio.Model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import afinal.proyecto.proyectofinaldemojunio.R;

/**
 * Created by PC on 7/6/2017.
 */

public class Paciente {
    public String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
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
    public int getPeso() {
        return peso;
    }
    public void setPeso(int peso) {
        this.peso = peso;
    }
    public String getUltimoEstudio() {
        return ultimoEstudio;
    }
    public void setUltimoEstudio(String ultimoEstudio) {
        this.ultimoEstudio = ultimoEstudio;
    }
    public Drawable getImagen() {
        return imagen;
    }
    public void setImagen(Drawable imagen) {
        this.imagen = imagen;
    }

    String apellido;
    Integer edad;
    String medicoCabecera;
    String sexo;
    double altura;
    int peso;
    String ultimoEstudio;
    Drawable imagen;

    public ArrayList<Paciente> getAllPacientes(Context context) {
        final ArrayList<Paciente> pacientesArray = new ArrayList<Paciente>();
        Paciente paciente;
        for (int i=0;i<12;i++)
        {
            paciente = new Paciente();
            switch (i)
            {
                case 0:
                    paciente.nombre = "Juan";
                    paciente.apellido = "Pérez";
                    paciente.edad = 26;
                    paciente.sexo = "Hombre";
                    paciente.altura = 1.78;
                    paciente.ultimoEstudio = "23/5/2016";
                    paciente.peso = 78;
                    paciente.imagen = context.getResources().getDrawable(R.drawable.paciente_1);
                    paciente.medicoCabecera = "Dr. Sánchez";
                    break;
                case 1:
                    paciente.nombre = "Sofía";
                    paciente.apellido = "Peralta";
                    paciente.edad = 21;
                    paciente.sexo = "Mujer";
                    paciente.altura = 1.69;
                    paciente.ultimoEstudio = "23/5/2016";
                    paciente.peso = 67;
                    paciente.imagen = context.getResources().getDrawable(R.drawable.paciente_2);
                    paciente.medicoCabecera = "Dr. Sánchez";
                    break;
                case 2:
                    paciente.nombre = "Marta";
                    paciente.apellido = "Peralta";
                    paciente.edad = 28;
                    paciente.sexo = "Mujer";
                    paciente.altura = 1.82;
                    paciente.ultimoEstudio = "23/5/2016";
                    paciente.peso = 81;
                    paciente.imagen = context.getResources().getDrawable(R.drawable.paciente_3);
                    paciente.medicoCabecera = "Dr. Suárez";
                    break;
                case 3:
                    paciente.nombre = "Pablo";
                    paciente.apellido = "Pérez";
                    paciente.edad = 41;
                    paciente.sexo = "Hombre";
                    paciente.altura = 1.91;
                    paciente.ultimoEstudio = "23/5/2016";
                    paciente.peso = 85;
                    paciente.imagen = context.getResources().getDrawable(R.drawable.paciente_4);
                    paciente.medicoCabecera = "Dr. Mendez";
                    break;
                case 4:
                    paciente.nombre = "Martín";
                    paciente.apellido = "Gómez";
                    paciente.edad = 39;
                    paciente.sexo = "Hombre";
                    paciente.altura = 1.65;
                    paciente.ultimoEstudio = "23/5/2016";
                    paciente.peso = 67;
                    paciente.imagen = context.getResources().getDrawable(R.drawable.paciente_5);
                    paciente.medicoCabecera = "Dr. Lopez";
                    break;

            }
            pacientesArray.add(paciente);
        }

        return pacientesArray;
    }
}
