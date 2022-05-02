package com.example.week7task.apis

import com.example.week7task.pokemon.models.Pokemon
import com.example.week7task.pokemon.models.PokemonList
import com.example.week7task.pokemon.models.SpeciesInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("/api/v2/pokemon/")
    suspend fun getPokemons(): Response<PokemonList>

    @GET("/api/v2/pokemon/{name}")
    suspend fun getPokemon(): Response<Pokemon>

    @GET("/api/v2/pokemon/{name}/")
    suspend fun getPokemon(@Path("name") name: String): Response<Pokemon>

    @GET("/api/v2/pokemon/")
    suspend fun getPokemons(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int): Response<PokemonList>


    @GET("{species_name}/")
    suspend fun getSpecies(@Path("species_name") species_name: String): Response<SpeciesInfo>
}