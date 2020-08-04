package spring.altarok.msscbeerservice.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import spring.altarok.msscbeerservice.domain.Beer;

import java.util.UUID;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {
    
}
