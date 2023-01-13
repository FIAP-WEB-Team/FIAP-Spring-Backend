package br.com.fiap.spring.service;

import br.com.fiap.spring.model.dto.CreatePassengerDTO;
import br.com.fiap.spring.model.dto.PassengerDTO;

import java.util.concurrent.ExecutionException;

public interface PassengerService {
    PassengerDTO searchPassenger(String id) throws ExecutionException, InterruptedException;
    PassengerDTO createPassenger(CreatePassengerDTO passenger) throws ExecutionException, InterruptedException;
}
