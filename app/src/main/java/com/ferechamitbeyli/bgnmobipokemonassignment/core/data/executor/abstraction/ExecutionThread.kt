package com.ferechamitbeyli.bgnmobipokemonassignment.core.data.executor.abstraction

import kotlinx.coroutines.CoroutineDispatcher

interface ExecutionThread {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}