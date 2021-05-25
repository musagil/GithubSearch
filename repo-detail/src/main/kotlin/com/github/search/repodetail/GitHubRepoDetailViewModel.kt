package com.github.search.repodetail

import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.github.search.ViewModelFactory
import com.github.search.data.GithubRepoFavorite
import com.github.search.data.RepositoryInput
import com.github.search.ktx.createViewModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.schedulers.Schedulers

internal class GitHubRepoDetailViewModel @AssistedInject constructor(
    private val repository: GitHubRepoDetailRepository,
    repositoryInput: RepositoryInput,
    @Assisted initialState: GitHubRepoDetailState
) : com.github.search.BaseViewModel<GitHubRepoDetailState>(
    initialState.copy(repositoryInput = repositoryInput)
) {

    @AssistedInject.Factory
    interface Factory : ViewModelFactory<GitHubRepoDetailState> {
        override fun create(initialState: GitHubRepoDetailState): GitHubRepoDetailViewModel
    }

    fun onRepoLoaded(repo: RepositoryInput) = withState {
        repository.observeRepo(repo.repositoryId)
            .subscribeOn(Schedulers.io())
            .execute {
                copy(isFavorite = it)
            }
    }

    fun onFavoriteClicked() = withState {
        val isFavorite = it.isFavorite() ?: false
        val repo = it.repositoryInput?.let {
            GithubRepoFavorite(
                it.repositoryId,
                it.name,
                it.desc
            )
        }
        if (repo != null) {
            if (isFavorite) {
                repository.unfavoriteRepo(repo)
            } else {
                repository.favoriteRepo(repo)
            }
        }
    }

    companion object : MvRxViewModelFactory<GitHubRepoDetailViewModel, GitHubRepoDetailState> {

        override fun create(
            viewModelContext: ViewModelContext,
            state: GitHubRepoDetailState
        ): GitHubRepoDetailViewModel =
            viewModelContext.createViewModel(state)
    }
}
