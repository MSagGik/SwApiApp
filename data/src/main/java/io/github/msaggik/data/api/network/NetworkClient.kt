package io.github.msaggik.data.api.network

import io.github.msaggik.data.api.network.dto.response.ResponseNetwork

interface NetworkClient {
    fun doRequestGetFilms(): ResponseNetwork
    fun doRequestGetCharacter(id: Int): ResponseNetwork
    fun doRequestGetPlanet(id: Int): ResponseNetwork
}