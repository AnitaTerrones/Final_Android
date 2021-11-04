package com.example.examenandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.examenandroid.entities.Entrenador;
import com.example.examenandroid.services.EntrenadorService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listaEntrenadorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_entrenador);

        Button btnPokemon = findViewById(R.id.btnCrearPokemons);
        Button btnVerPokemon = findViewById(R.id.btnVerPokemon);

        btnPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //voy a crear pokemon
                Intent intent = new Intent(listaEntrenadorActivity.this,crearPokemonActivity.class);
                startActivity(intent);
            }
        });

        btnVerPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //voy a ver lista de pokemons
                Intent intent = new Intent(listaEntrenadorActivity.this,listaPokemonActivity.class);
                startActivity(intent);
            }
        });


        ImageView ivImagen = findViewById(R.id.ivEntrenador);
        TextView tvNombre = findViewById(R.id.tvNombreEntre);
        TextView tvPueblo = findViewById(R.id.tvPuebloEntre);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://upn.lumenes.tk/entrenador/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EntrenadorService service = retrofit.create(EntrenadorService.class);
        Call<Entrenador> entrenadorCall = service.getAll();
        entrenadorCall.enqueue(new Callback<Entrenador>() {
            @Override
            public void onResponse(Call<Entrenador> call, Response<Entrenador> response) {
                Entrenador entranador = response.body();

                tvNombre.setText(entranador.getNombres());
                tvPueblo.setText(entranador.getPueblo());
                Picasso.get().load(entranador.getImagen()).into(ivImagen);
            }
            @Override
            public void onFailure(Call<Entrenador> call, Throwable t) {

            }
        });

    }
}