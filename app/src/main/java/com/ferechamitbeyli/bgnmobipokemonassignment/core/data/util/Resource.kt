package com.ferechamitbeyli.bgnmobipokemonassignment.core.data.util

import com.ferechamitbeyli.bgnmobipokemonassignment.core.common.util.UiText

sealed class Resource<T>(
    val data: T? = null,
    val error: UiText? = null,
    val errorCode: Int? = null
) {
    class Success<T>(data: T? = null) : Resource<T>(data)

    class Error<T>(code: Int? = null, text: UiText, data: T? = null) :
        Resource<T>(data = data, errorCode = code, error = text)

    class Loading<T>(data: T? = null) : Resource<T>(data)
}