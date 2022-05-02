package com.example.week7task

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.skydoves.progressview.progressView
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {
    lateinit var pokemons: ArrayList<Pokemon>
    private var nextUrl: String? = null
    private var prevUrl: String? = null
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressDialog = ProgressDialog(this)

        val rvPokemons = findViewById<RecyclerView>(R.id.pokemonCharacters)
//        pokemons = arrayListOf(Pokemon(R.drawable.ic_bulbasaur, "Bulbasaur"), Pokemon(R.drawable.ic_bulbasaur, "Bulbasaur"), Pokemon(R.drawable.ic_bulbasaur, "Bulbasaur"))

//        val adapter = PokemonsAdapter(pokemons)
//
//        rvPokemons.adapter = adapter
//        rvPokemons.layoutManager = GridLayoutManager(this, 2)

        showpDialog()
        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getPokemons()
            } catch (e: IOException) {
                Log.e("io", "IOException")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e("http", "HttpException")
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {
                val results = response.body()!!.results
                nextUrl = response.body()!!.next?.split("?")?.get(1)
                prevUrl = response.body()!!.previous?.toString()?.split("?")?.get(1)

                if (prevUrl == null) {
                    findViewById<FloatingActionButton>(R.id.floatingActionButtonPrev).visibility = View.GONE
                } else {
                    findViewById<FloatingActionButton>(R.id.floatingActionButtonPrev).visibility = View.VISIBLE
                }

                if (nextUrl == null) {
                    findViewById<FloatingActionButton>(R.id.floatingActionButtonNext).visibility = View.GONE
                } else {
                    findViewById<FloatingActionButton>(R.id.floatingActionButtonNext).visibility = View.VISIBLE
                }

                for (result in results) {
                    val response = try {
                        RetrofitInstance.spieces.getSpecies(result.name)
                    } catch (e: IOException) {
                        Log.e("http", "IOException")
                        return@launchWhenCreated
                    } catch (e: HttpException) {
                        Log.e("http", "HttpException")
                        return@launchWhenCreated
                    }

                    if (response.isSuccessful && response.body() != null) {
                        val color = response.body()!!.color
                        result.color = color.name
                    }

                    val imgResponse = try {
                        RetrofitInstance.api.getPokemon(result.name)
                    } catch (e: IOException) {
                        Log.e("http", "IOException")
                        return@launchWhenCreated
                    } catch (e: HttpException) {
                        Log.e("http", "HttpException")
                        return@launchWhenCreated
                    }

                    if (imgResponse.isSuccessful && imgResponse.body() != null) {
                        val front_default = imgResponse.body()!!.sprites.other.home.front_default
                        result.front_default = front_default
                    }
                }

                val adapter = PokemonsAdapter(results)

                rvPokemons.adapter = adapter
                rvPokemons.layoutManager = GridLayoutManager(this@MainActivity, 2)
                hidepDialog()
            } else {
                Log.e("isSuccessful", "false")
            }
        }


        findViewById<FloatingActionButton>(R.id.floatingActionButtonPrev).setOnClickListener {
            val query = prevUrl?.split("&")
            val offset = query?.get(0)?.split("=")?.get(1)?.toInt()
            val limit = query?.get(1)?.split("=")?.get(1)?.toInt()

//            println(listOf(offset, limit))

            showpDialog()
            lifecycleScope.launchWhenCreated {
                val res = try {
                    RetrofitInstance.api.getPokemons(offset!!, limit!!)
                } catch (e: IOException) {
                    Log.e("io", "IOException")
                    return@launchWhenCreated
                } catch (e: HttpException) {
                    Log.e("http", "HttpException")
                    return@launchWhenCreated
                }

                if (res.isSuccessful && res.body() != null) {
                    val results = res.body()!!.results
                    nextUrl = res.body()!!.next?.split("?")?.get(1)
                    prevUrl = res.body()!!.previous?.toString()?.split("?")?.get(1)

                    if (prevUrl == null) {
                        findViewById<FloatingActionButton>(R.id.floatingActionButtonPrev).visibility = View.GONE
                    } else {
                        findViewById<FloatingActionButton>(R.id.floatingActionButtonPrev).visibility = View.VISIBLE
                    }

                    if (nextUrl == null) {
                        findViewById<FloatingActionButton>(R.id.floatingActionButtonNext).visibility = View.GONE
                    } else {
                        findViewById<FloatingActionButton>(R.id.floatingActionButtonNext).visibility = View.VISIBLE
                    }

                    for (result in results) {
                        val response = try {
                            RetrofitInstance.spieces.getSpecies(result.name)
                        } catch (e: IOException) {
                            Log.e("http", "IOException")
                            return@launchWhenCreated
                        } catch (e: HttpException) {
                            Log.e("http", "HttpException")
                            return@launchWhenCreated
                        }

                        if (response.isSuccessful && response.body() != null) {
                            val color = response.body()!!.color
                            result.color = color.name
                        }

                        val imgResponse = try {
                            RetrofitInstance.api.getPokemon(result.name)
                        } catch (e: IOException) {
                            Log.e("http", "IOException")
                            return@launchWhenCreated
                        } catch (e: HttpException) {
                            Log.e("http", "HttpException")
                            return@launchWhenCreated
                        }

                        if (imgResponse.isSuccessful && imgResponse.body() != null) {
                            val front_default = imgResponse.body()!!.sprites.other.home.front_default
                            println(front_default)
                            result.front_default = front_default
                        }
                    }

                    val adapter = PokemonsAdapter(results)

                    rvPokemons.adapter = adapter
                    rvPokemons.layoutManager = GridLayoutManager(this@MainActivity, 2)
                    hidepDialog()
                }
            }
        }

        findViewById<FloatingActionButton>(R.id.floatingActionButtonNext).setOnClickListener {
            val query = nextUrl?.split("&")
            val offset = query?.get(0)?.split("=")?.get(1)?.toInt()
            val limit = query?.get(1)?.split("=")?.get(1)?.toInt()

//            println(listOf(offset, limit))

            showpDialog()
            lifecycleScope.launchWhenCreated {
                val res = try {
                    RetrofitInstance.api.getPokemons(offset!!, limit!!)
                } catch (e: IOException) {
                    Log.e("io", "IOException")
                    return@launchWhenCreated
                } catch (e: HttpException) {
                    Log.e("http", "HttpException")
                    return@launchWhenCreated
                }

                if (res.isSuccessful && res.body() != null) {
                    val results = res.body()!!.results
                    nextUrl = res.body()!!.next?.split("?")?.get(1)
                    prevUrl = res.body()!!.previous?.toString()?.split("?")?.get(1)

                    if (prevUrl == null) {
                        findViewById<FloatingActionButton>(R.id.floatingActionButtonPrev).visibility = View.GONE
                    } else {
                        findViewById<FloatingActionButton>(R.id.floatingActionButtonPrev).visibility = View.VISIBLE
                    }

                    if (nextUrl == null) {
                        findViewById<FloatingActionButton>(R.id.floatingActionButtonNext).visibility = View.GONE
                    } else {
                        findViewById<FloatingActionButton>(R.id.floatingActionButtonNext).visibility = View.VISIBLE
                    }

                    for (result in results) {
                        val response = try {
                            RetrofitInstance.spieces.getSpecies(result.name)
                        } catch (e: IOException) {
                            Log.e("http", "IOException")
                            return@launchWhenCreated
                        } catch (e: HttpException) {
                            Log.e("http", "HttpException")
                            return@launchWhenCreated
                        }

                        if (response.isSuccessful && response.body() != null) {
                            val color = response.body()!!.color
                            result.color = color.name
                        }

                        val imgResponse = try {
                            RetrofitInstance.api.getPokemon(result.name)
                        } catch (e: IOException) {
                            Log.e("http", "IOException")
                            return@launchWhenCreated
                        } catch (e: HttpException) {
                            Log.e("http", "HttpException")
                            return@launchWhenCreated
                        }

                        if (imgResponse.isSuccessful && imgResponse.body() != null) {
                            val front_default = imgResponse.body()!!.sprites.other.home.front_default
                            println(front_default)
                            result.front_default = front_default
                        }
                    }

                    val adapter = PokemonsAdapter(results)

                    rvPokemons.adapter = adapter
                    rvPokemons.layoutManager = GridLayoutManager(this@MainActivity, 2)
                    hidepDialog()
                }
            }
        }
    }

    protected fun showpDialog() {
        if (!progressDialog.isShowing) progressDialog.show()
    }

    protected fun hidepDialog() {
        if (progressDialog.isShowing) progressDialog.dismiss()
    }
}