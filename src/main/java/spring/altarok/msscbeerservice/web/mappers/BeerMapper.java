package spring.altarok.msscbeerservice.web.mappers;

import org.mapstruct.Mapper;
import spring.altarok.msscbeerservice.domain.Beer;
import spring.altarok.msscbeerservice.web.model.BeerDto;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);
}
