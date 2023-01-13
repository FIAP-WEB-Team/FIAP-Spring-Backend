package br.com.fiap.spring.service.firebase_impl;

import br.com.fiap.spring.model.dto.CreatePassengerDTO;
import br.com.fiap.spring.model.dto.PassengerDTO;
import br.com.fiap.spring.repository.FirebaseRepository;
import br.com.fiap.spring.service.PassengerService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class PassengerServiceImpl implements PassengerService {

    FirebaseRepository repository;

    public PassengerServiceImpl(FirebaseRepository repository) {
        this.repository = repository;
    }

    @Override
    public PassengerDTO searchPassenger(String id) throws ExecutionException, InterruptedException {
        PassengerDTO passenger = repository.getSingleDocumentFromCollection(PassengerDTO.class, "GolData/Data/Passengers", id);
        passenger.PassengerID = id;
        return passenger;
    }

    @Override
    public PassengerDTO createPassenger(CreatePassengerDTO passenger) throws ExecutionException, InterruptedException {
        String id = repository.createSingleDocumentInCollection("GolData/Data/Passengers", passenger);
        PassengerDTO passengerDTO = passenger.toPassengerDTO();
        passengerDTO.PassengerID = id;

        return passengerDTO;
    }
}
