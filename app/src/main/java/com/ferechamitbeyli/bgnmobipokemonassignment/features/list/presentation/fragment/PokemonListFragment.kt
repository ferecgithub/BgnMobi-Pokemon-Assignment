package com.ferechamitbeyli.bgnmobipokemonassignment.features.list.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.ferechamitbeyli.bgnmobipokemonassignment.R
import com.ferechamitbeyli.bgnmobipokemonassignment.core.common.helper.UIHelpers
import com.ferechamitbeyli.bgnmobipokemonassignment.core.common.helper.UIHelpers.handleActionBarVisibility
import com.ferechamitbeyli.bgnmobipokemonassignment.core.common.helper.UIHelpers.setCurrentLabelOntoActionBar
import com.ferechamitbeyli.bgnmobipokemonassignment.core.common.util.OnItemClickListener
import com.ferechamitbeyli.bgnmobipokemonassignment.core.common.util.State
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_list.PokemonListItem
import com.ferechamitbeyli.bgnmobipokemonassignment.databinding.FragmentOverlayPermissionBinding
import com.ferechamitbeyli.bgnmobipokemonassignment.databinding.FragmentPokemonListBinding
import com.ferechamitbeyli.bgnmobipokemonassignment.features.list.presentation.adapter.PokemonListAdapter
import com.ferechamitbeyli.bgnmobipokemonassignment.features.list.presentation.adapter.PokemonListLoadStateAdapter
import com.ferechamitbeyli.bgnmobipokemonassignment.features.list.presentation.viewmodel.PokemonListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonListFragment : Fragment(), OnItemClickListener<PokemonListItem> {

    private var _binding: FragmentPokemonListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PokemonListViewModel by viewModels()
    private val pokemonListAdapter by lazy { PokemonListAdapter(this@PokemonListFragment) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        setUpUI()
        observeFlows()
    }

    private fun setOnClickListeners() {
        binding.layoutPokemonListError.buttonLayoutErrorTryAgain.setOnClickListener {
            pokemonListAdapter.retry()
        }
    }

    private fun setUpUI() {
        setCurrentLabelOntoActionBar(
            requireActivity() as AppCompatActivity,
            findNavController().currentDestination?.label.toString()
        )

        requireActivity().findViewById<Toolbar>(R.id.toolbar_main).isVisible = true

//        handleActionBarVisibility(
//            requireActivity() as AppCompatActivity,
//            findNavController().currentDestination?.label.toString()
//        )

        binding.recyclerViewPokemonList.apply {
            adapter = pokemonListAdapter.withLoadStateHeaderAndFooter(
                header = PokemonListLoadStateAdapter { pokemonListAdapter.retry() },
                footer = PokemonListLoadStateAdapter { pokemonListAdapter.retry() }
            )
            pokemonListAdapter.addLoadStateListener { loadState ->
                binding.recyclerViewPokemonList.isVisible =
                    loadState.source.refresh is LoadState.NotLoading
                binding.progressBarPokemonList.isVisible =
                    loadState.source.refresh is LoadState.Loading
                binding.layoutPokemonListError.root.isVisible =
                    loadState.source.refresh is LoadState.Error
            }
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun observeFlows() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pokemonList
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest { result ->
                    when (result) {
                        is State.Success -> {
                            result.data?.let { pokemonPagingData ->
                                pokemonListAdapter.submitData(pokemonPagingData)
                            }
                        }
                        // The loading and error states will be handled by own structures of Paging3
                        else -> {
                            /** NO-OP **/
                        }
                    }
                }
        }
    }

    override fun onItemClick(position: Int, model: PokemonListItem) {
        if (model.name.isNotBlank()) {
            navigateToPokemonDetailFragment(name = model.name)
        }

    }

    private fun navigateToPokemonDetailFragment(name: String) {
        if (findNavController().currentDestination?.id == R.id.pokemonListFragment) {
            val action = PokemonListFragmentDirections
                .actionPokemonListFragmentToPokemonDetailFragment(name = name)
            findNavController().navigate(action)
        }
    }
}