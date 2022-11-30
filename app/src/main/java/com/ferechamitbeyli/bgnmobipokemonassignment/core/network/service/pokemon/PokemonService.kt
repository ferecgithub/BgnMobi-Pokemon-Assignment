package com.ferechamitbeyli.bgnmobipokemonassignment.core.network.service.pokemon

import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.dto.pokemon_detail.PokemonDetailDto
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.dto.pokemon_list.PokemonListDto
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.endpoint.PokemonEndpoints.POKEMON_LIST
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.service.base.BaseService
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.util.NetworkConstants.POKEMON_LIST_LIMIT
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.util.NetworkConstants.POKEMON_LIST_OFFSET
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Ferec Hamitbeyli on 28.11.2022
 *
 * [PokemonService] is a [Retrofit] service
 * that contains api functions regarding pokemon
 * information for Pokemon Assignment Application.
 */

interface PokemonService : BaseService {

    @GET(POKEMON_LIST)
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<PokemonListDto>

    @GET("$POKEMON_LIST/{id}")
    suspend fun getPokemonById(
        @Path("id") id: Int,
    ): Response<PokemonDetailDto>

    @GET("$POKEMON_LIST/{name}")
    suspend fun getPokemonByName(
        @Path("name") name: String,
    ): Response<PokemonDetailDto>
}