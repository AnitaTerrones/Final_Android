package com.example.examenandroid.services;

import com.example.examenandroid.entities.Pokemon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PokemonService {
    @GET("N00038907")
    Call<List<Pokemon>> getAll();

    @POST("N00038907/crear")
    Call<Pokemon> create(@Body Pokemon pokemon);
}
