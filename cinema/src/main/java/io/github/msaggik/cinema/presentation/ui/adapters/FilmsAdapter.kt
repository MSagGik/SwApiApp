package io.github.msaggik.cinema.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.msaggik.cinema.databinding.FilmItemBinding
import io.github.msaggik.cinema.domain.model.film.Film
import io.github.msaggik.ui.R

class FilmsAdapter (filmListAdd: List<Film>, private val filmClickListener: FilmClickListener) : RecyclerView.Adapter<FilmsAdapter.FilmViewHolder> () {

    private var filmList = filmListAdd

    fun setFilmList(filmListUpdate: List<Film>) {
        filmList = filmListUpdate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return FilmViewHolder(FilmItemBinding.inflate(layoutInspector, parent, false))
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(filmList[position])
        holder.itemView.setOnClickListener{
            filmClickListener.onFilmClick(filmList[position])
        }
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    fun interface FilmClickListener {
        fun onFilmClick(film: Film)
    }

    class FilmViewHolder(private val binding: FilmItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film) {
            binding.nameFilm.text = film.title
            binding.directorFilm.text = itemView.resources.getString(R.string.director_film_form, film.director)
            binding.producerFilm.text = itemView.resources.getString(R.string.producer_film_form, film.producer.joinToString(","))
            binding.releaseDateFilm.text = itemView.resources.getString(R.string.release_date_film_form, film.releaseDate?.year.toString())
        }
    }
}