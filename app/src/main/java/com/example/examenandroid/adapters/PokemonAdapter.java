package com.example.examenandroid.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.examenandroid.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examenandroid.entities.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {


    List<Pokemon> pokemons;

    public PokemonAdapter(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    @Override
    public PokemonAdapter.PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itempokemon,parent,false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PokemonAdapter.PokemonViewHolder holder, int position) {
        View view = holder.itemView;
        Pokemon pokemon = pokemons.get(position);

        TextView tvNombre = view.findViewById(R.id.tvNombrePokemon);
        TextView tvTipo = view.findViewById(R.id.tvTipoPokemon);
        ImageView ivImage = view.findViewById(R.id.ivImagenPokemon);
        TextView tvLatitud = view.findViewById(R.id.tvLatitudPokemon);
        TextView tvLogintud = view.findViewById(R.id.tvLonguitudPokemon);

        tvNombre.setText(pokemon.getNombre());
        tvTipo.setText(pokemon.getTipo());
        Picasso.get().load(pokemon.getImagen()).into(ivImage);
        tvLatitud.setText(pokemon.getLatitude().intValue());
        tvLogintud.setText(pokemon.getLongitude().intValue());
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }
    public class PokemonViewHolder extends RecyclerView.ViewHolder {
        public PokemonViewHolder(View itemView){
            super(itemView);
        }
    }
}