package com.ferechamitbeyli.bgnmobipokemonassignment.core.network.di

import androidx.viewbinding.BuildConfig
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.factory.RemoteFactory
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.factory.service.PokemonServiceFactory
import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.service.pokemon.PokemonService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by Ferec Hamitbeyli on 28.11.2022
 *
 * [NetworkModule] contains information about
 * all network related dependencies.
 */
@[Module InstallIn(SingletonComponent::class)]
object NetworkModule {

    const val BASE_URL = "https://pokeapi.co/api/v2/"

    @[Provides Singleton]
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @[Provides Singleton]
    fun providePokemonService(retrofit: Retrofit): PokemonService {
        return PokemonServiceFactory.createService(retrofit = retrofit)
    }

    @[Provides Singleton]
    fun provideRetrofit(
        remoteFactory: RemoteFactory
    ): Retrofit {
        return remoteFactory.createRetrofit(
            url = BASE_URL,
            isDebug = BuildConfig.DEBUG
        )

    }

}