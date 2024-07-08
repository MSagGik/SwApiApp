package com.msaggik.data.api.network.retrofit

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.msaggik.data.api.network.NetworkClient
import com.msaggik.data.api.network.dto.response.Response
import retrofit2.Retrofit

class RetrofitNetworkClient(
    private val context: Context,
    retrofit: Retrofit
) : NetworkClient {

    private val restService = retrofit.create(RestSwApi::class.java)

    override fun doRequestGetFilms(): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        val response = restService.getFilms().execute()
        val body = response.body() ?: Response()
        body.resultCode = response.code()
        return body
    }

    override fun doRequestGetCharacter(id: Int): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        val response = restService.getCharacter(id).execute()
        val body = response.body() ?: Response()
        body.resultCode = response.code()
        return body
    }

    override fun doRequestGetPlanet(id: Int): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        val response = restService.getPlanet(id).execute()
        val body = response.body() ?: Response()
        body.resultCode = response.code()
        return body
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}