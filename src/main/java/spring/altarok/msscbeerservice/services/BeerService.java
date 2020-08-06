package spring.altarok.msscbeerservice.services;

import spring.altarok.msscbeerservice.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {
    BeerDto getBeerById(UUID id);
}
