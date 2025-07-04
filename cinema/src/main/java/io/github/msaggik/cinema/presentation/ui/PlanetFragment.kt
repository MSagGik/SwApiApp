package io.github.msaggik.cinema.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import io.github.msaggik.ui.R
import io.github.msaggik.cinema.databinding.FragmentPlanetBinding
import io.github.msaggik.cinema.domain.model.planet.Planet
import io.github.msaggik.cinema.presentation.view_model.PlanetViewModel
import io.github.msaggik.cinema.presentation.view_model.state.PlanetState
import io.github.msaggik.util.showAndHideOthers
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PlanetFragment : Fragment() {

    companion object {

        private const val CHARACTER_NAME = "character_instance"
        private const val PLANET_ID = "planet_instance"

        fun createArgs(characterName: String, planetId: Int): Bundle =
            bundleOf(
                CHARACTER_NAME to characterName,
                PLANET_ID to planetId
            )
    }

    private val planetId by lazy {
        requireArguments().getInt(PLANET_ID)
    }

    private val characterName by lazy {
        requireArguments().getString(CHARACTER_NAME)
    }

    private var viewArray: Array<View>? = null

    private var _binding: FragmentPlanetBinding? = null
    private val binding: FragmentPlanetBinding get() = _binding!!

    private val planetViewModel: PlanetViewModel by viewModel() {
        parametersOf(planetId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlanetBinding.inflate(inflater, container, false)
        viewArray = arrayOf(
            binding.loadingListCharacter,
            binding.cardPlanet,
            binding.nothingFound,
            binding.somethingWentWrong
        )
        binding.nameCharacter.text = getString(R.string.home_world_form, characterName)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loadingListCharacter.showAndHideOthers(viewArray)

        planetViewModel.getStateLiveData().observe(viewLifecycleOwner) {
            render(it)
        }
        binding.buttonBack.setOnClickListener(listener)
    }

    private val listener: View.OnClickListener = object : View.OnClickListener {
        override fun onClick(p0: View?) {
            when (p0?.id) {
                io.github.msaggik.cinema.R.id.button_back -> {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun render(state: PlanetState) {
        when (state) {
            is  PlanetState.Loading -> binding.loadingListCharacter.showAndHideOthers(viewArray)
            is  PlanetState.Content -> showContent(state.planet)
            is  PlanetState.Error -> {
                binding.somethingWentWrong.showAndHideOthers(viewArray)
                Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_SHORT).show()
            }
            is PlanetState.Empty -> binding.nothingFound.showAndHideOthers(viewArray)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showContent(planet: Planet) {
        binding.namePlanet.text = planet.name
        binding.populationPlanet.text = getString(R.string.population_planet_form, planet.population.joinToString(","))
        binding.diameterPlanet.text = getString(R.string.diameter_planet_form, planet.diameter.toString())
        binding.typeClimatePlanet.text = getString(R.string.type_climate_planet_form, planet.climate)
        binding.gravityPlanet.text = getString(R.string.gravity_planet_form, planet.gravity)
        binding.typeLandscapePlanet.text = getString(R.string.type_landscape_planet_form, planet.terrain.joinToString(","))
        binding.cardPlanet.showAndHideOthers(viewArray)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewArray = emptyArray()
        viewArray = null
    }
}