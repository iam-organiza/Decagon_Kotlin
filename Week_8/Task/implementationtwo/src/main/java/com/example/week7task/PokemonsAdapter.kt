package com.example.week7task

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.week7task.pokemon.models.Pokemon
import com.example.week7task.pokemon.models.Result
import com.skydoves.progressview.ProgressView
import retrofit2.HttpException
import java.io.IOException

class PokemonsAdapter(private val pokemons: List<Result>): RecyclerView.Adapter<PokemonsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val sprite = view.findViewById<ImageView>(R.id.pokemon_sprite)
        val name = view.findViewById<TextView>(R.id.pokemon_name)
        val bg = view.findViewById<ViewGroup>(R.id.pokemon_display_bg)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.pokemon_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon: Result = pokemons[position]

        holder.itemView.apply {
            Glide.with(this)
                .load(pokemon.front_default)
                .into(holder.sprite)
        }

        try {
            holder.bg.setBackgroundColor(Color.parseColor(pokemon.color))
            holder.name.setTextColor(Color.parseColor("#FFFFFF"))
        } catch (e: Exception) {
            holder.bg.setBackgroundColor(Color.parseColor("#AD424242"))
            holder.name.setTextColor(Color.parseColor("#000000"))
        }

//        holder.sprite.setImageResource(pokemon.sprite)
        holder.name.text = pokemon.name

        holder.sprite.setOnClickListener {
//            Toast.makeText(holder.sprite.context, pokemon.name, Toast.LENGTH_SHORT).show()
//            println(pokemon.name)


            val intent = Intent(holder.itemView.context, PokemonDetails::class.java)
            intent.putExtra("name", pokemon.name)
            intent.putExtra("position", position)

            startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int = pokemons.size
}