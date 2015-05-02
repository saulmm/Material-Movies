package com.hackvg.domain;

import com.hackvg.model.MediaDataSource;
import com.squareup.otto.Bus;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class ConfigurationUsecaseController_Factory implements Factory<ConfigurationUsecaseController> {
  private final Provider<MediaDataSource> mediaDataSourceProvider;
  private final Provider<Bus> uiBusProvider;

  public ConfigurationUsecaseController_Factory(Provider<MediaDataSource> mediaDataSourceProvider, Provider<Bus> uiBusProvider) {  
    assert mediaDataSourceProvider != null;
    this.mediaDataSourceProvider = mediaDataSourceProvider;
    assert uiBusProvider != null;
    this.uiBusProvider = uiBusProvider;
  }

  @Override
  public ConfigurationUsecaseController get() {  
    return new ConfigurationUsecaseController(mediaDataSourceProvider.get(), uiBusProvider.get());
  }

  public static Factory<ConfigurationUsecaseController> create(Provider<MediaDataSource> mediaDataSourceProvider, Provider<Bus> uiBusProvider) {  
    return new ConfigurationUsecaseController_Factory(mediaDataSourceProvider, uiBusProvider);
  }
}

