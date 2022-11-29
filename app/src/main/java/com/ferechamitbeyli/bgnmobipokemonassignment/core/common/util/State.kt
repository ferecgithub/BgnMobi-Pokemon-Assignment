package com.ferechamitbeyli.bgnmobipokemonassignment.core.common.util

sealed class State<T>(
    val data: T? = null,
    val error: UiText? = null,
) {
    class Success<T>(data: T? = null) : State<T>(data)

    class Error<T>(text: UiText, data: T? = null) :
        State<T>(data = data, error = text)

    class Loading<T>(data: T? = null) : State<T>(data)

    class Idle<T> : State<T>()
}
