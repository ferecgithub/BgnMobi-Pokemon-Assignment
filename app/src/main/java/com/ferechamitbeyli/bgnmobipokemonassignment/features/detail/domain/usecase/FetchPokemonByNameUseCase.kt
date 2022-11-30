package com.ferechamitbeyli.bgnmobipokemonassignment.features.detail.domain.usecase

import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_detail.PokemonDetail
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.repository.pokemon.abstraction.PokemonRepository
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ferec Hamitbeyli on 30.11.2022.
 */

class FetchPokemonByNameUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(name: String): Flow<Resource<PokemonDetail>> = pokemonRepository.getPokemonByName(name = name)
}
