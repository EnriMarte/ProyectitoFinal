package afinal.proyecto.proyectofinaldemojunio.Model;

/**
 * Created by ianfr on 24/08/2017.
 */

public class Diagnosticos {
    int idDiagnostico;
    String nombre;
    String funciones;
    String condiciones;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFunciones() {
        return funciones;
    }

    public void setFunciones(String funciones) {
        this.funciones = funciones;
    }

    public String getCondiciones() {
        return condiciones;
    }

    public void setCondiciones(String condiciones) {
        this.condiciones = condiciones;
    }

    public int getIdDiagnostico() {
        return idDiagnostico;
    }
}
