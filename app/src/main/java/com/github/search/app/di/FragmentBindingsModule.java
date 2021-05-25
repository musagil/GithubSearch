package com.github.search.app.di;

import com.github.search.repodetail.GitHubRepoDetailFragment;
import com.github.search.repodetail.GitHubRepoDetailFragmentModule;
import com.github.search.repolist.GitHubRepoListFragment;
import com.github.search.repolist.GitHubRepoListFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
interface FragmentBindingsModule {

    @ContributesAndroidInjector(modules = GitHubRepoListFragmentModule.class)
    GitHubRepoListFragment productListFragment();

    @ContributesAndroidInjector(modules = GitHubRepoDetailFragmentModule.class)
    GitHubRepoDetailFragment productDetailFragment();

}
