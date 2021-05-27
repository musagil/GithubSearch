package com.github.search.repolist

import android.content.res.Resources
import com.github.search.FragmentNavigation
import com.github.search.NavigationViewBinding
import com.nhaarman.mockitokotlin2.mock

internal fun testViewBinding(
    fragment: GitHubRepoListFragment = GitHubRepoListFragment(),
    reduce: GitHubRepoListState.() -> GitHubRepoListState = { this },
    resources: Resources = mock(),
    navigation: FragmentNavigation = mock(),
    viewModel: GitHubRepoListViewModel = testViewModel(reduce = reduce)
) = GitHubRepoListViewBinding(
    fragment,
    viewModel,
    GitHubRepoAdapter(viewModel),
    NavigationViewBinding(fragment),
    navigation,
    resources
)
