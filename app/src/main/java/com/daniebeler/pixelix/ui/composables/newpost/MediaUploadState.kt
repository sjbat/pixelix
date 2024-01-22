package com.daniebeler.pixelix.ui.composables.newpost

import com.daniebeler.pixelix.domain.model.MediaAttachment

data class MediaUploadState(
    val isLoading: Boolean = false,
    val mediaAttachments: List<MediaAttachment> = emptyList(),
    val error: String = ""
)
