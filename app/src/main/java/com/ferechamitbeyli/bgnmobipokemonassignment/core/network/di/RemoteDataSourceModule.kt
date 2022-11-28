package com.ferechamitbeyli.bgnmobipokemonassignment.core.network.di

import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.datasource.pokemon.abstraction.PokemonRemoteDataSource
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.datasource.pokemon.implementation.PokemonRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Hilt module that provides/binds implementation of remote data sources.
 */
@[Module InstallIn(SingletonComponent::class)]
interface RemoteDataSourceModule {

    @get:Binds
    val PokemonRemoteDataSourceImpl.pokemonRemoteDataSource: PokemonRemoteDataSource

}