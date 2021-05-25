package com.github.search.repolist

import android.util.Log
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.github.search.ViewModelFactory
import com.github.search.data.GithubRepoFavorite
import com.github.search.ktx.createViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

internal class GitHubRepoListViewModel @AssistedInject constructor(
    private val repository: GitHubRepoListRepository,
    @Assisted initialState: GitHubRepoListState
) : com.github.search.BaseViewModel<GitHubRepoListState>(initialState) {

    init {
        observeFavorites()
    }

    private fun observeFavorites() {
        repository.observeFavorites()
            .execute {
                copy(favoriteRepositories = it)
            }
    }

    fun searchRepositories() {
        withState { state ->
            if (state.searchedQuery != null) {
                repository.fetchRepositories(state.searchedQuery, state.currentPage)
                    .doOnError {
                        Log.e("Error", it.message)
                    }.execute {
                        copy(gitHubRepositories = it)
                    }
            }
        }
    }

    fun onNewRepositoriesLoad(newRepositories: List<GitHubRepositoryListItem>) = setState {
        val currentShownList = shownRepositories.toMutableList()
        currentShownList.addAll(newRepositories)
        copy(shownRepositories = currentShownList.updateFavorites(favoriteRepositories()))
    }

    fun onNewQuery(query: String) = setState {
        copy(searchedQuery = query, shownRepositories = emptyList(), currentPage = 1)
    }

    fun onLoadMore() = setState {
        copy(currentPage = currentPage + 1)
    }

    fun onRepositoryClick(gitHubRepositoryListItem: GitHubRepositoryListItem) = setState {
        copy(clickedRepo = gitHubRepositoryListItem)
    }

    fun onNavigatedToRepoDetail() = setState {
        copy(clickedRepo = null)
    }

    fun onFavoritesLoaded(favorites: List<GithubRepoFavorite>) = setState {
        copy(shownRepositories = shownRepositories.updateFavorites(favorites))
    }

    fun onFavoriteClicked(item: GitHubRepositoryListItem) = withState {
        val isFavorite = item.isFavorite
        val repo = GithubRepoFavorite(
            item.id,
            item.name,
            item.desc ?: ""
        )

        if (isFavorite) {
            repository.unfavoriteRepo(repo)
        } else {
            repository.favoriteRepo(repo)
        }
    }

    @AssistedInject.Factory
    interface Factory : ViewModelFactory<GitHubRepoListState> {
        override fun create(initialState: GitHubRepoListState): GitHubRepoListViewModel
    }

    companion object :
        MvRxViewModelFactory<GitHubRepoListViewModel, GitHubRepoListState> {

        override fun create(
            viewModelContext: ViewModelContext,
            state: GitHubRepoListState
        ): GitHubRepoListViewModel =
            viewModelContext.createViewModel(state)

        private fun List<GitHubRepositoryListItem>.updateFavorites(favorites: List<GithubRepoFavorite>?) =
            map { listItem ->
                val isFavorite = favorites?.any { it.repoId == listItem.id } ?: false
                listItem.copy(isFavorite = isFavorite)
            }
    }
}
