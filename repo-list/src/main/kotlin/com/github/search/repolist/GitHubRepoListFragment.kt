package com.github.search.repolist

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.github.search.BaseFragment
import com.github.search.HasViewModelFactory
import com.github.search.ViewModelFactory
import com.github.search.repolist.databinding.GithubRepoListFragmentBinding
import javax.inject.Inject

class GitHubRepoListFragment :
    BaseFragment<GithubRepoListFragmentBinding>(),
    HasViewModelFactory<GitHubRepoListState> {

    @Inject
    internal lateinit var viewModelFactory: GitHubRepoListViewModel.Factory

    @Inject
    internal lateinit var viewBindingFactoryGitHub: GitHubRepoListViewBinding.Factory

    override val factory: ViewModelFactory<GitHubRepoListState>
        get() = viewModelFactory

    override val layoutId: Int
        get() = R.layout.github_repo_list_fragment

    private val viewModel by fragmentViewModel(GitHubRepoListViewModel::class)

    private val adapter by lazy { GitHubRepoAdapter(viewModel) }

    override fun invalidate() = viewDataBinding.viewBinding!!.notifyChange()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewBinding = viewBindingFactoryGitHub.create(viewModel, adapter)
    }
}
