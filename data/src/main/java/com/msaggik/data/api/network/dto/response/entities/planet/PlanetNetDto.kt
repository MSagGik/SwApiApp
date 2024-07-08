package com.msaggik.data.api.network.dto.response.entities.planet

import com.google.gson.annotations.SerializedName
import com.msaggik.data.api.network.dto.response.Response

data class PlanetNetDto(
    var name: String? = null,
    @SerializedName("rotation_period")
    var rotationPeriod: String? = null,
    @SerializedName("orbital_period")
    var orbitalPeriod: String? = null,
    var diameter: Int? = 0,
    var climate: String? = null,
    var gravity: String? = null,
    var terrain: String? = null,
    @SerializedName("surface_water")
    var surfaceWater: String? = null,
    var population: String? = null,
    var residents: ArrayList<String> = arrayListOf(),
    var films: ArrayList<String> = arrayListOf(),
    var created: String? = null,
    var edited: String? = null,
    var url: String? = null
)  : Response()