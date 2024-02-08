package com.daniebeler.pixelix.ui.composables.settings.preferences

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.daniebeler.pixelix.domain.usecase.GetHideSensitiveContent
import com.daniebeler.pixelix.domain.usecase.Logout
import com.daniebeler.pixelix.domain.usecase.StoreHideSensitiveContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val storeHideSensitiveContentUseCase: StoreHideSensitiveContent,
    private val getHideSensitiveContent: GetHideSensitiveContent,
    private val logoutUseCase: Logout
) : ViewModel() {
    var isSensitiveContentHidden by mutableStateOf(true)

    var cacheSize by mutableStateOf("")

    var versionName by mutableStateOf("")

    init {
        getHideSensitiveContent().asLiveData()
    }


    fun getVersionName(context: Context) {
        try {
            versionName = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

    fun storeHideSensitiveContent(value: Boolean) {
        isSensitiveContentHidden = value
        viewModelScope.launch {
            storeHideSensitiveContentUseCase(value)
        }
    }
}