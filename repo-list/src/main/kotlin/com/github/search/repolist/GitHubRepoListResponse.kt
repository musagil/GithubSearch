package com.github.search.repolist

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GitHubSearchResponse(
    @Json(name = "total_count") val totalCount: Int,
    @Json(name = "incomplete_results") val incompleteResults: Boolean,
    @Json(name = "items") val items: List<GitHubRepository>?
)

@JsonClass(generateAdapter = true)
data class GitHubRepository(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "full_name") val fullName: String,
    @Json(name = "owner") val owner: Owner,
    @Json(name = "description") val description: String?,
    @Json(name = "html_url") val htmlUrl: String,
    @Json(name = "created_at") val createdAt: String,
    @Json(name = "updated_at") val updatedAt: String,
    @Json(name = "stargazers_count") val stars: Int
) {

    @JsonClass(generateAdapter = true)
    data class Owner(
        @Json(name = "id") val id: Int,
        @Json(name = "login") val loginName: String,
        @Json(name = "avatar_url") val thumbnailUrl: String,
        @Json(name = "html_url") val htmlUrl: String
    )
}
