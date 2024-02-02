package com.daniebeler.pixelix.ui.composables.states

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FullscreenEmptyStateComposable(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        content()
    }
}

@Composable
fun FixedHeightEmptyStateComposable(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 50.dp), contentAlignment = Alignment.Center) {
        content()
    }
}