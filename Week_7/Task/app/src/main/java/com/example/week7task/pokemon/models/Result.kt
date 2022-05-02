package com.example.week7task.pokemon.models

data class Result(
    val name: String,
    var url: String,
    var color: String? = null,
    var front_default: String? = null
)