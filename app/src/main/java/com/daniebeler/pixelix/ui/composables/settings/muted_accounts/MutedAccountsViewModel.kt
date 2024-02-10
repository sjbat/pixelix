package com.daniebeler.pixelix.ui.composables.settings.muted_accounts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniebeler.pixelix.common.Resource
import com.daniebeler.pixelix.domain.model.Account
import com.daniebeler.pixelix.domain.usecase.GetMutedAccountsUseCase
import com.daniebeler.pixelix.domain.usecase.UnmuteAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MutedAccountsViewModel @Inject constructor(
    private val getMutedAccountsUseCase: GetMutedAccountsUseCase,
    private val unmuteAccountUseCase: UnmuteAccountUseCase
) : ViewModel() {


    var mutedAccountsState by mutableStateOf(MutedAccountsState())

    var unmuteAlert: String by mutableStateOf("")

    init {
        getMutedAccounts()
    }

    fun getMutedAccounts(refreshing: Boolean = false) {
        getMutedAccountsUseCase().onEach { result ->
            mutedAccountsState = when (result) {
                is Resource.Success -> {
                    MutedAccountsState(mutedAccounts = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    MutedAccountsState(error = result.message ?: "An unexpected error occurred")
                }

                is Resource.Loading -> {
                    MutedAccountsState(
                        isLoading = true,
                        isRefreshing = refreshing,
                        mutedAccounts = mutedAccountsState.mutedAccounts
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun unmuteAccount(accountId: String) {
        unmuteAccountUseCase(accountId).onEach { result ->
            mutedAccountsState = when (result) {
                is Resource.Success -> {
                    val newMutedAccounts =
                        mutedAccountsState.mutedAccounts.filter { account: Account -> account.id != accountId }
                    MutedAccountsState(mutedAccounts = newMutedAccounts)
                }

                is Resource.Error -> {
                    MutedAccountsState(error = result.message ?: "An unexpected error occurred")
                }

                is Resource.Loading -> {
                    MutedAccountsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}