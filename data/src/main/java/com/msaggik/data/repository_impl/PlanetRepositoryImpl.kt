package com.msaggik.data.repository_impl

import android.content.Context
import com.msaggik.cinema.domain.model.planet.Planet
import com.msaggik.cinema.domain.repository.PlanetRepository
import com.msaggik.common_ui.R
import com.msaggik.common_util.Resource
import com.msaggik.data.api.db.SwApiDataBase
import com.msaggik.data.api.db.mappers_db_to_cinema.toPlanet
import com.msaggik.data.api.network.NetworkClient
import com.msaggik.data.api.network.dto.response.entities.planet.PlanetNetDto
import com.msaggik.data.api.network.mappers_net_to_db.toPlanetDb

class PlanetRepositoryImpl(
    private val context: Context,
    private val network: NetworkClient,
    private val dataBase: SwApiDataBase
) : PlanetRepository {

    private val planetsDao = dataBase.planetsDao()

    override fun getPlanet(id: Int): Resource<Planet> {

        val resource: Resource<Planet>
        var planetDb = planetsDao.getDbPlanet(id)

        if(planetDb != null) {
            resource = Resource.Success(planetDb.toPlanet())
        } else {
            val response = network.doRequestGetPlanet(id)
            resource = when(response.resultCode) {
                -1 -> Resource.Error(context.getString(R.string.no_connection))
                200 -> {
                    val planetNet = (response as PlanetNetDto).toPlanetDb()
                    planetsDao.insertDbPlanet(planetNet)
                    Resource.Success(planetNet.toPlanet())
                }
                else -> Resource.Error(context.getString(R.string.error))
            }
        }
        return resource
    }
}