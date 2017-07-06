package afinal.proyecto.proyectofinaldemojunio.Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by PC on 7/6/2017.
 */

public class Usuario {
    String nombre;
    String apellido;
    String usuario;
    String contraseña;
    Date ultimaSesion;
    int matricula;
    String hospital;
    int credencial;

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
    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
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
    public int getCredencial() {
        return credencial;
    }
    public void setCredencial(int credencial) {
        this.credencial = credencial;
    }


    public ArrayList<Usuario> getAllUsuarios() {
        final ArrayList<Usuario> usuariosArray = new ArrayList<Usuario>();
        Usuario usuario;
        for (int i=0;i<12;i++)
        {
            usuario = new Usuario();
            switch (i)
            {
                case 0:
                    usuario.nombre = "Thelma";
                    usuario.apellido = "Torres";
                    usuario.usuario = "thelma.torres";
                    usuario.contraseña = "torres.thelma";
                    try {
                        ultimaSesion = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2013-04-26 08:34:55.705");
                    } catch (java.text.ParseException a){
                        ultimaSesion = null;
                    }
                    usuario.matricula = 12374829;
                    usuario.hospital = "Sanatorio Güemes";
                    usuario.credencial = 2;
                    break;
                case 1:
                    usuario.nombre = "Susana";
                    usuario.apellido = "Kalb";
                    usuario.usuario = "susana.kalb";
                    usuario.contraseña = "kalb.susana";
                    try {
                        ultimaSesion = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2013-04-26 08:34:55.705");
                    } catch (java.text.ParseException a){
                        ultimaSesion = null;
                    }
                    usuario.matricula = 12374829;
                    usuario.hospital = "Sanatorio Güemes";
                    usuario.credencial = 0;
                    break;
                case 2:
                    usuario.nombre = "Sebastián";
                    usuario.apellido = "Méndez";
                    usuario.usuario = "sebastian.mendez";
                    usuario.contraseña = "mendez.sebastian";
                    try {
                        ultimaSesion = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2013-04-26 08:34:55.705");
                    } catch (java.text.ParseException a){
                        ultimaSesion = null;
                    }
                    usuario.matricula = 12374829;
                    usuario.hospital = "Sanatorio Güemes";
                    usuario.credencial = 2;
                    break;
                case 3:
                    usuario.nombre = "Josefina";
                    usuario.apellido = "Álvarez";
                    usuario.usuario = "josefina.alvarez";
                    usuario.contraseña = "alvarez.josefina";
                    try {
                        ultimaSesion = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2013-04-26 08:34:55.705");
                    } catch (java.text.ParseException a){
                        ultimaSesion = null;
                    }
                    usuario.matricula = 12374829;
                    usuario.hospital = "Sanatorio Güemes";
                    usuario.credencial = 1;
                    break;
                case 4:
                    usuario.nombre = "Juan Roberto";
                    usuario.apellido = "Pérez";
                    usuario.usuario = "juan.perez";
                    usuario.contraseña = "perez.juan";
                    try {
                        ultimaSesion = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2013-04-26 08:34:55.705");
                    } catch (java.text.ParseException a){
                        ultimaSesion = null;
                    }
                    usuario.matricula = 12374829;
                    usuario.hospital = "Sanatorio Güemes";
                    usuario.credencial = 2;
                    break;
            }
            usuariosArray.add(usuario);
        }

        return usuariosArray;
    }
}
