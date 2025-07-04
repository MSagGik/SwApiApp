package io.github.msaggik.cinema.repository_impl

import android.content.Context
import io.github.msaggik.cinema.domain.model.film.Film
import io.github.msaggik.cinema.domain.repository.FilmsRepository
import io.github.msaggik.ui.R
import io.github.msaggik.util.Resource
import io.github.msaggik.data.api.db.SwApiDataBase
import io.github.msaggik.cinema.repository_impl.mappers_db_to_cinema.toFilm
import io.github.msaggik.data.api.network.NetworkClient
import io.github.msaggik.data.api.network.dto.response.entities.films.FilmsNetDto
import io.github.msaggik.data.api.network.mappers_net_to_db.toFilmDb

class FilmsRepositoryImpl(
    private val context: Context,
    private val network: NetworkClient,
    private val dataBase: SwApiDataBase
) : FilmsRepository {

    private val filmsDao = dataBase.filmsDao()

    override fun getFilms(): Resource<List<Film>> {
        val resource: Resource<List<Film>>

        var filmsDb = filmsDao.getDbFilms()

        if(filmsDb.isNotEmpty()) {
            resource = Resource.Success(filmsDb.map { it.toFilm() }.sortedBy { it.episodeId })
        } else {
            val response = network.doRequestGetFilms()
            resource = when(response.resultNetworkCode) {
                -1 -> Resource.Error(context.getString(R.string.no_connection))
                -2 -> Resource.Error(context.getString(R.string.something_went_wrong))
                200 -> {
                    val listFilmNet = (response as FilmsNetDto).results.map { it.toFilmDb() } ?: emptyList()
                    filmsDao.insertDbFilms(listFilmNet)
                    Resource.Success(listFilmNet.map { it.toFilm() }.sortedBy { it.episodeId})
                }
                else -> Resource.Error(context.getString(R.string.error))
            }
        }
        return resource
    }
}