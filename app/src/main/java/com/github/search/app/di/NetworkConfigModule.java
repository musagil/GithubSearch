package com.github.search.app.di;

import com.github.search.app.config.ProdNetworkConfig;
import com.github.search.network.NetworkConfig;

import javax.inject.Provider;

import dagger.Module;
import dagger.Provides;

@Module
interface NetworkConfigModule {

    @Provides
    static NetworkConfig networkConfig(Provider<ProdNetworkConfig> prodProvider) {
        return prodProvider.get();
    }
}
