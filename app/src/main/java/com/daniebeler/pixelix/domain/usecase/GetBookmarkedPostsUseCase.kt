package com.daniebeler.pixelix.domain.usecase

import com.daniebeler.pixelix.common.Resource
import com.daniebeler.pixelix.domain.model.Post
import com.daniebeler.pixelix.domain.repository.CountryRepository
import kotlinx.coroutines.flow.Flow

class GetBookmarkedPostsUseCase(
    private val repository: CountryRepository
) {
    operator fun invoke(): Flow<Resource<List<Post>>> {
        return repository.getBookmarkedPosts()
    }
}