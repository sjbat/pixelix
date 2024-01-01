package com.daniebeler.pixels.ui.composables.trending.trending_posts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.daniebeler.pixels.R
import com.daniebeler.pixels.domain.model.Post
import com.daniebeler.pixels.ui.composables.ErrorComposable
import com.daniebeler.pixels.ui.composables.LoadingComposable

@Composable
fun TrendingPostsComposable(
    navController: NavController,
    viewModel: TrendingPostsViewModel = hiltViewModel()
) {

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            content = {
                items(viewModel.dailyState.trendingPosts, key = {
                    it.id
                }) { photo ->
                    CustomPost(post = photo, navController = navController)
                }

            }
        )
        
        LoadingComposable(isLoading = viewModel.dailyState.isLoading)
        ErrorComposable(message = viewModel.dailyState.error)
    }


}

@Composable
fun CustomPost(post: Post, navController: NavController) {
    Box(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceBright)) {
        AsyncImage(
            model = post.mediaAttachments[0].previewUrl,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f)
                .clickable(onClick = {
                    navController.navigate("single_post_screen/" + post.id) {
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        )
    }


}

@Composable
fun Heading(text: String) {
    Text(text, fontSize = 32.sp, modifier = Modifier.padding(top = 24.dp, bottom = 6.dp))
}
