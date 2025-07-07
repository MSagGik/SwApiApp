package io.github.msaggik.cinema.presentation.ui

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
import io.github.msaggik.cinema.R
import io.github.msaggik.cinema.databinding.FragmentCharacterBinding
import io.github.msaggik.cinema.domain.model.character.Character
import io.github.msaggik.cinema.domain.model.film.Film
import io.github.msaggik.cinema.presentation.ui.adapters.CharacterAdapter
import io.github.msaggik.cinema.presentation.view_model.CharactersViewModel
import io.github.msaggik.cinema.presentation.view_model.state.CharactersState
import io.github.msaggik.util.showAndHideOthers
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
            binding.somethingWentWrong
        )
        binding.nameFilm.text = film?.title
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loadingListCharacter.showAndHideOthers(viewArray)

        binding.characterList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.characterList.adapter = characterAdapter

        charactersViewModel.getStateLiveData().observe(viewLifecycleOwner) {
            render(it)
        }
        binding.buttonBack.setOnClickListener(listener)
    }

    private val listener: View.OnClickListener = object : View.OnClickListener {
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
            PlanetFragment.createArgs(character.name, character.homeWorld)
        )
    }

    private fun render(state: CharactersState) {
        when (state) {
            is  CharactersState.Loading -> binding.loadingListCharacter.showAndHideOthers(viewArray)
            is  CharactersState.Content -> showContent(state.characters)
            is  CharactersState.Error -> {
                binding.somethingWentWrong.showAndHideOthers(viewArray)
                Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_SHORT).show()
            }
            is CharactersState.Empty ->
                binding.nothingFound.showAndHideOthers(viewArray)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showContent(characters: List<Character>) {
        binding.characterList.showAndHideOthers(viewArray)
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