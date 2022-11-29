package com.ferechamitbeyli.bgnmobipokemonassignment.features.list.domain.usecase

import androidx.paging.PagingData
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_list.PokemonListItem
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.repository.pokemon.abstraction.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ferec Hamitbeyli on 29.11.2022
 */

class FetchPokemonListUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(): Flow<PagingData<PokemonListItem>> =
        pokemonRepository.getPokemonList()
}