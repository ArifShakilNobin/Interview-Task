package com.brac.bracit.domains;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatteryStatistic {
    private List<String> batteryNames;
    private int totalWattCapacity;
    private double averageWattCapacity;
}
