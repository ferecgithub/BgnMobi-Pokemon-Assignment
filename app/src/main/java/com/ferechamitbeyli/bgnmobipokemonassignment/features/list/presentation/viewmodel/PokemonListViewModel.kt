package com.ferechamitbeyli.bgnmobipokemonassignment.features.list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ferechamitbeyli.bgnmobipokemonassignment.core.common.util.State
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_list.PokemonListItem
import com.ferechamitbeyli.bgnmobipokemonassignment.features.list.domain.usecase.FetchPokemonListUseCase
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
class PokemonListViewModel @Inject constructor(
    private val fetchPokemonListUseCase: FetchPokemonListUseCase
) : ViewModel() {

    private val mutablePokemonList: MutableStateFlow<State<PagingData<PokemonListItem>>> = MutableStateFlow(State.Idle())
    val pokemonList = mutablePokemonList.asStateFlow()

    init {
        fetchPokemonList()
    }

    private fun fetchPokemonList() {
        viewModelScope.launch {
            fetchPokemonListUseCase()
                .cachedIn(viewModelScope)
                .collect { pokemonListResult ->
                    mutablePokemonList.update { State.Success(pokemonListResult) }
                }
        }
    }
}