package com.ferechamitbeyli.bgnmobipokemonassignment.features.list.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ferechamitbeyli.bgnmobipokemonassignment.R
import com.ferechamitbeyli.bgnmobipokemonassignment.core.common.util.State
import com.ferechamitbeyli.bgnmobipokemonassignment.databinding.FragmentOverlayPermissionBinding
import com.ferechamitbeyli.bgnmobipokemonassignment.databinding.FragmentPokemonListBinding
import com.ferechamitbeyli.bgnmobipokemonassignment.features.list.presentation.adapter.PokemonListAdapter
import com.ferechamitbeyli.bgnmobipokemonassignment.features.list.presentation.viewmodel.PokemonListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonListFragment : Fragment() {

    private var _binding: FragmentPokemonListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PokemonListViewModel by viewModels()
    private val pokemonListAdapter by lazy { PokemonListAdapter() }

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

    }

    private fun setUpUI() {
        binding.recyclerViewPokemonList.apply {
            adapter = pokemonListAdapter
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


}