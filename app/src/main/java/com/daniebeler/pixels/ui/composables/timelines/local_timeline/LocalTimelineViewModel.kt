package com.daniebeler.pixels.ui.composables.timelines.local_timeline

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniebeler.pixels.common.Resource
import com.daniebeler.pixels.domain.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LocalTimelineViewModel @Inject constructor(
    private val repository: CountryRepository
): ViewModel() {

    var localTimelineState by mutableStateOf(LocalTimelineState())

    init {
        getLocalTimeline()
    }

    private fun getLocalTimeline() {
        repository.getLocalTimeline().onEach { result ->
            localTimelineState = when (result) {
                is Resource.Success -> {
                    LocalTimelineState(localTimeline = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    LocalTimelineState(error = result.message ?: "An unexpected error occurred")
                }

                is Resource.Loading -> {
                    LocalTimelineState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}