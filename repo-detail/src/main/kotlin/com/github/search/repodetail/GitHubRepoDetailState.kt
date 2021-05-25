package com.github.search.repodetail

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.github.search.data.RepositoryInput

data class GitHubRepoDetailState(
    val repositoryInput: RepositoryInput? = null,
    val isFavorite: Async<Boolean> = Uninitialized
) : MvRxState
