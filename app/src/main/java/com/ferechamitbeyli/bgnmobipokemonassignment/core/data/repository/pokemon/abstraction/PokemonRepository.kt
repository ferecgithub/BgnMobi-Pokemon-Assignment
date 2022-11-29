package com.ferechamitbeyli.bgnmobipokemonassignment.core.data.repository.pokemon.abstraction

import androidx.paging.PagingData
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_detail.PokemonDetail
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_list.PokemonList
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.model.pokemon_list.PokemonListItem
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.util.Resource
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.dto.pokemon_list.PokemonListItemDto
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.util.NetworkConstants
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface PokemonRepository {
    suspend fun getPokemonList(): Flow<PagingData<PokemonListItem>>
    suspend fun getPokemonById(id: Int): Flow<Resource<PokemonDetail>>
    suspend fun getPokemonByName(name: String): Flow<Resource<PokemonDetail>>
}