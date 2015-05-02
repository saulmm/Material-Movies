package com.hackvg.domain;

import com.hackvg.model.rest.RestDataSource;
import com.squareup.otto.Bus;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class GetMoviesUsecaseController_Factory implements Factory<GetMoviesUsecaseController> {
  private final Provider<RestDataSource> dataSourceProvider;
  private final Provider<Bus> uiBusProvider;

  public GetMoviesUsecaseController_Factory(Provider<RestDataSource> dataSourceProvider, Provider<Bus> uiBusProvider) {  
    assert dataSourceProvider != null;
    this.dataSourceProvider = dataSourceProvider;
    assert uiBusProvider != null;
    this.uiBusProvider = uiBusProvider;
  }

  @Override
  public GetMoviesUsecaseController get() {  
    return new GetMoviesUsecaseController(dataSourceProvider.get(), uiBusProvider.get());
  }

  public static Factory<GetMoviesUsecaseController> create(Provider<RestDataSource> dataSourceProvider, Provider<Bus> uiBusProvider) {  
    return new GetMoviesUsecaseController_Factory(dataSourceProvider, uiBusProvider);
  }
}

