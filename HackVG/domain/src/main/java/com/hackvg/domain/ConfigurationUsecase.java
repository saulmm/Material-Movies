package com.hackvg.domain;

import com.hackvg.model.entities.ConfigurationResponse;

/**
 * Created by saulmm on 15/02/15.
 */
public interface ConfigurationUsecase extends Usecase {

    public void onConfigurationReceived (ConfigurationResponse configuration);

    public void configure (ConfigurationResponse configuration);
}
