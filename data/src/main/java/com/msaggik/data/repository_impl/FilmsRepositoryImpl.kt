package com.msaggik.data.repository_impl

import android.content.Context
import com.msaggik.cinema.domain.model.film.Film
import com.msaggik.cinema.domain.repository.FilmsRepository
import com.msaggik.common_ui.R
import com.msaggik.common_util.Resource
import com.msaggik.data.api.db.SwApiDataBase
import com.msaggik.data.api.db.mappers_db_to_cinema.toFilm
import com.msaggik.data.api.network.NetworkClient
import com.msaggik.data.api.network.dto.response.entities.films.FilmsNetDto
import com.msaggik.data.api.network.mappers_net_to_db.toFilmDb

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
            resource = when(response.resultCode) {
                -1 -> Resource.Error(context.getString(R.string.no_connection))
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