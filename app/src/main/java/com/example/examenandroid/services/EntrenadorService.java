package com.example.examenandroid.services;

import com.example.examenandroid.entities.Entrenador;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EntrenadorService {
    @GET("N00038907")
    Call<Entrenador> getAll();

    @POST("N00038907")
    Call<Entrenador> create(@Body Entrenador entrenador);
}
