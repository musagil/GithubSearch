package com.github.search.repolist

import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue
import retrofit2.http.GET
import retrofit2.http.Query

internal interface GitHubRepoListRequests {

    @CheckReturnValue
    @GET("search/repositories")
    fun searchGithubRepo(
        @Query("q") searchParam: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = "",
        @Query("order") order: String = "desc",
        @Query("per_page") perPage: Int = 10,
        @Query("access_token") accessToken: String = "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"
    ): Observable<GitHubSearchResponse>
}
