package com.msaggik.data.api.network

import com.msaggik.data.api.network.dto.response.Response

interface NetworkClient {
    fun doRequestGetFilms(): Response
    fun doRequestGetCharacter(id: Int): Response
    fun doRequestGetPlanet(id: Int): Response
}