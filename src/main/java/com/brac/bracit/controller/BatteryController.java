package com.brac.bracit.controller;

import com.brac.bracit.domains.Battery;
import com.brac.bracit.domains.BatteryStatistic;
import com.brac.bracit.services.impl.IBatteryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/batteries")
@RequiredArgsConstructor
public class BatteryController {

    private final IBatteryService batteryService;

    /**
     * Adds batteries to the system.
     *
     * @param batteries List of Battery objects to be added
     * @return ResponseEntity containing the list of saved Battery objects
     */
    @PostMapping
    public ResponseEntity<List<Battery>> addBatteries(@Valid @RequestBody List<Battery> batteries) {
        List<Battery> savedBatteries = batteryService.saveBatteries(batteries);
        return ResponseEntity.ok(savedBatteries);
    }


    /**
     * Retrieves battery statistics based on a given postcode range.
     *
     * @param postcodeRange The range of postcodes to filter the battery statistics
     * @return ResponseEntity containing the battery statistics for the given postcode range
     */
    @GetMapping
    public ResponseEntity<BatteryStatistic> getBatteriesByPostcodeRange(@RequestParam String postcodeRange) {
        String[] range = postcodeRange.split("-");
        if (range.length != 2) {
            return ResponseEntity.badRequest().build();
        }
        BatteryStatistic statistics = batteryService.getBatteriesByPostcodeRange(range[0], range[1]);
        return ResponseEntity.ok(statistics);
    }
}
