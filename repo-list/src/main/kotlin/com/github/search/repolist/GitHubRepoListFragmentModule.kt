package com.github.search.repolist

import androidx.fragment.app.Fragment
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@AssistedModule
@Module(includes = [AssistedInject_GitHubRepoListFragmentModule::class])
abstract class GitHubRepoListFragmentModule {

    @Binds
    abstract fun bindFragment(fragment: GitHubRepoListFragment): Fragment

    @Module
    companion object {

        @JvmStatic
        @Provides
        internal fun provideRequests(retrofit: Retrofit) = retrofit.create<GitHubRepoListRequests>()
    }
}
