package com.github.search.repolist

import android.annotation.SuppressLint
import com.github.search.OpenForTesting
import com.github.search.data.GithubRepoFavorite
import com.github.search.data.db.GithubRepoFavoritesDao
import com.github.search.network.di.NetworkScheduler
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@OpenForTesting
internal class GitHubRepoListRepository @Inject constructor(
    private val requests: GitHubRepoListRequests,
    private val githubRepoFavoritesDao: GithubRepoFavoritesDao,
    @NetworkScheduler private val networkScheduler: Scheduler
) {

    @CheckReturnValue
    internal fun fetchRepositories(
        query: String,
        page: Int
    ): Observable<List<GitHubRepositoryListItem>> =
        requests
            .searchGithubRepo(searchParam = query, page = page)
            .subscribeOn(networkScheduler)
            .map(::mapToProductListItem)

    @CheckReturnValue
    fun observeFavorites(): Observable<List<GithubRepoFavorite>> =
        githubRepoFavoritesDao.getFavorites().subscribeOn(networkScheduler)

    @SuppressLint("RxJava2SubscribeMissingOnError")
    @CheckReturnValue
    fun favoriteRepo(githubRepoFavorite: GithubRepoFavorite): Disposable =
        githubRepoFavoritesDao.upsertFavorite(githubRepoFavorite).subscribe()

    @SuppressLint("RxJava2SubscribeMissingOnError")
    @CheckReturnValue
    fun unfavoriteRepo(githubRepoFavorite: GithubRepoFavorite): Disposable =
        githubRepoFavoritesDao.deleteFavorite(githubRepoFavorite).subscribe()

    companion object {
        fun mapToProductListItem(response: GitHubSearchResponse) =
            response.items?.map {
                GitHubRepositoryListItem(
                    id = it.id,
                    name = it.fullName,
                    image = it.owner.thumbnailUrl,
                    desc = it.description
                )
            }
    }
}
