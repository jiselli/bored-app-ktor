package com.exploring.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exploring.source.model.ActivityResponse
import com.exploring.source.repository.ActivityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val activityRepository: ActivityRepository,
) : ViewModel() {

    private val _viewState = MutableStateFlow(MainViewState.Initial)
    val viewState: StateFlow<MainViewState> = _viewState

    init {
        requestActivity()
    }

    fun requestActivity() {
        _viewState.update {
            it.copy(
                isLoading = true,
                errorMessage = null,
                data = null
            )
        }
        viewModelScope.launch {
            activityRepository.requireActivity()
                .collect { item ->
                    if (item.success) {
                        _viewState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = null,
                                data = item.responseBody as ActivityResponse
                            )
                        }
                    } else {
                        _viewState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = "${item.statusCode} ${item.errorMessage}",
                                data = null
                            )
                        }
                    }
                }
        }
    }
}

data class MainViewState(
    val isLoading: Boolean,
    val errorMessage: String?,
    val data: ActivityResponse?,
) {
    companion object {
        val Initial = MainViewState(
            isLoading = true,
            errorMessage = null,
            data = null
        )
    }
}