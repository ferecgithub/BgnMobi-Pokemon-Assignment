package com.ferechamitbeyli.bgnmobipokemonassignment.features.detail.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.ferechamitbeyli.bgnmobipokemonassignment.R
import com.ferechamitbeyli.bgnmobipokemonassignment.core.common.helper.UIHelpers.setCurrentLabelOntoActionBar
import com.ferechamitbeyli.bgnmobipokemonassignment.core.common.util.State
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_detail.PokemonDetail
import com.ferechamitbeyli.bgnmobipokemonassignment.core.service.OverlayService
import com.ferechamitbeyli.bgnmobipokemonassignment.databinding.FragmentPokemonDetailBinding
import com.ferechamitbeyli.bgnmobipokemonassignment.features.detail.presentation.viewmodel.PokemonDetailViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {
    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PokemonDetailViewModel by viewModels()
    private val arguments: PokemonDetailFragmentArgs by navArgs()

    private lateinit var pokemonDetail: PokemonDetail

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        setUpUI()
        observeFlows()
    }

    private fun setOnClickListeners() {
        binding.buttonPokemonDetailOpenOverlay.setOnClickListener {
            val service = Intent(requireActivity(), OverlayService::class.java)
            service.putExtra("name", pokemonDetail.name)
            service.putExtra("front_img", pokemonDetail.sprites?.front_default)
            service.putExtra("back_img", pokemonDetail.sprites?.back_default)
            requireActivity().startService(service)
        }
    }

    private fun setUpUI() {
        setCurrentLabelOntoActionBar(
            requireActivity() as AppCompatActivity,
            findNavController().currentDestination?.label.toString()
        )

        requireActivity().findViewById<Toolbar>(R.id.toolbar_main).isVisible = true

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        arguments.name?.let { name ->
            viewModel.fetchPokemonByName(name)
        }
    }

    private fun observeFlows() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pokemon
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest { result ->
                    when (result) {
                        is State.Success -> {
                            result.data?.let { pokemon ->
                                handleViewVisibilities(false)
                                populateViews(pokemon)
                                pokemonDetail = pokemon
                            }
                        }
                        is State.Error -> {
                            handleViewVisibilities(false)
                            Snackbar.make(
                                binding.root,
                                result.error?.asString(requireContext()).toString(),
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        is State.Idle -> {
                            /** NO-OP **/
                        }
                        is State.Loading -> {
                            handleViewVisibilities(true)
                        }
                    }
                }
        }
    }

    private fun populateViews(pokemonDetail: PokemonDetail) {
        binding.imageViewPokemonDetail.load(pokemonDetail.sprites?.front_default) {
            crossfade(true)
            crossfade(1500)
            error(R.drawable.ic_warning)
        }

        binding.textViewPokemonDetailName.text =
            getString(R.string.pokemon_name, pokemonDetail.name)
        binding.textViewPokemonDetailHeight.text =
            getString(R.string.pokemon_height, pokemonDetail.height.toString())
        binding.textViewPokemonDetailWeight.text =
            getString(R.string.pokemon_weight, pokemonDetail.weight.toString())
        binding.buttonPokemonDetailOpenOverlay.text =
            getString(R.string.showInOverlay, pokemonDetail.name)
    }

    private fun handleViewVisibilities(isLoading: Boolean) {
        binding.progressBarPokemonDetail.isVisible = isLoading
        binding.imageViewPokemonDetail.isVisible = !isLoading
        binding.textViewPokemonDetailName.isVisible = !isLoading
        binding.textViewPokemonDetailHeight.isVisible = !isLoading
        binding.textViewPokemonDetailWeight.isVisible = !isLoading
        binding.buttonPokemonDetailOpenOverlay.isVisible = !isLoading
    }
}