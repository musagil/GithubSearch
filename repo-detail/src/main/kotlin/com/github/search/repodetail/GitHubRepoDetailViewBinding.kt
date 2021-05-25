package com.github.search.repodetail

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.View.OnClickListener
import androidx.core.content.ContextCompat
import com.airbnb.mvrx.withState
import com.github.search.NavigationViewBinding
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

internal class GitHubRepoDetailViewBinding @AssistedInject constructor(
    fragment: GitHubRepoDetailFragment,
    @Assisted override val viewModel: GitHubRepoDetailViewModel,
    val navigationViewBinding: NavigationViewBinding,
    resources: Resources
) : com.github.search.BaseViewBinding<GitHubRepoDetailState>(fragment, viewModel) {

    val image: String
        get() = withState(viewModel) {
            it.repositoryInput?.image ?: ""
        }

    val onFavoriteClick = OnClickListener {
        viewModel.onFavoriteClicked()
    }

    val favoriteIcon: Drawable?
        get() = withState(viewModel) {
            ContextCompat.getDrawable(
                context,
                if (it.isFavorite() == true) {
                    R.drawable.ic_favorited
                } else {
                    R.drawable.ic_not_favorited
                }
            )
        }

    val name: String
        get() = withState(viewModel) {
            it.repositoryInput?.name ?: ""
        }

    val desc: String
        get() = withState(viewModel) {
            it.repositoryInput?.desc ?: ""
        }

    init {
        navigationViewBinding.toolbarTitle = resources.getString(R.string.loading)
        subscribeToRepoInput()
    }

    private fun subscribeToRepoInput() {
        viewModel.selectSubscribeNonNull(
            GitHubRepoDetailState::repositoryInput
        ) { repositoryInput ->
            viewModel.onRepoLoaded(repositoryInput)
            navigationViewBinding.toolbarTitle = repositoryInput.name
        }
    }

    @AssistedInject.Factory
    interface Factory {

        fun create(viewModel: GitHubRepoDetailViewModel): GitHubRepoDetailViewBinding
    }
}
