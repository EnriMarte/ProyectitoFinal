package afinal.proyecto.proyectofinaldemojunio.Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by PC on 7/6/2017.
 */

public class Usuario{

    int idUsuario;
    String nombre;
    String apellido;
    String usuario;
    String contrasenia;
    Date ultimaSesion;
    int matricula;
    String hospital;
    int idCredencial;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getContrasenia() {
        return contrasenia;
    }
    public void setContrasenia(String contraseña) {
        this.contrasenia = contraseña;
    }
    public Date getUltimaSesion() {
        return ultimaSesion;
    }
    public void setUltimaSesion(Date ultimaSesion) {
        this.ultimaSesion = ultimaSesion;
    }
    public int getMatricula() {
        return matricula;
    }
    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }
    public String getHospital() {
        return hospital;
    }
    public void setHospital(String hospital) {
        this.hospital = hospital;
    }
    public int getIdCredencial() {
        return idCredencial;
    }
    public void setIdCredencial(int credencial) {
        this.idCredencial = credencial;
    }
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }



}
