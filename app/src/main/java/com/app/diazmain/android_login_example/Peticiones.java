package com.app.diazmain.android_login_example;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Peticiones {

    @FormUrlEncoded
    @POST("login.php")                                               // Ruta del archivo
    public Call<String> iniciarSesion(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("selectUser.php")                                           // Ruta del archivo
    public Call<Usuario> obtenerDatosUsuario(
            @Field("username") String username
    );
}
