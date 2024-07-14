package com.brac.bracit.services.impl;

import com.brac.bracit.domains.Battery;
import com.brac.bracit.domains.BatteryStatistic;

import java.util.List;

public interface IBatteryService {
    Battery saveBattery(Battery battery);
    List<Battery> saveBatteries(List<Battery> batteries);
    BatteryStatistic getBatteriesByPostcodeRange(String startPostcode, String endPostcode);
}
