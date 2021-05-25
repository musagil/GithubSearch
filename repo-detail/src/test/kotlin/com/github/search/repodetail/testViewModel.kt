package com.github.search.repodetail

import com.github.search.data.RepositoryInput
import com.nhaarman.mockitokotlin2.mock

internal fun testViewModel(
    repository: GitHubRepoDetailRepository = mock(),
    repositoryInput: RepositoryInput = mock(),
    reduce: GitHubRepoDetailState.() -> GitHubRepoDetailState = { this }
) = GitHubRepoDetailViewModel(
    repository,
    repositoryInput,
    GitHubRepoDetailState().reduce()
)
