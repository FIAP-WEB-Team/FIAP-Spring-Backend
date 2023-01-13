package br.com.fiap.spring.service;

import br.com.fiap.spring.model.dto.FlightDTO;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface FlightService {
    List<FlightDTO> searchFlights(int queryLimit, String arrival, String departure) throws ExecutionException, InterruptedException;
}
