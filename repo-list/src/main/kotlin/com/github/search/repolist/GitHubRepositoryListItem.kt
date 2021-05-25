package com.github.search.repolist

data class GitHubRepositoryListItem(
    override val id: Int,
    val name: String,
    val image: String,
    val desc: String?,
    val isFavorite: Boolean = false
) : com.github.search.BindableItem
