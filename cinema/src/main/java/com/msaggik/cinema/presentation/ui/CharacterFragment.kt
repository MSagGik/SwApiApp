package com.msaggik.cinema.presentation.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.msaggik.cinema.R
import com.msaggik.cinema.databinding.FragmentCharacterBinding
import com.msaggik.cinema.databinding.FragmentFilmsBinding
import com.msaggik.cinema.domain.model.character.Character
import com.msaggik.cinema.domain.model.film.Film
import com.msaggik.cinema.presentation.ui.adapters.CharacterAdapter
import com.msaggik.cinema.presentation.ui.adapters.FilmsAdapter
import com.msaggik.cinema.presentation.view_model.CharactersViewModel
import com.msaggik.cinema.presentation.view_model.FilmsViewModel
import com.msaggik.cinema.presentation.view_model.state.CharactersState
import com.msaggik.cinema.presentation.view_model.state.FilmsState
import com.msaggik.common_util.Utils
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val DELAY_CLICK_CHARACTER = 1000L
class CharacterFragment : Fragment() {

    companion object {

        private const val FILM_INSTANCE = "film_instance"

        fun createArgs(film: Film): Bundle =
            bundleOf(
                FILM_INSTANCE to film
            )
    }

    private val film by lazy {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(FILM_INSTANCE, Film::class.java)
        } else {
            requireArguments().getParcelable(FILM_INSTANCE)
        }
    }

    private var _binding: FragmentCharacterBinding? = null
    private val binding: FragmentCharacterBinding get() = _binding!!

    private val charactersViewModel: CharactersViewModel by viewModel() {
        parametersOf(film?.id, film?.characters)
    }

    private var viewArray: Array<View>? = null

    private var characterList: MutableList<Character> = mutableListOf()

    private val characterAdapter: CharacterAdapter by lazy {
        CharacterAdapter(characterList) {
            if (clickCharacterDebounce()) {
                characterSelection(it)
            }
        }
    }

    private val handlerClickCharacter = Handler(Looper.getMainLooper())
    private var isClickCharacterAllowed = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        viewArray = arrayOf(
            binding.loadingListCharacter,
            binding.characterList,
            binding.nothingFound,
            binding.networkProblems
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Utils.visibilityView(viewArray, binding.loadingListCharacter)

        binding.characterList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.characterList.adapter = characterAdapter

        charactersViewModel.getStateLiveData().observe(viewLifecycleOwner) {
            render(it)
        }
        binding.buttonBack.setOnClickListener(listener)
    }

    private val listener: View.OnClickListener = object : View.OnClickListener {
        @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
        override fun onClick(p0: View?) {
            when (p0?.id) {
                R.id.button_back -> {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun clickCharacterDebounce(): Boolean {
        val current = isClickCharacterAllowed
        if (isClickCharacterAllowed) {
            isClickCharacterAllowed = false
            handlerClickCharacter.postDelayed({ isClickCharacterAllowed = true }, DELAY_CLICK_CHARACTER)
        }
        return current
    }

    private fun characterSelection(character: Character) {
        findNavController().navigate(
            R.id.action_characterFragment_to_planetFragment,
            PlanetFragment.createArgs(character.homeWorld))
    }

    private fun render(state: CharactersState) {
        when (state) {
            is  CharactersState.Loading -> Utils.visibilityView(viewArray, binding.loadingListCharacter)
            is  CharactersState.Content -> showContent(state.characters)
            is  CharactersState.Error -> {
                Utils.visibilityView(viewArray, binding.networkProblems)
                Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_SHORT).show()
            }
            is CharactersState.Empty -> Utils.visibilityView(viewArray, binding.nothingFound)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showContent(characters: List<Character>) {
        Utils.visibilityView(viewArray, binding.characterList)
        characterList.clear()
        characterList.addAll(characters)
        characterAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewArray = emptyArray()
        viewArray = null
    }
}