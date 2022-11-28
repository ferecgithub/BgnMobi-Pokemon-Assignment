package com.ferechamitbeyli.bgnmobipokemonassignment.core.data.repository.pokemon.abstraction

import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_detail.PokemonDetail
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_list.PokemonList
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getPokemonList(): Flow<Resource<PokemonList>>
    suspend fun getPokemonById(id: Int): Flow<Resource<PokemonDetail>>
    suspend fun getPokemonByName(name: String): Flow<Resource<PokemonDetail>>
}