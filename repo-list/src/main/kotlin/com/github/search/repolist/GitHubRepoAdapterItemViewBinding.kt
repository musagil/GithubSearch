package com.github.search.repolist

import android.graphics.drawable.Drawable
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.airbnb.mvrx.withState
import com.github.search.repolist.databinding.GithubRepoListAdapterItemBinding

internal class GitHubRepoAdapterItemViewBinding :
    com.github.search.AdapterItemViewBinding<GitHubRepositoryListItem, GithubRepoListAdapterItemBinding, GitHubRepoListViewModel>() {

    override fun createView(parent: ViewGroup) = parent.inflate(R.layout.github_repo_list_adapter_item)

    val name: String
        get() = item.name

    val desc: String
        get() = item.desc ?: ""

    val favoriteIcon: Drawable?
        get() = withState(viewModel) {
            ContextCompat.getDrawable(
                context,
                if (item.isFavorite) {
                    R.drawable.ic_favorited
                } else {
                    R.drawable.ic_not_favorited
                }
            )
        }

    val onFavoriteClick = OnClickListener {
        viewModel.onFavoriteClicked(item)
    }

    val image: String
        get() = item.image

    val conversationClickListener = OnClickListener {
        viewModel.onRepositoryClick(item)
    }
}
