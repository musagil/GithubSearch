package com.github.search.app.di;

import com.github.search.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
interface ActivityBindingsModule {
    @ContributesAndroidInjector
    MainActivity mainActivityInjector();
}
