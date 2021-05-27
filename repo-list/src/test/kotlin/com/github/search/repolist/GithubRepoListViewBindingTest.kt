package com.github.search.repolist

import android.content.res.Resources
import com.airbnb.mvrx.test.MvRxTestRule
import com.github.search.FragmentNavigation
import com.github.search.data.RepositoryInput
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.then
import io.reactivex.Observable
import org.assertj.core.api.Assertions.assertThat
import org.junit.ClassRule
import org.junit.Test

class GithubRepoListViewBindingTest {

    private val resources = mock<Resources> {
        on { getString(R.string.app_name) } doReturn ANY_APP_NAME
    }
    private val fragment = GitHubRepoListFragment()
    private val repository = mock<GitHubRepoListRepository> {
        on { fetchRepositories(any(), any()) } doReturn Observable.just(
            listOf(
                GitHubRepositoryListItem(
                    id = ANY_REPO_ID,
                    name = ANY_REPO_NAME,
                    image = ANY_REPO_AVATAR_URL,
                    desc = ANY_REPO_DESC
                )
            )
        )

        on { observeFavorites() } doReturn Observable.empty()
    }
    private val viewModel = testViewModel(repository)
    private val viewBinding =
        testViewBinding(fragment, viewModel = viewModel, resources = resources)

    @Test
    fun `given view binding initialised the title should be right one`() {
        assertThat(viewBinding.navigationViewBinding.toolbarTitle).isEqualTo(ANY_APP_NAME)
    }

    @Test
    fun `given view binding initialised adapter should be repo adapter`() {
        assertThat(viewBinding.adapter).isInstanceOf(GitHubRepoAdapter::class.java)
    }

    @Test
    fun `given view binding initialised loading indicator should be invisible`() {
        assertThat(viewBinding.shouldShowRefreshing).isFalse()
    }

    @Test
    fun `given clickedRepo, should navigate`() {
        val viewModel = testViewModel(repository) {
            copy(
                clickedRepo = GitHubRepositoryListItem(
                    id = ANY_REPO_ID,
                    name = ANY_REPO_NAME,
                    image = ANY_REPO_AVATAR_URL,
                    desc = ANY_REPO_DESC,
                    isFavorite = false
                )
            )
        }
        val navigation = mock<FragmentNavigation>()
        testViewBinding(fragment, viewModel = viewModel, navigation = navigation)

        then(navigation).should().navigate(
            R.id.repoDetailFragment,
            RepositoryInput(
                repositoryId = ANY_REPO_ID,
                image = ANY_REPO_AVATAR_URL,
                name = ANY_REPO_NAME,
                desc = ANY_REPO_DESC
            )
        )
    }

    companion object {

        private const val ANY_REPO_ID = 1
        private const val ANY_REPO_NAME = "name"
        private const val ANY_REPO_DESC = "desc"
        private const val ANY_REPO_AVATAR_URL = "url"
        private const val ANY_APP_NAME = "Github Search"

        @JvmField
        @ClassRule
        val mvrxTestRule = MvRxTestRule()
    }
}
