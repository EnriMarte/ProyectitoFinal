package afinal.proyecto.proyectofinaldemojunio.Model;

import java.util.ArrayList;

/**
 * Created by PC on 8/6/2017.
 */

public class Credencial {
    int idCredenciales;
    String nombre;
    String restricciones;
    int nivelPermiso;

    public int getIdCredenciales() {
        return idCredenciales;
    }

    public void setIdCredenciales(int idCredenciales) {
        this.idCredenciales = idCredenciales;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getRestricciones() {
        return restricciones;
    }
    public void setRestricciones(String restricciones) {
        this.restricciones = restricciones;
    }
    public int getNivelPermiso() {
        return nivelPermiso;
    }
    public void setNivelPermiso(int nivelPermiso) {
        this.nivelPermiso = nivelPermiso;
    }
}
