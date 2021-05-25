package com.github.search.repolist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

internal class GitHubRepoAdapter(
    private val viewModel: GitHubRepoListViewModel
) : com.github.search.BindableListAdapter<GitHubRepositoryListItem, GitHubRepoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            parent,
            GitHubRepoAdapterItemViewBinding(),
            viewModel
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int) = getItem(position).id.toLong()

    class ViewHolder(
        parent: ViewGroup,
        private val viewBinding: GitHubRepoAdapterItemViewBinding,
        private val viewModel: GitHubRepoListViewModel
    ) : RecyclerView.ViewHolder(
        viewBinding.createView(parent)
    ) {

        fun bind(gitHubRepositoryListItem: GitHubRepositoryListItem) {
            viewBinding.bindItem(gitHubRepositoryListItem, viewModel)
        }
    }
}
