package com.github.search.repodetail

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.github.search.HasViewModelFactory
import com.github.search.ViewModelFactory
import com.github.search.repodetail.databinding.GithubRepoDetailFragmentBinding
import javax.inject.Inject

class GitHubRepoDetailFragment :
    com.github.search.BaseFragment<GithubRepoDetailFragmentBinding>(),
    HasViewModelFactory<GitHubRepoDetailState> {

    @Inject
    internal lateinit var viewModelFactory: GitHubRepoDetailViewModel.Factory

    @Inject
    internal lateinit var viewBindingFactory: GitHubRepoDetailViewBinding.Factory

    override val factory: ViewModelFactory<GitHubRepoDetailState>
        get() = viewModelFactory

    override val layoutId: Int
        get() = R.layout.github_repo_detail_fragment

    private val viewModel by fragmentViewModel(GitHubRepoDetailViewModel::class)

    override fun invalidate() = viewDataBinding.viewBinding!!.notifyChange()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewBinding = viewBindingFactory.create(viewModel)
    }
}
