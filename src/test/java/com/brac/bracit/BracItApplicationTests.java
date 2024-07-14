package com.brac.bracit;

import com.brac.bracit.controller.BatteryController;
import com.brac.bracit.domains.Battery;
import com.brac.bracit.domains.BatteryStatistic;
import com.brac.bracit.services.BatteryService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
class BatteryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BatteryService batteryService;

    private List<Battery> batteries;

    /**
     * Sets up the necessary configurations and data for testing.
     */
    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(new BatteryController(batteryService)).build();
        batteries = Arrays.asList(
                new Battery("battery1", "12345", 100),
                new Battery("battery2", "23456", 200),
                new Battery("battery3", "34567", 300)
        );
    }

    /**
     * Test case for the addBatteries method. This test verifies that the method returns the expected
     * list of Battery objects when given a valid JSON array of batteries. It mocks the batteryService to return the expected
     * list of batteries. Then, it performs a POST request to the "/api/v1/battery" endpoint with the JSON array
     * and expects a successful response (status code 200) with the expected JSON content.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    public void testAddBatteries() throws Exception {
        when(batteryService.saveBatteries(anyList())).thenReturn(batteries);

        mockMvc.perform(post("/api/v1/battery")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"name\":\"Battery1\",\"postcode\":\"12345\",\"wattCapacity\":100}," +
                                "{\"name\":\"Battery2\",\"postcode\":\"23456\",\"wattCapacity\":200}," +
                                "{\"name\":\"Battery3\",\"postcode\":\"34567\",\"wattCapacity\":300}]"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":null,\"name\":\"battery1\",\"postcode\":\"12345\",\"wattCapacity\":100}," +
                        "{\"id\":null,\"name\":\"battery2\",\"postcode\":\"23456\",\"wattCapacity\":200}," +
                        "{\"id\":null,\"name\":\"battery3\",\"postcode\":\"34567\",\"wattCapacity\":300}]"));
    }


    /**
     * Test case for the getBatteriesByPostcodeRange method. This test verifies that the method returns the expected
     * BatteryStatistic object when given a valid postcode range. It mocks the batteryService to return the expected
     * statistics object. Then, it performs a GET request to the "/api/v1/battery" endpoint with the postcode range
     * parameter and expects a successful response (status code 200) with the expected JSON content.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    public void testGetBatteriesByPostcodeRange() throws Exception {
        BatteryStatistic statistics = new BatteryStatistic();
        statistics.setBatteryNames(Arrays.asList("Battery1", "Battery2"));
        statistics.setTotalWattCapacity(300);
        statistics.setAverageWattCapacity(150);

        when(batteryService.getBatteriesByPostcodeRange("12345", "23456")).thenReturn(statistics);

        mockMvc.perform(get("/api/v1/battery?postcodeRange=12345-23456"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"batteryNames\":[\"Battery1\",\"Battery2\"],\"statistics\":{\"totalWattCapacity\":300,\"averageWattCapacity\":150}}"));
    }

}
