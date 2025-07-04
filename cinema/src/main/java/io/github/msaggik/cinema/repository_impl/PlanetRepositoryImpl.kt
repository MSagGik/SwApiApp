package io.github.msaggik.cinema.repository_impl

import android.content.Context
import io.github.msaggik.cinema.domain.model.planet.Planet
import io.github.msaggik.cinema.domain.repository.PlanetRepository
import io.github.msaggik.ui.R
import io.github.msaggik.util.Resource
import io.github.msaggik.data.api.db.SwApiDataBase
import io.github.msaggik.cinema.repository_impl.mappers_db_to_cinema.toPlanet
import io.github.msaggik.data.api.network.NetworkClient
import io.github.msaggik.data.api.network.dto.response.entities.planet.PlanetNetDto
import io.github.msaggik.data.api.network.mappers_net_to_db.toPlanetDb

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
            resource = when(response.resultNetworkCode) {
                -1 -> Resource.Error(context.getString(R.string.no_connection))
                -2 -> Resource.Error(context.getString(R.string.something_went_wrong))
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