package com.msaggik.cinema.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.msaggik.cinema.R
import com.msaggik.cinema.databinding.FragmentFilmsBinding
import com.msaggik.cinema.domain.model.film.Film
import com.msaggik.cinema.presentation.ui.adapters.FilmsAdapter
import com.msaggik.cinema.presentation.view_model.FilmsViewModel
import com.msaggik.cinema.presentation.view_model.state.FilmsState
import com.msaggik.common_util.Utils
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val DELAY_CLICK_FILM = 1000L
class FilmsFragment : Fragment() {

    private var _binding: FragmentFilmsBinding? = null
    private val binding: FragmentFilmsBinding get() = _binding!!

    private var viewArray: Array<View>? = null

    private val filmsViewModel: FilmsViewModel by viewModel()

    private var filmList: MutableList<Film> = mutableListOf()

    private val filmsAdapter: FilmsAdapter by lazy {
        FilmsAdapter(filmList) {
            if (clickFilmDebounce()) {
                filmSelection(it)
            }
        }
    }

    private val handlerClickFilm = Handler(Looper.getMainLooper())
    private var isClickFilmAllowed = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmsBinding.inflate(inflater, container, false)
        viewArray = arrayOf(
            binding.loadingListFilm,
            binding.filmsList,
            binding.nothingFound,
            binding.networkProblems
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.filmsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.filmsList.adapter = filmsAdapter

        filmsViewModel.getStateLiveData().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun render(state: FilmsState) {
        when (state) {
            is  FilmsState.Loading -> Utils.visibilityView(viewArray, binding.loadingListFilm)
            is  FilmsState.Content -> showContent(state.films)
            is  FilmsState.Error -> {
                Utils.visibilityView(viewArray, binding.networkProblems)
                Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_SHORT).show()
            }
            is FilmsState.Empty -> Utils.visibilityView(viewArray, binding.nothingFound)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showContent(films: List<Film>) {
        Utils.visibilityView(viewArray, binding.filmsList)
        filmList.clear()
        filmList.addAll(films)
        filmsAdapter.notifyDataSetChanged()
    }

    private fun clickFilmDebounce(): Boolean {
        val current = isClickFilmAllowed
        if (isClickFilmAllowed) {
            isClickFilmAllowed = false
            handlerClickFilm.postDelayed({ isClickFilmAllowed = true }, DELAY_CLICK_FILM)
        }
        return current
    }

    private fun filmSelection(film: Film) {
        findNavController().navigate(
            R.id.action_filmsFragment_to_characterFragment,
            CharacterFragment.createArgs(film))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewArray = emptyArray()
        viewArray = null
    }
}