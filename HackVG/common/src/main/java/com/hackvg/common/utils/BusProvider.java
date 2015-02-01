package com.hackvg.common.utils;

import com.squareup.otto.Bus;


public class BusProvider {

    private static final Bus REST_BUS = new Bus();
    private static final Bus UI_BUS = new Bus();

    private BusProvider() {};

    public static Bus getRestBusInstance() {

        return REST_BUS;
    }

    public static Bus getUIBusInstance () {

        return UI_BUS;
    }
}