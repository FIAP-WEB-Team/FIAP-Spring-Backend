package br.com.fiap.spring.controller;

import br.com.fiap.spring.model.dto.CreatePassengerDTO;
import br.com.fiap.spring.model.dto.PassengerDTO;
import br.com.fiap.spring.service.PassengerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("passengers")
public class PassengerController {
    private final PassengerService service;

    public PassengerController(PassengerService service) {
        this.service = service;
    }

    @GetMapping(path = "{id}", produces = "application/json")
    public PassengerDTO searchPassenger(@PathVariable String id) {
        try {
            return service.searchPassenger(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PassengerDTO createPassenger(@RequestBody CreatePassengerDTO createPassengerDTO) {
        try {
            return service.createPassenger(createPassengerDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
