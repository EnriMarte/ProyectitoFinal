package com.example.a42341041.proyectitofinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
    }
}
