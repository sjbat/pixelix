package com.daniebeler.pfpixelix.ui.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.daniebeler.pfpixelix.R
import com.daniebeler.pfpixelix.ui.composables.timelines.global_timeline.GlobalTimelineComposable
import com.daniebeler.pfpixelix.ui.composables.timelines.home_timeline.HomeTimelineComposable
import com.daniebeler.pfpixelix.ui.composables.timelines.local_timeline.LocalTimelineComposable
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeComposable(navController: NavController) {

    val pagerState = rememberPagerState { 3 }

    val scope = rememberCoroutineScope()


    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
        TopAppBar(windowInsets = WindowInsets(0, 0, 0, 0),
            title = {
            Text(stringResource(id = R.string.app_name))
        })

    }) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            PrimaryTabRow(selectedTabIndex = pagerState.currentPage) {
                Tab(text = { Text(stringResource(R.string.home)) },
                    selected = pagerState.currentPage == 0,
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onBackground,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    })

                Tab(text = { Text(stringResource(R.string.local)) },
                    selected = pagerState.currentPage == 1,
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onBackground,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    })

                Tab(text = { Text(stringResource(R.string.global)) },
                    selected = pagerState.currentPage == 2,
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onBackground,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(2)
                        }
                    })
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.background)
            ) { tabIndex ->
                when (tabIndex) {
                    0 -> Box(modifier = Modifier.fillMaxSize()) {
                        HomeTimelineComposable(navController)
                    }

                    1 -> Box(modifier = Modifier.fillMaxSize()) {
                        LocalTimelineComposable(navController)
                    }

                    2 -> Box(modifier = Modifier.fillMaxSize()) {
                        GlobalTimelineComposable(navController)
                    }
                }
            }
        }
    }
}