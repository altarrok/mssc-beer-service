package spring.altarok.msscbeerservice.services;

import org.springframework.stereotype.Service;
import spring.altarok.msscbeerservice.web.model.BeerDto;

import java.util.UUID;

@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public BeerDto getBeerById(UUID id) {
        return BeerDto.builder().build();
    }
}
