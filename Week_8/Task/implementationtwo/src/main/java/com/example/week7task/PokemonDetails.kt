package com.example.week7task

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.week7task.pokemon.models.Pokemon
import com.skydoves.progressview.ProgressView
import retrofit2.HttpException
import java.io.IOException

class PokemonDetails : AppCompatActivity() {
    lateinit var pokemonName: String
    var pokemonPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)

        pokemonName = intent.getStringExtra("name").toString()
        pokemonPosition = intent.getIntExtra("position", 0)

        findViewById<ImageView>(R.id.back_to_pokemons_icon).setOnClickListener {
            goBack()
        }

        findViewById<TextView>(R.id.back_to_pokemons).setOnClickListener {
            goBack()
        }

        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getPokemon(pokemonName)
            } catch (e: IOException) {
                Log.e("http", "IOException")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e("http", "HttpException")
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {
                val pokemon: Pokemon = response.body()!!
                findViewById<TextView>(R.id.progress_index).text = "#${pokemon.id}"
                findViewById<TextView>(R.id.pokemonName).text = pokemon.name
                findViewById<TextView>(R.id.pokemonHeight).text = "${pokemon.height} M"
                findViewById<TextView>(R.id.pokemonWeight).text = "${pokemon.weight} KG"


                for (stat in pokemon.stats) {
                    when (stat.stat.name) {
                        "hp" -> {
                            val progressView = findViewById<ProgressView>(R.id.pokemon_progress_hp)
                            progressView.progress = stat.base_stat.toFloat()
                            progressView.labelText = "Hp (${stat.base_stat}%)"
                        }
                        "attack" -> {
                            val progressView = findViewById<ProgressView>(R.id.pokemon_progress_atk)
                            progressView.progress = stat.base_stat.toFloat()
                            progressView.labelText = "Attack (${stat.base_stat}%)"
                        }
                        "defense" -> {
                            val progressView = findViewById<ProgressView>(R.id.pokemon_progress_def)
                            progressView.progress = stat.base_stat.toFloat()
                            progressView.labelText = "Defense (${stat.base_stat}%)"
                        }
                        "speed" -> {
                            val progressView = findViewById<ProgressView>(R.id.pokemon_progress_spd)
                            progressView.progress = stat.base_stat.toFloat()
                            progressView.labelText = "Speed (${stat.base_stat}%)"
                        }
                        "special-attack" -> {
                            val progressView = findViewById<ProgressView>(R.id.pokemon_progress_exp)
                            progressView.progress = stat.base_stat.toFloat()
                            progressView.labelText = "Special Attack (${stat.base_stat}%)"
                        }
                        else -> null
                    }
                }

                Glide.with(this@PokemonDetails)
                    .load(pokemon.sprites.other.home.front_default)
                    .into(findViewById(R.id.pokemonImage))
            } else {
                Log.e("isSuccessful", "false ${response.body()}")
            }
        }
    }

    fun goBack() {
        val intent = Intent(this, MainActivity::class.java)
        ContextCompat.startActivity(this, intent, null)
    }
}