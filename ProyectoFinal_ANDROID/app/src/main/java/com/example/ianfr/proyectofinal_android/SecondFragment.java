package com.example.ianfr.proyectofinal_android;
// Para poder usar el contexto desde aca
//getActivity().getApplicationContext()
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import java.util.ArrayList;

import static com.example.ianfr.proyectofinal_android.R.layout.fragment_second;

public class SecondFragment extends Fragment{

    public static SecondFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", instance);
        SecondFragment secondFragment = new SecondFragment();
        secondFragment.setArguments(args);
        return secondFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(fragment_second, container, false);
        //#####Programar entre esto#####

        // Construct the data source
        final ArrayList<Pacientes> pacientesArray = new ArrayList<Pacientes>();
        Pacientes paciente;
        for (int i=0;i<5;i++)
        {
            paciente = new Pacientes();
            switch (i)
            {
                case 0:
                    paciente.nombre = "Juan";
                    paciente.apellido = "Pérez";
                    paciente.edad = 26;
                    paciente.sexo = "Hombre";
                    paciente.altura = 1.78;
                    paciente.ultimoEstudio = "23/5/2016";
                    paciente.peso = 78;
                    paciente.imagen = getResources().getDrawable(R.drawable.paciente_1);
                    paciente.medicoCabecera = "Dr. Sánchez";
                    break;
                case 1:
                    paciente.nombre = "Sofía";
                    paciente.apellido = "Peralta";
                    paciente.edad = 21;
                    paciente.sexo = "Mujer";
                    paciente.altura = 1.69;
                    paciente.ultimoEstudio = "23/5/2016";
                    paciente.peso = 67;
                    paciente.imagen = getResources().getDrawable(R.drawable.paciente_2);
                    paciente.medicoCabecera = "Dr. Sánchez";
                    break;
                case 2:
                    paciente.nombre = "Marta";
                    paciente.apellido = "Peralta";
                    paciente.edad = 28;
                    paciente.sexo = "Mujer";
                    paciente.altura = 1.82;
                    paciente.ultimoEstudio = "23/5/2016";
                    paciente.peso = 81;
                    paciente.imagen = getResources().getDrawable(R.drawable.paciente_3);
                    paciente.medicoCabecera = "Dr. Suárez";
                    break;
                case 3:
                    paciente.nombre = "Pablo";
                    paciente.apellido = "Pérez";
                    paciente.edad = 41;
                    paciente.sexo = "Hombre";
                    paciente.altura = 1.91;
                    paciente.ultimoEstudio = "23/5/2016";
                    paciente.peso = 85;
                    paciente.imagen = getResources().getDrawable(R.drawable.paciente_4);
                    paciente.medicoCabecera = "Dr. Mendez";
                    break;
                case 4:
                    paciente.nombre = "Martín";
                    paciente.apellido = "Gómez";
                    paciente.edad = 39;
                    paciente.sexo = "Hombre";
                    paciente.altura = 1.65;
                    paciente.ultimoEstudio = "23/5/2016";
                    paciente.peso = 67;
                    paciente.imagen = getResources().getDrawable(R.drawable.paciente_5);
                    paciente.medicoCabecera = "Dr. Lopez";
                    break;
            }
            pacientesArray.add(paciente);
        }
        // Create the adapter to convert the array to views
        final listPacientesAdapter adapter = new listPacientesAdapter(getActivity(), pacientesArray);
        // Attach the adapter to a ListView
        final ListView pacientes = (ListView)view.findViewById(R.id.listaPacientes);
        final ListView pacientesSearch = (ListView)view.findViewById(R.id.listaPacientesSearch);
        pacientesSearch.setVisibility(View.GONE);
        pacientes.setAdapter(adapter);

        pacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Pacientes p = pacientesArray.get(position);
                Bundle b = new Bundle();
                Intent cargarPopup = new Intent(getActivity(), popup.class);

                b.putString("nombre", p.nombre);
                b.putString("apellido", p.apellido);
                b.putString("medCabeza", p.medicoCabecera);
                b.putInt("edad", p.edad);
                b.putInt("numPaciente", position);
                b.putString("sexo", p.sexo);
                b.putString("ultEstudio", p.ultimoEstudio);
                b.putInt("peso", p.peso);
                b.putDouble("altura", p.altura);

                cargarPopup.putExtras(b);
                startActivity(cargarPopup);
            }
        });

        SearchView sv = (SearchView)view.findViewById(R.id.searchView);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchString) {/*
                adapter.getFilter().filter(searchString);
                return false;*/
                String simpleSearch;
                if (searchString.length() > 0) {

                    ArrayList<Pacientes> classSearchArray = new ArrayList<Pacientes>();
                    simpleSearch = searchString.toLowerCase();
                    for (Pacientes classObj : pacientesArray) {
                        if (classObj.getNombre().toLowerCase().contains(simpleSearch) ||
                                classObj.getApellido().toLowerCase().contains(simpleSearch) ||
                                classObj.getDoctor().toLowerCase().contains(simpleSearch)) {
                            classSearchArray.add(classObj);
                        }
                    }
                    listPacientesAdapter adapter = new listPacientesAdapter(getActivity(), classSearchArray);
                    pacientesSearch.setAdapter(adapter);

                    pacientes.setVisibility(View.GONE); //original list view
                    pacientesSearch.setVisibility(View.VISIBLE); //search list view

                } else {
                    simpleSearch = null;
                    pacientes.setVisibility(View.VISIBLE);
                    pacientesSearch.setVisibility(View.GONE);
                }
                return false;
            }
        });
        //#####Y esto#####
        return view;
    }


}
