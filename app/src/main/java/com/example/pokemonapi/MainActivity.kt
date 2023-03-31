package com.example.pokemonapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    var pokeImageURL = ""
    var pokeName = ""
    var pokeType = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getPokeImageURL()
        getPokeName()
        getPokeType()
        val button = findViewById<Button>(R.id.pokeButton)
        val imageview = findViewById<ImageView>(R.id.poke_Image)
        val textName = findViewById<TextView>(R.id.poke_Name) as TextView
        val textType = findViewById<TextView>(R.id.poke_Type) as TextView
        getNextPokemon(button,imageview,textName,textType)
    }

    var pokemonID = Random.nextInt(500)

    private fun getPokeImageURL() {
        val client = AsyncHttpClient()
        client["https://pokeapi.co/api/v2/pokemon/$pokemonID/", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Pokemon", "response successful")
                pokeImageURL = json.jsonObject.getJSONObject("sprites").getString("back_default")
                Log.d("pokeImageURL", "pokemon image URL set")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("pokemon Error", errorResponse)
            }
        }]
    }

    private fun getPokeName() {
        val client = AsyncHttpClient()
        client["https://pokeapi.co/api/v2/pokemon/$pokemonID/", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Pokemon", "response successful")
                pokeName = json.jsonObject.getJSONObject("species").getString("name")
                Log.d("pokeName", "pokemon Name set")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("pokemon Error", errorResponse)
            }
        }]
    }

    private fun getPokeType() {
        val client = AsyncHttpClient()
        client["https://pokeapi.co/api/v2/pokemon/$pokemonID/", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Pokemon", "response successful")
                pokeType = json.jsonObject.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name")
                Log.d("pokeType", "pokemon Type set")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("pokemon Error", errorResponse)
            }
        }]
    }

    private fun getNextPokemon(button: Button, imageView: ImageView, textName: TextView, textType: TextView) {
        button.setOnClickListener {
            pokemonID = Random.nextInt(500)
            getPokeImageURL()
            getPokeName()
            getPokeType()

            Glide.with(this)
                . load(pokeImageURL)
                .fitCenter()
                .into(imageView)

            textName.text = pokeName
            textType.text = pokeType
        }
    }


}