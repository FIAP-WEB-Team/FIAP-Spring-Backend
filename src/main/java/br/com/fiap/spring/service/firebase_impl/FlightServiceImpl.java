package br.com.fiap.spring.service.firebase_impl;

import br.com.fiap.spring.model.dto.FlightDTO;
import br.com.fiap.spring.repository.FirebaseRepository;
import br.com.fiap.spring.service.FlightService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FlightServiceImpl implements FlightService {
    FirebaseRepository repository;

    public FlightServiceImpl(FirebaseRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<FlightDTO> searchFlights(int queryLimit, String arrival, String departure) throws ExecutionException, InterruptedException {
        HashMap<String, String> filters = new HashMap<>();
        if (arrival != null)
            filters.put("Arrival", arrival);
        if (departure != null)
            filters.put("Departure", departure);
        return repository.getMultipleDocumentsFromCollection(FlightDTO.class, "GolData/Data/Flights", queryLimit, filters);
    }
}
