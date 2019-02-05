package com.app.diazmain.android_login_example;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView tvUser, tvEmail, tvFrase;
    String usuario;

    String URL_SERVIDOR = "http://192.168.0.108/pruebas/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        obtenerDatosUsuario();
    }

    private void init() {
        usuario = getIntent().getStringExtra("username");

        tvUser = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        tvFrase = findViewById(R.id.tvFrase);
    }

    private void obtenerDatosUsuario() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_SERVIDOR)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Peticiones peticion = retrofit.create(Peticiones.class);
        Call<Usuario> call = peticion.obtenerDatosUsuario(usuario);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                tvUser.setText("Usuario: "+response.body().getUsername());
                tvEmail.setText("Email: "+response.body().getEmail());
                tvFrase.setText("'"+response.body().getFrase()+"'");
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                tvFrase.setText("Error al obtener los datos del usuario: "+t.getLocalizedMessage());
            }
        });
    }
}
