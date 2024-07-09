package com.msaggik.cinema.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.msaggik.cinema.R
import com.msaggik.cinema.domain.model.film.Film

class CharacterFragment : Fragment() {

    companion object {

        private const val FILM_INSTANCE = "film_instance"

        fun createArgs(film: Film): Bundle =
            bundleOf(
                FILM_INSTANCE to film
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character, container, false)
    }

}