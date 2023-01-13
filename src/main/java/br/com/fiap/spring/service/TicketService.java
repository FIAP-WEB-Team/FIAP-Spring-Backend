package br.com.fiap.spring.service;

import br.com.fiap.spring.model.dto.TicketDTO;

import java.util.concurrent.ExecutionException;

public interface TicketService {
    TicketDTO searchTicket(String id) throws ExecutionException, InterruptedException;
    TicketDTO createTicket(TicketDTO ticket) throws ExecutionException, InterruptedException;
}
