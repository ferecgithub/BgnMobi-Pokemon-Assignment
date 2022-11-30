package com.ferechamitbeyli.bgnmobipokemonassignment.features.detail.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ferechamitbeyli.bgnmobipokemonassignment.core.common.util.State
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_detail.PokemonDetail
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.util.Resource
import com.ferechamitbeyli.bgnmobipokemonassignment.features.detail.domain.usecase.FetchPokemonByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ferec Hamitbeyli on 30.11.2022.
 */

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val fetchPokemonByNameUseCase: FetchPokemonByNameUseCase
) : ViewModel() {

    private val mutablePokemon: MutableStateFlow<State<PokemonDetail>> =
        MutableStateFlow(State.Idle())
    val pokemon = mutablePokemon.asStateFlow()

    fun fetchPokemonByName(name: String) {
        viewModelScope.launch {
            fetchPokemonByNameUseCase(name = name).collect { pokemonResult ->
                when (pokemonResult) {
                    is Resource.Error -> {
                        mutablePokemon.update { pokemonResult.error?.let { State.Error(it) }!! }
                    }
                    is Resource.Loading -> {
                        mutablePokemon.update { State.Loading() }
                    }
                    is Resource.Success -> {
                        mutablePokemon.update { State.Success(pokemonResult.data) }
                    }
                }
            }
        }

    }
}