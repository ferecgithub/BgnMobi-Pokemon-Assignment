package com.ferechamitbeyli.bgnmobipokemonassignment.core.data.di

import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.repository.pokemon.abstraction.PokemonRepository
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.repository.pokemon.implementation.PokemonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
interface RepositoryModule {

    @get:Binds
    val PokemonRepositoryImpl.pokemonRepository: PokemonRepository

}