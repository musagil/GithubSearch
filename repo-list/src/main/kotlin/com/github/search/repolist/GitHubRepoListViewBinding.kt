package com.github.search.repolist

import android.content.res.Resources
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.withState
import com.github.search.FragmentNavigation
import com.github.search.NavigationViewBinding
import com.github.search.data.RepositoryInput
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

internal class GitHubRepoListViewBinding @AssistedInject constructor(
    fragment: GitHubRepoListFragment,
    @Assisted override val viewModel: GitHubRepoListViewModel,
    @Assisted val adapter: GitHubRepoAdapter,
    val navigationViewBinding: NavigationViewBinding,
    private val navigation: FragmentNavigation,
    resources: Resources
) : com.github.search.BaseViewBinding<GitHubRepoListState>(fragment, viewModel) {

    val onLoadMore = {
        viewModel.onLoadMore()
    }

    val gitHubRepositoryListItems: List<GitHubRepositoryListItem>
        get() = withState(viewModel) {
            it.shownRepositories
        }

    val shouldShowRefreshing: Boolean
        get() = withState(viewModel) {
            it.gitHubRepositories is Loading
        }

    val swipeToRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        viewModel.searchRepositories()
    }

    val noResultVisibility
        get() = withState(viewModel) {
            if (it.shownRepositories.isNullOrEmpty()) VISIBLE else GONE
        }

    init {
        navigationViewBinding.toolbarTitle = resources.getString(R.string.app_name)
        navigationViewBinding.toolbarMenuResId = R.menu.github_repo_list_search_menu
        navigationViewBinding.searchableCallback = { query ->
            viewModel.onNewQuery(query)
        }
        subscribeToProductClickedId()
        subscribeToSearchedQuery()
        subscribeToLoadMore()
        subscribeToNewRepositories()
        subscribeFavorites()
    }

    private fun subscribeFavorites() {
        viewModel.asyncSubscribe(GitHubRepoListState::favoriteRepositories, uniqueOnly()) {
            viewModel.onFavoritesLoaded(it)
        }
    }

    private fun subscribeToSearchedQuery() {
        viewModel.selectSubscribeNonNull(GitHubRepoListState::searchedQuery, uniqueOnly()) {
            viewModel.searchRepositories()
        }
    }

    private fun subscribeToNewRepositories() {
        viewModel.asyncSubscribe(GitHubRepoListState::gitHubRepositories, uniqueOnly()) {
            viewModel.onNewRepositoriesLoad(it)
        }
    }

    private fun subscribeToLoadMore() {
        viewModel.selectSubscribe(GitHubRepoListState::currentPage, uniqueOnly()) {
            viewModel.searchRepositories()
        }
    }

    private fun subscribeToProductClickedId() {
        viewModel.selectSubscribeNonNull(
            GitHubRepoListState::clickedRepo
        ) { repo ->
            navigation.navigate(
                R.id.repoDetailFragment,
                RepositoryInput(
                    repo.id,
                    repo.image,
                    repo.name,
                    repo.desc ?: ""
                )
            )
            viewModel.onNavigatedToRepoDetail()
        }
    }

    @AssistedInject.Factory
    interface Factory {

        fun create(
            viewModel: GitHubRepoListViewModel,
            adapter: GitHubRepoAdapter
        ): GitHubRepoListViewBinding
    }
}
