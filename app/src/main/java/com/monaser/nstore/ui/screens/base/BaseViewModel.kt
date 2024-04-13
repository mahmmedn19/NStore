package com.monaser.nstore.ui.screens.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<T, E>(initialState: T) : ViewModel() {
    abstract val TAG: String
    protected open fun log(message: String) {
        Log.e(TAG, message)
    }

    protected val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected val _effect = MutableSharedFlow<E>()
    val effect = _effect.asSharedFlow()
    private var job: Job? = null

    protected fun <T : BaseUiEffect> effectActionExecutor(
        _effect: MutableSharedFlow<T>,
        effect: T,
    ) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    protected fun <T> tryToExecute(
        block: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit = { e -> log(e.message ?: "Error") },
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        job?.cancel()
        job = viewModelScope.launch(dispatcher) {
            try {
                val result = block()
                log("Result: $result")
                onSuccess(result)
            } catch (e: Throwable) {
                log(e.message ?: "Error")
                onError(e)
            }
        }
    }

}

interface BaseUiEffect