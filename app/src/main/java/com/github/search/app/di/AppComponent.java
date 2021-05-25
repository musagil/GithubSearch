package com.github.search.app.di;

import com.github.search.PerApplication;
import com.github.search.RoomScope;
import com.github.search.app.App;
import com.github.search.network.NetworkModule;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(
        modules = {
                ActivityBindingsModule.class,
                AndroidSupportInjectionModule.class,
                AppModule.class,
                RoomModule.class,
                NetworkModule.class,
                NetworkConfigModule.class,
                FragmentBindingsModule.class
        }
)
@PerApplication
@RoomScope
public interface AppComponent extends AndroidInjector<App> {

    @Component.Factory
    interface Factory extends AndroidInjector.Factory<App> {
    }
}
