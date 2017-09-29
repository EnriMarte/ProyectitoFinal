package programacion.tp5;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class DataBaseManager {

    ArrayList<String> arrayList = new ArrayList<String>();
    String dbLocation = "jdbc:mysql//10.0.2.2:3306/db";
    private String user;
    private String date;
    private String pass;

    public boolean login(String user, String pass){
        this.user = user;
        this.pass = pass;
        login.start();
        if (user.equals("ok"))
            return true;
        else
            return false;
    }

    public ArrayList<String> getAllUsers() {
        arrayList.clear();
        getAllUsersThread.start();
        return arrayList;
    }

    public ArrayList<String> getUsersByAlphabet() {
        arrayList.clear();
        getUsersByAlphabetThread.start();
        return arrayList;
    }

    public ArrayList<String> getUsersByLogin() {
        arrayList.clear();
        getUsersByLoginThread.start();
        return arrayList;
    }

    public String getLastLogin(String user) {
        this.user = user;
        getLastLoginThread.start();
        return date;
    }

    Thread getAllUsersThread = new Thread(){
        @Override
        public void run(){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(dbLocation, "root", "root");

                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("select * from usuarios");

                int pos = 0;

                if (resultSet.first()) {
                    do {
                        arrayList.add(pos, resultSet.getString(pos));
                        pos++;
                    } while (resultSet.next());
                    Log.d("Bien", "Se devolvió todo");
                } else {
                    Log.d("Error", "No se devolvió nada");
                }
            } catch (ClassNotFoundException e){
                Log.d("Error", "Ocurrió un error: " + e);
            } catch (SQLException e){
                Log.d("Error", "Ocurrió un error: " + e);
            } catch (Exception e){
                Log.d("Error", "Ocurrió un error: " + e);
            }
        }
    };

    Thread getUsersByAlphabetThread = new Thread(){
        @Override
        public void run(){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(dbLocation, "root", "root");

                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("select * from usuarios order by nombre asc");

                int pos = 0;

                if (resultSet.first()) {
                    do {
                        arrayList.add(pos, resultSet.getString(pos));
                        pos++;
                    } while (resultSet.next());
                    Log.d("Bien", "Se devolvió todo");
                } else {
                    Log.d("Error", "No se devolvió nada");
                }
            } catch (ClassNotFoundException e){
                Log.d("Error", "Ocurrió un error: " + e);
            } catch (SQLException e){
                Log.d("Error", "Ocurrió un error: " + e);
            } catch (Exception e){
                Log.d("Error", "Ocurrió un error: " + e);
            }
        }
    };

    Thread getUsersByLoginThread = new Thread(){
        @Override
        public void run(){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(dbLocation, "root", "root");

                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("select * from usuarios order by fecha asc");

                int pos = 0;

                if (resultSet.first()) {
                    do {
                        arrayList.add(pos, resultSet.getString(pos));
                        pos++;
                    } while (resultSet.next());
                    Log.d("Bien", "Se devolvió todo");
                } else {
                    Log.d("Error", "No se devolvió nada");
                }
            } catch (ClassNotFoundException e){
                Log.d("Error", "Ocurrió un error: " + e);
            } catch (SQLException e){
                Log.d("Error", "Ocurrió un error: " + e);
            } catch (Exception e){
                Log.d("Error", "Ocurrió un error: " + e);
            }
        }
    };

    Thread getLastLoginThread = new Thread(){
        @Override
        public void run(){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(dbLocation, "root", "root");

                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("select login from usuarios where nombre='"+user+"'");

                if (resultSet.first()) {

                    date = resultSet.getString(0);

                    Log.d("Bien", "Se devolvió todo");
                } else {
                    Log.d("Error", "No se devolvió nada");
                }
            } catch (ClassNotFoundException e){
                Log.d("Error", "Ocurrió un error: " + e);
            } catch (SQLException e){
                Log.d("Error", "Ocurrió un error: " + e);
            } catch (Exception e){
                Log.d("Error", "Ocurrió un error: " + e);
            }
        }
    };

    Thread login = new Thread(){
        @Override
        public void run(){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(dbLocation, "root", "root");

                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("select nombre from usuarios where nombre='"+user+"' and contrasena='"+pass+"'");

                if (resultSet.first()) {
                    user = "ok";

                    Log.d("Bien", "Se devolvió todo");
                } else {
                    Log.d("Error", "No se devolvió nada");
                }
            } catch (ClassNotFoundException e){
                Log.d("Error", "Ocurrió un error: " + e);
            } catch (SQLException e){
                Log.d("Error", "Ocurrió un error: " + e);
            } catch (Exception e){
                Log.d("Error", "Ocurrió un error: " + e);
            }
        }
    };
}
