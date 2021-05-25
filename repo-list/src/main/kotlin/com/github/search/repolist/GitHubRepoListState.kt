package com.github.search.repolist

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.github.search.data.GithubRepoFavorite

data class GitHubRepoListState(
    val gitHubRepositories: Async<List<GitHubRepositoryListItem>> = Uninitialized,
    val favoriteRepositories: Async<List<GithubRepoFavorite>> = Uninitialized,
    val shownRepositories: List<GitHubRepositoryListItem> = emptyList(),
    val clickedRepo: GitHubRepositoryListItem? = null,
    val searchedQuery: String? = null,
    val currentPage: Int = 1
) : MvRxState
