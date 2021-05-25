package com.github.search.repodetail

import android.content.res.Resources
import com.github.search.NavigationViewBinding
import com.nhaarman.mockitokotlin2.mock

internal fun testViewBinding(
    fragment: GitHubRepoDetailFragment = GitHubRepoDetailFragment(),
    reduce: GitHubRepoDetailState.() -> GitHubRepoDetailState = { this },
    resources: Resources = mock(),
    viewModel: GitHubRepoDetailViewModel = testViewModel(reduce = reduce)
) = GitHubRepoDetailViewBinding(
    fragment,
    viewModel,
    NavigationViewBinding(fragment),
    resources
)
