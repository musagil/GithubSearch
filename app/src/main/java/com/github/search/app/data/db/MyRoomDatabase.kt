package com.github.search.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.search.data.GithubRepoFavorite
import com.github.search.data.db.GithubRepoFavoritesDao

@Database(entities = [GithubRepoFavorite::class], version = 1)
abstract class MyRoomDatabase : RoomDatabase() {
    abstract fun favoriteDao(): GithubRepoFavoritesDao
}
