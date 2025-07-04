package io.github.msaggik.data.api.network.dto.response.entities.character

import com.google.gson.annotations.SerializedName
import io.github.msaggik.data.api.network.dto.response.ResponseNetwork

data class CharacterNetDto(
    val name: String,
    var height: String? = null,
    var mass: String? = null,
    @SerializedName("hair_color")
    var hairColor: String? = null,
    @SerializedName("skin_color")
    var skinColor: String? = null,
    @SerializedName("eye_color")
    var eyeColor: String? = null,
    @SerializedName("birth_year")
    var birthYear: String? = null,
    var gender: String? = null,
    var homeworld: String? = null,
    var films: ArrayList<String> = arrayListOf(),
    var species: ArrayList<String> = arrayListOf(),
    var vehicles: ArrayList<String> = arrayListOf(),
    var starships: ArrayList<String> = arrayListOf(),
    var created: String? = null,
    var edited: String? = null,
    var url: String? = null
) : ResponseNetwork()
