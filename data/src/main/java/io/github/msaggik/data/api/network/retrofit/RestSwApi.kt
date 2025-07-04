package io.github.msaggik.data.api.network.retrofit

import io.github.msaggik.data.api.network.dto.response.entities.character.CharacterNetDto
import io.github.msaggik.data.api.network.dto.response.entities.films.FilmsNetDto
import io.github.msaggik.data.api.network.dto.response.entities.planet.PlanetNetDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RestSwApi {

    @GET("/api/films")
    fun getFilms(): Call<FilmsNetDto>

    @GET("/api/people/{id}")
    fun getCharacter(@Path("id") id: Int): Call<CharacterNetDto>

    @GET("/api/planets/{id}")
    fun getPlanet(@Path("id") id: Int): Call<PlanetNetDto>
}