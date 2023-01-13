package br.com.fiap.spring.controller;

import br.com.fiap.spring.model.dto.FlightDTO;
import br.com.fiap.spring.service.FlightService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("flights")
public class FlightController {
    private final FlightService service;

    public FlightController(FlightService service) {
        this.service = service;
    }

    @GetMapping
    public List<FlightDTO> searchFlights(@RequestParam(required = false, value = "arrival") String arrival,
                                         @RequestParam(required = false, value = "departure") String departure) {
        try {
            return service.searchFlights(10, arrival, departure);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}