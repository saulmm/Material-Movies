package com.hackvg.android.utils;

import com.squareup.otto.Bus;


public class BusProvider {

    private static final Bus REST_BUS = new Bus();

    private BusProvider() {};

    public static Bus getBusInstance() {

        return REST_BUS;
    }
}