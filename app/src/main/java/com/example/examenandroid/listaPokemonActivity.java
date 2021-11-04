package com.example.examenandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.examenandroid.adapters.PokemonAdapter;
import com.example.examenandroid.entities.Pokemon;
import com.example.examenandroid.services.PokemonService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listaPokemonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pokemon);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://upn.lumenes.tk/pokemons/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokemonService service = retrofit.create(PokemonService.class);
        Call<List<Pokemon>> pokemonCall = service.getAll();
        pokemonCall.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {

                List<Pokemon> pokemons = response.body();
                RecyclerView rv = findViewById(R.id.rvPokemons);
                rv.setHasFixedSize(true);
                rv.setLayoutManager(new LinearLayoutManager(listaPokemonActivity.this));
                PokemonAdapter adapter = new PokemonAdapter(pokemons);

                rv.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<Pokemon>> call, Throwable t) {

            }
        });
    }
}