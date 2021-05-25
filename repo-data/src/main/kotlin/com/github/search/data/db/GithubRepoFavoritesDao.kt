package com.github.search.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.search.data.GithubRepoFavorite
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue

@Dao
interface GithubRepoFavoritesDao {

    @CheckReturnValue
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun upsertFavorite(githubRepoFavorite: GithubRepoFavorite): Completable

    @CheckReturnValue
    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE repoId = :repoId)")
    fun isFavorite(repoId: Int): Observable<Boolean>

    @CheckReturnValue
    @Delete
    fun deleteFavorite(githubRepoFavorite: GithubRepoFavorite): Completable

    @CheckReturnValue
    @Query("SELECT * FROM favorites")
    fun getFavorites(): Observable<List<GithubRepoFavorite>>
}
