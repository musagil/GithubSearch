package com.github.search.repolist

import com.airbnb.mvrx.test.MvRxTestRule
import com.airbnb.mvrx.withState
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import org.assertj.core.api.Assertions.assertThat
import org.junit.ClassRule
import org.junit.Test

class GithubRepoListViewModelTest {

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

    @Test
    fun `given that navigation to repo detail, clicked id should be cleared`() {
        viewModel.onNavigatedToRepoDetail()

        withState(viewModel) {
            assertThat(it.clickedRepo).isNull()
        }
    }

    @Test
    fun `given a query, should set proper state`() {
        viewModel.onNewQuery(ANY_QUERY)

        viewModel.searchRepositories()

        withState(viewModel) {
            assertThat(it.gitHubRepositories()).isEqualTo(
                listOf(
                    GitHubRepositoryListItem(
                        id = ANY_REPO_ID,
                        name = ANY_REPO_NAME,
                        image = ANY_REPO_AVATAR_URL,
                        desc = ANY_REPO_DESC
                    )
                )
            )
        }
    }

    companion object {
        private const val ANY_REPO_ID = 1
        private const val ANY_REPO_NAME = "name"
        private const val ANY_REPO_DESC = "desc"
        private const val ANY_REPO_AVATAR_URL = "url"
        private const val ANY_APP_NAME = "Github Search"
        private const val ANY_QUERY = "any_query"

        @JvmField
        @ClassRule
        val mvrxTestRule = MvRxTestRule()
    }
}
