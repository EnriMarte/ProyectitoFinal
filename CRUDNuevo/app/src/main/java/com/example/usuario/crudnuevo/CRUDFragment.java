package com.example.usuario.crudnuevo;

import android.app.ActionBar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Usuario on 16/6/2017.
 */

public class CRUDFragment extends Fragment implements View.OnClickListener {
    DialogFragment df;
    TextView textView;
    public CRUDFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.crud_fragment_layout, container, false);

        Button btn = (Button) v.findViewById(R.id.btn);
        textView =(TextView)v.findViewById(R.id.texto);



        btn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        String urlDeApi = "192.99.56.223/basedd/holamundo.php";

                Log.d("Get All", urlDeApi);
                FragmentManager fm = getFragmentManager();
                ViewAllFragment viewAllFragment = new ViewAllFragment();
                viewAllFragment.show(fm,"View all people");

        }
    }


