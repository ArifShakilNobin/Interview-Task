package com.brac.bracit.services;

import com.brac.bracit.domains.Battery;
import com.brac.bracit.domains.BatteryStatistic;
import com.brac.bracit.repositories.BatteryRepository;
import com.brac.bracit.services.impl.IBatteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BatteryService implements IBatteryService {

    private final BatteryRepository batteryRepository;

    @Override
    public Battery saveBattery(Battery battery) {
        return batteryRepository.save(battery);
    }

    @Override
    public List<Battery> saveBatteries(List<Battery> batteries) {
        return batteryRepository.saveAll(batteries);
    }

    @Override
    public BatteryStatistic getBatteriesByPostcodeRange(String startPostcode, String endPostcode) {

        List<Battery> batteries = batteryRepository.findByPostcodeBetween(startPostcode, endPostcode);

        List<String> batteryNames = batteries.stream().map(Battery::getName).sorted().collect(Collectors.toList());
        int totalWattCapacity = batteries.stream().mapToInt(Battery::getWattCapacity).sum();
        double averageWattCapacity = batteries.stream().mapToInt(Battery::getWattCapacity).average().orElse(0.0);
//        BatteryStatistic batteryStatistic = BatteryStatistic.builder()
//                .batteryNames(batteryNames)
//                .totalWattCapacity(totalWattCapacity)
//                .averageWattCapacity(averageWattCapacity)
//                .build();
//        return batteryStatistic;

        BatteryStatistic statistics = new BatteryStatistic();
        statistics.setBatteryNames(batteryNames);
        statistics.setTotalWattCapacity(totalWattCapacity);
        statistics.setAverageWattCapacity(averageWattCapacity);

        return statistics;
    }
}
