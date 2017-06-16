package afinal.proyecto.proyectofinaldemojunio.Model;

import java.util.ArrayList;

/**
 * Created by PC on 8/6/2017.
 */

public class Funcion {
    String nombre;
    String funcion;
    String[] variables = new String[4];

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getFuncion() {
        return funcion;
    }
    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }
    public String[] getVariables() {
        return variables;
    }
    public void setVariables(String[] variables) {
        this.variables = variables;
    }

    public ArrayList<Funcion> getAllFunciones() {
        final ArrayList<Funcion> funcionesArray = new ArrayList<Funcion>();
        Funcion funcion;
        for (int i=0;i<2;i++)
        {
            funcion = new Funcion();
            switch (i)
            {
                case 0:
                    funcion.nombre = "pH";
                    funcion.funcion = "-log(10)x";
                    funcion.variables[0] = "[H+]";
                    break;
                case 1:
                    funcion.nombre = "Exceso de base";
                    funcion.funcion = "(x-24,4+(2,3*y+7,7)*(z-7,4))*(1-0,023*z)";
                    funcion.variables[0] = "HCO3";
                    funcion.variables[1] = "Hb";
                    funcion.variables[2] = "pH";
                    break;
            }
            funcionesArray.add(funcion);
        }

        return funcionesArray;
    }
}
