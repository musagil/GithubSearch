package com.github.search.app.di

import androidx.room.Room
import com.github.search.RoomScope
import com.github.search.app.App
import com.github.search.app.data.db.MyRoomDatabase
import dagger.Module
import dagger.Provides

@Module
internal interface RoomModule {
    companion object {
        @Provides
        @RoomScope
        fun providesRoomDatabase(app: App): MyRoomDatabase =
            Room.databaseBuilder(app, MyRoomDatabase::class.java, "githubRepositories.db")
                .build()

        @Provides
        fun providesFavoritesDao(myRoomDatabase: MyRoomDatabase) =
            myRoomDatabase.favoriteDao()
    }
}
