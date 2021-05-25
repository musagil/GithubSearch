package com.github.search.repodetail

import com.airbnb.mvrx.test.MvRxTestRule
import com.airbnb.mvrx.withState
import com.github.search.data.RepositoryInput
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import org.assertj.core.api.Assertions.assertThat
import org.junit.ClassRule
import org.junit.Test

class GithubRepoDetailViewModelTest {

    @Test
    fun `given a favorite repo, should set proper state`() {
        val repository = mock<GitHubRepoDetailRepository> {
            on { observeRepo(any()) } doReturn Observable.just(true)
        }
        val viewModel = testViewModel(repository = repository, repositoryInput = ANY_REPO_INPUT)
        viewModel.onRepoLoaded(ANY_REPO_INPUT)

        withState(viewModel) {
            assertThat(it.isFavorite()).isEqualTo(true)
        }
    }

    @Test
    fun `given a not favorite repo, should set proper state`() {
        val repository = mock<GitHubRepoDetailRepository> {
            on { observeRepo(any()) } doReturn Observable.just(false)
        }
        val viewModel = testViewModel(repository = repository, repositoryInput = ANY_REPO_INPUT)
        viewModel.onRepoLoaded(ANY_REPO_INPUT)

        withState(viewModel) {
            assertThat(it.isFavorite()).isEqualTo(false)
        }
    }

    companion object {
        private const val ANY_REPO_ID = 1
        private const val ANY_REPO_NAME = "name"
        private const val ANY_REPO_DESC = "desc"
        private const val ANY_REPO_AVATAR_URL = "url"
        private const val ANY_APP_NAME = "Github Search"

        private val ANY_REPO_INPUT = RepositoryInput(
            repositoryId = ANY_REPO_ID,
            image = ANY_REPO_AVATAR_URL,
            desc = ANY_REPO_DESC,
            name = ANY_REPO_NAME
        )

        @JvmField
        @ClassRule
        val mvrxTestRule = MvRxTestRule()
    }
}
