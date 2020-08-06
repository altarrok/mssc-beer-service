package spring.altarok.msscbeerservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.altarok.msscbeerservice.domain.Beer;
import spring.altarok.msscbeerservice.exceptions.BeerNotFoundException;
import spring.altarok.msscbeerservice.repositories.BeerRepository;
import spring.altarok.msscbeerservice.web.mappers.BeerMapper;
import spring.altarok.msscbeerservice.web.model.BeerDto;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerDto getBeerById(UUID id) {
        return beerMapper.beerToBeerDto(beerRepository.findById(id).orElseThrow(BeerNotFoundException::new));
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(BeerNotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());
        beer.setQuantityOnHand(beerDto.getQuantityOnHand());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }
}
