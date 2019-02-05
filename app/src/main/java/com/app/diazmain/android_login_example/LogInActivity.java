package com.app.diazmain.android_login_example;

import android.content.Context;
import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText tietUser, tietPass;
    TextInputLayout tilUser, tilPass;
    MaterialButton btnLogIn;

    String URL_SERVIDOR = "http://192.168.0.108/pruebas/";

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        init();
    }

    /**
     * <p>Método para inicializar las variables a utilizar por la activity</p>
     */
    private void init() {
        tilUser = findViewById(R.id.tilUsername);
        tilPass = findViewById(R.id.tilPassword);

        tietUser = findViewById(R.id.tietUsername);
        tietPass = findViewById(R.id.tietPassword);

        btnLogIn = findViewById(R.id.btnLogIn);

        btnLogIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogIn) {
            if (!tietUser.getText().toString().equals("")) {
                if (!tietPass.getText().toString().equals("")) {
                    conectarServidor();
                    tilUser.setErrorEnabled(false);
                    tilPass.setErrorEnabled(false);
                } else {
                    tilPass.setError("Llena este campo");
                }
            } else {
                tilUser.setError("Llena este campo");
            }
        }
    }

    private void conectarServidor() {

        String usuario = tietUser.getText().toString();
        String contra = tietPass.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_SERVIDOR)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Peticiones peticion = retrofit.create(Peticiones.class);
        Call<String> call = peticion.iniciarSesion(usuario, contra);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Snackbar.make();
                Toast.makeText(context, response.body(), Toast.LENGTH_LONG).show();
                if (response.body().equals("Acceso correcto")) {
                    acceso();
                } else {
                    Toast.makeText(context, response.body(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.w("error de conexion",t.toString());
            }
        });
    }

    public void acceso() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", tietUser.getText().toString());
        startActivity(intent);
    }
}
