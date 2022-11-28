package com.ferechamitbeyli.bgnmobipokemonassignment.core.data.executor.implementation

import com.ferechamitbeyli.bgnmobipokemonassignment.core.data.executor.abstraction.ExecutionThread
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ExecutionThreadImpl @Inject constructor() : ExecutionThread {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
}