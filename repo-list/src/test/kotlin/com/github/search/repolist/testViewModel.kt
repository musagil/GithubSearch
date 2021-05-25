package com.github.search.repolist

import com.nhaarman.mockitokotlin2.mock

internal fun testViewModel(
    repository: GitHubRepoListRepository = mock(),
    reduce: GitHubRepoListState.() -> GitHubRepoListState = { this }
) = GitHubRepoListViewModel(
    repository,
    GitHubRepoListState().reduce()
)
