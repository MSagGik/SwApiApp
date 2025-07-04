package io.github.msaggik.cinema.presentation.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.msaggik.cinema.R
import io.github.msaggik.cinema.databinding.FragmentFilmsBinding
import io.github.msaggik.cinema.domain.model.film.Film
import io.github.msaggik.cinema.presentation.ui.adapters.FilmsAdapter
import io.github.msaggik.cinema.presentation.view_model.FilmsViewModel
import io.github.msaggik.cinema.presentation.view_model.state.FilmsState
import io.github.msaggik.util.closeKeyBoard
import io.github.msaggik.util.showAndHideOthers
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val DELAY_CLICK_FILM = 1000L
private const val FILTER_DEBOUNCE_DELAY = 1000L
private val FILTER_TOKEN = Any()
class FilmsFragment : Fragment() {

    private var _binding: FragmentFilmsBinding? = null
    private val binding: FragmentFilmsBinding get() = _binding!!

    private var viewArray: Array<View>? = null
    private var filterFilm = ""

    private var latestFilterText: String? = null
    private val handlerFilterFilm = Handler(Looper.getMainLooper())

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
            binding.somethingWentWrong
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loadingListFilm.showAndHideOthers(viewArray)

        binding.filmsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.filmsList.adapter = filmsAdapter

        filmsViewModel.getStateLiveData().observe(viewLifecycleOwner) {
            render(it)
        }

        binding.inputFilter.addTextChangedListener(inputSearchWatcher)
        binding.buttonClear.setOnClickListener(listener)
    }

    private val listener: View.OnClickListener = object : View.OnClickListener {
        @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
        override fun onClick(p0: View?) {
            when (p0?.id) {
                R.id.button_clear -> {
                    binding.inputFilter.setText("")
                    binding.buttonClear.visibility = View.GONE
                    requireContext().closeKeyBoard(binding.inputFilter)
                    filmsAdapter.setFilmList(filmList)
                    filmsAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private val inputSearchWatcher = object : TextWatcher {
        override fun beforeTextChanged(oldText: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onTextChanged(inputText: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val isInputText = !inputText.isNullOrEmpty()
            binding.buttonClear.isVisible = isInputText
            if (isInputText) {
                filterFilm = inputText.toString()
                filterDebounce(filterFilm)
            } else {
                filmsAdapter.setFilmList(filmList)
                filmsAdapter.notifyDataSetChanged()
            }
        }

        override fun afterTextChanged(resultText: Editable?) {
        }
    }

    fun filterDebounce(changedText: String) {
        if (latestFilterText == changedText) {
            return
        }

        latestFilterText = changedText
        handlerFilterFilm.removeCallbacksAndMessages(FILTER_TOKEN)

        val filterRunnable = Runnable { filterFilms(changedText) }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            handlerFilterFilm.postDelayed(
                filterRunnable,
                FILTER_TOKEN,
                FILTER_DEBOUNCE_DELAY
            )
        } else {
            val postTime = SystemClock.uptimeMillis() + FILTER_DEBOUNCE_DELAY
            handlerFilterFilm.postAtTime(
                filterRunnable,
                FILTER_DEBOUNCE_DELAY,
                postTime,
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filterFilms(filter: String) {
        val filterList = filmList.filter { it.title.contains(filter) }
        filmsAdapter.setFilmList(filterList)
        filmsAdapter.notifyDataSetChanged()
    }

    private fun render(state: FilmsState) {
        when (state) {
            is  FilmsState.Loading -> binding.loadingListFilm.showAndHideOthers(viewArray)
            is  FilmsState.Content -> showContent(state.films)
            is  FilmsState.Error -> {
                binding.somethingWentWrong.showAndHideOthers(viewArray)
                Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_SHORT).show()
            }
            is FilmsState.Empty -> binding.nothingFound.showAndHideOthers(viewArray)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showContent(films: List<Film>) {
        binding.filmsList.showAndHideOthers(viewArray)
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