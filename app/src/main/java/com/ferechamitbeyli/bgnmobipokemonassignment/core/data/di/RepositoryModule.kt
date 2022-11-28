package com.ferechamitbeyli.bgnmobipokemonassignment.core.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
interface RepositoryModule {

    /*
    @get:Binds
    val ProductRepositoryImpl.productRepository: ProductRepository
     */
}