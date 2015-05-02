package com.hackvg.domain;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DummyModule_ProvideDummyFactory implements Factory<DummyClass> {
  private final DummyModule module;

  public DummyModule_ProvideDummyFactory(DummyModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public DummyClass get() {  
    DummyClass provided = module.provideDummy();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<DummyClass> create(DummyModule module) {  
    return new DummyModule_ProvideDummyFactory(module);
  }
}

