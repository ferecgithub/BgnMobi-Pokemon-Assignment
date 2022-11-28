package com.ferechamitbeyli.bgnmobipokemonassignment.core.data.di

import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.executor.abstraction.ExecutionThread
import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.executor.implementation.ExecutionThreadImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
interface ExecutorModule {

    @get:[Binds Singleton]
    val ExecutionThreadImpl.executionThread: ExecutionThread
}