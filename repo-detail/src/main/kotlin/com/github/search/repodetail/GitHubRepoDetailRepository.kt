package com.github.search.repodetail

import android.annotation.SuppressLint
import com.github.search.OpenForTesting
import com.github.search.data.GithubRepoFavorite
import com.github.search.data.db.GithubRepoFavoritesDao
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@OpenForTesting
internal class GitHubRepoDetailRepository @Inject constructor(
    private val githubRepoFavoritesDao: GithubRepoFavoritesDao
) {

    @SuppressLint("RxJava2SubscribeMissingOnError")
    @CheckReturnValue
    fun favoriteRepo(githubRepoFavorite: GithubRepoFavorite): Disposable =
        githubRepoFavoritesDao.upsertFavorite(githubRepoFavorite).subscribe()

    @SuppressLint("RxJava2SubscribeMissingOnError")
    @CheckReturnValue
    fun unfavoriteRepo(githubRepoFavorite: GithubRepoFavorite): Disposable =
        githubRepoFavoritesDao.deleteFavorite(githubRepoFavorite).subscribe()

    @CheckReturnValue
    fun observeRepo(repoId: Int): Observable<Boolean> =
        githubRepoFavoritesDao.isFavorite(repoId)
}
