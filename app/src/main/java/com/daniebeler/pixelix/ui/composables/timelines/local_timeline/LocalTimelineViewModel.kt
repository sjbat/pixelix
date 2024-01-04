package com.daniebeler.pixelix.ui.composables.timelines.local_timeline

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniebeler.pixelix.common.Resource
import com.daniebeler.pixelix.domain.repository.CountryRepository
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
        loadMorePosts()
    }

    fun loadMorePosts() {

        val maxId = if (localTimelineState.localTimeline.isEmpty()) {
            ""
        } else {
            localTimelineState.localTimeline.last().id
        }

        repository.getLocalTimeline(maxId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    localTimelineState = localTimelineState.copy(
                        localTimeline = localTimelineState.localTimeline + (result.data
                            ?: emptyList()),
                        error = "",
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    localTimelineState = localTimelineState.copy(
                        localTimeline = localTimelineState.localTimeline,
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    localTimelineState = localTimelineState.copy(
                        localTimeline = localTimelineState.localTimeline,
                        error = "",
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)

    }
}