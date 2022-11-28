package com.ferechamitbeyli.bgnmobipokemonassignment.core.network.factory.service

import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.factory.base.RetrofitServiceFactory
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.service.pokemon.PokemonService
import retrofit2.Retrofit

/**
 * [RetrofitServiceFactory] that creates [PokemonService] instance.
 */
object PokemonServiceFactory : RetrofitServiceFactory<PokemonService> {
    override fun createService(retrofit: Retrofit): PokemonService {
        return retrofit.create(PokemonService::class.java)
    }
}