package com.github.search.repodetail

import android.content.res.Resources
import com.airbnb.mvrx.test.MvRxTestRule
import com.github.search.NavigationViewBinding
import com.github.search.data.RepositoryInput
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import org.assertj.core.api.Assertions.assertThat
import org.junit.ClassRule
import org.junit.Test

class GithubRepoDetailViewBindingTest {

    private val resources = mock<Resources> {
        on { getString(R.string.app_name) } doReturn ANY_APP_NAME
    }
    private val fragment = GitHubRepoDetailFragment()
    private val repository = mock<GitHubRepoDetailRepository> {
        on { observeRepo(any()) } doReturn Observable.empty()
    }
    private val viewModel = testViewModel(
        repository = repository,
        repositoryInput = RepositoryInput(
            repositoryId = ANY_REPO_ID,
            image = ANY_REPO_AVATAR_URL,
            desc = ANY_REPO_DESC,
            name = ANY_REPO_NAME
        )
    )
    private val viewBinding =
        GitHubRepoDetailViewBinding(
            fragment,
            viewModel,
            NavigationViewBinding(fragment),
            resources
        )

    @Test
    fun `given view binding initialised the title should be right one`() {
        assertThat(viewBinding.navigationViewBinding.toolbarTitle).isEqualTo(ANY_REPO_NAME)
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
