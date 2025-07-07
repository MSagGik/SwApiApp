package io.github.msaggik.data.api.network.retrofit

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import io.github.msaggik.data.api.network.NetworkClient
import io.github.msaggik.data.api.network.dto.response.ResponseNetwork
import retrofit2.Call
import retrofit2.Retrofit
import javax.net.ssl.SSLHandshakeException

class RetrofitNetworkClient(
    private val context: Context,
    retrofit: Retrofit
) : NetworkClient {

    private val restService = retrofit.create(RestSwApi::class.java)

    override fun doRequestGetFilms(): ResponseNetwork {
        return safeRequest(restService.getFilms())
    }

    override fun doRequestGetCharacter(id: Int): ResponseNetwork {
        return safeRequest(restService.getCharacter(id))
    }

    override fun doRequestGetPlanet(id: Int): ResponseNetwork {
        return safeRequest(restService.getPlanet(id))
    }

    private fun safeRequest(call: Call<out ResponseNetwork>): ResponseNetwork {
        if (!isConnected()) {
            return ResponseNetwork().apply {
                resultNetworkCode = -1
            }
        }

        return try {
            val response = call.execute()
            response.body()?.apply {
                resultNetworkCode = response.code()
            } ?: ResponseNetwork().apply {
                resultNetworkCode = response.code()
            }
        } catch (e: Exception) {
            ResponseNetwork().apply {
                resultNetworkCode = -2
            }
        }
    }

    private fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
}