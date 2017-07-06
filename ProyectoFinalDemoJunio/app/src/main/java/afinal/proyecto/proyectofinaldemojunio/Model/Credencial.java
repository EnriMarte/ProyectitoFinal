package afinal.proyecto.proyectofinaldemojunio.Model;

import java.util.ArrayList;

/**
 * Created by PC on 8/6/2017.
 */

public class Credencial {
    String nombre;
    String restricciones;
    int nivelPermiso;

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

    public ArrayList<Credencial> getAllCredenciales() {
        final ArrayList<Credencial> credencialesArray = new ArrayList<Credencial>();
        Credencial credencial;
        for (int i=0;i<4;i++)
        {
            credencial = new Credencial();
            switch (i)
            {
                case 0:
                    credencial.nombre = "Administrador";
                    credencial.restricciones = "Ninguno (superusuario)";
                    nivelPermiso = 0;
                    break;
                case 1:
                    credencial.nombre = "Jefe de área";
                    credencial.restricciones = "Ninguno";
                    nivelPermiso = 1;
                    break;
                case 2:
                    credencial.nombre = "Médico";
                    credencial.restricciones = "Credenciales";
                    nivelPermiso = 2;
                    break;
                case 3:
                    credencial.nombre = "Anónimo";
                    credencial.restricciones = "Todo, menos laboratorio";
                    nivelPermiso = 3;
                    break;
            }
            credencialesArray.add(credencial);
        }

        return credencialesArray;
    }
}
