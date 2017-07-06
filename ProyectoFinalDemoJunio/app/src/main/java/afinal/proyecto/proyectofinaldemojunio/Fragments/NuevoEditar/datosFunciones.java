package afinal.proyecto.proyectofinaldemojunio.Fragments.NuevoEditar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import static java.lang.Math.*;

import com.irozon.sneaker.Sneaker;

import afinal.proyecto.proyectofinaldemojunio.Model.Funcion;
import afinal.proyecto.proyectofinaldemojunio.R;

/**
 * Created by ianfr on 23/06/2017.
 */

public class datosFunciones extends Fragment {

    public int editarONuevo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.datos_funciones, container, false);

        Button button = (Button)view.findViewById(R.id.botonNuevaFuncion);
        final Funcion funcion = new Funcion();

        if (editarONuevo == 0){
            button.setText("Guardar cambios");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editarONuevo == 0){
                    Sneaker.with(getActivity())
                            .setTitle("Cambios guardados")
                            .setDuration(3000)
                            .setMessage("Los cambios se realizaron satisfactoriamente")
                            .setIcon(R.drawable.ic_cloud_done)
                            .sneak(R.color.colorAccent);
                }else{
                    Sneaker.with(getActivity())
                            .setTitle("Función creada")
                            .setDuration(3000)
                            .setMessage("Se ha agregado la función a la base de datos")
                            .setIcon(R.drawable.ic_cloud_done)
                            .sneak(R.color.colorAccent);
                }
            }
        });

        final WebView webView = (WebView)view.findViewById(R.id.webViewFuncion);

        Button b = (Button)view.findViewById(R.id.butRaizN);
        b.setText(Html.fromHtml("<sup><small>4</small></sup>√x"));

        view.findViewById(R.id.butNum0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "0");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });

        view.findViewById(R.id.butNum1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "1");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });

        view.findViewById(R.id.butNum2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "2");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");

            }
        });

        view.findViewById(R.id.butNum3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "3");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });

        view.findViewById(R.id.butNum4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "4");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });

        view.findViewById(R.id.butNum5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "5");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });
        view.findViewById(R.id.butNum6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "6");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });

        view.findViewById(R.id.butNum7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "7");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });

        view.findViewById(R.id.butNum8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "8");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });

        view.findViewById(R.id.butNum9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "9");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });

        view.findViewById(R.id.butXCuadrado).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "^2");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });

        view.findViewById(R.id.butXCubo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "^3");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });

        view.findViewById(R.id.butXN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "^");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });

        view.findViewById(R.id.butRaizCuadrada).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "^(1/2)");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });

        view.findViewById(R.id.butRaizCubica).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "^(1/3)");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });

        view.findViewById(R.id.butRaizN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "^(1/4)");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });

        view.findViewById(R.id.butAbreParentesis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "(");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });

        view.findViewById(R.id.butCierraParentesis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + ")");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });

        view.findViewById(R.id.butSumar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "+");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });

        view.findViewById(R.id.butRestar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "-");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });

        view.findViewById(R.id.butAgregaComa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + ".");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });

        view.findViewById(R.id.butNuevaVar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!funcion.getFuncion().contains("x")){
                    funcion.setFuncion(funcion.getFuncion() + "x");
                } else if (!funcion.getFuncion().contains("y")){
                    funcion.setFuncion(funcion.getFuncion() + "y");
                } else if (!funcion.getFuncion().contains("z")){
                    funcion.setFuncion(funcion.getFuncion() + "z");
                } else if (!funcion.getFuncion().contains("a")){
                    funcion.setFuncion(funcion.getFuncion() + "a");
                }
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });

        view.findViewById(R.id.butDividir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "/");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });

        view.findViewById(R.id.butMultiplicar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.setFuncion(funcion.getFuncion() + "*");
                webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
            }
        });

        view.findViewById(R.id.butBorrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = funcion.getFuncion();
                if (temp.length() > 0) {
                    temp = temp.substring(0, temp.length() - 1);
                    funcion.setFuncion(temp);
                    webView.loadData(funcion.getFuncion(), "text/html; charset=utf-8", "utf-8");
                }
            }
        });

        return view;
    }
}

