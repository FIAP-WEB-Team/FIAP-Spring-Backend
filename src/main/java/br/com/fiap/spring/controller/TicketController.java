package br.com.fiap.spring.controller;

import br.com.fiap.spring.model.dto.TicketDTO;
import br.com.fiap.spring.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tickets")
public class TicketController {
    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @GetMapping(path = "{id}", produces = "application/json")
    public TicketDTO searchTicket(@PathVariable String id) {
        try {
            return service.searchTicket(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketDTO createTicket(@RequestBody TicketDTO ticketDTO) {
        try {
            return service.createTicket(ticketDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
