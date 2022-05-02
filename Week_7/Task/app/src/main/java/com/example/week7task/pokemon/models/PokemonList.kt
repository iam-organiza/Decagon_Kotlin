package com.example.week7task.pokemon.models

data class PokemonList(
    val count: Int? = null,
    val next: String? = null,
    val previous: Any? = null,
    val results: List<Result>
)