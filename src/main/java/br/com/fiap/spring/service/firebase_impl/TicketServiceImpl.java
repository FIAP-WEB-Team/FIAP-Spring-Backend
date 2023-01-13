package br.com.fiap.spring.service.firebase_impl;

import br.com.fiap.spring.model.dto.TicketDTO;
import br.com.fiap.spring.repository.FirebaseRepository;
import br.com.fiap.spring.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class TicketServiceImpl implements TicketService {
    FirebaseRepository repository;

    public TicketServiceImpl(FirebaseRepository repository) {
        this.repository = repository;
    }
    @Override
    public TicketDTO searchTicket(String id) throws ExecutionException, InterruptedException {
        return repository.getSingleDocumentFromCollection(TicketDTO.class, "GolData/Data/BuyTickets", id);
    }

    @Override
    public TicketDTO createTicket(TicketDTO ticket) throws ExecutionException, InterruptedException {
        repository.createSingleDocumentInCollection("GolData/Data/BuyTickets", ticket);
        return ticket;
    }
}
