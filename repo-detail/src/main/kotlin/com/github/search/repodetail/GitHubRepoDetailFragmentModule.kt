package com.github.search.repodetail

import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MvRx
import com.github.search.data.RepositoryInput
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.Provides

@AssistedModule
@Module(includes = [AssistedInject_GitHubRepoDetailFragmentModule::class])
abstract class GitHubRepoDetailFragmentModule {

    @Binds
    abstract fun bindFragment(fragment: GitHubRepoDetailFragment): Fragment

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun repoInput(fragment: GitHubRepoDetailFragment): RepositoryInput {
            val arguments = fragment.requireArguments()
            return arguments.getParcelable(MvRx.KEY_ARG)!!
        }
    }
}
