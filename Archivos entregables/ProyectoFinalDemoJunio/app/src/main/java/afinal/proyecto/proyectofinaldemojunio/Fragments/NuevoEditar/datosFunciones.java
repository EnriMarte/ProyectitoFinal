package afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Math.*;

import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import afinal.proyecto.proyectofinaldemojunio.Fragments.fragmentConfirFunc;
import afinal.proyecto.proyectofinaldemojunio.MainActivity;
import afinal.proyecto.proyectofinaldemojunio.Model.Funcion;
import afinal.proyecto.proyectofinaldemojunio.R;

/**
 * Created by ianfr on 23/06/2017.
 */

public class datosFunciones extends Fragment {

    public int editarONuevo;
    ArrayList<String> vars = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.datos_funciones, container, false);

        Button button = (Button)view.findViewById(R.id.botonNuevaFuncion);
        final Funcion funcion = new Funcion();
        final EditText editText = (EditText) view.findViewById(R.id.editTextFuncion);

        if (editarONuevo == 0){
            button.setText("Guardar cambios");
        }

        final MainActivity parent = (MainActivity) getActivity();
        final TextView txtNombreFunc = (EditText)view.findViewById(R.id.nombreFuncion);
        txtNombreFunc.setText(parent.getFuncionEditando().getNombre());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!txtNombreFunc.getText().toString().equals("") &&
                        !editText.getText().toString().equals("")) {
                        parent.setVariablesFunciones(vars);
                        parent.setFuncion(editText.getText().toString());
                        parent.setNombreFuncion(txtNombreFunc.getText().toString());
                        if (vars.size() == 0)
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container,
                                            new fragmentConfirFunc(), "confVarsTag")
                                    .addToBackStack("confVarsTag").commit();
                         else
                        getFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container,
                                        new condicionesFunciones(), "condicionesTag")
                                .addToBackStack("condicionesTag").commit();
                } else {
                    Toast.makeText(getActivity(), "Complete los campos", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button b = (Button)view.findViewById(R.id.butRaizN);
        b.setText(Html.fromHtml("<sup><small>4</small></sup>√x"));

        funcion.setFuncion("");

        view.findViewById(R.id.butNum0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "0");
                editText.setText(funcion.getFuncion());
            }
        });

        view.findViewById(R.id.butNum1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "1");
                editText.setText(funcion.getFuncion());
            }
        });

        view.findViewById(R.id.butNum2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "2");
                editText.setText(funcion.getFuncion());

            }
        });

        view.findViewById(R.id.butNum3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "3");
                editText.setText(funcion.getFuncion());
            }
        });

        view.findViewById(R.id.butNum4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "4");
                editText.setText(funcion.getFuncion());
            }
        });

        view.findViewById(R.id.butNum5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "5");
                editText.setText(funcion.getFuncion());
            }
        });
        view.findViewById(R.id.butNum6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "6");
                editText.setText(funcion.getFuncion());
            }
        });

        view.findViewById(R.id.butNum7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "7");
                editText.setText(funcion.getFuncion());
            }
        });

        view.findViewById(R.id.butNum8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "8");
                editText.setText(funcion.getFuncion());
            }
        });

        view.findViewById(R.id.butNum9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "9");
                editText.setText(funcion.getFuncion());
            }
        });

        view.findViewById(R.id.butXCuadrado).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "^2");
                editText.setText(funcion.getFuncion());
            }
        });

        view.findViewById(R.id.butXCubo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "^3");
                editText.setText(funcion.getFuncion());
            }
        });

        view.findViewById(R.id.butXN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "^");
                editText.setText(funcion.getFuncion());
            }
        });

        view.findViewById(R.id.butRaizCuadrada).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "^(1/2)");
                editText.setText(funcion.getFuncion());
            }
        });

        view.findViewById(R.id.butRaizCubica).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "^(1/3)");
                editText.setText(funcion.getFuncion());
            }
        });

        view.findViewById(R.id.butRaizN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "^(1/4)");
                editText.setText(funcion.getFuncion());
            }
        });

        view.findViewById(R.id.butAbreParentesis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "(");
                editText.setText(funcion.getFuncion());
            }
        });

        view.findViewById(R.id.butCierraParentesis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + ")");
                editText.setText(funcion.getFuncion());
            }
        });

        view.findViewById(R.id.butSumar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "+");
                editText.setText(funcion.getFuncion());
            }
        });

        view.findViewById(R.id.butRestar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "-");
                editText.setText(funcion.getFuncion());
            }
        });

        view.findViewById(R.id.butAgregaComa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + ".");
                editText.setText(funcion.getFuncion());
            }
        });

        view.findViewById(R.id.nuevaVarButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nuevaVar = (EditText)view.findViewById(R.id.nuevaVarText);
                if (!vars.contains(nuevaVar.getText().toString())){
                    vars.add(nuevaVar.getText().toString());
                }
                funcion.setFuncion(funcion.getFuncion() + nuevaVar.getText().toString());
                editText.setText(funcion.getFuncion());
            }
        });

        view.findViewById(R.id.butDividir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "/");
                editText.setText(funcion.getFuncion());
            }
        });

        view.findViewById(R.id.butMultiplicar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "*");
                editText.setText(funcion.getFuncion());
            }
        });

        view.findViewById(R.id.butBorrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = funcion.getFuncion();
                if (temp.length() > 0) {
                    temp = temp.substring(0, temp.length() - 1);
                    funcion.setFuncion(temp);
                    editText.setText(funcion.getFuncion());
                }
            }
        });

        view.findViewById(R.id.varO2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "O2");
                editText.setText(funcion.getFuncion());
                if (!vars.contains("O2")){
                    vars.add("O2");
                }
            }
        });

        view.findViewById(R.id.varPh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "pH");
                editText.setText(funcion.getFuncion());
                if (!vars.contains("pH")){
                    vars.add("pH");
                }
            }
        });

        view.findViewById(R.id.varPo2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "pO2");
                editText.setText(funcion.getFuncion());
                if (!vars.contains("pO2")){
                    vars.add("pO2");
                }
            }
        });

        view.findViewById(R.id.btnAgregarOtraVar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.findViewById(R.id.nuevaVarText).getVisibility() == View.INVISIBLE){
                    view.findViewById(R.id.nuevaVarText).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.nuevaVarButton).setVisibility(View.VISIBLE);
                } else {
                    view.findViewById(R.id.nuevaVarText).setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.nuevaVarButton).setVisibility(View.INVISIBLE);
                }
            }
        });

        return view;
    }
}

