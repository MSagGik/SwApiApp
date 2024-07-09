package com.msaggik.cinema.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.msaggik.cinema.R
import com.msaggik.cinema.domain.model.film.Film

class PlanetFragment : Fragment() {

    companion object {

        private const val PLANET_INSTANCE = "planet_instance"

        fun createArgs(id: Int): Bundle =
            bundleOf(
                PLANET_INSTANCE to id
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_planet, container, false)
    }
    
}