package com.ferechamitbeyli.bgnmobipokemonassignment.core.network.factory.base

import com.ferechamitbeyli.bgnmobipokemonassignment.core.network.service.base.BaseService
import retrofit2.Retrofit

/**
 * Abstract factory class that is responsible for the creation of Api services.
 *
 * [T] is api service type that will be created and returned.
 */
interface RetrofitServiceFactory<T : BaseService> {
    fun createService(retrofit: Retrofit): T
}