package afinal.proyecto.proyectofinaldemojunio.Model;

/**
 * Created by ianfr on 11/08/2017.
 */

import bsh.Interpreter;

public class JavaParser {
    private void runString(String code){


        Interpreter interpreter = new Interpreter();
        try {
            interpreter.set("context", this);//set any variable, you can refer to it directly from string
            interpreter.eval(code);//execute code
        }
        catch (Exception e){//handle exception
            e.printStackTrace();
        }
    }
}
